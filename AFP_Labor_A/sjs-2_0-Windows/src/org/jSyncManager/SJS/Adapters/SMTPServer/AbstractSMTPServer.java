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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.c4j.ContractReference;

import org.jSyncManager.SJS.API.AbstractRequestResponseAdapter;


//~ ===========================================================================
/** 
 * This is an abstract adapter to be used for handling smtp requests. This
 * class will take case of parsing received command and ensuring that any
 * state requirements for the entered commands are met before forwarding the
 * command for action to the subclass implementation.
 *
 * @author Jeff CROWE &lt;jcrowe@uvic.ca&gt;
 * @author Michael Walts &lt;mwalts@uvic.ca&gt;
 * @version 1.0
 */

@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.AbstractSMTPServerContract")
public abstract class AbstractSMTPServer extends AbstractRequestResponseAdapter {
//~  ========================================================================

   /** String returned for invalid syntax */
   protected static final String INVALID_SYNTAX = "501 invalid syntax";

   /** String returned for invalid e-mail address syntax */
   protected static final String INVALID_ADDR_SYNTAX = "501 invalid address syntax";

   /** String returned for a command run out of sequence */
   protected static final String INVALID_SEQUENCE = "503 Command out of sequence";

   /** String returned when a parameter is not implemented */
   protected static final String PARAM_NOT_IMPLEMENTED = "502 Command parameter not implemented";

   /**
    * A string to provide a regex denoting the basic valid e-mail
    * characters.
    */
   protected static final String validEmailChars = "[\\w|+|!|#|$|%|*|/|\\?|\\||\\^|\\{|\\}|'|\\~|\\&|`|\\+|\\-|\\=|_]";

   /**
    * A string to provide the basic regex for a valid domain name.
    */
   protected static final String domainRegex = "(\\w+(?:\\.[a-zA-Z]+)*|\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\]|\\[IPv6(?:\\:[0-9a-fA-F]{0,4}){1,8}\\])";

   /**
    * A string to provide the basic regex for a valid domain name
    * preceeded by an '@'.
    */
   protected static final String atDomainRegex = "@" + domainRegex;

   /**
    * A pattern object for easily matching data within RCPT TO:
    * statements.
    */
   protected static final Pattern rcptAddrExpr = Pattern.compile("<((?:" +
         atDomainRegex + ",)*)((?:\"(?:.*)\"|(?:" + validEmailChars + "+|" +
         validEmailChars + "+(?:\\." + validEmailChars + "+)*))" +
         atDomainRegex + ")?>");

   /**
    * A pattern object for easily matching data within MAIL FROM:
    * statements.
    */
   protected static final Pattern mailAddrExpr = Pattern.compile(
         "<((\"(?:.*)\"|(?:" + validEmailChars + "+|" + validEmailChars +
         "+(?:\\." + validEmailChars + "+)*))" + atDomainRegex + ")?>");

   /**
    * A pattern object for easily matching relays.
    */
   protected static final Pattern relayPattern = Pattern.compile(atDomainRegex);

//~  ========================================================================

   /** stores all the command strings mapped to the handlers for those string */
   protected HashMap<String, SmtpCommand> commands = new HashMap<String, SmtpCommand>();

   /**
    * A bit mask field to store the current state of the connection
    * being handled by this instance
    */
   protected State state = new State();

   /**
    * bit used in construction of SmtpCommands, currently supports
    * maximum 64 commands
    */
   protected long currentMask = 1;

//~  ========================================================================

/**
    * AbstractSMTPServer constructor, initializes the commands
    */
   public AbstractSMTPServer() {
      initCommands();
   }   // end AbstractSMTPServer()

//~  ========================================================================

