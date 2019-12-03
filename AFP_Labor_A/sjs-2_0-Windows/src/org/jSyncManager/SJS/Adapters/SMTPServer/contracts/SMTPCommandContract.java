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

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.AbstractSMTPServer.SmtpCommand;


/** 
 * This class lists the contracts and invariants for the SMTPCommand
 * abstract class
 *
 * @author Matt Campbell
 * @version 1.0
 *
 * @see SmtpCommand
 */
public class SMTPCommandContract extends ContractBase<SmtpCommand> {
/** 
    * A general constructor that sets the instance variable<br/>
    * This is of the form required by C4J
    *
    * @param t the reference to the base class
    */
   public SMTPCommandContract(SmtpCommand t) {
	   super(t);
   }   // end SMTPCommandContract()

   //~  ========================================================================

   /** 
    * The invariant for this class. Each of these conditions must be
    * satisfied before and after each method call
    */
   public void classInvariant() {
      assert ((String)super.getTargetField("cmd")) != null;
      assert ((Long)super.getTargetField("mask")) >= 0;
   }   // end classInvariant()

   /** 
    * These conditions must be true before running the isStateSet method
    *
    * @see SmtpCommand#isStateSet(String...)
    */
   public void pre_isStateSet(String... command) {
      assert command.length > 0;
   }   // end pre_isStateSet()

   /** 
    * These conditions must be true before running the process method
    *
    * @param args The parameter to the method
    *
    * @see SmtpCommand#process(String)
    */
   public void pre_process(String args) {
      assert args != null;
   }   // end pre_process()

   /** 
    * These conditions must be true at the conclusion of running process
    * Method
    *
    * @param args The parameter to the method
    *
    * @see SmtpCommand#process(String)
    */
   public void post_process(String args) {
      String output = (String) super.getReturnValue();
      assert output != null;
   }   // end post_process()
}   // end SMTPCommandContract
