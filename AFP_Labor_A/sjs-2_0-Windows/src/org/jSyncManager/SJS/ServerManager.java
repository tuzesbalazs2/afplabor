// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2004 Brad BARCLAY <bbarclay@jsyncmanager.org>
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
// $Id: ServerManager.java,v 1.1 2004/12/05 04:31:54 yaztromo Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS;

import java.net.*;

import java.rmi.*;

import java.util.StringTokenizer;


//~ ===========================================================================
/** 
 * The Internet Server Service Management Tool. This class serves as the
 * entry point to the ISS Management Tool. It uses RMI to connect to the ISS
 * Server instance running on the local host.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public class ServerManager {
//~  ========================================================================

   /** A string constant representing all bind addresses for IPv4. */
   public static final String ALL_IPv4_BOUND_ADDRESSES = "0.0.0.0";

   /** A string constant containing the service name for RMI binding. */
   private static final String rmiBindingName = "//localhost/Server";

//~  ========================================================================
   /** This class cannot be instantiated.
    */
   private ServerManager() {
      // noop
   }   // end ServerManager()

//~  ========================================================================

   /** 
    * The main entrypoint for the Internet Service Server Manager.
    *
    * @param args the command-line arguments passed to the program. <P>
    */
   public static void main(String[] args) {
      System.out.println("jSyncManager.org Simple Java Server v1.0.");
      System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

      if ((args == null) || (args.length == 0) ||
            ! isKnownVerb(args[0].toUpperCase())) {
         System.out.println("Syntax:\n");
         System.out.println(
            "  CREATE <ip address>:<port> name=\"<service name>\" adapter=\"<full path>\"");
         System.out.println(
            "  SET [<ip address>:<port> | name=\"<service name>\"] <parameter name>=\"<parameter value>\"");
         System.out.println(
            "  DESCRIBE [<ip address>:<port> | name=\"<service name>\"]");
         System.out.println(
            "  DESCRIBE [<ip address>:<port> | name=\"<service name>\"] <parameter name>");
         System.out.println(
            "  REMOVE [<ip address>:<port> | name=\"<service name>\"]");
         System.out.println(
            "  REMOVE [<ip address>:<port> | name=\"<service name>\"] <parameter name>");
         System.out.println(
            "  DENY INCOMING REQUESTS [<ip address>:<port> | name=\"<service name>\"]");
         System.out.println(
            "  ACCEPT INCOMING REQUESTS [<ip address>:<port> | name=\"<service name>\"]");
         System.out.println("  STATUS [<ip address>:<port> | name=\"name\"]");
         System.out.println("  START SERVER [server config file]");
         System.out.println("  SHUTDOWN [NOW]");
         System.exit(1);
      }   // end if

      //if (System.getSecurityManager() == null) System.setSecurityManager(new RMISecurityManager());
      String firstArg = args[0].toUpperCase();

      // If the command is to start the server, do so now to skip the rest.
      if (firstArg.equals("START")) {
         processVerbStart(args);

         // The server itself should issue the exit command, but just in case...
         return;
      }   // end if

      ServerRemoteInterface serv = null;

      try {
         serv = (ServerRemoteInterface) Naming.lookup(rmiBindingName);
      }   // end try
      catch (Exception e) {
         //LOGME
         System.err.println("Server Manager Exception: " + e.toString());
         e.printStackTrace();
         System.exit(2);
      }   // end catch

      try {
         // Call the desired method based on the first parsed word.
         if (firstArg.equals("CREATE")) {
            processVerbCreate(args, serv);
         }   // end if

         if (firstArg.equals("SET")) {
            processVerbSet(args, serv);
         }   // end if

         if (firstArg.equals("DESCRIBE")) {
            processVerbDescribe(args, serv);
         }   // end if

         if (firstArg.equals("REMOVE")) {
            processVerbRemove(args, serv);
         }   // end if

         if (firstArg.equals("DENY")) {
            processVerbDeny(args, serv);
         }   // end if

         if (firstArg.equals("ACCEPT")) {
            processVerbAccept(args, serv);
         }   // end if

         if (firstArg.equals("STATUS")) {
            processVerbStatus(args, serv);
         }   // end if

         if (firstArg.equals("SHUTDOWN")) {
            processVerbShutdown(args, serv);
         }   // end if
      }   // end try
      catch (RemoteException re) {
         System.out.println(
            "\nAn RMI exception occurred while talking to the server.");
         System.err.println(re.getMessage());
         System.exit(3);
      }   // end catch

      System.exit(0);
   }   // end main()