   /** 
    * Registers all of the available commands into the given map.
    * through the SmtpCommand constructor
    */
   protected void initCommands() {
      new CommandHelo();

      //HELO must come before EHLO
      new CommandEhlo();
      new CommandMail();
      new CommandRcpt();
      new CommandData();
      new CommandQuit();
      new CommandNoop();
      new CommandHelp();
      new CommandRset();
      new CommandVrfy();
      new CommandExpn();
   }   // end initCommands()

// --------------------------------------------------------------------------
   /** 
    * Implementation of the processRequest method from
    * AbstractResponseAdapter. This method will get which command handler
    * should be used from the commands map (filled by registerCommands), and
    * delegates the processing of the command to that handler.
    *
    * @param request parameter value
    *
    * @return returned
    *
    * @throws Exception exception
    */
   protected String processRequest(String request) throws Exception {
      if (request == null) {
         return "500 Command unrecognized";
      }   // end if

      // Log request
      getLogManager().writeGeneralEntry(request);

      //check for data mode, if so, use DATA command and don't mess with the args
      SmtpCommand dataCmd = commands.get("DATA");

      if (dataCmd != null) {
         if (state.areBitsSet(dataCmd.mask)) {
            return dataCmd.process(request);
         }   // end if
      }   // end if

      //Not data mode, run command
      request = stripBackspace(request.trim());

      int commandEndIdx = request.indexOf(' ');

      if (commandEndIdx == -1) {
         commandEndIdx = request.length();
      }   // end if

      String command = request.substring(0, commandEndIdx).toUpperCase();
      String args = request.substring(commandEndIdx);

      SmtpCommand smtpCommand = commands.get(command);

      if (smtpCommand != null) {
         String result = smtpCommand.process(args.trim());

         //log result
         getLogManager().writeGeneralEntry(result);


         return result;
      }   // end if

      //log error
      String commandError = "500 Command unrecognized \"" + request + "\"";

      if (getLogManager() != null) {
         getLogManager().writeGeneralEntry(commandError);
      }   // end if

      return commandError;
   }   // end processRequest()

// --------------------------------------------------------------------------
   /** 
    * Interprets the backspace characters in args
    *
    * @param args a string that may contain backspace characters
    *
    * @return a string that does not contain backspace characters or characters
    *         that should have been deleted by a backspace
    */
   private String stripBackspace(String args) {
      char[] oldString = args.toCharArray();
      char[] ourString = new char[args.length()];

      int ourIndex = 0;

      for (int i = 0; i < args.length(); i++) {
         if (oldString[i] != '\b') {
            ourString[ourIndex] = oldString[i];
            ourIndex++;
         }   // end if
         else {
            ourIndex--;
            ourIndex = (ourIndex < 0) ? 0 : ourIndex;
         }   // end else
      }   // end for

      return new String(ourString, 0, ourIndex);
   }   // end stripBackspace()

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct HELO command
    *
    * @param args parameter value
    *
    * @return the appropriate response to this command
    *
    * @throws Exception exception
    */
   protected abstract String processHelo(String args) throws Exception;

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct EHLO command
    *
    * @param args parameter value
    *
    * @return the appropriate response to this command
    *
    * @throws Exception exception
    */
   protected abstract String processEhlo(String args) throws Exception;

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct MAIL command
    *
    * @param args The parameters passed to this command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processMail(String args);

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct RCPT command.
    *
    * @param relays a list of relays.
    * @param addr The parameters passed to this command.
    *
    * @return the appropriate response to this command.
    */
   protected abstract String processRcpt(List<String> relays, String addr);

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct DATA command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processData();

// --------------------------------------------------------------------------
   /** 
    * Processes a string received while in the DATA state
    *
    * @param args The current part of the e-mail message
    */
   protected abstract void processMessagePart(String args);

// --------------------------------------------------------------------------
   /** 
    * run at the end of a message, after "." appears on a line by itself
    *
    * @return the appropriate response to this command
    */
   protected abstract String processMessageFinished();

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct RSET command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processRset();

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct VRFY command
    *
    * @param args The parameters passed to this command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processVrfy(String args);

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct EXPN command
    *
    * @param args The parameters passed to this command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processExpn(String args);

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct HELP command
    *
    * @param args The parameters passed to this command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processHelp(String args);

// --------------------------------------------------------------------------
   /** 
    * Processes a syntactically correct QUIT command
    *
    * @return the appropriate response to this command
    *
    * @throws Exception exception
    */
   protected abstract String processQuit() throws Exception;

// --------------------------------------------------------------------------
   /** 
    * Processes NOOP command
    *
    * @return the appropriate response to this command
    */
   protected abstract String processNoop();

// --------------------------------------------------------------------------
   /** 
    * General summary
    *
    * @return returned
    */
   @Override
   protected String getConnectionHeaderString() {
      try {
         String serverHost = InetAddress.getLocalHost().getCanonicalHostName();

         return "220 " + serverHost + " SMTP SMTPServer";
      }   // end try
      catch (UnknownHostException e) {
         //TODO log this
         e.printStackTrace();

         return "220 dont.know.who.i.am SMTP SMTPServer";
      }   // end catch
   }   // end getConnectionHeaderString()

   
   public static Pattern getRcptAddrExpr()
   {
   	return rcptAddrExpr;
   }

