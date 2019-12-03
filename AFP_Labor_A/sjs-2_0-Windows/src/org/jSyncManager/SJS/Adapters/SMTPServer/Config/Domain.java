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
 * This class represents an email domain used for sending,
 * receiving, and storing messages
 *
 * @author $author$
 * @version $Revision$
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts.DomainContract")
public class Domain {
//~  ========================================================================

   /** string type of domain */
   private String domain;

//~  ========================================================================

   /**
    * Constructor, default and empty
    */
   public Domain(){

   }
// ---------------------------------------------------------------------------

   /**
    * Constructor
    * @param domain the name of the domain
    */
   public Domain(String domain){
	   this.domain = domain;
   }

// ~  ========================================================================

   /**
    * Gets the domain of the email address
    *
    * @return string type of domain
    */
   public String getDomain() {
      return domain;
   }   // end getDomain()

// ---------------------------------------------------------------------------
   /**
    * sets the domain for the email address
    *
    * @param domain parameter value
    */
   public void setDomain(String domain) {
      this.domain = domain;
   }   // end setDomain()

// ---------------------------------------------------------------------------
   /**
    * Returns the domain name.
    *
    * @return returned
    */
   public String toString() {
      return this.domain;
   }   // end toString()

// ~  ========================================================================
}   // end Domain
