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

package org.jSyncManager.SJS.Adapters.SMTPServer;

import net.sourceforge.c4j.ContractReference;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;


/** 
 * A class which represents an address of some mail sender/receiver.
 *
 * @author Torben Werner
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.AddressContract")
public class Address {
   //~  ========================================================================

   /** Variable definition... */
   protected Domain domain;

   // Some username of a sender/receiver
   /** Variable definition... */
   protected User user;

   //~  ========================================================================

   /** 
    * General summary
    *
    * @param address parameter value
    */
   public Address(String address) {
      String[] parts = address.trim().split("@");

      if (parts.length != 2) {
         return;
      }
      user = new User(parts[0], null);
      domain = new Domain(parts[1]);
   }   // end Address()

/**
    * Constructor, accepts a user and domain
    * @param user
    * @param domain
    */
   public Address(User user, Domain domain) {
      this.user = user;
      this.domain = domain;
   }   // end Address()

   //~  ========================================================================

   /** 
    * Returns the username of the sender/receiver
    *
    * @return the username of the sender/receiver
    */
   public User getUser() {
      return this.user;
   }   // end getUser()

   /** 
    * Returns the domain of the sender/receiver
    *
    * @return the domain of the sender/receiver
    */
   public Domain getDomain() {
      return this.domain;
   }   // end getDomain()

   /** 
    * stuff
    *
    * @return returned
    */
   public boolean isLocal() {
      return user.getPath() != null;
   }   // end isLocal()

   /** 
    * Returns a string representation of the Address object (i.e.
    * "joe@example.com") for and Address representing a sender/receiver with
    * the username "joe" at the domain "example.com"
    *
    * @return returned
    */
   public String toString() {
      return this.user.getName() + "@" + this.domain.getDomain();
   }   // end toString()
}   // end Address
