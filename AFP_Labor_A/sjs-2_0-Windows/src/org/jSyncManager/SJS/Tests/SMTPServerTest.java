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
 * @author Cecelia Redding &lt;cecelia@uvic.ca&gt;
 * @author Crystal Gold &lt;cgold@uvic.ca&gt;
 */
public class SMTPServerTest extends TestSMTPServer {
//~  ========================================================================

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int HELO_SUCCESS = 250;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int HELO_FAIL_NO_PARAMS = 501;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int HELO_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int EHLO_SUCCESS = 250;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int EHLO_FAIL_NO_PARAMS = 501;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int EHLO_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "221 &lt;domain&gt; Service
    * closing transmission channel" reply code.
    */
   public final static int QUIT_SUCCESS = 221;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int QUIT_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int NOOP_SUCCESS = 250;

   /** A final integer representing the "214 Help message" reply code. */
   public final static int HELP_SUCCESS = 214;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int HELP_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int EXPN_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "502 Command not implemented"
    * reply code.
    */
   public final static int EXPN_SUCCESS_SYNTAX = 502;

   /**
    * A final integer representing the "500 Syntax error, command
    * unrecognized" reply code.
    */
   public final static int FAIL_UNKNOWN_COMMAND = 500;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int RSET_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "250 Requested mail action okay,
    * completed" reply code.
    */
   public final static int RSET_SUCCESS = 250;

   /**
    * A final integer representing the "501 Syntax error in parameters
    * or arguments" reply code.
    */
   public final static int VRFY_FAIL_PARAMS = 501;

   /**
    * A final integer representing the "502 Command not implemented"
    * reply code.
    */
   public final static int VRFY_SUCCESS_SYNTAX = 502;

   /** An instance of {@link LogManager} for logging test results. */
   private LogManager testLog = null;

//~  ========================================================================

