// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Ahmad H ahmadh@uvic.ca
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
// $Id: DomainContract.java,v 1.0
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;


/**
 * Contract for the Domain Class.
 * @author Ahmad
 * @version v1.0
  */
public class DomainContract extends ContractBase<Domain> {
   /**
    * Constructor, get a reference for the Domain object.
    *
    * @param t the object.
    */
   public DomainContract(Domain t) {
	   super(t);
   }   // end DomainContract()

   //~  ========================================================================

   /**
    * Invariant checks.
    */
   public void classInvariant() {
      assert super.getTargetField("domain") != null;
   }   // end classInvariant()

   /**
    * Pre-condition for the Domain constructor.
    *
    * @param domain See the Domain class.
    */
   public void pre_Domain(String domain) {
      assert domain != null;
   }   // end pre_Domain()

   /**
    * Post-condition for the Domain constructor.
    *
    * @param domain See the Domain class.
    */
   public void post_Domain(String domain) {
      assert super.getTargetField("domain") != null;
   }   // end post_Domain()

   /**
    * Pre-condition for the setDomain.
    *
    * @param domain See the Domain class.
    */
   public void pre_setDomain(String domain) {
      assert domain != null;
   }   // end pre_setDomain()

   /**
    * Pre-condition for the setDomain.
    *
    * @param domain See the Domain class.
    */
   public void post_setDomain(String domain) {
      assert super.getTargetField("domain") != null;
   }   // end post_setDomain()

   /**
    * Pre-condition for toString.
    */
   public void pre_toString() {
      assert super.getTargetField("domain") != null;
   }   // end pre_toString()

   /**
    * Post-condition for toString.
    */
   public void post_toString() {
      assert super.getReturnValue() != null;
   }   // end post_toString()
}   // end DomainContract
