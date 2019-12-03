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

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.ConfigDAO;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.XMLDAOFactory;


/** 
 * Contract for the XMLDAOFactory class
 *
 * @author Wenfeng Su
 * @author Matt Campbell
 * @version v1.0
 */
public class XMLDAOFactoryContract extends ContractBase<XMLDAOFactory> {
   //~  ========================================================================

   /** 
    * General summary
    *
    * @param t parameter value
    */
   public XMLDAOFactoryContract(XMLDAOFactory t) {
      super(t);
   }   // end XMLDAOFactoryContract()

   //~  ========================================================================

   /** 
    * The post condition for getConfigDAO method
    */
   public void post_getConfigDAO() {
      ConfigDAO output = (ConfigDAO) super.getReturnValue();
      assert output != null;
   }   // end post_getConfigDAO()

   /** 
    * The pre condition for instantiateDAO method
    *
    * @param daoClass
    */
   public void pre_instantiateDAO(Class daoClass) {
      assert daoClass != null;
   }   // end pre_instantiateDAO()

   /** 
    * There is no post condition for instatiateDAO method
    *
    * @param daoClass parameter value
    */
   public void post_instantiateDAO(Class daoClass) {
      ConfigDAO output = (ConfigDAO) super.getReturnValue();
      assert output != null;
   }   // end post_instantiateDAO()
}   // end XMLDAOFactoryContract
