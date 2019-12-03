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
package org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.GenericDAO;


/** 
 * The contract file for the GenericDAO interface.<br/
 * > All subtypes are bound by these contracts in addition to any they may
 * enforce.
 *
 * @author Matt Campbell
 * @version 1.0
 */
public class GenericDAOContract extends ContractBase<GenericDAO> {
   //~  ========================================================================

/** 
    * General Constructor for C4J format
    *
    * @param t parameter value
    */
   public GenericDAOContract(GenericDAO t) {
      this.m_target = t;
   }   // end GenericDAOContract()

   //~  ========================================================================

   /** 
    * The class invariant. These conditions must hold at the beginning
    * and end of each method call
    */
   public void classInvariant() {
   }   // end classInvariant()

   /** 
    * The conditions that must hold at the beginning of the loadConfig
    * method
    *
    * @param configFile parameter value
    *
    * @see GenericDAO#loadConfig(String)
    */
   public void pre_loadConfig(String configFile) {
      assert configFile != null;
      assert configFile.endsWith(".xml");
   }   // end pre_loadConfig()

   /** 
    * The conditions that must hold at the conclusion of the loadConfig
    * method
    *
    * @param configFile parameter value
    *
    * @see GenericDAO#loadConfig(String)
    */
   public void post_loadConfig(String configFile) {
      Object t = super.getReturnValue();
      assert t != null;
   }   // end post_loadConfig()
}   // end GenericDAOContract
