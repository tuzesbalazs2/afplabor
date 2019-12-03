// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Torben WERNER <teisler@uvic.ca>
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
// $Id: MessageStore.java,v 1.0 2007/10/26 19:00:00 torbenwerner Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;

import java.io.IOException;


//~  ========================================================================
/**
 * An interface to define the methods used to store and retrieve messages from some medium.
 * @author Torben Werner
 */
public interface MessageStoreInterface {
//~  ========================================================================

   /** 
    * Returns an array of all the messages stored in a valid user's
    * mbox.
    *
    * @param user the User who's messages will be retrieved.
    *
    * @return an array of Message objects representing each of the user's
    *         messages.
    */
   public abstract Message[] getMessages(User user) throws IOException;

// ---------------------------------------------------------------------------
   /** 
    * Stores an array of Message objects to some medium where they can
    * later be retrieved.
    *
    * @param msgs the array of Message objects which will each be stored.
    *
    * @throws IOException exception
    */
   public abstract void storeMessages(Message[] msgs) throws IOException;

// ---------------------------------------------------------------------------
   /** 
    * Stores a single Message object to some medium where it can later
    * be retrieved.
    *
    * @param msg the Message object which will be stored.
    *
    * @throws IOException exception
    */
   public abstract void storeMessage(Message msg, Address to) throws IOException;
   
// ~  ========================================================================
}   // end MessageStore
