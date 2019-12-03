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

import org.jSyncManager.SJS.Adapters.SMTPServer.SMTPServer;

import java.util.List;


/** 
 * This class specifies the contracts for relevant methods of the SMTPServer class.
 *
 * @author akaspar
 */
public class SMTPServerContract extends ContractBase<SMTPServer> {
   //~  ========================================================================

   /** The server class being checked */
   SMTPServer server;

   //~  ========================================================================

/**
    * Creates a new SMTPServerContract for the passed server object (this is handled automatically)
    * by the library
    * @param server The server to have its contracts tested
    */
   public SMTPServerContract(SMTPServer server) {
      super(server);
      this.server = server;
   }   // end SMTPServerContract()

   //~  ========================================================================

   /** 
    * Specifies the states of the server that should never change. These
    * fields are used throughout the server without null checking.
    */
   public void classInvariant() {
      assert super.getTargetField("server") != null;
      assert super.getTargetField("ms") != null;
      assert super.getTargetField("currentMsg") != null;
      assert super.getTargetField("config") != null;
   }   // end classInvariant()

   /** 
    * Code doesn't check to ensure that args is not null, so it is
    * checked in this contract.
    *
    * @param args Should not be null.
    */
   public void pre_processExpn(String args) {
      assert args != null;
   }   // end pre_processExpn()

   /** 
    * Code doesn't check to ensure that args is not null, so it is
    * checked in this contract.
    *
    * @param args Should not be null.
    */
   public void pre_processHelo(String args) {
      assert args != null;
   }   // end pre_processHelo()

   /** 
    * Code doesn't check to ensure that address is not null, so it is
    * checked in this contract.
    *
    * @param address Should not be null.
    */
   public void pre_getAddress(String address) {
      assert address != null;
   }   // end pre_getAddress()

   /** 
    * Code doesn't check to ensure that addr is not null, so it is
    * checked in this contract.
    *
    * @param addr Should not be null.
    */
   public void pre_checkAddress(String addr) {
      assert addr != null;
   }   // end pre_checkAddress()

   /** 
    * Code doesn't check to ensure that args is not null, so it is
    * checked in this contract.
    *
    * @param args Should not be null.
    */
   public void pre_processEhlo(String args) {
      assert args != null;
   }   // end pre_processEhlo()

   /** 
    * ¥ÊCode doesn't check to ensure that addr is not null, so it is
    * checked in this contract.
    *
    * @param relays Don't care about this, it is never used in the method.
    * @param addr Should not be null.
    */
   public void pre_processRcpt(List<String> relays, String addr) {
      assert addr != null;
   }   // end pre_processRcpt()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    *
    * @param relays
    * @param addr
    */
   public void post_processRcpt(List<String> relays, String addr) {
      assert getReturnValue() != null;
   }   // end post_processRcpt()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    */
   public void post_getUsers() {
      assert getReturnValue() != null;
   }   // end post_getUsers()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    */
   public void post_getDomains() {
      assert getReturnValue() != null;
   }   // end post_getDomains()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    *
    * @param addr
    */
   public void post_checkAddress(String addr) {
      assert getReturnValue() != null;
   }   // end post_checkAddress()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    */
   public void post_processData() {
      assert getReturnValue() != null;
   }   // end post_processData()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    *
    * @param args
    */
   public void post_processExpn(String args) {
      assert getReturnValue() != null;
   }   // end post_processExpn()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    *
    * @param args
    */
   public void post_processHelo(String args) {
      assert getReturnValue() != null;
   }   // end post_processHelo()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    *
    * @param args
    */
   public void post_processHelp(String args) {
      assert getReturnValue() != null;
   }   // end post_processHelp()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    *
    * @param args
    */
   public void post_processMail(String args) {
      assert getReturnValue() != null;
   }   // end post_processMail()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    */
   public void post_processMessageFinished() {
      assert getReturnValue() != null;
   }   // end post_processMessageFinished()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    */
   public void post_processNoop() {
      assert getReturnValue() != null;
   }   // end post_processNoop()

   /** 
    * Check to ensure that the server always returns a response (if
    * there is a problem, it should return an error string, never null).
    */
   public void post_processQuit() {
      assert getReturnValue() != null;
   }   // end post_processQuit()
}   // end SMTPServerContract