   public static Pattern getMailAddrExpr()
   {
   	return mailAddrExpr;
   }

   public HashMap<String, SmtpCommand> getCommands()
   {
   	return commands;
   }

   public long getStateLong()
   {
   	return this.state.getState();
   }

   public long getCurrentMask()
   {
   	return currentMask;
   }
   
//~ ===========================================================================
   /** 
    * Handles the DATA command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandDataContract")
   public class CommandData extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandData() {
         super("DATA");
      }   // end CommandData()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         //see if the data state is already set
         if (isStateSet("DATA")) {
            return processNextData(args);
         }   // end if
         else {
            if (isStateSet("MAIL", "RCPT")) {
               //make sure there are no arguments
               if (! args.equals("")) {
                  return INVALID_SYNTAX;
               }   // end if

               applyState();

               return processData();

            }   // end if
            else {
               return INVALID_SEQUENCE;
            }   // end else
         }   // end else
      }   // end process()

      /** 
       * General summary
       *
       * @param data parameter value
       *
       * @return returned
       */
      private String processNextData(String data) {
         if (data.equals(".")) {
            long mask;
            mask = commands.get("HELO").mask | commands.get("EHLO").mask;
            state.clearBits(~ mask);

            return processMessageFinished();

         }   // end if
         processMessagePart(data);

         return null;
      }   // end processNextData()

//~ ===========================================================================
   }   // end CommandData

//~ ===========================================================================
   /** 
    * Handles the EHLO command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandEhloContract")
   public class CommandEhlo extends CommandHelo {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandEhlo() {
         super("EHLO");
         mask |= commands.get("HELO").mask;   // sets both
      }   // end CommandEhlo()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         if ((args == null) || (args.length() == 0)) {
            return "501 " + cmd + " requires domain";
         }   // end if
         else if (args.indexOf(' ') >= 0) {
            // only accept one argument
            return INVALID_SYNTAX;
         }   // end else if

         // the HELO command clears any states (such as to), so the only state set bit set will be HELO
         state.setState(mask);

         return processEhlo(args);
      }   // end process()
   }   // end CommandEhlo

//~ ===========================================================================
   /** 
    * Handles the EXPN command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandExpnContract")
   public class CommandExpn extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandExpn() {
         super("EXPN");
      }   // end CommandExpn()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         //EXPN command must take exactly one parameter
         //EXPN does not take more than one parameters
         if (! args.equals("")) {
            return processExpn(args);
         }   // end if
         else {
            return INVALID_SYNTAX;
         }   // end else
      }   // end process()

//~ ===========================================================================
   }   // end CommandExpn

//~ ===========================================================================
   /** 
    * Handles the HELO command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandHeloContract")
   public class CommandHelo extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandHelo() {
         super("HELO");
      }   // end CommandHelo()

// -------------------------------------------------------------------------- 
      /** 
       * General summary
       *
       * @param cmd parameter value
       */
      protected CommandHelo(String cmd) {
         super(cmd);
      }   // end CommandHelo()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         if ((args == null) || (args.length() == 0)) {
            return "501 " + cmd + " requires domain";
         }   // end if
         else if (args.indexOf(' ') >= 0) {
            // only accept one argument
            return INVALID_SYNTAX;
         }   // end else if

         // the HELO command clears any states (such as to), so the only state set bit set will be HELO
         state.setState(mask);

         return processHelo(args);
      }   // end process()
   }   // end CommandHelo

