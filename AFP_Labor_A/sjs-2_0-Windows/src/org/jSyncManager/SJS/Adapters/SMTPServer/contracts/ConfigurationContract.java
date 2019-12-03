// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) Aaron Kaspar <AUTHOR_EMAIL>
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

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Configuration;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;

import java.util.Map;


/** 
 * Contract class for the Conriguration class.
 * @author akaspar
 * @version $Revision$
  */
public class ConfigurationContract extends ContractBase<Configuration> {
   //~  ========================================================================

   /** 
    * Create a new ConfigurationContract class (handled by the C4J library)
    *
    * @param config Dont worry aboudit
    */
   public ConfigurationContract(Configuration config) {
      super(config);
   }   // end ConfigurationContract()

   //~  ========================================================================

   /** 
    * Ensure that the class always has domains and users to return
    */
   public void classInvariant() {
      Map<String, Domain> targetField = (Map<String, Domain>) super.getTargetField(
            "domains");
      assert targetField != null;

      Map<String, User> targetField2 = (Map<String, User>) super.getTargetField(
            "users");
      assert targetField2 != null;
   }   // end classInvariant()
}   // end ConfigurationContract
