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
// $Id: Mbox.java,v 1.0 2007/10/26 19:00:00 torbenwerner Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;
import org.jSyncManager.SJS.Adapters.SMTPServer.util.SMTPUtils;

import net.sourceforge.c4j.ContractReference;
   
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//~  ========================================================================
/** 
 * This class implements the MessageStore interface by storing and
 * retrieving messages to and from a file formatted according to the mbox
 * specifications.
 *
 * @author Torben Werner
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.MboxContract")
public class Mbox implements MessageStoreInterface {
//~  ========================================================================

   /** Convenience variable for the directory path separator */
   public static final String PATH_SEP = File.separator;
   
//~  ========================================================================
   
   /**
    * Constructor, default, empty
    */
   public Mbox(){
	   
   }// end Mbox()

//~  ========================================================================

   /** 
    * Stores an array of Message objects to some medium where they can
    * later be retrieved.
    *
    * @param msgs the array of Message objects which will each be stored.
    *
    * @throws IOException exception
    */
   public void storeMessages(Message[] msgs) throws IOException {
      for (int i = 0; i < msgs.length; i++) {
         Iterator<Address> recipients = msgs[i].getToIter();
         while(recipients.hasNext()){
            this.storeMessage(msgs[i], recipients.next());
         }
      }   // end for
   }   // end storeMessages()

// ---------------------------------------------------------------------------
   /** 
    * Stores a single Message object to some medium where it can later
    * be retrieved.
    *
    * @param msg the Message object which will be stored.
    *
    * @throws IOException exception
    */
   public void storeMessage(Message msg, Address to) throws IOException {
      // TO-DO: Open or create the user's mbox file.
      File mboxFile = new File(to.getUser().getPath() + PATH_SEP + to.getUser().getName());
      

      if (! mboxFile.exists()) {
         mboxFile.createNewFile();
      }   // end if

      BufferedWriter bw = new BufferedWriter(new FileWriter(mboxFile, true));

      // TO-DO: Append the message to the end of the file
      DateFormat df = new SimpleDateFormat("EEE MMM d kk:mm:ss yyyy");
      
      //From joe@example.com Sat Jan  3 01:05:34 1996
      bw.write("From " + msg.getFrom() + " " + df.format(msg.getReceivedDate()) +
         "\n");

      //Return-Path: <joe@example.com>
      bw.write("Return-Path: <" + msg.getFrom() + ">\n");
      
      String deliveredToStr = "";
      String toStr = "";
      Iterator<Address> recipients = msg.getToIter();
      while(recipients.hasNext()){
         if(!deliveredToStr.equals("")) deliveredToStr += ", ";
         if(!toStr.equals("")) toStr += ", ";
         
         Address recipient = recipients.next();
         deliveredToStr += recipient;
         toStr += recipient + " (" + recipient.getUser().getName() + ")";
      }
      
      //Delivered-To: djb@example.com
      bw.write("Delivered-To: " + deliveredToStr + "\n");

      df = new SimpleDateFormat("EEE MMM d kk:mm:ss yyyy Z");
      //Date: 3 Jan 1996 01:05:34 -0000
      bw.write("Date: " + df.format(msg.getReceivedDate()) + "\n");

      //From: Joe <joe@example.com>
      bw.write("From: " + msg.getFrom().getUser().getName() + " <" +
         msg.getFrom() + ">\n");      
      
      //To: djb@example.com (djb), abc@example.com (abc), ...
      bw.write("To: " + toStr + ")\n");
      
      // Pattern to replace 'From ' lines from beginning of message data
      String patternStr = "^From ";
      Pattern pattern = Pattern.compile(patternStr,Pattern.MULTILINE);
      Matcher matcher;
      
      //body text
      // We need to replace any 'From ' lines from the body
      String body = msg.getBody();
      matcher = pattern.matcher(body);
      body = matcher.replaceAll(">From ");
      bw.write(body);
      bw.write("\n");
      bw.flush();
      bw.close();
   }   // end storeMessage()