//~ ===========================================================================
   /** 
    * Handles the HELP command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandHelpContract")
   public class CommandHelp extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandHelp() {
         super("HELP");
      }   // end CommandHelp()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         String data = args;

         //check for one parameter, and if it is valid
         if ((data.length() != 0)) {
            if (! commands.containsKey(data.toUpperCase()) &&
                  (data.indexOf(' ') == -1)) {
               //There is one parameter, but we don't recognize it
               return PARAM_NOT_IMPLEMENTED;
            }   // end if
            else if (data.indexOf(' ') != -1) {
               //There was more then one parameter, bad syntax
               return INVALID_SYNTAX;
            }   // end else if
         }   // end if

         // Command is ok.
         return processHelp(data);
      }   // end process()

//~ ===========================================================================
   }   // end CommandHelp

//~ ===========================================================================
   /** 
    * Handles the MAIL command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandMailContract")
   public class CommandMail extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandMail() {
         super("MAIL");
      }   // end CommandMail()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         if (! args.toUpperCase().startsWith("FROM:")) {
            return INVALID_SYNTAX;
         }   // end if

         // If we're not in the HELO state, or we're in the MAIL state, 
         if ((! isStateSet("HELO")) || (isStateSet("MAIL"))) {
            return INVALID_SEQUENCE;
         }   // end if

         // Parse out the address.
         Matcher m = mailAddrExpr.matcher(args);

         // If we can't find the pattern, the syntax is invalid.
         if (! m.find()) {
            return INVALID_SYNTAX;
         }   // end if

         // We get here if we've found a match.  Grab the first capturing group.
         String parsedArgs = m.group(1);

         //System.err.println("MAIL: Got match: \""+parsedArgs+"\".");
         String ret = processMail(parsedArgs);

         //apply the MAIL state, if the delegate doesn't return an error.
         if ((ret.charAt(0) != '5') && (ret.charAt(0) != '4')) {
            state.insertBits(commands.get("MAIL").mask);
         }   // end if

         // Return the MAIL command
         return ret;

      }   // end process()
   }   // end CommandMail

//~ ===========================================================================
   /** 
    * Handles the NOOP command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandNoopContract")
   public class CommandNoop extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandNoop() {
         super("NOOP");
      }   // end CommandNoop()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         return processNoop();
      }   // end process()
   }   // end CommandNoop

//~ ===========================================================================
   /** 
    * Handles the QUIT command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandQuitContract")
   public class CommandQuit extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandQuit() {
         super("QUIT");
      }   // end CommandQuit()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         if (args.length() > 0) {
            return INVALID_SYNTAX;
         }   // end if

         // as documented in the disconnect method, this wont disconnect immediately, so our
         // reply to the quit message will be sent back
         // setting state so that testing team can reset the HELO command
         // using commands
         state.setState(0);

         return processQuit();
      }   // end process()
   }   // end CommandQuit

//~ ===========================================================================
   /** 
    * Handles the RCPT command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandRcptContract")
   public class CommandRcpt extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandRcpt() {
         super("RCPT");
      }   // end CommandRcpt()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         if (! args.toUpperCase().startsWith("TO:")) {
            return INVALID_SYNTAX;
         }   // end if

         if (! isStateSet("HELO", "MAIL")) {
            return INVALID_SEQUENCE;
         }   // end if

         // Parse out the address and any @-domain entries.
         Matcher m = rcptAddrExpr.matcher(args);

         // If we can't find the pattern, the syntax is invalid.
         if (! m.find()) {
            return INVALID_ADDR_SYNTAX;
         }   // end if

         // We get here if we've found a match.  Grab the first capturing group.
         String parsedArgs = m.group(3);


         //System.err.println("RCPT: Got match: \""+parsedArgs+"\".");
         //if (m.group(1)!=null && !m.group(1).equals("")) System.err.println("RCPT: Got relays: \""+m.group(1)+"\".");

         // If we got relays, parse them out and add them to a list we can pass.
         ArrayList<String> relays = null;

         if ((m.group(1) != null) && ! m.group(1).equals("")) {
            Matcher m2 = relayPattern.matcher(m.group(1));
            relays = new ArrayList<String>();

            // While we keep finding @domain in the list, parse out the domains.
            while (m2.find()) {
               relays.add(m.group(1));
            }   // end while
         }   // end if

         String ret = processRcpt(relays, parsedArgs);

         //apply the RCPT state, if the delegate didn't return an error code.
         if ((ret.charAt(0) != '5') && (ret.charAt(0) != '4')) {
            state.insertBits(commands.get("RCPT").mask);
         }   // end if

         return ret;
      }   // end process()
   }   // end CommandRcpt

//~ ===========================================================================
   /** 
    * Handles the RSET command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandRsetContract")
   public class CommandRset extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandRset() {
         super("RSET");
      }   // end CommandRset()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         //make sure nothing was entered for arguments
         if (! args.equals("")) {
            return INVALID_SYNTAX;
         }   // end if

         long mask = commands.get("HELO").mask | commands.get("EHLO").mask;
         state.clearBits(~ mask);

         return processRset();
      }   // end process()
   }   // end CommandRset

//~ ===========================================================================
   /** 
    * Handles the VRFY command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.CommandVrfyContract")
   public class CommandVrfy extends SmtpCommand {
//~  =====================================================================

      /** 
       * General summary
       */
      protected CommandVrfy() {
         super("VRFY");
      }   // end CommandVrfy()