//~ ===========================================================================
   /** 
    * Processes the CREATE command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbCreate(String[] args,
      ServerRemoteInterface serv) throws RemoteException {
      try {
         // Parse the ip:port
         SocketAddress addr = parseSocketAddress(args[1]);

         // Parse out the name
         String name = parsePropertyString(args[2], "name");

         // Now parse out the adapter
         String adapter = parsePropertyString(args[3], "adapter");

         // Whew -- everything was parsed.
         serv.createService(name, addr, adapter);
      }   // end try
      catch (ParseException e) {
         System.out.println(
            "We were unable to process your request due to a parsing error:");
         System.out.println(" > " + e.getMessage());
         System.out.println("Syntax:");
         System.out.println(
            "  CREATE <ip address>:<port> name=\"<service name>\" adapter=\"<full path>\"");

         return;
      }   // end catch
      catch (ClassNotFoundException cnfe) {
         System.out.println(
            "The specified adapter could not be loaded.  The service has not been created.");

         return;
      }   // end catch
   }   // end processVerbCreate()

// ---------------------------------------------------------------------------
   /** 
    * Processes the SET command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbSet(String[] args, ServerRemoteInterface serv)
      throws RemoteException {
      try {
         if (args.length < 3) {
            // Sets the parameter for all connection managers.
            String name = parsePropertyName(args[1]);
            String value = parsePropertyString(args[1], name);
            serv.setParameterInAllServices(name, value);
         }   // end if
         else {
            // There are more parameters.  Figure out what they mean.
            if (args[1].startsWith("name=")) {
               // Parse out the name
               String service = parsePropertyString(args[1], "name");
               String name = parsePropertyName(args[2]);
               String value = parsePropertyString(args[2], name);
               serv.setServiceParameter(service, name, value);
            }   // end if
            else {
               // This is a request by ip:port
               SocketAddress addr = parseSocketAddress(args[1]);
               String name = parsePropertyName(args[2]);
               String value = parsePropertyString(args[2], name);
               serv.setServiceParameter(addr, name, value);
            }   // end else
         }   // end else
      }   // end try
      catch (ParseException e) {
         // Oops!  The port token isn't a valid integer numeric...
         System.out.println(
            "We were unable to process your request due to a parsing error:");
         System.out.println(" > " + e.getMessage());
         System.out.println("Syntax:\n");
         System.out.println(
            "  SET [<ip address>:<port> | name=\"<service name>\"] <parameter name>=\"<parameter value>\"");

         return;
      }   // end catch
      catch (ConnectionManagerNotFoundException e1) {
         // The connection manager for the specified address:port wasn't found.
         System.out.println("We were unable to find the specified service.");

         return;
      }   // end catch
   }   // end processVerbSet()

// ---------------------------------------------------------------------------
   /** 
    * Processes the DESCRIBE command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbDescribe(String[] args,
      ServerRemoteInterface serv) throws RemoteException {
      if (args.length < 2) {
         // Display the status for all connection managers.
         System.out.println(serv.describeAllServices());
      }   // end if
      else {
         // There are more parameters.  Figure out what they mean.
         try {
            if (args[1].startsWith("name=")) {
               // This is a request by service name
               String name = parsePropertyString(args[1], "name");

               if (args.length < 3) {
                  System.out.println(serv.describeService(name));
               }   // end if 
               else {
                  System.out.println(args[2] + " = " +
                     serv.describeServiceParameter(name, args[2]));
               }   // end else
            }   // end if
            else if (args[1].indexOf(':') != -1) {
               // This is a request by ip:port
               SocketAddress addr = parseSocketAddress(args[1]);

               if (args.length < 3) {
                  System.out.println(serv.describeService(addr));
               }   // end if 
               else {
                  System.out.println(args[2] + " = " +
                     serv.describeServiceParameter(addr, args[2]));
               }   // end else
            }   // end else if
            else {
               // This is a request by parameter
               System.out.println(serv.describeAllServicesWithParameter(args[1]));
            }   // end else
         }   // end try
         catch (ParseException e) {
            // Oops!  The port token isn't a valid integer numeric...
            System.out.println(
               "We were unable to process your request due to a parsing error:");
            System.out.println(" > " + e.getMessage());
            System.out.println("Syntax:\n");
            System.out.println(
               "  DESCRIBE [<ip address>:<port> | name=\"<service name>\"] [<parameter name>]");

            return;
         }   // end catch
         catch (ConnectionManagerNotFoundException e1) {
            // The connection manager for the specified address:port wasn't found.
            System.out.println("We were unable to find the specified service.");

            return;
         }   // end catch
      }   // end else
   }   // end processVerbDescribe()

// ---------------------------------------------------------------------------
   /** 
    * Processes the REMOVE command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbRemove(String[] args,
      ServerRemoteInterface serv) throws RemoteException {
      if (args.length < 2) {
         // Remove all services.
         serv.removeAllServices();
      }   // end if
      else {
         // There are more parameters.  Figure out what they mean.
         try {
            if (args[1].startsWith("name=")) {
               // This is a request by service name
               String name = parsePropertyString(args[1], "name");

               if (args.length < 3) {
                  serv.removeService(name);
               }   // end if 
               else {
                  serv.removeServiceParameter(name, args[2]);
               }   // end else
            }   // end if
            else if (args[1].indexOf(':') != -1) {
               // This is a request by ip:port
               SocketAddress addr = parseSocketAddress(args[1]);

               if (args.length < 3) {
                  serv.removeService(addr);
               }   // end if 
               else {
                  serv.removeServiceParameter(addr, args[2]);
               }   // end else
            }   // end else if
            else {
               // This is a request by parameter
               System.out.println("Removing param \"" + args[1] +
                  "\" in all services.");
               serv.removeParameterInAllServices(args[1]);
            }   // end else
         }   // end try
         catch (ParseException e) {
            // Oops!  The port token isn't a valid integer numeric...
            System.out.println(
               "We were unable to process your request due to a parsing error:");
            System.out.println(" > " + e.getMessage());
            System.out.println("Syntax:\n");
            System.out.println(
               "  DESCRIBE [<ip address>:<port> | name=\"<service name>\"] [<parameter name>]");

            return;
         }   // end catch
         catch (ConnectionManagerNotFoundException e1) {
            // The connection manager for the specified address:port wasn't found.
            System.out.println("We were unable to find the specified service.");

            return;
         }   // end catch
      }   // end else
   }   // end processVerbRemove()

// ---------------------------------------------------------------------------
   /** 
    * Processes the DENY command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbDeny(String[] args, ServerRemoteInterface serv)
      throws RemoteException {
      // DENY INCOMING REQUESTS [<ip address>:<port> | name="<service name>"]
      try {
         if (! args[1].toUpperCase().equals("INCOMING") &&
               ! args[2].toUpperCase().equals("REQUESTS")) {
            // If the arguments are wrong, it's the same as if they weren't present...
            throw new ArrayIndexOutOfBoundsException();
         }   // end if
      }   // end try
      catch (ArrayIndexOutOfBoundsException e) {
         System.out.println(
            "We were unable to process your request due to a parsing error:");
         System.out.println(" > " + e.getMessage());
         System.out.println("Syntax:\n");
         System.out.println(
            "  DENY INCOMING REQUESTS [<ip address>:<port> | name=\"<service name>\"]");

         return;
      }   // end catch

      if (args.length < 4) {
         // Deny connections for all connection managers.
         serv.denyAllIncomingRequests();
         System.out.println("Denying incoming requests from all services.");
      }   // end if
      else {
         // There are more parameters.  Figure out what they mean.
         try {
            if (args[3].startsWith("name=")) {
               // This is a request by service name
               String name = parsePropertyString(args[3], "name");
               serv.denyIncomingRequests(name);
               System.out.println("Denying incoming requests from service \"" +
                  name + "\".");
            }   // end if
            else {
               // This is a request by ip:port
               SocketAddress addr = parseSocketAddress(args[3]);
               serv.denyIncomingRequests(addr);
               System.out.println(
                  "Denying incoming requests from address:port \"" +
                  addr.toString() + "\".");
            }   // end else
         }   // end try
         catch (ParseException e) {
            // Oops!
            System.out.println(
               "We were unable to process your request due to a parsing error:");
            System.out.println(" > " + e.getMessage());
            System.out.println("Syntax:\n");
            System.out.println(
               "  DENY INCOMING REQUESTS [<ip address>:<port> | name=\"<service name>\"]");

            return;
         }   // end catch
         catch (ConnectionManagerNotFoundException e1) {
            // The connection manager for the specified address:port wasn't found.
            System.out.println("We were unable to find the specified service.");

            return;
         }   // end catch
      }   // end else
   }   // end processVerbDeny()

// ---------------------------------------------------------------------------
   /** 
    * Processes the ACCEPT command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbAccept(String[] args,
      ServerRemoteInterface serv) throws RemoteException {
      try {
         if (! args[1].toUpperCase().equals("INCOMING") &&
               ! args[2].toUpperCase().equals("REQUESTS")) {
            // If the arguments are wrong, it's the same as if they weren't present...
            throw new ArrayIndexOutOfBoundsException();
         }   // end if
      }   // end try
      catch (ArrayIndexOutOfBoundsException e) {
         System.out.println(
            "We were unable to process your request due to a parsing error:");
         System.out.println(" > " + e.getMessage());
         System.out.println("Syntax:\n");
         System.out.println(
            "  ACCEPT INCOMING REQUESTS [<ip address>:<port> | name=\"<service name>\"]");

         return;
      }   // end catch

      if (args.length < 4) {
         // Accept connections for all connection managers.
         serv.acceptAllIncomingRequests();
         System.out.println("Accepting incoming requests from all services.");
      }   // end if
      else {
         // There are more parameters.  Figure out what they mean.
         try {
            if (args[3].startsWith("name=")) {
               // This is a request by service name
               String name = parsePropertyString(args[3], "name");
               serv.acceptIncomingRequests(name);
               System.out.println("Accepting incoming requests from service \"" +
                  name + "\".");
            }   // end if
            else {
               // This is a request by ip:port
               SocketAddress addr = parseSocketAddress(args[3]);
               serv.acceptIncomingRequests(addr);
               System.out.println(
                  "Accepting incoming requests from address:port \"" +
                  addr.toString() + "\".");
            }   // end else
         }   // end try
         catch (ParseException e) {
            // Oops!
            System.out.println(
               "We were unable to process your request due to a parsing error:");
            System.out.println(" > " + e.getMessage());
            System.out.println("Syntax:\n");
            System.out.println(
               "  ACCEPT INCOMING REQUESTS [<ip address>:<port> | name=\"<service name>\"]");

            return;
         }   // end catch
         catch (ConnectionManagerNotFoundException e1) {
            // The connection manager for the specified address:port wasn't found.
            System.out.println("We were unable to find the specified service.");

            return;
         }   // end catch
         catch (ServiceAlreadyRunningException e2) {
            System.out.println(
               "The specified service is already actively accepting connections.");
         }   // end catch
      }   // end else
   }   // end processVerbAccept()

// ---------------------------------------------------------------------------
   /** 
    * Processes the STATUS command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbStatus(String[] args,
      ServerRemoteInterface serv) throws RemoteException {
      if (args.length < 2) {
         // Display the status for all connection managers.
         System.out.println(serv.getAllStatus());
      }   // end if
      else {
         // There are more parameters.  Figure out what they mean.
         try {
            if (args[1].startsWith("name=")) {
               // This is a request by service name
               String name = parsePropertyString(args[1], "name");
               System.out.println(serv.getStatus(name));
            }   // end if
            else {
               // This is a request by ip:port
               SocketAddress addr = parseSocketAddress(args[1]);
               System.out.println(serv.getStatus(addr));
            }   // end else
         }   // end try
         catch (ParseException e) {
            // Oops!  The port token isn't a valid integer numeric...
            System.out.println(
               "We were unable to process your request due to a parsing error:");
            System.out.println(" > " + e.getMessage());
            System.out.println("Syntax:\n");
            System.out.println(
               "  STATUS [<ip address>:<port> | name=\"name\"]\n");

            return;
         }   // end catch
         catch (ConnectionManagerNotFoundException e1) {
            // The connection manager for the specified address:port wasn't found.
            System.out.println("We were unable to find the specified service.");

            return;
         }   // end catch
      }   // end else
   }   // end processVerbStatus()

// ---------------------------------------------------------------------------
   /** 
    * Processes the START command
    *
    * @param args parameter value
    */
   private static void processVerbStart(String[] args) {
      if (! args[1].toUpperCase().equals("SERVER")) {
         System.out.println("Syntax:\n");
         System.out.println("  START SERVER [server config file]\n");
      }   // end if

      try {
         ServerRemoteInterface serv = (ServerRemoteInterface) Naming.lookup(rmiBindingName);

         if (serv == null) {
            throw new Exception();
         }
         // Do something just to check if the server is there or not.
         // Don't worry -- if it isn't, this will throw an exception for us.
         serv.getAllStatus();

         System.out.println("The Server appears to be already running.");
         System.exit(2);
      }   // end try
      catch (Exception e) {
         // This is okay.  It means that the server isn't already running, so we do ignore it.
      }   // end catch


      String filename = null;

      if (args.length == 3) {
         filename = args[2];
      }   // end if

      String[] args2 = new String[1];
      args2[0] = filename;

      try {
         Server.main(args2);
      }   // end try
      catch (Exception e) {
         if (e instanceof java.io.FileNotFoundException) {
            System.out.println("The file \"" + args[2] +
               "\" could not be found.");
            System.exit(2);
         }   // end if
         else {
            e.printStackTrace();
         }   // end else
      }   // end catch
   }   // end processVerbStart()

