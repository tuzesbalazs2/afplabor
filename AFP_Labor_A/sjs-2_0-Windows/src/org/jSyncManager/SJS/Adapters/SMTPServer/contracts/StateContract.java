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
import org.jSyncManager.SJS.Adapters.SMTPServer.AbstractSMTPServer.State;


/** 
 * This class lists the contracts and invariants for the SMTPCommand
 * abstract class
 *
 * @author Matt Campbell
 * @version 1.0
 *
 * @see SmtpCommand
 */
public class StateContract extends ContractBase<State> {

/** 
    * A general constructor that sets the instance variable<br/>
    * This is of the form required by C4J
    *
    * @param t the reference to the base class
    */
   public StateContract(State t) {
	   super(t);
   }   // end StateContract()

   //~  ========================================================================

   /** 
    * The invariant for this class. Each of these conditions must be
    * satisfied before and after each method call
    */
   public void classInvariant() {
	   assert ((Long)super.getTargetField("state")) >= 0;
   }   // end classInvariant()

   /** 
    * These conditions must be true before running the areBitsSet method
    *
    * @param mask The parameter to the method
    *
    * @see State#areBitsSet(long)
    */
   public void pre_areBitsSet(long mask) {
      assert mask >= 0;
   }   // end pre_areBitsSet()

   /** 
    * These conditions must be true before running the areBitsSet method
    *
    * @param s The parameter to the method
    *
    * @see State#setState(long)
    */
   public void pre_setState(long s) {
      assert s >= 0;
   }   // end pre_setState()

   /** 
    * These conditions must be true at the conclusion of running
    * getState Method
    *
    * @see State#setState(long)
    */
   public void post_getState() {
      long output = (Long) super.getReturnValue();
      assert output >= 0;
   }   // end post_getState()

   /** 
    * These conditions must be true before running the insertBits method
    *
    * @param mask The parameter to the method
    *
    * @see State#insertBits(long)
    */
   public void pre_insertBits(long mask) {
      assert mask >= 0;
   }   // end pre_insertBits()

   /** 
    * These conditions must be true before running the clearBits method
    *
    * @param mask The parameter to the method
    *
    * @see State#clearBits(long)
    */
   public void pre_clearBits(long mask) {
      assert mask >= 0;
   }   // end pre_clearBits()

   /** 
    * These conditions must be true at the conclusion of running
    * clearBits Method
    *
    * @param mask The parameter to the method
    *
    * @see State#clearBits(long)
    */
   public void post_clearBits(long mask) {
      long output = (Long) super.getReturnValue();
      assert output >= 0;
   }   // end post_clearBits()
}   // end StateContract
