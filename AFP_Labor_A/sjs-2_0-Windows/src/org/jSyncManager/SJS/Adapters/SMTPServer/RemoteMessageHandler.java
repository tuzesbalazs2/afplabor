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

import java.util.Enumeration;
import java.util.Hashtable;

import net.sourceforge.c4j.ContractReference;

import org.jSyncManager.SJS.LogManager;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;

//~  ========================================================================
/**
 * This class is used to sort messages that must be relayed to a specific
 * remote server. It then stores these message in the appropriate RemoteServerMessage Object.
 * 
 * @author Crystal Gold &lt;cgold@uvic.ca&gt;
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.RemoteMessageHandlerContract")
public class RemoteMessageHandler {
	
	/**
	 * The hashtable in which all RemoteServerMessages are stored.
	 */
	private Hashtable<String, RemoteServerMessage> table = new Hashtable<String, RemoteServerMessage>();
	
	/**
	 * The LogManager currently in use
	 */
	LogManager logManager = null;

	/**
	 * Creates a new handler for remote messages.
	 * 
	 * @param logManager The log manager for logging remote handle errors.
	 */
	public RemoteMessageHandler(LogManager logManager) {
		this.logManager = logManager;
	}

	/**
	 * Stores incoming messages in a map with the server IP as key
	 * @param currentMsg the message to be stores
	 * @param recipient the address of the recipient
	 */
	public void store(Message currentMsg, Address recipient) {
		// Get the serverIP of the recipient
		Domain domain = recipient.getDomain();
		String serverIP = DNSUtil.MXLookup(domain.getDomain());
		
		// Check that the serverIP is not null 
		if (serverIP == null){
			// If it is, log the error
			logManager.writeExceptionEntry("Error: cannot find MX entry for " + domain.toString());
		} 
		// Otherwise, Determine if the serverIP is a preexisting key
		// If it is, store the message there
		else if (table.containsKey(serverIP)){
			RemoteServerMessage RSM = (RemoteServerMessage) table.get(serverIP);  // Get the list of messages
			if (! RSM.getDomains().contains(domain)){ // If the domain is not in the domain list - add it
				RSM.addDomain(domain);
			} // end-if
			if (! RSM.getRcpts().contains(recipient)){ // If the recipient is not in the recipient list - add it
				RSM.addRcpt(recipient);
			} // end-if
		}
		// If not, create a new key and store the message under that
		else {
			RemoteServerMessage RSM = new RemoteServerMessage(logManager, serverIP, currentMsg);
			RSM.addDomain(domain);
			RSM.addRcpt(recipient);
			table.put(serverIP, RSM);
		} // end-if
	} // end store()

	/**
	 * Sends all stored messages to their respective servers.
	 *
	 */
	public void send() {
		RemoteServerMessage RSM = null;
		// If there are entries in the table, tell the remote server message to send
		// If empty, do nothing
		if (!table.isEmpty()){

			Enumeration<RemoteServerMessage> e = table.elements();
			while (e.hasMoreElements()){
				RSM = (RemoteServerMessage) e.nextElement();

				boolean sent = RSM.send();
				// If sent = false, RSM.send failed.  Log error.
				if(!sent) {
				   logManager.writeExceptionEntry("Send remote messages to server IP" + RSM.getServer() + "failed");
				} else {
				   logManager.writeGeneralEntry("Send remote messages to server IP" + RSM.getServer() + "worked");
				}
			} // end-while
			table.clear();
		} // end-if
	} // end send()

}