//~  =====================================================================

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
      public String process(String args) throws Exception {
         if (args.length() == 0) {
            return INVALID_SYNTAX;
         }   // end if

         return processVrfy(args);
      }   // end process()

//~ ===========================================================================
   }   // end CommandVrfy

//~ ===========================================================================
   /** 
    * Handles the response of the system to a single command
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.SMTPCommandContract")
   public abstract class SmtpCommand {
 //~  =====================================================================

      /** The string command value */
      protected String cmd;

      /** Single bit which represents this command */
      protected long mask;

//~  =====================================================================

/** constructs a new command with the given command string.
        * This uses the currentMask field to create a unique bit mask for this field
        * The command also self registers in the commands map of the owning class
        * @param cmd command string which uniquely identifies this command
        */
      protected SmtpCommand(String cmd) {
         this.cmd = cmd;
         this.mask = currentMask;
         currentMask <<= 1;
         commands.put(cmd, this);
      }   // end SmtpCommand()

//~  =====================================================================

      /** 
       * Applies the state mask for this command to the state
       */
      protected void applyState() {
         state.insertBits(mask);
      }   // end applyState()

// --------------------------------------------------------------------------
      /** 
       * Checks whether all state bits representing the given
       * commands are set in the given state
       *
       * @return If the state bits are set
       */
      public boolean isStateSet(String... command) {
         for (String cmd : command) {
            long mask = commands.get(cmd).mask;

            if (! state.areBitsSet(mask)) {
               return false;
            }   // end if
         }   // end for

         return true;
      }   // end isStateSet()

// --------------------------------------------------------------------------
      /** 
       * Processes the given request with this command handler
       * implementation
       *
       * @param args
       *
       * @return The result string
       *
       * @throws Exception
       */
      public abstract String process(String args) throws Exception;

	public String getCmd()
	{
		return cmd;
	}

	public long getMask()
	{
		return mask;
	}

//===========================================================================
   }   // end SmtpCommand

//===========================================================================
   /** 
    * Stores a mutable long for the masks which represent the state
    * information
    */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.contracts.StateContract")
   public static class State {
      //~  =====================================================================

      /** the actual state mask of bits */
      long state;

      //~  =====================================================================

//===========================================================================
/** creates a state with no bits set
        */
      public State() {
         this(0);
      }   // end State()

// --------------------------------------------------------------------------
/** creates a state with some bits initially set
        * @param s the initial state value 
        */
      public State(long s) {
         this.state = s;
      }   // end State()

//~  =====================================================================

      /** 
       * Checks whether the given bits are currently set in the state
       *
       * @param mask long value of bits to check for
       *
       * @return true if all bits in the given mask are set, false otherwise
       */
      public boolean areBitsSet(long mask) {
         return (state & mask) == mask;
      }   // end areBitsSet()

// --------------------------------------------------------------------------
      /** 
       * Sets the state to exactly the given value, any current bits
       * are cleared
       *
       * @param s the new state value
       */
      public void setState(long s) {
         this.state = s;
      }   // end setState()

// --------------------------------------------------------------------------
      /** 
       * get the current state value
       *
       * @return the current state
       */
      public long getState() {
         return state;
      }   // end getState()

// --------------------------------------------------------------------------
      /** 
       * inserts the given bits into the current state
       *
       * @param mask
       */
      public void insertBits(long mask) {
         this.state |= mask;
      }   // end insertBits()

// --------------------------------------------------------------------------
      /** 
       * Removes the bits specified in the mask from the state
       *
       * @param mask
       */
      public void clearBits(long mask) {
         this.state &= ~ mask;
      }   // end clearBits()
   }   // end State

//~ ===========================================================================
}   // end AbstractSMTPServer
