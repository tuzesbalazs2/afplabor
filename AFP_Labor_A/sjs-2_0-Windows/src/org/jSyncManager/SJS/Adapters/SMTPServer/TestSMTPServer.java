// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Jeff CROWE <jcrowe@uvic.ca>, Michael Walts <mwalts@uvic.ca>
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
// $Id: AbstractSmtpAdapter.java,v 1.0 2007/10/03 20:00:00 jeffcrowe Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer;

import java.net.InetAddress;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//~ ===========================================================================
/** 
 * Realization of the AbstractSmtpAdapter Handles all of the commands from
 * the Smtp standard.
 *
 * @author Jeff CROWE &lt;jcrowe@uvic.ca&gt;
 * @author Michael Walts &lt;mwalts@uvic.ca&gt;
 * @version 1.0
 */
public class TestSMTPServer extends AbstractSMTPServer {
//~  ========================================================================

   /** Stores the name that the user has identified themselves as in HELO */
   protected String domain = null;

   /**
    * Stores a self reference, for use by the commands if they need to
    * access server methods
    */
   protected TestSMTPServer server = this;

//~  ========================================================================

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

// --------------------------------------------------------------------------
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

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    */
   @Override
   protected String processMail(String args) {
      if (args == null) {
         return INVALID_SYNTAX;
      }

      return "250 " + args + "....sender ok";
   }   // end processMail()

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param relays parameter value
    * @param addr parameter value
    *
    * @return returned
    */
   @Override
   protected String processRcpt(List<String> relays, String addr) {
      if (addr == null) {
         return INVALID_SYNTAX;
      }

      return "250 OK";
   }   // end processRcpt()

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processData() {
      return "354 Enter mail, end with \".\" on a line by itself.";
   }   // end processData()

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    */
   @Override
   protected void processMessagePart(String args) {
      //yep, do nothing, absolutely nothing.  This method
      //will recieve an entire message, one line at a time
   }   // end processMessagePart()

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processMessageFinished() {
      return "250 Message accepted for delivery";
   }   // end processMessageFinished()

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processRset() {
      // Command is ok.
      return "250 OK";
   }   // end processRset()

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @param args parameter value
    *
    * @return returned
    */
   @Override
   protected String processVrfy(String args) {
      //VRFY must take exactly one parameter which could be a username or an email address
      //Accepted email address should start with a letter
      //and in the format of "username@domain.com"
      //username can contain underscore(_), hyphen(-), or dot(.)
      //the second part of the domain name should not be longer than 5 letters
      if (args.contains("@")) {
         String regExp1 = "[a-zA-Z][\\w\\.-]*@\\w+\\.\\w{1,5}";
         Pattern pattern1 = Pattern.compile(regExp1);
         Matcher matcher1 = pattern1.matcher(args);

         if (! matcher1.find() || args.contains(" ")) {
            return INVALID_SYNTAX;
         }   // end if
      }   // end if
      else {
         String regExp2 = "[a-zA-Z][\\w\\.-]*";
         Pattern pattern2 = Pattern.compile(regExp2);
         Matcher matcher2 = pattern2.matcher(args);

         if (! matcher2.find() || args.contains(" ")) {
            return INVALID_SYNTAX;
         }   // end if
      }   // end else

      return PARAM_NOT_IMPLEMENTED;
   }   // end processVrfy()

// --------------------------------------------------------------------------
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

// --------------------------------------------------------------------------
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

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String processNoop() {
      return "250 OK";
   }   // end processNoop()

// --------------------------------------------------------------------------
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

// --------------------------------------------------------------------------
   /** 
    * Detects source routes,and strips them off to return just the
    * address Source routes are of the form &064;one,&064;two:joe@three.com
    * Will also return quoted strings directly without param checking
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


//~ ===========================================================================
}   // end TestSMTPServer