   /** 
    * One-time setup method.  Runs before the first test is run.
    */
   @BeforeClass
   public static void beginClass() {
      System.out.println("Starting TestSMTPServer tests.");
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
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end beginMethod()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests general syntax errors.
    */
   @Test
   public void general() {
      try {
         // test case sensitivity (should work)
         int result1 = Integer.parseInt(processRequest("quIT").substring(0, 3));

         // test command length (should work)
         int result2 = Integer.parseInt(processRequest(
                  "NOOP butterisbetterbetterbutterisbetterthanbutterbetterbetterbutterisbetterthanbetterbutterbetterbetterbetterbutterisbetterthanbetterbetterbutterbetterbetterbetterbetterbutterisbetterthanbetterbetterbetterbutterbetterbetterbetterbetterbetterbutterisbetterthanbetterbetterbetterbetterbutterbutterisbetterbetterbutterisbetterthanbutterbetterbetterbutterisbetterthanbetterbutterbetterbetterbetterbutterisbetterthanbetterbetterbutterbetterbetterbetterbetterbutterisbetterthanbetterbetterbetterbutterbetterbetterbetterbetterbetterbutterisbetterthanbetterbetterbetterbetterbutter")
                  .substring(0, 3));

         // test unknown command (should fail)
         int result3 = Integer.parseInt(processRequest("SHIMMY").substring(0, 3));
         assertEquals(result1, QUIT_SUCCESS);
         assertEquals(result2, NOOP_SUCCESS);
         assertEquals(result3, FAIL_UNKNOWN_COMMAND);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end general()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the HELO command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testHELO() {
      try {
         // tests that should be successful
         int result1 = Integer.parseInt(processRequest("HELO engr.uvic.ca")
                  .substring(0, 3));

         // tests that should fail
         int result2 = Integer.parseInt(processRequest("HELO").substring(0, 3));
         int result3 = Integer.parseInt(processRequest(
                  "HELO engr.uvic.ca uvic.ca").substring(0, 3));
         assertEquals(result1, HELO_SUCCESS);
         assertEquals(result2, HELO_FAIL_NO_PARAMS);
         assertEquals(result3, HELO_FAIL_PARAMS);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testHELO()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the EHLO command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testEHLO() {
      try {
         // tests that should be successfull
         int result1 = Integer.parseInt(processRequest("EHLO engr.uvic.ca")
                  .substring(0, 3));

         // tests that should fail
         int result2 = Integer.parseInt(processRequest("EHLO").substring(0, 3));
         int result3 = Integer.parseInt(processRequest(
                  "EHLO engr.uvic.ca uvic.ca").substring(0, 3));
         assertEquals(result1, EHLO_SUCCESS);
         assertEquals(result2, EHLO_FAIL_NO_PARAMS);
         assertEquals(result3, EHLO_FAIL_PARAMS);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testEHLO()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the QUIT command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testQUIT() {
      try {
         // tests that should be successfull
         int result1 = Integer.parseInt(processRequest("QUIT").substring(0, 3));

         // tests that should fail
         int result2 = Integer.parseInt(processRequest("QUIT butter")
                  .substring(0, 3));
         int result3 = Integer.parseInt(processRequest("QUIT butter is better")
                  .substring(0, 3));
         assertEquals(result1, QUIT_SUCCESS);
         assertEquals(result2, QUIT_FAIL_PARAMS);
         assertEquals(result3, QUIT_FAIL_PARAMS);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testQUIT()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the NOOP command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testNOOP() {
      try {
         // tests that should be successfull
         int result1 = Integer.parseInt(processRequest("NOOP").substring(0, 3));
         int result2 = Integer.parseInt(processRequest("NOOP butter")
                  .substring(0, 3));
         int result3 = Integer.parseInt(processRequest("NOOP butter is better")
                  .substring(0, 3));
         assertEquals(result1, NOOP_SUCCESS);
         assertEquals(result2, NOOP_SUCCESS);
         assertEquals(result3, NOOP_SUCCESS);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testNOOP()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the HELP command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testHELP() {
      try {
         int result1 = Integer.parseInt(processRequest("HELP").substring(0, 3));   // Test with no params (expected: success)
         int result2 = Integer.parseInt(processRequest("HELP QUIT")
                  .substring(0, 3));   // Test with one params (expected: success)
         int result3 = Integer.parseInt(processRequest("HELP HELP QUIT")
                  .substring(0, 3));   // Test with two params (expected: fail - multiple parameters passed)
         assertEquals(HELP_SUCCESS, result1);
         assertEquals(HELP_SUCCESS, result2);
         assertEquals(HELP_FAIL_PARAMS, result3);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testHELP()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the EXPN command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testEXPN() {
      try {
         int result1 = Integer.parseInt(processRequest("EXPN").substring(0, 3));   // Test with no params (expected: fail - no param given)
         int result2 = Integer.parseInt(processRequest("EXPN bseng")
                  .substring(0, 3));   // Test with one parameter (expected: fail - verification not implemented)
         int result3 = Integer.parseInt(processRequest("EXPN bseng seng")
                  .substring(0, 3));   // Test with two parameters (exected: fail - multiple parameters passed)
         assertEquals(EXPN_FAIL_PARAMS, result1);
         assertEquals(EXPN_SUCCESS_SYNTAX, result2);
         assertEquals(EXPN_FAIL_PARAMS, result3);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testEXPN()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the RSET command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testRSET() {
      try {
         // Test with no params (expected: success)
         int result1 = Integer.parseInt(processRequest("RSET").substring(0, 3));

         // Test with one params (expected: fail - one or more parameters given)
         int result2 = Integer.parseInt(processRequest("RSET garbage")
                  .substring(0, 3));
         assertEquals(RSET_SUCCESS, result1);
         assertEquals(RSET_FAIL_PARAMS, result2);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testRSET()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the VRFY command for correct command syntax
    * and order of commands.
    */
   @Test
   public void testVRFY() {
      try {
         // Test with no params (expected: fail - no parameters specified)
         int result1 = Integer.parseInt(processRequest("VRFY").substring(0, 3));

         // Test with one params (expected: success)
         int result2 = Integer.parseInt(processRequest(
                  "VRFY bob.loblaw@foobar.com").substring(0, 3));

         // Test with two params (expected: fail - too many parameters secified)
         int result3 = Integer.parseInt(processRequest(
                  "VRFY bob.loblaw@foobar.com garbage").substring(0, 3));
         assertEquals(VRFY_FAIL_PARAMS, result1);
         assertEquals(VRFY_SUCCESS_SYNTAX, result2);
         assertEquals(VRFY_FAIL_PARAMS, result3);
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testVRFY()
   
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
      System.out.println("Finished TestSMTPServer tests.");
   }   // end endClass()
   
// ---------------------------------------------------------------------------

   /** 
    * Main method. Sets the {@link JUnitCore} to {@link SMTPServerTest}.
    *
    * @param args command line parameters are disregarded.
    */
   public static void main(String[] args) {
      JUnitCore.main("org.jSyncManager.SJS.Tests.SMTPServerTest");
   }   // end main()
   
// ~ ===========================================================================
}   // end SMTPServerTest
