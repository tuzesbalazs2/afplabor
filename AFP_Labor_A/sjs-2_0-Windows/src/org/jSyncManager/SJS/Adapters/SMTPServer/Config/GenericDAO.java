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

import java.io.Serializable;

import net.sourceforge.c4j.ContractReference;


//~  ========================================================================
/** 
 * Implement this interface to provide a particular implementation
 * of a DAO object. This interface provides a generic set of methods.
 * 
 * 
 * @author $author$
 * @version $Revision$
  *
 * @param <T> parameter value
 * @param <ID> parameter value
 */
@ContractReference(contractClassName="org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts.GenericDAOContract")
public interface GenericDAO<T, ID extends Serializable> {
//~  ========================================================================

   /** 
    * Loads the configuration from a file. 
    *
    * @param configFile parameter value
    *
    * @return returned
    */
   public T loadConfig(String configFile);
   
// ~  ========================================================================
}   // end GenericDAO
