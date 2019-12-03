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
// $Id: DAOFactoryContract.java,v 1.0
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.DAOFactory;


/**
 * Contract for the DAOFactory class.
 * @author Ahmad
 * @version v1.0
  */
public class DAOFactoryContract extends ContractBase<DAOFactory> {


   /**
    * Constructor, get a reference for the DAOFactory object
    *
    * @param t the object.
    */
   public DAOFactoryContract(DAOFactory t) {
	   super(t);
   }   // end DAOFactoryContract()

   //~  ========================================================================

   /**
    * Invariant checks.
    */
   public void classInvariant() {
      //nop
   }   // end classInvariant()

   /**
    * The pre-condition for the instance method.
    *
    * @param factory See the DAOFactory class.
    */
   public void pre_instance(Class<?> factory) {
      assert factory != null;
   }   // end pre_instance()
}   // end DAOFactoryContract
