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

import java.lang.reflect.ParameterizedType;


//~  ========================================================================
/** 
 * Abstract class representing a generic XML data access object.
 * @author $author$
 * @version $Revision$
  *
 * @param <T> parameter value
 * @param <ID> parameter value
 */
public abstract class GenericXMLDAO<T, ID extends Serializable>
   implements GenericDAO<T, ID> {
//~  ========================================================================

   /** Variable definition... */
   private Class<T> persistentClass;

//~  ========================================================================

   /** 
    * General summary
    */
   @SuppressWarnings("unchecked")
   public GenericXMLDAO() {
      this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];
   }   // end GenericXMLDAO()

//~  ========================================================================

   /** 
    * General summary
    *
    * @return returned
    */
   public Class<T> getPersistentClass() {
      return persistentClass;
   }   // end getPersistentClass()
   
// ~  ========================================================================
}   // end GenericXMLDAO
