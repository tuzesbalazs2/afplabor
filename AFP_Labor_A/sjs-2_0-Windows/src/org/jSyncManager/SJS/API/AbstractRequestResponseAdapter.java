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
// $Id: Launcher.java,v 3.4 2004/08/17 01:23:34 yaztromo Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.API;

import java.io.*;


//~ ===========================================================================
/** 
 * An abstract parent for all Request/Response adapters. This adapter class
 * expands upon the AbstractAdapter to provide request/response style
 * communications.  Sub-classes need implement only one method to handle their
 * communications.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public abstract class AbstractRequestResponseAdapter extends AbstractAdapter {
//~  ========================================================================

   /**
    * A flag to denote wether or not this adapter should disconnect
    * after an operation.
    */
   private boolean disconnect = false;

//~  ========================================================================

/** Constructs a new AbstractRequestResponseAdapter instance.
     */
   public AbstractRequestResponseAdapter() {
      // Do nothing
      super();
   }   // end AbstractRequestResponseAdapter()

//~  ========================================================================

   /** 
    * The processConnection method. This method is called when a
    * connection for this adapter type is received. Subclasses need to
    * implement this method in order to do anything useful.
    *
    * @param in the InputStream object for communications.
    * @param out the OutputStream object for communications.
    *
    * @exception Exception any checked exception not caught and handled by the
    *            adapter.
    */
   @Override protected final void processConnection(InputStream in, OutputStream out)
      throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      PrintWriter pw = new PrintWriter(out, true);

      String header = getConnectionHeaderString();

      if (header != null) {
         pw.println(header);
      }   // end if

      String response;

      // This will continue the request/response mechanism until null has been returned,
      // signifying the communication has ended.
      try {
         while (! disconnect) {
            if ((response = processRequest(br.readLine())) == null) {
               continue;
            }   // end if
            pw.println(response);
         }   // end while
      }   // end try
      catch (java.net.SocketTimeoutException ste) {
         // Socket timeout occurred.
         String toMsg = getTimeoutMessage();

         if (toMsg != null) {
            pw.println(toMsg);
         }   // end if
         getLogManager().writeGeneralEntry("Connection timed out.");

         return;
      }   // end catch
   }   // end processConnection()

// ---------------------------------------------------------------------------
   /** 
    * The disconnect method. Implementations should call this method
    * when it is time to disconnect. Note that this will not cause the adapter
    * to disconnect immediately, but only after the current call to
    * processRequest returns.
    */
   @Override protected final void disconnect() {
      disconnect = true;
      super.disconnect();
   }   // end disconnect()

// ---------------------------------------------------------------------------
   /** 
    * The processRequest method. This method needs to be implemented by
    * subclasses in order to facilitate the  request/response system. This
    * method may return null, in which case no response will be returned to
    * the connecting host for this request.
    *
    * @param request the request to be processed.
    *
    * @return the response to be sent.
    *
    * @exception Exception any checked exception that goes unhandled.
    */
   protected abstract String processRequest(String request)
      throws Exception;

// ---------------------------------------------------------------------------
   /** 
    * The Connection Header method. This method returns the data which
    * should be displayed when a connection is received. If this method
    * returns null, as in the default implementation, no header will be
    * displayed.
    *
    * @return the connection header string, or null for no connection header
    *         message.
    */
   protected String getConnectionHeaderString() {
      return null;
   }   // end getConnectionHeaderString()

// ---------------------------------------------------------------------------
   /** 
    * The timeout error message. This method returns the message to
    * display when the connection times out. The default implementation
    * returns null.  Subclasses should override this to display the relevent
    * message when a timeout occurs (if any).
    *
    * @return the timeout error message.
    */
   protected String getTimeoutMessage() {
      return null;
   }   // end getTimeoutMessage()

//~ ===========================================================================
}   // end AbstractRequestResponseAdapter
