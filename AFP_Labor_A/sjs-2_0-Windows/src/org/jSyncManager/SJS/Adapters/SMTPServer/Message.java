// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Torben WERNER <teisler@uvic.ca>
// --------------------------------------------------------------------------
// OSI Certified Open Source Software
// --------------------------------------------------------------------------
//
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 2 of the License, or (at your option)
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//
// --------------------------------------------------------------------------
// $Id: Message.java,v 1.0 2007/10/26 19:00:00 torbenwerner Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.c4j.ContractReference;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;


/**
 * This class provides an object representation of an e-mail message. It
 * has several get methods which make it easy to access different properties
 * of a typical e-mail.
 *
 * @author Torben Werner, Marc Windle
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.MessageContract")
public class Message {
//~  ========================================================================

   /** Sending address of the message */
   protected Address from;

//~  ========================================================================

   /** Receiving address of the message */
   protected List<Address> to;

   // TO-DO: Implement the Storable interface!
   /** The date when this message was received */
   protected Date received;

   // TO-DO: Implement the Storable interface!
   /** The date when this message was sent */
   protected Date sent;

   // Message content
   /** Message body */
   //protected String body;
   protected StringBuffer body = new StringBuffer();

//~  ========================================================================

   /**
    * Constructor, default empty constructor
    */
   public Message() {
      to = new ArrayList<Address>();
   }   // end Message()

   /**
    * A constructor which takes a from/to (sender/receiver), body, and send/receive dates as arguments.
    * @param sent the date at which this message was sent
    * @param received the date at which this message was received
    * @param from the address of the sender
    * @param to the address of the receiver
    * @param body the body content of the message
    */
   public Message(Date sent, Date received, Address from, List<Address> to,
      String body) {
      this.sent = sent;
      this.received = received;
      this.from = from;
      this.to = to;
      this.body.append(body);
   }   // end Message()

   /**
    * A constructor which takes a from/to (sender/receiver), and body and initializes
    * the sent/received dates to null.
    * The dates are defaulted to null.
    * @param from the address of the sender
    * @param to the address of the receiver
    * @param body the body content of the message
    */
   public Message(Address from, List<Address> to, String body) {
      this(null, null, from, to, body);
   }   // end Message()

//~  ========================================================================

   /**
    * Returns the address of the sender of a message
    *
    * @return the address of the sender of a message
    */
   public Address getFrom() {
      return this.from;
   }   // end getFrom()

// ---------------------------------------------------------------------------
   /**
    * Sets the from address of this message
    *
    * @param from The address as a String
    */
   public void setFrom(String from){
		// TO-DO: break into user/domain
   		String[] address = from.split("@");

   		// User in from might not exist on this system, so pass null for the
   		// path string in the User object
   		this.from = new Address(new User(address[0],null), new Domain(address[1]));
	}    // end setFrom

 // ---------------------------------------------------------------------------
   /**
    * Sets the from address of this message
    *
    * @param from The address as a String
    */
   public void setFrom(Address from){
		this.from = from;
   }     // end setFrom

// ---------------------------------------------------------------------------
   /**
    * Sets the received date of this message
    *
    * @param received date as a Date
    */
   public void setReceived(Date received){
		this.received = received;
	}    // end setReceived

// ---------------------------------------------------------------------------
   /**
    * Sets the sent date of this message
    *
    * @param sent date as a Date
    */
   public void setSent(Date sent){
		this.sent = sent;
	}    // end setSent

// ---------------------------------------------------------------------------
   /**
    * Sets the to address of this message
    *
    * @param to The to address as an Adddress
    */
   public void setTo(List<Address> to){
		this.to = to;
	}    // end setTo

// ---------------------------------------------------------------------------
   /**
    * Returns the address of the receiver of a message
    *
    * @return to address of the receiver of a message
   */
   public List<Address> getTo() {
      return this.to;
   }   // end getTo()

// ---------------------------------------------------------------------------
    /**
    * Returns the address of the receiver of a message
    *
    * @return the address of the receiver of a message
    */
   public Iterator<Address> getToIter() {
      return to.iterator();
   }   // end getTo()

// ---------------------------------------------------------------------------
   /**
    * Returns the address of the receiver of a message
    *
    * @param address the address of the receiver of a message
    */
   public void addTo(Address address) {
      to.add(address);
   }   // end getTo()
// ---------------------------------------------------------------------------
   /**
    * Appends data to the end of the message body
    *
    * @param data The string data to append to the message body
    */
   public void appendData(String data){
	   if(data != null){
			this.body.append(data);
		}// end if
	}    // end appendData

// ---------------------------------------------------------------------------
   /**
    * Returns the body content of a message
    *
    * @return the body content of a message
    */
   public String getBody() {
	   if(this.body == null){
		   return "";
	   }
      return this.body.toString();
   }   // end getBody()

// ---------------------------------------------------------------------------
   /**
    * Returns the date which this message was sent
    *
    * @return the date which this message was sent
    */
   public Date getSentDate() {
      return this.sent;
   }   // end getSentDate()

// ---------------------------------------------------------------------------
   /**
    * Returns the date which this received was sent
    *
    * @return the date which this received was sent
    */
   public Date getReceivedDate() {
	   if(this.received == null){
		   return Calendar.getInstance().getTime();
	   }
      return this.received;
   }   // end getReceivedDate()

// ---------------------------------------------------------------------------
   /**
    * Returns a string representation of the message in a common format
    *
    * @return returned
    */
   public String toString() {
      String str = "FROM: " + this.from.toString() + "\n" +
         "Received: " + this.received.toString() + "\n";
      String recipientStr = "";
      Iterator<Address> recipients = getToIter();
      while(recipients.hasNext()){
         if(!recipientStr.equals("")) recipientStr += ", ";
         str += recipients.next().toString();
      }
      str += "TO: " + recipientStr +
         this.body.toString();
      return str;
   }   // end toString()
}   // end Message
