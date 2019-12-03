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

package org.jSyncManager.SJS.Adapters.SMTPServer.Config;

import java.util.HashMap;
import java.util.Map;


//~  ========================================================================
/** 
 * Encapsulates the important properties and their corresponding values for a given
 *    configuration file.
 * @author $author$
 * @version $Revision$
  */
public class Configuration {
//~  ========================================================================

   /** Singleton instance */
   public static Configuration INSTANCE = new Configuration();
      
   /** Map containing all valid domains */
   Map<String, Domain> domains = new HashMap<String, Domain>();

   /** Map containing all valid users */
   Map<String, User> users = new HashMap<String, User>();

//~  ========================================================================

   private Configuration(){}
   
 //~  ========================================================================

   /** 
    * Map containing all valid users
    *
    * @return returs map of all users
    */
   public Map<String, User> getUsers() {
      return users;
   }   // end getUsers()

   /** 
    * Map containing all valid domains
    *
    * @param users parameter value
    */
   public void setUsers(Map<String, User> users) {
      this.users = users;
   }   // end setUsers()

// ---------------------------------------------------------------------------
   /** 
    * Gets the current map of domains
    *
    * @return returns the map of domains
    */
   public Map<String, Domain> getDomains() {
      return domains;
   }   // end getDomains()

// ---------------------------------------------------------------------------
   /** 
    * Sets the current map of domains
    *
    * @param domains The new domains Map
    */
   public void setDomains(Map<String, Domain> domains) {
      this.domains = domains;
   }   // end setDomains()
   
// ~  ========================================================================
}   // end Configuration
