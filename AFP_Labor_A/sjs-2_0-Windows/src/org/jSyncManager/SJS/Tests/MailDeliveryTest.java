// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) YEAR AUTHOR_NAME <AUTHOR_EMAIL>
// --------------------------------------------------------------------------
// OSI Certified Open Source Software
// --------------------------------------------------------------------------//
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 2 of the License, or (at your option)
// any later version.//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// more details.//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA//
// --------------------------------------------------------------------------
// $Id: FILE_NAME.java,v 0.1 CREATION_DATE CREATION_TIME SOURCEFORGE_ID Exp $
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

import org.jSyncManager.SJS.LogManager;
import org.jSyncManager.SJS.Adapters.SMTPServer.SMTPServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;


/**
 * Description of the type...
 *
 * @author campbelm
 * @version $Revision$
 */
public class MailDeliveryTest extends SMTPServer {
//~  ========================================================================

   /** this is because we aren't actually running the server so no Log has been created yet
    *  we need to run a dummy log
    *  */
   private LogManager testLog = null;

   /** An array of strings that are the specified domains */
   public String[] domains = null;

   /** An array of strings that are the specified users */
   public String[] users = null;

   /** The code for bad sequence. This happens when the RCPT TO command is invalid */
   public final int DATA_BAD_SEQ_ERROR = 503;

   /** The code for successful data ending with a "." */
   public final int DATA_FINNISHED = 250;

   /** The code for a successful call to the DATA command */
   public final int DATA_SUCCESS = 354;

   /** The code for a failed DATA call due to an unavailable RCPT TO field */
   public final int SENDER_ERROR = 550;

   /** Variable definition... */
   public final int SENDER_SUCCESS = 250;

//~  ========================================================================

