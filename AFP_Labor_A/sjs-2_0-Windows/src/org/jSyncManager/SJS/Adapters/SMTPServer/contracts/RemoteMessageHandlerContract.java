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

package org.jSyncManager.SJS.Adapters.SMTPServer.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Address;
import org.jSyncManager.SJS.Adapters.SMTPServer.Message;
import org.jSyncManager.SJS.Adapters.SMTPServer.RemoteMessageHandler;


/** 
 * Description of the type...
 * @author Matt Campbell
 * @version 1.0
  */
public class RemoteMessageHandlerContract extends ContractBase<RemoteMessageHandler> {
   //~  ========================================================================

   /** 
    * Constructor as C4J demands
    *
    * @param t the instance to contract
    */
   public RemoteMessageHandlerContract(RemoteMessageHandler t) {
      super(t);
   }   // end RemoteMessageHandlerContract()

   //~  ========================================================================

   /** 
    * Class invariant. Must hold true at the begining and end of each method call
    */
   public void classInvariant() {
      assert super.getTargetField("table") != null;
      assert super.getTargetField("logManager") != null;
   }   // end classInvariant()

   /** 
    * These conditions must hold true before the store method can happen
    *
    * @param currentMsg parameter value
    * @param recipient parameter value
    */
   public void pre_store(Message currentMsg, Address recipient) {
      assert currentMsg != null;
      assert recipient != null;
      assert recipient.getUser() != null;
      assert recipient.getDomain() != null;
   }   // end pre_store()
}   // end RemoteMessageHandlerContract
