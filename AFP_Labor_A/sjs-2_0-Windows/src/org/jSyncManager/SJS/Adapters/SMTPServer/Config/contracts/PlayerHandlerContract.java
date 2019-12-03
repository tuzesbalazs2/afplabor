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

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.ConfigDAOXML.PlayerHandler;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Configuration;

import org.xml.sax.Attributes;


/** 
 * Description of the type...
 *
 * @author Matt Campbell
 * @version 1.0
 */
public class PlayerHandlerContract extends ContractBase<PlayerHandler> {
   //~  ========================================================================

/** 
    * Constructor as C4J demands
    *
    * @param t parameter value
    */
   public PlayerHandlerContract(PlayerHandler t) {
      super(t);
   }   // end PlayerHandlerContract()

   //~  ========================================================================

   /** 
    * Class invariant. This must hold true before and after each method
    * call
    */
   public void classInvariant() {
      assert super.getTargetField("text") != null;
      assert super.getTargetField("config") != null;
   }   // end classInvariant()

   /** 
    * These conditions must hold before the startElement method is run.
    *
    * @param namespaceURI parameter value
    * @param lname parameter value
    * @param qname parameter value
    * @param attrs parameter value
    *
    * @see PlayerHandler#startElement(String, String, String, Attributes)
    */
   public void pre_startElement(String namespaceURI, String lname,
      String qname, Attributes attrs) {
      assert namespaceURI != null;
      assert lname != null;
      assert qname != null;
      assert attrs != null;
   }   // end pre_startElement()

   /** 
    * These conditions must hold before the startElement method is run.
    *
    * @param uri parameter value
    * @param localName parameter value
    * @param qname parameter value
    *
    * @see PlayerHandler#endElement(String, String, String)
    */
   public void pre_endElement(String uri, String localName, String qname) {
      assert uri != null;
      assert localName != null;
      assert qname != null;
   }   // end pre_endElement()

   /** 
    * These conditions must hold after the getText Method is run
    *
    * @see PlayerHandler#getText()
    */
   public void post_getText() {
      String output = (String) super.getReturnValue();
      assert output != null;
   }   // end post_getText()

   /** 
    * These conditons must hold after the getConfiguration method
    *
    * @see PlayerHandler#getConfiguration()
    */
   public void post_getConfiguration() {
      Configuration output = (Configuration) super.getReturnValue();
      assert output != null;
   }   // end post_getConfiguration()
}   // end PlayerHandlerContract