   /**
    * General summary
    *
    * @throws Exception exception
    */
   @BeforeClass
   public static void beginClass() throws Exception {
      System.out.println("Starting MailDeliveryTest tests.");
   }   // end beginClass()

//~  ========================================================================
   /**
    * The method executed before the main tests are run. It creates a new
    * LogManager and gets the users and domains from the SMTPServer.
    *
    */
   @Before
   public void beginMethod() {
      try {
         if (testLog == null) {
            testLog = new LogManager(".", "txt", "SMTPTest");
            setLogManager(testLog);
         }   // end if

         users = getUsers();
         domains = getDomains();
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end beginMethod()

//~  ========================================================================
   

   /**
    * This is the main SMPTServer tester. It checks if the server correctly
    *
    *  - returns DATA_BAD_SEQ_ERROR in the case of out of sequence commands
    *  - correctly returns DATA_FINNISHED when the data is finished.
    *  - shows DATA_SUCCESS if the data is sent successfully.
    *  - returns SENDER_ERROR if the sender does not exist.
    *  - returns SENDER_SUCCESS if the sender exists.
    */
   @Test
   public void testMailReturns() {
      String newUser = generateNewUser();
      String newDomain = generateNewDomain();

      try {
         //Begin test of invalid command sequence.
         processRequest("EHLO example.com");
         processRequest("MAIL FROM:<test@example.com>");

         //send empty RCPT TO
         processRequest("RCPT TO:");

         //send empty DATA, expect fail due to empty RCPT TO:
         int result1 = Integer.parseInt(processRequest("DATA").substring(0, 3));

         //resend RCPT TO
         processRequest("RCPT TO:" + generateRandomLowerString());
         //resend DATA - server should return ERROR
         int result2 = Integer.parseInt(processRequest("DATA").substring(0, 3));

         processRequest("QUIT");

         assertEquals(DATA_BAD_SEQ_ERROR, result1);
         assertEquals(DATA_BAD_SEQ_ERROR, result2);
         //end test

         //this loop tests all the users from all possible domains and ensures
         //all the appropriate responses are received from the server
         for (int i = 0; i < domains.length; i++) {
            for (int j = 0; j < users.length; j++) {
               processRequest("EHLO example.com");
               processRequest("MAIL FROM:<test@example.com>");

               int rcptResponce = Integer.parseInt(processRequest("RCPT TO:<" +
                        users[j] + "@" + domains[j] + ">").substring(0, 3));
               int dataResponce = Integer.parseInt(processRequest("DATA")
                        .substring(0, 3));
               processRequest("Some Simple Test Data");

               int dataFinishedResponce = Integer.parseInt(processRequest(".")
                        .substring(0, 3));
               processRequest("QUIT");

               assertEquals(SENDER_SUCCESS, rcptResponce);
               assertEquals(DATA_SUCCESS, dataResponce);
               assertEquals(DATA_FINNISHED, dataFinishedResponce);
            }   // end for
         }   // end for

         //Begin test with valid domain and invalid user.
         processRequest("EHLO example.com");
         processRequest("MAIL FROM:<test@example.com>");

         int rcptResponse = Integer.parseInt(processRequest("RCPT TO:<" +
                  newUser + "@" + domains[0] + ">").substring(0, 3));
         int dataResponse = Integer.parseInt(processRequest("DATA")
                  .substring(0, 3));
         processRequest("Some Simple Test Data");
         processRequest(".").substring(0, 3);
         processRequest("QUIT");
         //end of test
         //Begin test for valid user and invalid domain.
         processRequest("EHLO example.com");
         processRequest("MAIL FROM:<test@example.com>");

         int rcptResponse1 = Integer.parseInt(processRequest("RCPT TO:<" +
                  users[0] + "@" + newDomain + ">").substring(0, 3));
         int dataResponse1 = Integer.parseInt(processRequest("DATA")
                  .substring(0, 3));
         processRequest("Some Simple Test Data");
         processRequest(".").substring(0, 3);
         processRequest("QUIT");
         //end test
         //Begin test invalid user and invalid domain.
         processRequest("EHLO example.com");
         processRequest("MAIL FROM:<test@example.com>");

         int rcptResponse2 = Integer.parseInt(processRequest("RCPT TO:<" +
                  newUser + "@" + newDomain + ">").substring(0, 3));
         int dataResponse2 = Integer.parseInt(processRequest("DATA")
                  .substring(0, 3));
         processRequest("Some Simple Test Data");
         processRequest(".").substring(0, 3);
         processRequest("QUIT");

         System.out.println(newUser);
         System.out.println(newDomain);

         assertEquals(DATA_BAD_SEQ_ERROR, dataResponse);
         assertEquals(SENDER_ERROR, rcptResponse);

         assertEquals(DATA_SUCCESS, dataResponse1);
         assertEquals(SENDER_SUCCESS, rcptResponse1);

         assertEquals(DATA_SUCCESS, dataResponse2);
         assertEquals(SENDER_SUCCESS, rcptResponse2);
         //end test

      }   // end try
      catch (Exception e) {
         e.printStackTrace();
         fail(e.getMessage());
      }   // end catch
   }   // end testMailReturns()
// --------------------------------------------------------------------------
   /**
    * This tests the MBox implementation
    */
   @Test
   public void testMBoxes() {
      String fromUser = users[0] + "@" + domains[0];
      String toUser = users[1] + "@" + domains[0];

      try {
         String expected = null;
         String actual = readMbox(toUser);

         //tests the case that no messages have been sent to the user's box
         //expected null = null
         assertEquals(expected, actual);

         //this loop tests 1 message boxes, 2 message boxes and multi
         //message boxes
         for (int i = 0; i < 5; i++) {
            String message = "This is a simple test message " + i;

            //this is here because when appending JUnit remembers the
            //null, we don't want that
            if (expected == null) {
               expected = "From " + fromUser + "\n" + message + "\n\n";
            }   // end if
            else {
               expected = expected + "From " + fromUser + "\n" + message +
                  "\n\n";
            }   // end else
            processRequest("EHLO example.com");
            processRequest("MAIL FROM:<" + fromUser + ">");
            processRequest("RCPT TO:<" + toUser + ">");
            processRequest("DATA");
            processRequest(message);
            processRequest(".");
            processRequest("QUIT");

            String[] user = fromUser.split("@");
            actual = readMbox(user[0]);

            //expect all passes, the messages should all be appended in a
            //predictable manner
            assertEquals(expected, actual);
         }   // end for
      }   // end try
      catch (Exception e) {
         e.printStackTrace();
         fail(e.getMessage());
      }   // end catch
   }   // end testMBoxes()
// --------------------------------------------------------------------------
   /**
    * Tear down method. Runs after each test method.
    */
   @After
   public void endMethod() {
      cleanMboxes();
   }   // end endMethod()
// --------------------------------------------------------------------------
   /**
    * Tear down method. Runs once after the class.
    */
   @AfterClass
   public static void endClass() {
      System.out.println("Finished MailDeliveryTest tests.");
   }   // end endClass()
// --------------------------------------------------------------------------
   /**
    * Reads the mailbox for a particular user and returns it as one string.
    *
    * @param fromUser the user to read the mailbox for.
    *
    * @return the user mailbox in string format.
    *
    * @throws Exception exception
    */
   public String readMbox(String fromUser) throws Exception {
      File f = getMboxForUser(fromUser);

      if (f == null) {
         return null;
      }   // end if

      BufferedReader br = new BufferedReader(new FileReader(f));

      String input = null;
      String next = "";

      while ((next = br.readLine()) != null) {
         input = input + next + "\n";
      }   // end while

      if (input != null) {
         input = input + "\n";
      }   // end if

      return input;
   }   // end readMbox()
// --------------------------------------------------------------------------
   /**
    * Clear the mailboxes of the users.
    */
   public void cleanMboxes() {
      for (int i = 0; i < users.length; i++) {
         File f = getMboxForUser(users[i]);

         if (f != null) {
            f.delete();
         }   // end if
      }   // end for
   }   // end cleanMboxes()
// --------------------------------------------------------------------------
   /**
    * Generates random lower strings.
    *
    * @return randomly generated string.
    */
   public String generateRandomLowerString() {
      Random rand = new Random();

      int length = rand.nextInt(10);
      char[] tempArray = new char[length];

      for (int i = 0; i < tempArray.length; i++) {
         int nextInt = 97 + rand.nextInt(26);
         tempArray[i] = (char) nextInt;
      }   // end for

      return new String(tempArray);
   }   // end generateRandomLowerString()
// --------------------------------------------------------------------------
   /**
    * Generates random upper strings.
    *
    * @return randomly generated string.
    */
   public String generateRandomUpperString() {
      Random rand = new Random();

      int length = rand.nextInt(10);
      char[] tempArray = new char[length];

      for (int i = 0; i < tempArray.length; i++) {
         int nextInt = 65 + rand.nextInt(26);
         tempArray[i] = (char) nextInt;
      }   // end for

      return new String(tempArray);
   }   // end generateRandomUpperString()
// --------------------------------------------------------------------------
   /**
    * Generates a random username.
    *
    * @return randomly generated username that is not in users[] and always at least one char long
    */
   public String generateNewUser() {
      String newUser = generateRandomLowerString();

      for (int i = 0; i < users.length; i++) {
         if (newUser.equals(users[i])) {
            newUser = generateRandomLowerString();
            i = -1;
         }   // end if
      }   // end for

      //it is possible that the random length is 0
      //so we deal with that case here
      if (newUser.equals("")) {
         newUser = "a" + newUser;
      }   // end if

      return newUser;
   }   // end generateNewUser()
// --------------------------------------------------------------------------
   /**
    * Generates a random domain name.
    *
    * @return randomly generated domain that is not in domains[], always ends in .com and always at least one char long
    */
   public String generateNewDomain() {
      String newDomain = generateRandomLowerString() + ".com";

      for (int i = 0; i < domains.length; i++) {
         if (newDomain.equals(users[i])) {
            newDomain = generateRandomLowerString() + ".com";
            i = -1;
         }   // end if
      }   // end for

      if (newDomain.equals(".com")) {
         newDomain = "a" + newDomain;
      }   // end if

      return newDomain;
   }   // end generateNewDomain()
   
//~  ========================================================================
   
   /**
    * Main method. Initialises the JUnit tests.
    * This is so we can just run the class and do the tests without using the build file.
    * <p>
    * <p>
    * note that doing this requires the change of one variables in SMTPServer but is merely
    * a debug tool
    *
    * @param args runtime arguments (ignored).
    */
   public static void main(String[] args) {
      JUnitCore.main("org.jSyncManager.SJS.Tests.MailDeliveryTest");
   }   // end main()
}   // end MailDeliveryTest
