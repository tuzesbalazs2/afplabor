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
// $Id: SMTPServer.java,v 1.0 2007/10/28 20:58:00 torbenwerner Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.c4j.ContractReference;

import org.jSyncManager.SJS.LogManager;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.ConfigDAO;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Configuration;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.DAOFactory;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;

//~  ========================================================================
/** 
 * This class extends the AbstractSMTPServer class by providing
 * methods to handle incoming message data
 *
 * @author Torben Werner
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.SMTPServerContract")
public class SMTPServer extends AbstractSMTPServer {
//~  ========================================================================

   /**
    * Stores a self reference, for use by the commands if they need to
    * access server methods
    */
   protected SMTPServer server = this;

   /** Configuration representing the settings in the property file for the SMTP server */
   private Configuration config = null;
   
   /** The message storage management object */
   private MessageStoreInterface ms = new Mbox();
   
   /** The current message being received from some client */
   private Message currentMsg = new Message();

   /** Stores the name that the user has identified themselves as in HELO */
   protected String domain = null;

//~  ========================================================================

  /**
    * Constructor, initializes config values, sets up mailbox
    */
   public SMTPServer() {
      super();
      init();
   }   // end SMTPServer()

//~  ========================================================================

   /** 
    * Load configuration values, setup mailbox
    */
   public void init() {
      DAOFactory factory = DAOFactory.instance(DAOFactory.DEFAULT);
      ConfigDAO configDAO = factory.getConfigDAO();
      
      // possible locations for smtp_server.xml
      String propertyfile = System.getProperty("sjs.smtpserver.propertyfile");
      File option1 = null;
      if (propertyfile != null) {
         option1 = new File(propertyfile);
      }
      File option2 = new File("../property_files/smtp_server.xml");
      File option3 = new File("~/.sjs/smtp_server.xml");
      File option4 = new File("./property_files/smtp_server.xml");

      // determine which location is correct, else throw exception
      String smtpServerXML = "";
      if (option1 != null && option1.exists()) {
         try {
			smtpServerXML = option1.getCanonicalPath();
         } catch (IOException e) {
            throw new RuntimeException("Cannot find smtp_server.xml!");
         }
      } else if (option2 != null && option2.exists()) {
    	  smtpServerXML = "../property_files/smtp_server.xml";
      } else if (option3 != null && option3.exists()) {
    	  smtpServerXML = "~/.sjs/smtp_server.xml";
      } else if (option4 != null && option4.exists()) {
    	  smtpServerXML = "./property_files/smtp_server.xml";
      } else {
          throw new RuntimeException("Cannot find smtp_server.xml!");
      }
      
      // load the xml
      config = configDAO.loadConfig(smtpServerXML);

      // on error, throw new exception
      if (config == null) {
         throw new RuntimeException("Error retrieving smtp_server.xml!");
      }   // end if
   }   // end init()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processData() {
      return "354 Enter mail, end with \".\" on a line by itself.";
   }   // end processData()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    *
    * @throws Exception exception
    */
   @Override
   protected String processEhlo(String args) throws Exception {
      return processHelo(args);
   }   // end processEhlo()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    */
   @Override
   protected String processExpn(String args) {
      //Noramlly multi param syntax check is done in super class...
      //however, when usernames are created a correct username should
      //return a "550 that is a username" not a mailing list
      //and valid usernames can contain spaces
      if (args.indexOf(' ') != -1) {
         return INVALID_SYNTAX;
      }   // end if
      else {
         return PARAM_NOT_IMPLEMENTED;
      }   // end else
   }   // end processExpn()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    *
    * @throws Exception exception
    */
   @Override
   protected String processHelo(String args) throws Exception {
      domain = args;

      String clientIP;
      String clientHost;
      InetAddress clientAddress = getInetAddress();

      // during normal use we should always have a client connected, and therefore have a client address.
      if (clientAddress != null) {
         clientIP = clientAddress.getHostAddress();
         clientHost = clientAddress.getCanonicalHostName();
      }   // end if
      else {
         // if we dont have a client, we must be running junit tests...
         clientIP = "127.0.0.2";
         clientHost = "test.local";
      }   // end else

      String serverHost = InetAddress.getLocalHost().getCanonicalHostName();

      return "250 " + serverHost + " Hello " + clientHost + " [" + clientIP +
      "], pleased to meet you";
   }   // end processHelo()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    */
   @Override
   protected String processHelp(String args) {
      return "214 OK";
   }   // end processHelp()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    */
   @Override
   protected String processMail(String args) {
	  currentMsg.setReceived(Calendar.getInstance().getTime());
      if (args == null) {
         return INVALID_SYNTAX;
      }
      currentMsg.setFrom(args); 
      return "250 " + args + "....sender ok";
   }   // end processMail()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processMessageFinished() {
      Iterator<Address> recipients = currentMsg.getToIter();
      RemoteMessageHandler rmh = new RemoteMessageHandler(getLogManager());
      while(recipients.hasNext()){
         Address recipient = recipients.next();
         if(recipient.isLocal()){
            try{
               ms.storeMessage(currentMsg, recipient);               
            }catch(IOException e){
               getLogManager().writeExceptionEntry("Could not store message for local recipient "+recipient);
            }
         }else{
            // Push this recipient and message to the remote delivery
        	rmh.store(currentMsg, recipient); 
         }
      }
      rmh.send(); // Flush the remote messages for delivery
      currentMsg = null;
      return "250 Message accepted for delivery";     
   }   // end processMessageFinished()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    */
   @Override
   protected void processMessagePart(String args) {
      currentMsg.appendData(args + "\n");
   }   // end processMessagePart()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processNoop() {
      return "250 OK";
   }   // end processNoop()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    *
    * @throws Exception exception
    */
   @Override
   protected String processQuit() throws Exception {
      server.disconnect();

      String serverHost = InetAddress.getLocalHost().getCanonicalHostName();

      return "221 " + serverHost + " closing connection";
   }   // end processQuit()

