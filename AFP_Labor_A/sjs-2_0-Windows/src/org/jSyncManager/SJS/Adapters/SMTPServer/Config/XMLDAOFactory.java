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


//~  ========================================================================
/** 
 * Description of the type...
 * @author $author$
 * @version $Revision$
  */
public class XMLDAOFactory extends DAOFactory {
//~  ========================================================================

   /** 
    * gets the config and returns a ConfigDAO data access object 
    *
    * @return ConfigDAO object, type of ConfigDAO  
    */
   @Override
   public ConfigDAO getConfigDAO() {
      return (ConfigDAO) instantiateDAO(org.jSyncManager.SJS.Adapters.SMTPServer.Config.ConfigDAOXML.class);
   }   // end getConfigDAO()

// ---------------------------------------------------------------------------
   /** 
    * instantiates a daoClass and cast it to ConfigDAO 
    *
    * @param daoClass parameter value
    *
    * @return returned
    */
   @SuppressWarnings({"unchecked", "unused"})
   private ConfigDAO instantiateDAO(Class daoClass) {
      try {
         return (ConfigDAO) daoClass.newInstance();
      }   // end try
      catch (Exception ex) {
         throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
      }   // end catch
   }   // end instantiateDAO()
   
// ~  ========================================================================
}   // end XMLDAOFactory
