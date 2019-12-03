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

//File: SMTPMailTest.java
//Author: York Li <liyy@uvic.ca>
//Date Created: October 5, 2007
//Description: JUnit tests for the SMTPServer processRequest function
package org.jSyncManager.SJS.Tests;

import org.jSyncManager.SJS.Adapters.SMTPServer.TestSMTPServer;
import org.jSyncManager.SJS.LogManager;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;


//~ ===========================================================================
/** 
 * JUnit tests for the {@link TestSMTPServer} processRequest function.
 *
 * @author York Li &lt;liyy@uvic.ca&gt;
 */
public class SMTPMailTest extends TestSMTPServer {
//~  ========================================================================

   /**
    * A final integer representing the "503 Bad sequence of commands"
    * reply code.
    */
   public final static int MAIL_FAIL_BAD_SEQ = 503;

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int MAIL_SUCCESS = 250;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int MAIL_FAIL_BAD_PARAMS = 501;

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int RCPT_SUCCESS = 250;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int RCPT_FAIL_BAD_PARAMS = 501;

   /**
    * A final integer representing the "452 Requested action not taken:
    * insufficient system storage" reply code.
    */
   public final static int RCPT_FAIL_TOO_MANY = 452;   // Not being used yet

   /**
    * A final integer representing the "503 Bad sequence of commands"
    * reply code.
    */
   public final static int RCPT_FAIL_BAD_SEQ = 503;

   /**
    * A final integer representing the "503 Bad sequence of commands"
    * reply code.
    */
   public final static int DATA_FAIL_BAD_SEQ = 503;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int DATA_FAIL_BAD_PARAMS = 501;

   /**
    * A final integer representing the "354 Start mail input; end with
    * &lt;CRLF&gt;.&lt;CRLF&gt;" reply code.
    */
   public final static int DATA_ENTER_SUCCESS = 354;

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int DATA_EXIT_SUCCESS = 250;

   /**
    * A final integer representing the "500 Syntax error, command
    * unrecognized" reply code.
    */
   public final static int FAIL_UNKNOWN_COMMAND = 500;

   //~  ========================================================================

   /** An instance of {@link LogManager} for logging test results. */
   private LogManager testLog = null;

   //~  ========================================================================

   /** 
    * One-time setup method.  Runs before the first test is run.
    */
   @BeforeClass
   public static void beginClass() {
      System.out.println("Starting SMTPMail tests.");
   }   // end beginClass()

// ---------------------------------------------------------------------------
   