// ---------------------------------------------------------------------------
   /** 
    * Checking recipent's email address 
    *
    * @param relays parameter value
    * @param addr parameter value
    *
    * @return the correct message or the error message 
    */
   @Override
   protected String processRcpt(List<String> relays, String addr) {
      // make sure user & domain are valid
      // pass to Mbox
      String response = checkAddress(addr);
      if(response.equals("250 OK")){
         String[] address = addr.split("@");
         //Try and get the user from the local configuration: Make them otherwise
         User user = config.getUsers().get(address[0]);
		 if(user==null){
		    user = new User(address[0], null);
		 }
		 Domain dom = config.getDomains().get(address[1]);
		 // may not be a local domain, create one if remote
		 if(dom == null) {
			 dom = new Domain(address[1]);
		 }
		 currentMsg.addTo(new Address(user,dom));
      }
      return response;
   }   // end processRcpt()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processRset() {
      currentMsg = new Message();
      return "250 OK";
   }   // end processRset()

// ---------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    */
   @Override
   protected String processVrfy(String args) {
	String response = checkAddress(args);
	if(response.equals("250 OK")){
		return args;
	}else if(response.equals(INVALID_SYNTAX)){
		return "553 "+args.substring(Math.min(args.length(),5))+"... User address required";
	}  // end else if
	return response;
   }   // end processVrfy()

