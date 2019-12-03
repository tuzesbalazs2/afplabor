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

import java.util.List;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.AbstractSMTPServer;
import org.jSyncManager.SJS.Adapters.SMTPServer.AbstractSMTPServer.State;


/** 
 * This class lists the contracts and invariants for the AbstactSMTPServer
 * class
 *
 * @author Matt Campbell
 * @version 1.0
 *
 * @see AbstractSMTPServer
 */
public class AbstractSMTPServerContract extends ContractBase<AbstractSMTPServer> {
/** 
    * A general constructor that sets the instance variable<br/>
    * This is of the form required by C4J
    *
    * @param t the reference to the base class
    */
   public AbstractSMTPServerContract(AbstractSMTPServer t) {
      super(t);
   }   // end AbstractSMTPServerContract()

   //~  ========================================================================

   /** 
    * The invariant for this class. Each of these conditions must be
    * satisfied before and after each method call
    */
   public void classInvariant() {
      assert super.getTargetField("commands") != null;
      assert ((State)super.getTargetField("state")).getState() >= 0;
      assert ((Long)super.getTargetField("currentMask"))>= 0;
   }   // end classInvariant()

   /** 
    * These conditions must be true before running the processRequest
    * method
    *
    * @param request The parameter to the method
    *
    * @see AbstractSMTPServer#processRequest(String)
    */
   public void pre_processRequest(String request) {
      assert request != null;
   }   // end pre_processRequest()

   /** 
    * These conditions must be true at the conclusion of running
    * processRequest Method
    *
    * @param request The parameter to the method
    *
    * @see AbstractSMTPServer#processRequest(String)
    */
   public void post_processRequest(String request) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processRequest()

   /** 
    * These conditions must be true before running the stripBackspace
    * method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#stripBackspace(String)
    */
   public void pre_stripBackspace(String args) {
      assert args != null;
   }   // end pre_stripBackspace()

   /** 
    * These conditions must be true at the conclusion of running
    * stripBackspace Method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#stripBackspace(String)
    */
   public void post_stripBackspace(String args) {
      assert super.getReturnValue() != null;
   }   // end post_stripBackspace()

   /** 
    * These conditions must be true before running the processHelo
    * method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processHelo(String)
    */
   public void pre_processHelo(String args) {
      assert args != null;
   }   // end pre_processHelo()

   /** 
    * These conditions must be true at the conclusion of running
    * processHelo Method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processHelo(String)
    */
   public void post_processHelo(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processHelo()

   /** 
    * These conditions must be true at the conclusion of running the
    * processMail method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processMail(String)
    */
   public void post_processMail(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processMail()

   /** 
    * These conditions must be true before of running processRcpt Method
    *
    * @param relays The list parameter to the method
    * @param addr The string command to the method
    *
    * @see AbstractSMTPServer#processRcpt(List, String)
    */
   public void pre_processRcpt(List<String> relays, String addr) {
      assert relays != null;
      assert addr != null;
      assert addr.matches(AbstractSMTPServer.getMailAddrExpr().toString());
   }   // end pre_processRcpt()

   /** 
    * These conditions must be true at the conclusion of running
    * processRcpt Method
    *
    * @param relays The list parameter to the method
    * @param addr The string command to the method
    *
    * @see AbstractSMTPServer#processRcpt(List, String)
    */
   public void post_processRcpt(List<String> relays, String addr) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processRcpt()

   /** 
    * These conditions must be true before running the processData
    * method
    *
    * @param args The parameter to the method
    */
   public void pre_processData(String args) {
   }   // end pre_processData()

   /** 
    * These conditions must be true at the conclusion of running the
    * processData method
    *
    * @param args The parameter to the method
    */
   public void post_processData(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processData()

   /** 
    * These conditions must be true before running the
    * processMessagePart method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processMessagePart(String)
    */
   public void pre_processMessagePart(String args) {
      assert args != null;
   }   // end pre_processMessagePart()

   /** 
    * These conditions must be true at the conclusion of running the
    * processFinnished method
    *
    * @param args The parameter to the method
    */
   public void post_processFinished(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processFinished()

   /** 
    * These conditions must be true at the conclusion of running the
    * processRset method
    *
    * @param args The parameter to the method
    */
   public void post_processRset(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processRset()

   /** 
    * These conditions must be true before running the processVrfy
    * method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processVrfy(String)
    */
   public void pre_processVrfy(String args) {
      assert args != null;
   }   // end pre_processVrfy()

   /** 
    * These conditions must be true at the conclusion of running the
    * processVrfy method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processVrfy(String)
    */
   public void post_processVrfy(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processVrfy()

   /** 
    * These conditions must be true before running the processExpn
    * method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processExpn(String)
    */
   public void pre_processExpn(String args) {
      assert args != null;
   }   // end pre_processExpn()

   /** 
    * These conditions must be true at the conclusion of running the
    * processExpn method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processExpn(String)
    */
   public void post_processExpn(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processExpn()

   /** 
    * These conditions must be true before running the processHelp
    * method
    *
    * @param args The parameter to the method
    */
   public void pre_processHelp(String args) {
      assert args != null;
   }   // end pre_processHelp()

   /** 
    * These conditions must be true at the conclusion of running the
    * processHelp method
    *
    * @param args The parameter to the method
    *
    * @see AbstractSMTPServer#processHelp(String)
    */
   public void post_processHelp(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processHelp()

   /** 
    * These conditions must be true at the conclusion of running the
    * processQuit method
    *
    * @param args The parameter to the method
    */
   public void post_processQuit(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processQuit()

   /** 
    * These conditions must be true at the conclusion of running the
    * processNoop method
    *
    * @param args The parameter to the method
    */
   public void post_processNoop(String args) {
      String output = (String) super.getReturnValue();

      //ensure that the output is never null
      assert output != null;

      //test to see if the first 3 characters of the output string are numbers
      assert Integer.parseInt(output.substring(0, 3)) >= 0;
   }   // end post_processNoop()
}   // end AbstractSMTPServerContract
