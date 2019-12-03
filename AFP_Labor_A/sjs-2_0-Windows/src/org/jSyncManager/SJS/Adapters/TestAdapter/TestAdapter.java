// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2004 Brad BARCLAY <bbarclay@jsyncmanager.org>
// --------------------------------------------------------------------------
// OSI Certified Open Source Software
// --------------------------------------------------------------------------
//
// This program is free software; you can redistribute it and/or modify it 
// under the terms of the GNU General Public License as published by the Free 
// Software Foundation; either version 2 of the License, or (at your option) 
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
// more details.
//
// You should have received a copy of the GNU General Public License along 
// with this program; if not, write to the Free Software Foundation, Inc., 
// 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//
// --------------------------------------------------------------------------
// $Id: TestAdapter.java,v 1.1 2004/12/05 04:31:54 yaztromo Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.TestAdapter;

import org.jSyncManager.SJS.API.AbstractRequestResponseAdapter;


// ===========================================================================
/** 
 * A simple Test Adapter to demonstrate the Internet Server Service. This
 * is a very simple Internet Server application built upon the
 * AbstractRequestResponseAdapter in order to create a simple server which
 * accepts requests, and uses them to increment a counter, completing when the
 * user enters the word "QUIT".
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public class TestAdapter extends AbstractRequestResponseAdapter {
//~  ========================================================================

   /** A static field to hold the disconnect message for this adapter. */
   public static final String DISCONNECT_MESSAGE = "+BYE";

   /** A counter to keep track of how many requests have been processed. */
   private int counter = 0;

//~  ========================================================================

   /** Constructs a new instance of this TestAdapter.
     */
   public TestAdapter() {
      super();
   }   // end TestAdapter()

//~  ========================================================================

   /** 
    * The processRequest method. In this implementation, this method
    * simply returns "+OK" followed by an incrementing value, unless the
    * request "QUIT" is received.
    *
    * @param request the request to be processed.
    *
    * @return the response to be sent.
    *
    * @exception Exception any checked exception that goes unhandled.
    */
   @Override protected String processRequest(String request) throws Exception {
      if (request.toUpperCase().equals("QUIT")) {
         disconnect();
         getLogManager().writeGeneralEntry("User ended the session.");

         return DISCONNECT_MESSAGE;
      }   // end if

      return "+OK " + Integer.toString(++counter);
   }   // end processRequest()

// ---------------------------------------------------------------------------
   /** 
    * The Connection Header method. This method returns a sample header
    * string.
    *
    * @return the sample connection header string.
    */
   @Override protected String getConnectionHeaderString() {
      return "Welcome to the Simple Java Server Test Adapter.\nType \"QUIT\" to disconnect.";
   }   // end getConnectionHeaderString()

// ---------------------------------------------------------------------------
   /** 
    * The timeout error message. This method returns the message to
    * display when the connection times out.
    *
    * @return the timeout error message "+BYE"
    */
   @Override protected String getTimeoutMessage() {
      return DISCONNECT_MESSAGE;
   }   // end getTimeoutMessage()

//~ ===========================================================================
}   // end TestAdapter
