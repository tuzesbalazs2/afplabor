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

import org.jSyncManager.SJS.Adapters.TestAdapter.TestAdapter;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;


//~ ===========================================================================
/** 
 * JUnit tests for the {@link TestAdapter} class.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 */
public class TestAdapterTester extends TestAdapter {
//~  ========================================================================

   /** 
    * Set up method.  Runs before each test method.
    */
   @Before
   public void setUp() {
      // Set up for the test, manipulating instance variables
   }   // end setUp()
   
// ---------------------------------------------------------------------------

   /** 
    * A method that tests the TestAdapter class.
    */
   @Test
   public void testRequest() {
      try {
         assertEquals("+OK 1", processRequest("TEST1"));
         assertEquals("+OK 2", processRequest("TEST2"));
         assertEquals("+OK 3", processRequest("TEST3"));
      }   // end try
      catch (Exception e) {
         fail(e.toString());
      }   // end catch
   }   // end testRequest()
   
// ---------------------------------------------------------------------------

   /** 
    * Main method. Sets the {@link JUnitCore} to {@link
    * TestAdapterTester}.
    *
    * @param args command line parameters are disregarded.
    */
   public static void main(String[] args) {
      org.junit.runner.JUnitCore.main(
         "org.jSyncManager.SJS.Tests.TestAdapterTester");
   }   // end main()
   
// ~ ===========================================================================
}   // end TestAdapterTester