// ---------------------------------------------------------------------------
   /** 
    * Processes the SHUTDOWN command
    *
    * @param args parameter value
    * @param serv parameter value
    *
    * @throws RemoteException exception
    */
   private static void processVerbShutdown(String[] args,
      ServerRemoteInterface serv) throws RemoteException {
      try {
         if ((args.length >= 2) && args[1].toUpperCase().equals("NOW")) {
            // Shut down immediately!
            System.out.println("Requesting IMMEDIATE server shutdown...");
            serv.shutdownNow();
         }   // end if
         else {
            // Shutdown nicely.
            System.out.print(
               "Requesting server shutdown (this could take some time)...");
            serv.shutdownNicely();
         }   // end else
      }   // end try
      catch (UnmarshalException e) {
         // Ignore -- this should be expected as the server has died and can't return back to us.
         // It actually means everything worked :).
         System.out.println("Complete.");
      }   // end catch
   }   // end processVerbShutdown()

// ---------------------------------------------------------------------------
   /** 
    * Determines if the specified action is known to the system
    *
    * @param verb the verb to test
    *
    * @return true of the verb is known, false otherwise
    */
   private static boolean isKnownVerb(String verb) {
      if (verb.equals("CREATE") || verb.equals("SET") ||
            verb.equals("DESCRIBE") || verb.equals("REMOVE") ||
            verb.equals("DENY") || verb.equals("ACCEPT") ||
            verb.equals("STATUS") || verb.equals("START") ||
            verb.equals("SHUTDOWN")) {
         return true;
      }   // end if 
      else {
         return false;
      }   // end else
   }   // end isKnownVerb()