// ---------------------------------------------------------------------------
   /** 
    * Detects source routes,and strips them off to return just the
    * address Source routes are of the form &064;one,&064;two:joe@three.com
    * Will also return qouted strings directly without param checking
    *
    * @param address address that may contain a source route, of the form
    *        "address&gt;"  (&lt;address&gt; with the first &lt; taken off)
    *
    * @return the address that no longer contains a source route, null if the
    *         address is invalid
    */
   protected String getAddress(String address) {
      //auto pass qouted stings
      if (address.contains("\"")) {
         String postQoute;
         postQoute = address.substring(address.indexOf('"'));

         return postQoute.substring(1,
            (postQoute.indexOf("\"") < 0) ? postQoute.indexOf("\"")
                                          : postQoute.length());
      }   // end if

      String[] routeParts = address.split(",");

      //Go through all but the last string, and make sure they start with a @
      for (int i = 0; i < (routeParts.length - 1); i++) {
         //this is an invalid routePart
         if (! routeParts[i].startsWith("@")) {
            return null;
         }   // end if
      }   // end for

      //if there was (at least one) "," then it must have been a route, which means
      //that it should contain a ":" and the address i order to be valid
      if (routeParts.length > 1) {
         String[] finalRouteParts = routeParts[routeParts.length - 1].split(":");

         if (finalRouteParts.length == 1) {
            return null;
         }   // end if
         address = finalRouteParts[1];
      }   // end if

      //now we check for spaces, as valid addresses do not contain them
      if (address.contains(" ")) {
         return null;
      }   // end if

      return address.substring(0, address.length() - 1);
   }   // end getAddress()

// ---------------------------------------------------------------------------
   /** 
    * Gets a user's mbox file object.
    *
    * @param username parameter value
    *
    * @return file object if user exists or null otherwise
    */
   public File getMboxForUser(String username) {
      Map<String, User> users = config.getUsers();
      
      if (users.containsKey(username)) {
         String path = users.get(username).getPath();
         String name = users.get(username).getName();
         
         if (path != null) {
            return new File(path+name);
         }   // end if
      }   // end if

      return null;
   }   // end getMboxForUser()

// ---------------------------------------------------------------------------
   /** 
    * Gets a list of users.
    *
    * @return string array of usernames
    */
   public String[] getUsers() {
      Set<String> usernames = config.getUsers().keySet();

      return usernames.toArray(new String[usernames.size()]);
   }   // end getUsers()

// ---------------------------------------------------------------------------
   /** 
    * Gets a list of domains.
    *
    * @return string array of domains
    */
   public String[] getDomains() {
      Set<String> domains = config.getDomains().keySet();

      return domains.toArray(new String[domains.size()]);
   }   // end getDomains()

   /**
    * Checks validity of address.
    * 
    * @param the address to check as a String
    * @return true if valid, false otherwise
    */
    private String checkAddress(String addr){
      //VRFY must take exactly one parameter which could be a username or an email address
      //Accepted email address should start with a letter
      //and in the format of "username@domain.com"
      //username can contain underscore(_), hyphen(-), or dot(.)
      //the second part of the domain name should not be longer than 5 letters
      if (addr.contains("@")) {
         String regExp1 = "[a-zA-Z][\\w\\.-]*@\\w+\\.\\w{1,5}";
         Pattern pattern1 = Pattern.compile(regExp1);
         Matcher matcher1 = pattern1.matcher(addr);

         if (! matcher1.find() || addr.contains(" ")) {
            return INVALID_SYNTAX;
         }   // end if
      }   // end if
      else {
         String regExp2 = "[a-zA-Z][\\w\\.-]*";
         Pattern pattern2 = Pattern.compile(regExp2);
         Matcher matcher2 = pattern2.matcher(addr);

         if (! matcher2.find() || addr.contains(" ")) {
            return INVALID_SYNTAX;
         }   // end if
      }   // end else

    	// make sure user & domain are valid
      // pass to Mbox
      if (addr.charAt(0) == '@') {
         return INVALID_ADDR_SYNTAX;
      }   // end if

      String[] parts = addr.split("@");

      if (parts.length != 2) {   // too many @s
         return INVALID_ADDR_SYNTAX;
      }   // end if

      Map<String, User> users = config.getUsers();
      Map<String, Domain> domains = config.getDomains();
      
      //If the domain is local, but the user isn't found, then they are invalid
      if (!users.containsKey(parts[0]) && domains.containsKey(parts[1])) {
         return "550 Unexpected Address Error";
      }   // end if
      //good rcpt
      return "250 OK"; 
    }    // end checkAddress
   
// ~  ========================================================================
}   // end SMTPServer
