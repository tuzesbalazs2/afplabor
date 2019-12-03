// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Ahmad H ahmadh@uvic.ca
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
// $Id: MessageContract.java,v 1.0
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Adapters.SMTPServer.contracts;

import java.util.Date;
import java.util.List;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Address;
import org.jSyncManager.SJS.Adapters.SMTPServer.Message;


/**
 * Contract for the Message Class
 * @author Ahmad
 * @version v1.0
  */
public class MessageContract extends ContractBase<Message> {

   /**
    * Constructor, get a reference for the Message object
    *
    * @param t the object.
    */
   public MessageContract(Message t) {
	   super(t);
   }   // end MessageContract()

   //~  ========================================================================

   /**
    * Invariant checks.
    */
   public void classInvariant() {
      assert super.getTargetField("from") != null;
      assert super.getTargetField("to") != null;

      //assert super.getTargetField("sent") != null;
   }   // end classInvariant()

   /**
    * Pre-Message constructor condition.
    */
   public void pre_Message() {
	   assert((Address)super.getTargetField("to")) != null;
   }   // end pre_Message()

   /**
    * Pre-Message (second) constructor condition.
    *
    * @param sent See Message class.
    * @param received See Message class.
    * @param from See Message class.
    * @param to See Message class.
    * @param body See Message class.
    */
   public void pre_Message(Date sent, Date received, Address from,
      List<Address> to, String body) {
      assert from != null;
      assert to != null;
      assert body != null;
   }   // end pre_Message()

   /**
    * Post-Message (second) constructor condition.
    *
    * @param sent See Message class.
    * @param received See Message class.
    * @param from See Message class.
    * @param to See Message class.
    * @param body See Message class.
    */
   public void post_Message(Date sent, Date received, Address from,
      List<Address> to, String body) {
      assert super.getTargetField("from") != null;
      assert super.getTargetField("to") != null;
   }   // end post_Message()

   /**
    * Pre-Message (third) constructor condition.
    *
    * @param from See Message class.
    * @param to See Message class.
    * @param body See Message class.
    */
   public void pre_Message(Address from, List<Address> to, String body) {
      assert from != null;
      assert to != null;
      assert body != null;
   }   // end pre_Message()

   /**
    * Post-Message (third) constructor condition..
    *
    * @param from See Message class.
    * @param to See Message class.
    * @param body See Message class.
    */
   public void post_Message(Address from, List<Address> to, String body) {
      assert super.getTargetField("from") != null;
      assert super.getTargetField("to") != null;
   }   // end post_Message()

   /**
    * Pre-condition for setFrom.
    *
    * @param from See Message class.
    */
   public void pre_setFrom(Address from) {
      assert from != null;
   }   // end pre_setFrom()

   /**
    * Post-condition for setFrom.
    *
    * @param from See Message class.
    */
   public void post_setFrom(Address from) {
      assert super.getTargetField("from") != null;
   }   // end post_setFrom()

   /**
    * Pre-condition for setFrom.
    *
    * @param from See Message class.
    */
   public void pre_setFrom(String from) {
      assert from != null;
   }   // end pre_setFrom()

   /**
    * Post-condition for setFrom.
    *
    * @param from See Message class.
    */
   public void post_setFrom(String from) {
      assert super.getTargetField("from") != null;
   }   // end post_setFrom()

   /**
    * Pre-condition for setReceived.
    *
    * @param received See Message class.
    */
   public void pre_setReceived(Date received) {
      assert received != null;
   }   // end pre_setReceived()

   /**
    * Post-condition for setReceived.
    *
    * @param received See Message class.
    */
   public void post_setReceived(Date received) {
      assert super.getTargetField("received") != null;
   }   // end post_setReceived()

   /**
    * Pre-condition for setSent.
    *
    * @param sent See Message class.
    */
   public void pre_setSent(Date sent) {
      assert sent != null;
   }   // end pre_setSent()

   /**
    * Post-condition for setSent.
    *
    * @param sent See Message class.
    */
   public void post_setSent(Date sent) {
      assert super.getTargetField("sent") != null;
   }   // end post_setSent()

   /**
    * Pre-condition for setTo.
    *
    * @param to See Message class.
    */
   public void pre_setTo(List<Address> to) {
      assert to != null;
   }   // end pre_setTo()

   /**
    * Post-condition for setTo method.
    *
    * @param to See Message class.
    */
   public void post_setTo(List<Address> to) {
      assert super.getTargetField("to") != null;
   }   // end post_setTo()

   /**
    * Pre-condition for getToIter method.
    */
   public void pre_getToIter() {
      assert super.getTargetField("to") != null;
   }   // end pre_getToIter()

   /**
    * Pre-condition for addTo method.
    *
    * @param address See Message class.
    */
   public void pre_addTo(Address address) {
      assert address != null;
      assert super.getTargetField("to") != null;
   }   // end pre_addTo()

   /**
    * Pre-condition for appendData method.
    *
    * @param data See Message class.
    */
   public void pre_appendData(String data) {
      assert data != null;
      assert super.getTargetField("body") != null;
   }   // end pre_appendData()

   /**
    * Pre-condition for toString method.
    */
   public void pre_toString() {
      assert super.getTargetField("from") != null;
      assert super.getTargetField("to") != null;
      assert super.getTargetField("received") != null;
      assert super.getTargetField("body") != null;
   }   // end pre_toString()

   /**
    * Post-condition for toString method.
    */
   public void post_toString() {
      assert super.getReturnValue() != null;
   }   // end post_toString()
}   // end MessageContract