   /** 
    * Set up method.  Runs before each test method.
    */
   @Before
   public void beginMethod() {
      try {
         if (testLog == null) {
            testLog = new LogManager(".", "txt", "SMTPTest");
            setLogManager(testLog);
         }   // end if
         processRequest("QUIT");
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end beginMethod()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the MAIL command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testMAIL() {
	   int result1;
	   int result2;
	   int result3;
	   int result4;
	   int result5;
	   int result6;
	   int result7;
	   int result8;
	   int result9;
      try {
         result1 = Integer.parseInt(processRequest("MAIL FROM:<me@uvic.ca>")
                  .substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_SEQ, result1);

         processRequest("EHLO engr.uvic.ca");

         result2 = Integer.parseInt(processRequest("MAIL FROM:<me@uvic.ca>")
                  .substring(0, 3));
         assertEquals(MAIL_SUCCESS, result2);

         result3 = Integer.parseInt(processRequest("MAIL FROM:<me@uvic.ca>")
                  .substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_SEQ, result3);

         processRequest("RSET");

         result4 = Integer.parseInt(processRequest("MAIL FROM:")
                  .substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, result4);

         result5 = Integer.parseInt(processRequest("MAIL FROM:me@uvic.ca")
                  .substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, result5);

         result6 = Integer.parseInt(processRequest("MAIL FROM:<me@uvic.ca>")
                  .substring(0, 3));
         assertEquals(MAIL_SUCCESS, result6);

         processRequest("RSET");

         result7 = Integer.parseInt(processRequest(
                  "MAIL FROM:<me+you@uvic.ca>").substring(0, 3));
         assertEquals(MAIL_SUCCESS, result7);

         processRequest("RSET");

         result8 = Integer.parseInt(processRequest(
                  "MAIL FROM:<\"some guy\"@uvic.ca>").substring(0, 3));
         assertEquals(MAIL_SUCCESS, result8);

         processRequest("RSET");

         result9 = Integer.parseInt(processRequest(
                  "MAIL FROM:<some guy@uvic.ca>").substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, result9);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testMAIL()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the RCPT command for correct command syntax
    * and order of commands.
    */
   @Test
   public final void testRCPT() {
      try {
         int rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<me@example.com>").substring(0, 3));
         assertEquals(RCPT_FAIL_BAD_SEQ, rcptResult);

         processRequest("EHLO engr.uvic.ca");
         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<me@example.com>").substring(0, 3));
         assertEquals(RCPT_FAIL_BAD_SEQ, rcptResult);

         processRequest("MAIL FROM:<me@example.com>");
         rcptResult = Integer.parseInt(processRequest("RCPT").substring(0, 3));
         assertEquals(RCPT_FAIL_BAD_PARAMS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<me@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         //TODO: Change upper bound to 99 when max RCPT test is implemented.
         for (int i = 0; i < 10; i++) {
            rcptResult = Integer.parseInt(processRequest(
                     "RCPT TO:<me@example.com>").substring(0, 3));
            assertEquals(RCPT_SUCCESS, rcptResult);
         }   // end for
         /*
          * TODO: When dev team implements check for max 100 recipients.
            rcptResult = Integer.parseInt(processRequest("RCPT TO:<me@example.com>").substring(0, 3));
            assertEquals(RCPT_FAIL_TOO_MANY, rcptResult);
          */


         // The following tests check a variety of valid and invalid e-mail addresses to see if they are accepted.
         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<me+you@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<me@example.co.uk>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<@test.com,test1@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<@test1.com,@test2.com,test2@example.com>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<\"some guy\"@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<.someguy@example.com>").substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, rcptResult);

         // These tests verify whether or not the valid e-mail address examples from 
         // http://en.wikipedia.org/wiki/E-mail_address work or not.
         // The following are all valid addresses, and should pass.
         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<Abc@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<Abc.123@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<user+mailbox/department=shipping@example.com>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<!#$%&'*+-/=?^_`.{|}~@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<\"Abc@def\"@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<\"Fred Bloggs\"@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<\"Joe.\\\\Blow\"@example.com>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         // These tests verify whether or not the valid e-mail address examples from 
         // http://en.wikipedia.org/wiki/E-mail_address work or not.
         // The following are all invalid addresses, and should fail.
         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<Abc.example.com>").substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<Abc.@example.com>").substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<Abc..123@example.com>").substring(0, 3));
         assertEquals(MAIL_FAIL_BAD_PARAMS, rcptResult);

         // Tests IPv4 and IPv6 dotted addreses
         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someone@[127.0.0.1]>").substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someoneelse@[IPv6:2001:0db8:0000:0000:0000:0000:1428:57ab]>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someoneelse@[IPv6:2001:0db8:0000:0000:0000::1428:57ab]>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someoneelse@[IPv6:2001:0db8:0:0:0:0:1428:57ab]>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someoneelse@[IPv6:2001:0db8:0:0::1428:57ab]>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someoneelse@[IPv6:2001:0db8::1428:57ab]>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

         rcptResult = Integer.parseInt(processRequest(
                  "RCPT TO:<someoneelse@[IPv6:2001:db8::1428:57ab]>")
                  .substring(0, 3));
         assertEquals(RCPT_SUCCESS, rcptResult);

      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testRCPT()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the DATA command for correct command syntax
    * and order of commands.
    */
   @Test
   public final void testDATA() {
      try {
    	   String cmdResult = null;
         int dataResult = Integer.parseInt(processRequest("DATA").substring(0, 3));
         assertEquals(DATA_FAIL_BAD_SEQ, dataResult);

         dataResult = Integer.parseInt(processRequest(".").substring(0, 3));
         assertEquals(FAIL_UNKNOWN_COMMAND, dataResult);

         processRequest("EHLO engr.uvic.ca");
         dataResult = Integer.parseInt(processRequest("DATA").substring(0, 3));
         assertEquals(DATA_FAIL_BAD_SEQ, dataResult);

         processRequest("MAIL FROM:<me@example.com>");
         dataResult = Integer.parseInt(processRequest("DATA").substring(0, 3));
         assertEquals(DATA_FAIL_BAD_SEQ, dataResult);

         processRequest("RCPT TO:<me@example.com>");

         dataResult = Integer.parseInt(processRequest("DATA garbage")
                  .substring(0, 3));
         assertEquals(DATA_FAIL_BAD_PARAMS, dataResult);

         dataResult = Integer.parseInt(processRequest("DATA").substring(0, 3));
         assertEquals(DATA_ENTER_SUCCESS, dataResult);

         cmdResult = processRequest("MAIL FROM:<me@example.com>");
         assertEquals(null, cmdResult);

         dataResult = Integer.parseInt(processRequest(".").substring(0, 3));
         assertEquals(DATA_EXIT_SUCCESS, dataResult);

         dataResult = Integer.parseInt(processRequest("DATA").substring(0, 3));
         assertEquals(DATA_FAIL_BAD_SEQ, dataResult);

      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testDATA()
   
// ---------------------------------------------------------------------------

   /** 
    * Tear down method.  Runs after each test method.
    */
   @After
   public void endMethod() {
   }   // end endMethod()
   
// ---------------------------------------------------------------------------

   /** 
    * One-time teardown method.  Runs after the last test has been run.
    */
   @AfterClass
   public static void endClass() {
      System.out.println("Finished SMTPMail tests.");
   }   // end endClass()
   
// ---------------------------------------------------------------------------

   /** 
    * Main method. Sets the {@link JUnitCore} to {@link SMTPMailTest}.
    *
    * @param args command line parameters are disregarded.
    */
   public static void main(String[] args) {
      JUnitCore.main("org.jSyncManager.SJS.Tests.SMTPMailTest");
   }   // end main()
   
// ~ ===========================================================================
}   // end SMTPMailTest
