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

import net.sourceforge.c4j.ContractReference;

//~  ========================================================================
/**
 * Data Access Object Factory that encapsulates the underlying implementation details
 *  for a given method of data storage. Currently supports XML storage only.
 *
 * @author $author$
 * @version $Revision$
  */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts.DAOFactoryContract")
public abstract class DAOFactory {
//~  ========================================================================

   /** Default DAO implementation to use */
   @SuppressWarnings("unchecked")
   public static final Class DEFAULT = XMLDAOFactory.class;

//~  ========================================================================

   /**
    * Creating the DAOFactory instance
    *
    * @param factory parameter value
    *
    * @return returned
    */
   public static DAOFactory instance(Class<?> factory) {
      try {
         return (DAOFactory) factory.newInstance();
      }   // end try
      catch (Exception ex) {
         throw new RuntimeException("Couldn't create DAOFactory: " + factory);
      }   // end catch
   }   // end instance()

// ---------------------------------------------------------------------------
   /**
    * Getter method to return the configDAO
    *
    * @return returned
    */
   public abstract ConfigDAO getConfigDAO();

// ~  ========================================================================
}   // end DAOFactory
