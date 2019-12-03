// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Cecelia Redding <cecelia@uvic.ca>
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
// $Id: FILE_NAME.java,v 0.1 CREATION_DATE CREATION_TIME SOURCEFORGE_ID Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer;

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

import net.sourceforge.c4j.ContractReference;

import org.jSyncManager.SJS.LogManager;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;

//~  ========================================================================
/**
 * This class is used to store a message that must be relayed to a specific
 * remote server. It contains the server, the original message object, 
 * a list of domains that relay to this server, and a list of recipients that 
 * will be sent the message (a subset of the recipients within the original 
 * message object).
 * 
 * @author Cecelia Redding
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.RemoteServerMessageContract")
public class RemoteServerMessage {
//~  ========================================================================
   
   /** 
    * Response code indicating success 
	*/
   private static final int OK = 250;
	
   /**
   *  Response code indicating successful initiation of a data block 
   */
   private static final int DATA_OK = 354;
	
   /** 
   * The port number used by SMTP servers 
   */
   private static final int DEFAULT_SMTP_PORT = 25;
	
   /**
    * The name of the remote server
    */
   private String server;
   
   /**
    * The port used to connect to the remote server
    */
   private int port;
   
   /**
    * A list of domains that get relayed to this server
    */
   private List<Domain> domains;
   
   /**
    * A list of recipients that get sent the message from this server
    */
   private List<Address> rcpts;
   
   /**
    * The message to be sent to the remote server
    */
   private Message message;
   
   /**
    * The log manager for logging messages.
    */
   private LogManager logManager;
   
//~  ========================================================================

   /**
    * Constructor to create a new RemoteServerMessage
    * 
    * @param server The name of the server
    * @param message The message to be relayed
    */
   public RemoteServerMessage(LogManager logManager, String server, Message message) {
      this.logManager = logManager;
	   this.server = server;
      this.message = message;
      domains = new ArrayList<Domain>();
      rcpts = new ArrayList<Address>();
      port = DEFAULT_SMTP_PORT;
   }
   
//~  ========================================================================
   
   /**
    * Finally relays the one message to the server for all recipients
    * 
    * @return If it was successful
    */
   public boolean send() {
		Socket smtpSocket = null;
		DataOutputStream output = null;
		BufferedReader input = null;
		String response = null;
		try {
			//connect to the remote server
			smtpSocket = new Socket(server, port);
			output = new DataOutputStream(smtpSocket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
			input.readLine();
			
			response = sendLine("HELO engr.uvic.ca\n", input, output);
			if(Integer.parseInt(response.substring(0, 3)) != OK) {
            logManager.writeExceptionEntry("HELO command failed");
				return false;
			}   //end if
			
			response = sendLine("MAIL FROM: <" + message.getFrom().toString() + ">\n", input, output);
			if(Integer.parseInt(response.substring(0, 3)) != OK) {
            logManager.writeExceptionEntry("MAAIL command failed");
				return false;
			}   //end if
			
			//iterate through the recipent list and send them to the server
			Iterator<Address> iter = rcpts.iterator();
			while(iter.hasNext()) {
				response = sendLine("RCPT TO: <" + iter.next().toString() + ">\n", input, output);
				if(Integer.parseInt(response.substring(0, 3)) != OK) {
               logManager.writeExceptionEntry("RCPT command failed");
					return false;
				}   //end if
			}   //end while
			
			//begin DATA block
			response = sendLine("DATA\r\n", input, output);
			if(Integer.parseInt(response.substring(0, 3)) != DATA_OK) {
            logManager.writeExceptionEntry("DATA command failed");
				return false;
			}   //end if
			
			sendLineData("Return-Path: <" + message.getFrom().toString() + ">\n", output);
			sendLineData(buildReceivedMessage(), output);
         
         sendLineData("\r\n", output);
			
			// send the message body line by line
         String[] body = message.getBody().split("\n");
         for(int i = 0; i < body.length; i++) {
            sendLineData(body[i] + "\n", output);
         }   //end for
			
         // send the "." to end data section
         response = closeConnection(input, output);
         
			if(Integer.parseInt(response.substring(0, 3)) != OK) {
            logManager.writeExceptionEntry("Sending message failed");
				return false;
			}   //end if
			
		} catch (IOException ioe) {
         logManager.writeExceptionEntry("Caught an IOException in send(): " + ioe.getMessage());
			return false;
		}   //end try	
      return true;
   }
   
   private String closeConnection(BufferedReader input, DataOutputStream output){
	   try{
		   output.writeBytes("\r\n.\r\n");
		   return input.readLine();
	   } catch(IOException ioe) {
			logManager.writeGeneralEntry("Caught an IO Exception in closeConnection(): " + ioe.getMessage());
	   }   //end try
	   return null;
   }
   
//	 ---------------------------------------------------------------------------
   /**
	 * Sends a message to the remote server and returns the server's respose.
	 * 
	 * @param line the message to send to the remote server
	 * @param output DataOutputStream used to send messages to the server
	 */
	private void sendLineData(String line, DataOutputStream output) {
		try {
			output.writeBytes(line);
		} catch(IOException ioe) {
			logManager.writeGeneralEntry("Caught an IO Exception in sendLineData(): " + ioe.getMessage());
		}   //end try
	}
   
	/**
	 * Sends a message to the remote server and returns the server's respose.
	 * 
	 * @param line the message to send to the remote server
	 * @param input BufferedReader used for reading server responses
	 * @param output DataOutputStream used to send messages to the server
	 * 
	 * @return the server's response message
	 */
	private String sendLine(String line, BufferedReader input, DataOutputStream output) {
		String response = "";
		try {
			output.writeBytes(line);
			response = input.readLine();
		} catch(IOException ioe) {
			logManager.writeGeneralEntry("Caught an IO Exception in sendLine(): " + ioe.getMessage());
		}   //end try
		
		return response;
	}
	
// ---------------------------------------------------------------------------
	
	private String buildReceivedMessage() {
		SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy HH:mm:ss Z");
		String received = "Received: by SJS with SMTP; " + formatter.format(new Date()) + "\n";
		return received;
	}
   
// ---------------------------------------------------------------------------
   
   /**
    * @param rcpt A rcpt that gets sent the message by this server
    */
   public void addRcpt(Address rcpt) {
      rcpts.add(rcpt);
   }
   
// ---------------------------------------------------------------------------
   
   /**
    * @param domain A domain that gets relayed to this server
    */
   public void addDomain(Domain domain) {
      domains.add(domain);
   }
   
// ---------------------------------------------------------------------------
   
   /**
    * @return The list of recipients that get sent the message by this server
    */
   public List<Address> getRcpts() {
      return rcpts;
   }
   
// ---------------------------------------------------------------------------
   
   /**
    * @return The list of domains that get relayed to this server
    */
   public List<Domain> getDomains() {
      return domains;
   }
   
// ---------------------------------------------------------------------------
   
   /**
    * @return The name of the remote server
    */
   public String getServer() {
      return server;
   }
   
   /**
    * @return The port used by the remote server
    */
   public int getPort() {
      return port;
   }

   /**
    * @param port The port used by the remote server
    */
   public void setPort(int port) {
      this.port = port;
   }
//~  ========================================================================
}
