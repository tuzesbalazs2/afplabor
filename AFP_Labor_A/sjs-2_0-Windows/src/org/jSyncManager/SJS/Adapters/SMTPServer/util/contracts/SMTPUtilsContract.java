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
// $Id: SMTPUtilsContract.java,v 1.0
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Adapters.SMTPServer.util.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.util.SMTPUtils;


/**
 * Contract for the SMTPUtils Class
 * @author Ahmad
 * @version v1.0
  */
public class SMTPUtilsContract extends ContractBase<SMTPUtils> {

   /**
    * Constructor, get a reference for the SMTPUtils object
    *
    * @param t the object.
    */
   public SMTPUtilsContract(SMTPUtils t) {
	   super(t);
   }   // end SMTPUtilsContract()

   //~  ========================================================================

   /**
    * Invariant checks.
    */
   public void classInvariant() {
      //nop
   }   // end classInvariant()

   /**
    * Pre-condition for the isValidAddress method.
    *
    * @param address See the SMTPUtils class.
    */
   public void pre_isValidAddress(String address) {
      assert address != null;
   }   // end pre_isValidAddress()

   /**
    * Post-condition for the isValidAddress method.
    *
    * @param address See the SMTPUtils class.
    */
   public void post_isValidAddress(String address) {
      assert super.getReturnValue() != null;
   }   // end post_isValidAddress()
}   // end SMTPUtilsContract
