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

package org.jSyncManager.SJS.Adapters.SMTPServer.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.DNSUtil;


/** 
 * Contract for DNSUtile
 * @author Matt Campbell
 * @version 1.0
  */
public class DNSUtilContract extends ContractBase<DNSUtil> {
   //~  ========================================================================

   /** 
    * Constructor as required for C4J
    *
    * @param t parameter value
    */
   public DNSUtilContract(DNSUtil t) {
      super(t);
   }   // end DNSUtilContract()

   //~  ========================================================================

   /** 
    * Class invariant. This must hold true both before and after each method call
    */
   public void classInvariant() {
   }   // end classInvariant()

   /** 
    * Must h old true before running MXlookup method
    *
    * @param domain parameter value
    */
   public void pre_MXLookup(String domain) {
      assert domain != null;
   }   // end pre_MXLookup()

   /** 
    * Must h old true after running MXlookup method
    *
    * @param domain parameter value
    */
   public void post_MXLookup(String domain) {
      String output = (String) super.getReturnValue();
      assert output != null;
   }   // end post_MXLookup()

   /** 
    * Must h old true before running doLookup method
    *
    * @param hostName parameter value
    */
   public void pre_doLookup(String hostName) {
      assert hostName != null;
   }   // end pre_doLookup()

   /** 
    * Must h old true after running doLookup method
    *
    * @param hostName parameter value
    */
   public void post_doLookup(String hostName) {
      String output = (String) super.getReturnValue();
      assert output != null;
   }   // end post_doLookup()
}   // end DNSUtilContract
