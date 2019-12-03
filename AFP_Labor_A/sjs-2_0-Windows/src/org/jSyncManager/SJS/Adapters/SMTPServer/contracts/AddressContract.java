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
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;


/** 
 * This class specifies the contracts for relevant methods of the Address class.
 * @author akaspar
 * @version $Revision$
  */
public class AddressContract extends ContractBase<Address> {
   //~  ========================================================================

   /** The address class being checked. */
   Address addy;

   //~  ========================================================================

/**
    * Creates the AddressContract class. You shouldn't need to worry about this, since it's handled by the 
    * C4J library
    * @param addy
    */
   public AddressContract(Address addy) {
      super(addy);
      this.addy = addy;
   }   // end AddressContract()

   //~  ========================================================================

   /** 
    * Specifies the states of the server that should never change. These
    * fields are used throughout the class without null checking.
    */
   public void classInvariant() {
      assert super.getTargetField("domain") != null;
      assert super.getTargetField("user") != null;
   }   // end classInvariant()

   /** 
    * The address parameter is not check for nullity, and it is assumed
    * that there is an @ symbol in it. This contract should ensure that the
    * address is formatted as expected.
    *
    * @param address
    */
   public void pre_Address(String address) {
      assert address != null;
      assert (address.indexOf("@") > 0) &&
         (address.indexOf("@") < (address.length() - 1));
   }   // end pre_Address()

   /** 
    * Neither parameter is check for nullity before being used. This
    * contract ensures they are not null.
    *
    * @param user
    * @param domain
    */
   public void pre_Address(User user, Domain domain) {
      assert user != null;
      assert domain != null;
   }   // end pre_Address()
}   // end AddressContract