// ---------------------------------------------------------------------------
   /** 
    * Parses a string representation of a socket address, and returns a corresponding
    * InetSocketAddress instance if possible
    *
    * @param arg the String representation of the socket address.
    *
    * @return InetSocketAddress instance
    *
    * @throws ParseException exception
    */
   private static InetSocketAddress parseSocketAddress(String arg)
      throws ParseException {
      try {
         StringTokenizer st = new StringTokenizer(arg, ":");

         if (st.countTokens() != 2) {
            // Oops!  Missing the port, or request malformed.
            System.out.println(
               "We were unable to parse the address:port information.");
            throw new ParseException();
         }   // end if

         String address = st.nextToken();

         if (address.toUpperCase().equals("ALL")) {
            address = ALL_IPv4_BOUND_ADDRESSES;
         }   // end if

         int port = 0;
         port = Integer.parseInt(st.nextToken());

         return new InetSocketAddress(java.net.InetAddress.getByName(address),
            port);
      }   // end try
      catch (Exception e) {
         throw new ParseException(e.getMessage());
      }   // end catch
   }   // end parseSocketAddress()

// ---------------------------------------------------------------------------
   /** 
    * Returns the value of a property string containing a key value pair
    *
    * @param arg String containing a key value pair, ex: name=value
    * @param param the name of the property
    *
    * @return returned
    *
    * @throws ParseException exception
    */
   private static String parsePropertyString(String arg, String param)
      throws ParseException {
      try {
         // Parse out the name
         if (! arg.startsWith(param + "=")) {
            throw new ParseException();
         }   // end if

         int delim = arg.indexOf('=');

         return arg.substring(delim + 1);
      }   // end try
      catch (Exception e) {
         throw new ParseException(e.getMessage());
      }   // end catch
   }   // end parsePropertyString()

// ---------------------------------------------------------------------------
   /** 
    * Returns the name of the property from a string containing a key value pair
    *
    * @param arg String containing a key value pair, ex: name=value
    *
    * @return returned
    *
    * @throws ParseException exception
    */
   private static String parsePropertyName(String arg)
      throws ParseException {
      try {
         // Parse out the name
         int delim = arg.indexOf('=');

         return arg.substring(0, delim);
      }   // end try
      catch (Exception e) {
         throw new ParseException(e.getMessage());
      }   // end catch
   }   // end parsePropertyName()

//~  ========================================================================

   /** 
    * The ParseException inner class.
    *
    * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
    * @version 0.1
    */
   private static class ParseException extends Exception {
//~  =====================================================================

      /** Constructs a new ParseException.
        */
      public ParseException() {
         super();
      }   // end ParseException()

      /** Constructs a new ParseException with the specified message.
        * @param msg the message to attach to this exception.
        */
      public ParseException(String msg) {
         super(msg);
      }   // end ParseException()
   }   // end ParseException

//~ ===========================================================================
}   // end ServerManager