// ---------------------------------------------------------------------------
   /** 
    * Returns a Message object from a String of message data.
    *
    * @param msg a string containing all parts of a message in mbox format
    *
    * @return the message object created from the string information
    */
   private Message createMessageFromMboxData(String[] msg, User user) {	   
	   //TODO: Fix this!
      // Gets the "from" user (group 1), domain (group 2)
	   String fromExp = "^From " + SMTPUtils.groupedAddressRegex + "$";
	   
	   // Gets the "to" user (group 1) and domain (group 2)
	   String toExp = "^Delivered-To: .*$";
	   
	   // Gets the "received" date (group 1)
	   String receivedDateExp = "^((\\w|:|-)*)$";
	   
	   // To replace ">From " with "From "
	   String bodyFixExp = "^>From ";
	   
	   // Get From address
	   // Should be the first line in the message data
	   Address from;
	   Pattern pattern = Pattern.compile(fromExp);
	   Matcher matcher = pattern.matcher(msg[0]);
	   
	   // User path for a from will be null (i.e. we don't know if that user has a mbox on our server)
	   from = new Address(new User(matcher.group(1),null), new Domain(matcher.group(2)));
	   
	   // Get To address
	   // Should be the 3rd line in the message data
	   List<Address> recipients = new ArrayList<Address>();
	   pattern = Pattern.compile(toExp);
	   matcher = pattern.matcher(msg[2]);
	   String[] addresses = matcher.group().split(",[ ]*");
	   for(int i=0; i<addresses.length; i++){
	      if(addresses[i].trim().equals("")) continue;
	      recipients.add(new Address(addresses[i]));
	   }
	   if(recipients.size()==0) return null;
	   
	   // Get Received Date
	   // Should be the 4th line in the message date
	   Date received;
	   SimpleDateFormat df = new SimpleDateFormat("EEE MMM d kk:mm:ss yyyy Z");
	   pattern = Pattern.compile(receivedDateExp);
	   matcher = pattern.matcher(msg[3]);
	   try{
		   received = df.parse(matcher.group(1));
	   }   // end try
	   catch(ParseException e){
		   received = null;
	   }   // end catch
	   
	   // Get and fix the body
	   // Should be the 7th line in the message data
	   // (We ignore the last line since it is simply a newline inserted by us)
	   String body = "";
	   pattern = Pattern.compile(bodyFixExp,Pattern.MULTILINE);
	   for(int i = 6; i < msg.length - 1; i++){
		   matcher = pattern.matcher(msg[i]);
		   body += matcher.replaceAll("From ");
	   }   // end for
	   
	   return new Message(null, received, from, recipients, body);
   }   // end createMessageFromMbox()

// ---------------------------------------------------------------------------
   /** 
    * Returns an array of all the messages stored in a valid user's
    * mbox.
    *
    * @param user the User who's messages will be retrieved.
    *
    * @return an array of Message objects representing each of the user's
    *         messages.
    */
   public Message[] getMessages(User user) throws IOException{
      // Return the messages in a user's mbox file
	   File mboxFile = new File(user.getPath() + PATH_SEP + user.getName());
	   
	   // Make sure the user's mbox exists
	   if(!mboxFile.exists()){
		   return null; // No mbox = no messages
	   }// end if
	   
	   ArrayList<Message> messages = new ArrayList<Message>();
	   
	   BufferedReader in = new BufferedReader(new FileReader(mboxFile));
	   ArrayList<String> msgData = new ArrayList<String>();
	   
	   String patternStr = "^From ";
	   Pattern pattern = Pattern.compile(patternStr);
	   Matcher matcher;
	   
	   // Keep track if we have read the first line yet
	   // This is so we know if the 'From ' line is for the current message or the next one
	   boolean firstLine = true;
	   String line;
	   do{
		   line = in.readLine();
		   matcher = pattern.matcher(line);
		   if(!firstLine && matcher.matches()){
			   // Create a message from the data that we have read and reset the 
			   messages.add(createMessageFromMboxData((String[])msgData.toArray(), user));
			   msgData.clear();
			   firstLine = true;
		   }   // end if
		   msgData.add(line);
		   firstLine = false;
	   }while(line != null);
	   
	   in.close();
	   
	   return (Message[])messages.toArray();
   }   // end getMessages()
   
// ~  ========================================================================
}   // end Mbox
