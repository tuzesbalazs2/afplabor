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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Address;
import org.jSyncManager.SJS.Adapters.SMTPServer.AbstractSMTPServer;
import org.jSyncManager.SJS.Adapters.SMTPServer.RemoteServerMessage;
import org.jSyncManager.SJS.LogManager;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;


/** 
 * This class specifies the contracts for relevant methods of the RemoteServerMessage class.
 * @author kclarke
 * @version $Revision$
  */
public class RemoteServerMessageContract extends ContractBase<RemoteServerMessage> {
   //~  ========================================================================

   /** A reference to the base object */
   private RemoteServerMessage m_target;

   //~  ========================================================================

/**
    * Creates the RemoteServerMessageContract class. 
    * 
    * 
    */
   public RemoteServerMessageContract(RemoteServerMessage target) {
   }   // end RemoteServerMessageContract()

   //~  ========================================================================

   /** 
    * Invariants...
    * 
    */
   public void classInvariant() {
   }   // end classInvariant()

   /** 
    * This contract ensures that the input and output streams referred to by this
    * method are not null. Cannot close the connection on one or more null streams.
    *
    * @param input
    * @param output
    */
   public void pre_closeConnection(BufferedReader input, DataOutputStream output) {
      assert input != null;
      assert output != null;
   }   // end pre_closeConnection()

   /** 
    * This contract ensures that the line and output parameters are not null.
    * As a result the method will not send data based on a null line and it will
    * not send data if the output stream is null.
    * 
    * @param line
    * @param output
    */
   public void pre_sendLineData(String line, DataOutputStream output) {
      assert line != null;
      assert output != null;
   }   // end pre_sendLineData()

   /** 
    * This contract ensures that line, input, and output parameters are not null.
    * As a result the method will not send null strings, or will not send a string
    * if the input or output streams are null.
    *
    * @param line
    * @param input
    * @param output
    */
   public void pre_sendLine(String line, BufferedReader input, DataOutputStream output) {
      assert line != null;
      assert input != null;
      assert output != null;
   }   // end pre_sendLine()

   /** 
    * This contract ensures that the recipient to be added is not null.
    *
    * @param rcpt
    */
   public void pre_addRcpt(Address rcpt) {
	   assert rcpt != null;
   }   // end pre_addRcpt()

   /** 
    * This contract ensures that the domain to be added is not null. 
    *
    * @param domain
    */
   public void pre_addDomain(Domain domain) {
	   assert domain != null;
   }   // end pre_addDomain()

   /** 
    * This contract ensures that the supplied number to be assigned to port is within the
    * range of acceptable values according to the Internet Assigned Numbers Authority (IANA).
    * 
    * @param port
    */
   public void pre_setPort(int port) {
	   assert (port >= 0) && (port <= 65535);
   }   // end pre_setPort()
   
   /** 
    * 
    *
    */
   public void post_send() {
   }   // end post_send()

   /** 
    * This contract ensures that the input and output streams referred to by this
    * method are not null. Cannot close the connection on one or more null streams.
    *
    * @param input
    * @param output
    */
   public void post_closeConnection(BufferedReader input, DataOutputStream output) {
      assert input != null;
      assert output != null;
   }   // end post_closeConnection()

   /** 
    * This contract ensures that the line and output parameters are not null.
    * As a result the method will not send data based on a null line and it will
    * not send data if the output stream is null.
    * 
    * @param line
    * @param output
    */
   public void post_sendLineData(String line, DataOutputStream output) {
      assert line != null;
      assert output != null;
   }   // end post_sendLineData()

   /** 
    * This contract ensures that line, input, and output parameters are not null.
    * As a result the method will not send null strings, or will not send a string
    * if the input or output streams are null.
    *
    * @param line
    * @param input
    * @param output
    */
   public void post_sendLine(String line, BufferedReader input, DataOutputStream output) {
   }   // end post_sendLine()

   /** 
    * 
    *
    */
   public void post_buildReceivedMessage() {
   }   // end post_buildReceivedMessage()

   /** 
    * This contract ensures that the recipient to be added is not null.
    *
    * @param rcpt
    */
   public void post_addRcpt(Address rcpt) {
	   assert rcpt != null;
   }   // end post_addRcpt()

   /** 
    * This contract ensures that the domain to be added is not null. 
    *
    * @param domain
    */
   public void post_addDomain(Domain domain) {
   }   // end post_addDomain()

   /** 
    * This contract ensures that the value returned is not null.
    *
    */
   public void post_getRcpts() {
   }   // end post_getRcpts()

   /** 
    * This contract ensures that the value returned is not null. 
    *
    */
   public void post_getDomains() {
   }   // end post_getDomains()

   /** 
    * This contract ensures that the value returned is not null.
    *
    */
   public void post_getServer() {
   }   // end post_getServer()

   /** 
    * This contract ensures that the value returned is not null.
    *
    */
   public void post_getPort() {
   }   // end post_getPort()

   /** 
    * This contract ensures that the supplied port number is assigned to the
    * port instance variable.
    * 
    * @param port
    */
   public void post_setPort(int port) {
   }   // end post_setPort()
   
}   // end RemoteServerMessageContract
