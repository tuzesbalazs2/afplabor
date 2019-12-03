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
// $Id: Server.java,v 1.1 2004/12/05 04:31:54 yaztromo Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS;

import org.w3c.dom.*;

import java.io.File;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import java.rmi.*;
import java.rmi.server.*;

import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


//~ ===========================================================================
/** 
 * The Simple Java Server Internet Server Service. This executable class is
 * used to start the Simple Java Server Internet Server Service. It takes as
 * its sole parameter an XML data file name which it will parse to configure
 * the server. If this parameter is not specified, the name "server.xml" will
 * be assumed.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public class Server extends UnicastRemoteObject implements ServerRemoteInterface {
//~  ========================================================================

   /**
    * A field to store the servers XML document root.
    */
   private Document doc = null;

   /**
    * A field to store the server's XML config file.
    */
   private File xmlFile = null;

   /**
    * A field to store the HashMap mapping bind addresses to
    * ConnectionManager instances.
    */
   private HashMap<SocketAddress, ConnectionManager> inetAddrMap = new HashMap<SocketAddress, ConnectionManager>();

   /**
    * A field to store the HashMap mapping service names to
    * ConnectionManager instances.
    */
   private HashMap<String, ConnectionManager> serviceNameMap = new HashMap<String, ConnectionManager>();

   /**
    * A field to store the HashMap mapping ConnectionManager instances
    * to their Threads.
    */
   private HashMap<ConnectionManager, Thread> threadMap = new HashMap<ConnectionManager, Thread>();

   /**
    * A handle to the log manager instance.
    */
   private LogManager log = null;

   /**
    * A field to hold the ThreadGroup for all server threads.
    */
   private ThreadGroup serverThreadGroup = null;

//~  ========================================================================

   /** Creates a new instance of the Server.
     * @param filename the path and filename to the XML data to construct this object from.
     * @exception Exception in the event an exception we can't handle is thrown.
     */
   public Server(String filename) throws Exception {
      this(new File(filename));
   }   // end Server()

// ---------------------------------------------------------------------------
   /** Creates a new instance of the Server.
     * @param file the file object of the XML data to construct this object from.
     * @exception Exception in the event an exception we can't handle is thrown.
     */
   public Server(File file) throws Exception {
      super();
      xmlFile = file;

      if (! file.exists()) {
         throw new java.io.FileNotFoundException("XML data file not found: " +
            file.toString());
      }   // end if

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(file);

      Element docElement = doc.getDocumentElement();

      String logPath = docElement.getAttribute("logpath");

      if (logPath != null) {
         log = new LogManager(logPath, "ISS", "ISS");
      }   // end if

      NodeList nodes = docElement.getElementsByTagName("service");
      serverThreadGroup = new ThreadGroup("Server Threads");
      org.jSyncManager.SJS.API.AbstractAdapter.setThreadGroup(serverThreadGroup);

      log.writeGeneralEntry("Starting the Internet Server Service");

      for (int i = 0; i < nodes.getLength(); i++) {
         Element service = (Element) nodes.item(i);
         ConnectionManager cm = new ConnectionManager(service, serverThreadGroup);

         // We keep maps for both name -> connection manager and address:port -> connection manager
         // in order to quickly find the connection manager when requested by the server manager.
         serviceNameMap.put(cm.getServiceName(), cm);
         inetAddrMap.put(cm.getBindAddress(), cm);

         // We keep a map of the threads in order to access them from their connection manager handle.
         Thread t = new Thread(serverThreadGroup, cm);
         threadMap.put(cm, t);

         t.start();
      }   // end for
   }   // end Server()

//~  ========================================================================

   /** 
    * A method to describe the specified service.
    *
    * @param address the host name or IP address the service to describe is
    *        bound to.
    * @param port the port the service to describe is bound to.
    *
    * @return a String describing the service requested.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String describeService(String address, int port)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      return describeService(new InetSocketAddress(InetAddress.getByName(
               address), port));
   }   // end describeService()

// ---------------------------------------------------------------------------
   /** 
    * A method to describe the specified service.
    *
    * @param serviceName the service to be described.
    *
    * @return a String describing the service requested.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String describeService(String serviceName)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      return cm.toString();
   }   // end describeService()

// ---------------------------------------------------------------------------
   /** 
    * A method to describe the specified service.
    *
    * @param address the SocketAddress of the service to be described.
    *
    * @return a String describing the service requested.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String describeService(SocketAddress address)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      return cm.toString();
   }   // end describeService()

// ---------------------------------------------------------------------------
   /** 
    * A method to describe the specified service parameter.
    *
    * @param address the host name or IP address the service to describe is
    *        bound to.
    * @param port the port the service to describe is bound to.
    * @param param the name of the parameter to query.
    *
    * @return a String describing the service parameter requested.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String describeServiceParameter(String address, int port, String param)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      return describeServiceParameter(new InetSocketAddress(
            InetAddress.getByName(address), port), param);
   }   // end describeServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to describe the specified service parameter.
    *
    * @param serviceName the name of the service to query.
    * @param param the name of the parameter to query.
    *
    * @return a String describing the service parameter requested.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String describeServiceParameter(String serviceName, String param)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      return cm.getProperty(param);
   }   // end describeServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to describe the specified service parameter.
    *
    * @param address the SocketAddress the service to query is bound to.
    * @param param the name of the parameter to query.
    *
    * @return a String describing the service parameter requested.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String describeServiceParameter(SocketAddress address, String param)
      throws RemoteException, ConnectionManagerNotFoundException {
      return ((ConnectionManager) inetAddrMap.get(address)).getProperty(param);
   }   // end describeServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to deny incoming requests to the specified service.
    *
    * @param address the SocketAddress the service to query is bound to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void denyIncomingRequests(SocketAddress address)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if
      cm.stopListening();
   }   // end denyIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to deny incoming requests to the specified service.
    *
    * @param serviceName the name of the service to query.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void denyIncomingRequests(String serviceName)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if
      cm.stopListening();
   }   // end denyIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to accept incoming requests to the specified service.
    *
    * @param address the hostname or IP address of the service to process.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    * @exception ServiceAlreadyRunningException if the specified service is
    *            already running.
    * @throws RuntimeException exception
    */
   public void acceptIncomingRequests(SocketAddress address)
      throws RemoteException, ConnectionManagerNotFoundException,
         ServiceAlreadyRunningException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      Thread t = (Thread) threadMap.get(cm);

      if (t == null) {
         throw new RuntimeException(
            "A serious, unexpected error has occurred in Server.class.\nacceptIncomingRequests(SocketAddress) -> found the ConnectionManager, but not it's associated Thread!\nPlease report this error.  Aborting...");
      }   // end if

      if (! t.isAlive()) {
         t = new Thread(cm);
         threadMap.put(cm, t);
         t.start();
      }   // end if
      else {
         throw new ServiceAlreadyRunningException();
      }   // end else
   }   // end acceptIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to accept incoming requests to the specified service.
    *
    * @param serviceName the name of the service to process.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    * @exception ServiceAlreadyRunningException if the specified service is
    *            already running.
    * @throws RuntimeException exception
    */
   public void acceptIncomingRequests(String serviceName)
      throws RemoteException, ConnectionManagerNotFoundException,
         ServiceAlreadyRunningException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      Thread t = (Thread) threadMap.get(cm);

      if (t == null) {
         throw new RuntimeException(
            "A serious, unexpected error has occurred in Server.class.\nacceptIncomingRequests(SocketAddress) -> found the ConnectionManager, but not it's associated Thread!\nPlease report this error.  Aborting...");
      }   // end if

      if (! t.isAlive()) {
         t = new Thread(cm);
         threadMap.put(cm, t);
         t.start();
      }   // end if
      else {
         throw new ServiceAlreadyRunningException();
      }   // end else
   }   // end acceptIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to query the status of the specified service.
    *
    * @param address the hostname or IP address of the service to process.
    *
    * @return the status of the specified service.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String getStatus(SocketAddress address)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      return cm.getStatus();
   }   // end getStatus()

// ---------------------------------------------------------------------------
   /** 
    * A method to query the status of the specified service.
    *
    * @param serviceName the name of the service to process.
    *
    * @return the status of the specified service.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String getStatus(String serviceName)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      return cm.getStatus();
   }   // end getStatus()

// ---------------------------------------------------------------------------
   /** 
    * A method to shutdown the entire Internet Service Server.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void shutdownNow() throws RemoteException {
      log.writeGeneralEntry(
         "The Internet Server Service has been asked to shutdown IMMEDIATELY.");
      System.exit(0);
   }   // end shutdownNow()

// ---------------------------------------------------------------------------
   /** 
    * A method to shutdown the entire Internet Service Server as soon as
    * all existing connections have ended.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void shutdownNicely() throws RemoteException {
      // Iterate through shutting down all the connection managers.
      log.writeGeneralEntry(
         "The Internet Server Service has been asked to shutdown...");

      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         cm.stopListening();
      }   // end for

      // Next iterate through all the threads, joining them so we don't return until they've all closed down.
      Thread[] threads = new Thread[serverThreadGroup.activeCount()];
      int k = serverThreadGroup.enumerate(threads, true);

      for (int i = 0; i < k; i++) {
         // Iterate through all threads
         try {
            threads[i].join();
         }   // end try
         catch (InterruptedException e) {
            // We don't really care, so do nothing.
            e.printStackTrace();
         }   // end catch
      }   // end for
      log.writeGeneralEntry("The Internet Server Service has completed.");
      System.exit(0);
   }   // end shutdownNicely()

// ---------------------------------------------------------------------------
   /** 
    * A method to deny incoming requests to the specified service.
    *
    * @param address the hostname or IP address of the service to process.
    * @param port the port the service to describe is bound to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void denyIncomingRequests(String address, int port)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      denyIncomingRequests(new InetSocketAddress(InetAddress.getByName(address),
            port));
   }   // end denyIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to accept incoming requests to the specified service.
    *
    * @param address the hostname or IP address of the service to process.
    * @param port the port the service to describe is bound to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    * @exception ServiceAlreadyRunningException if the specified service is
    *            already running.
    */
   public void acceptIncomingRequests(String address, int port)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException, ServiceAlreadyRunningException {
      acceptIncomingRequests(new InetSocketAddress(InetAddress.getByName(
               address), port));
   }   // end acceptIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to query the status of the specified service.
    *
    * @param address the name of the service to process.
    * @param port the port the service to describe is bound to.
    *
    * @return the status of the specified service.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public String getStatus(String address, int port)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      return getStatus(new InetSocketAddress(InetAddress.getByName(address),
            port));
   }   // end getStatus()

// ---------------------------------------------------------------------------
   /** 
    * A method to set the specified service parameter.
    *
    * @param address the host name or IP address the service to describe is
    *        bound to.
    * @param port the port the service to describe is bound to.
    * @param param the name of the parameter to query.
    * @param value the value to set the parameter to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void setServiceParameter(String address, int port, String param,
      String value)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      setServiceParameter(new InetSocketAddress(InetAddress.getByName(address),
            port), param, value);
   }   // end setServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to set the specified service parameter.
    *
    * @param serviceName the name of the service to query.
    * @param param the name of the parameter to query.
    * @param value the value to set the parameter to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void setServiceParameter(String serviceName, String param,
      String value) throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if
      cm.setProperty(param, value);
      writeXMLDataFile();
   }   // end setServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to set the specified service parameter.
    *
    * @param address the SocketAddress the service to query is bound to.
    * @param param the name of the parameter to query.
    * @param value the value to set the parameter to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void setServiceParameter(SocketAddress address, String param,
      String value) throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if
      cm.setProperty(param, value);
      writeXMLDataFile();
   }   // end setServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * Creates the specified service.
    *
    * @param name the name to assign to the service.
    * @param address the IP address to bind to.
    * @param port the port to listen to.
    * @param adapterName the classname of the adapter to use.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ClassNotFoundException if the specified adapter cannot be
    *            found.
    * @throws UnknownHostException exception
    */
   public void createService(String name, String address, int port,
      String adapterName)
      throws RemoteException, ClassNotFoundException, UnknownHostException {
      createService(name,
         new InetSocketAddress(InetAddress.getByName(address), port),
         adapterName);
   }   // end createService()

// ---------------------------------------------------------------------------
   /** 
    * Creates the specified service.
    *
    * @param name the name to assign to the service.
    * @param address the SocketAddress object to bind to.
    * @param adapterName the classname of the adapter to use.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ClassNotFoundException if the specified adapter cannot be
    *            found.
    */
   public void createService(String name, SocketAddress address,
      String adapterName) throws RemoteException, ClassNotFoundException {
      if (! (address instanceof InetSocketAddress)) {
         throw new RemoteException("Unknown SocketAddress type!");
      }   // end if

      InetSocketAddress addr = (InetSocketAddress) address;

      try {
         Element newElement = doc.createElement("SERVICE");
         newElement.setAttribute("name", name);
         newElement.setAttribute("ipaddress", addr.getAddress().getHostAddress());
         newElement.setAttribute("port", Integer.toString(addr.getPort()));
         newElement.setAttribute("adapter", adapterName);
         doc.getDocumentElement().appendChild(newElement);

         // Add the new Connection Manager
         ConnectionManager cm = new ConnectionManager(newElement,
               serverThreadGroup);

         // We keep maps for both name -> connection manager and address:port -> connection manager
         // in order to quickly find the connection manager when requested by the server manager.
         serviceNameMap.put(cm.getServiceName(), cm);
         inetAddrMap.put(cm.getBindAddress(), cm);

         // We keep a map of the threads in order to access them from their connection manager handle.
         Thread t = new Thread(serverThreadGroup, cm);
         threadMap.put(cm, t);

         t.start();
         writeXMLDataFile();
      }   // end try
      catch (Exception e) {
         // LOGME
         throw new RemoteException("DOM Exception:", e);
      }   // end catch
      catch (OutOfMemoryError e1) {
         // This is serious.  We couldn't create the new adapter because we're out of memory.
         if (log != null) {
            log.writeCriticalEntry(
               "Unable to allocate new connection manager -- out of memory!");
            log.writeExceptionEntry(e1);
         }   // end if
      }   // end catch
   }   // end createService()

// ---------------------------------------------------------------------------
   /** 
    * Describes all the services running on this server.
    *
    * @return returned
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public String describeAllServices() throws RemoteException {
      StringBuffer sb = new StringBuffer();

      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         sb.append(cm.toString());
         sb.append("\n");
      }   // end for

      return sb.toString();
   }   // end describeAllServices()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves the status for all services running on this server.
    *
    * @return returned
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public String getAllStatus() throws RemoteException {
      StringBuffer sb = new StringBuffer();

      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         sb.append(cm.getStatus());
         sb.append("\n");
      }   // end for

      return sb.toString();
   }   // end getAllStatus()

// ---------------------------------------------------------------------------
   /** 
    * Describes all the services with the specified parameter running on
    * this server.
    *
    * @param param the name of the parameter to describe.
    *
    * @return returned
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public String describeAllServicesWithParameter(String param)
      throws RemoteException {
      StringBuffer sb = new StringBuffer();

      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         String p;

         if ((p = cm.getProperty(param)) != null) {
            sb.append("Service \"" + cm.getServiceName() +
               "\" has parameter \"" + param + "\" set to \"" + p + "\".\n");
         }   // end if
         else {
            sb.append("Service \"" + cm.getServiceName() +
               "\" does not have a parameter named \"" + param + "\".\n");
         }   // end else
      }   // end for

      return sb.toString();
   }   // end describeAllServicesWithParameter()

// ---------------------------------------------------------------------------
   /** 
    * Sets the specified parameter to the specified value in all
    * services running on this system.
    *
    * @param param the name of the parameter to be set.
    * @param value the value to set the parameter to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void setParameterInAllServices(String param, String value)
      throws RemoteException {
      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         cm.setProperty(param, value);
      }   // end for
      writeXMLDataFile();
   }   // end setParameterInAllServices()

// ---------------------------------------------------------------------------
   /** 
    * Causes the server to deny all incoming requests in all services.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void denyAllIncomingRequests() throws RemoteException {
      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         cm.stopListening();
      }   // end for
   }   // end denyAllIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * Causes the server to accept all incoming requests in all services.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @throws RuntimeException exception
    */
   public void acceptAllIncomingRequests() throws RemoteException {
      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();

         if (! cm.isListening()) {
            Thread t = (Thread) threadMap.get(cm);

            if (t == null) {
               throw new RuntimeException(
                  "The Server has encountered a fatal exception.\nacceptAllIncomingRequests() --> could not map a connection manager to it's thread!");
            }   // end if

            if (! t.isAlive()) {
               t = new Thread(cm);
               threadMap.put(cm, t);
               t.start();
            }   // end if
         }   // end if
      }   // end for
   }   // end acceptAllIncomingRequests()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified service.
    *
    * @param address the host name or IP address the service to remove is bound
    *        to.
    * @param port the port the service to remove is bound to.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void removeService(String address, int port)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      removeService(new InetSocketAddress(InetAddress.getByName(address), port));
   }   // end removeService()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified service.
    *
    * @param serviceName the service to be removed.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void removeService(String serviceName)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      cm.stopListening();
      doc.getDocumentElement().removeChild(cm.getElement());

      writeXMLDataFile();
   }   // end removeService()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified service.
    *
    * @param address the SocketAddress of the service to be removed.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void removeService(SocketAddress address)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      cm.stopListening();
      doc.getDocumentElement().removeChild(cm.getElement());

      writeXMLDataFile();
   }   // end removeService()

// ---------------------------------------------------------------------------
   /** 
    * Writes out the XML property file after any changes.
    */
   private void writeXMLDataFile() {
      try {
         StreamResult sr = new StreamResult(xmlFile);
         DOMSource domSrc = new DOMSource(doc);
         TransformerFactory.newInstance().newTransformer().transform(domSrc, sr);
      }   // end try
      catch (TransformerException e) {
         if (log != null) {
            log.writeCriticalEntry("We were unable to write the XML data file.");
            log.writeExceptionEntry(e);
         }   // end if
      }   // end catch
   }   // end writeXMLDataFile()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified service parameter.
    *
    * @param address the host name or IP address the service to remove the
    *        parameter from is bound to.
    * @param port the port the service to remove is bound to.
    * @param param parameter value
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception UnknownHostException if the specified hostname is unknown.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void removeServiceParameter(String address, int port, String param)
      throws RemoteException, UnknownHostException,
         ConnectionManagerNotFoundException {
      removeServiceParameter(new InetSocketAddress(InetAddress.getByName(
               address), port), param);
   }   // end removeServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified service parameter.
    *
    * @param serviceName the service to remove the parameter from.
    * @param param parameter value
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void removeServiceParameter(String serviceName, String param)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) serviceNameMap.get(serviceName);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      cm.removeProperty(param);
      writeXMLDataFile();
   }   // end removeServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified service.
    *
    * @param address the SocketAddress of the service to remove the parameter
    *        from.
    * @param param parameter value
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    * @exception ConnectionManagerNotFoundException if the requested service
    *            can't be found.
    */
   public void removeServiceParameter(SocketAddress address, String param)
      throws RemoteException, ConnectionManagerNotFoundException {
      ConnectionManager cm = (ConnectionManager) inetAddrMap.get(address);

      if (cm == null) {
         throw new ConnectionManagerNotFoundException();
      }   // end if

      cm.removeProperty(param);
      writeXMLDataFile();
   }   // end removeServiceParameter()

// ---------------------------------------------------------------------------
   /** 
    * Removes all services from the server.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void removeAllServices() throws RemoteException {
      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         cm.stopListening();
         doc.getDocumentElement().removeChild(cm.getElement());
      }   // end for

      writeXMLDataFile();
   }   // end removeAllServices()

// ---------------------------------------------------------------------------
   /** 
    * A method to remove the specified parameter from all services.
    *
    * @param param the parameter to be removed from all services.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void removeParameterInAllServices(String param)
      throws RemoteException {
      for (Iterator<ConnectionManager> iter = serviceNameMap.values().iterator(); iter.hasNext();) {
         // Iterate through all services
         ConnectionManager cm = (ConnectionManager) iter.next();
         cm.removeProperty(param);
      }   // end for

      writeXMLDataFile();
   }   // end removeParameterInAllServices()

//~ ===========================================================================
   /** 
    * The application main entry point.
    *
    * @param args the command-line arguments as passed by the JRE.
    */
   public static void main(String[] args) {
      String filename = (((args != null) && (args.length > 0) &&
            (args[0] != null) && ! args[0].equals("")) ? args[0] : "server.xml");

      try {
         //if (System.getSecurityManager() == null) System.setSecurityManager(new RMISecurityManager());
         Server s = new Server(filename);
         Naming.rebind("Server", s);
      }   // end try
      catch (java.rmi.ConnectException e) {
         System.out.println(
            "The RMI Registry service isn't running.  Please start it and try your command again.");
         System.exit(2);
      }   // end catch
      catch (Exception e1) {
         System.out.println(
            "*** The Internet Server Service caught an uncaught exception:");
         e1.printStackTrace(System.out);
         System.exit(2);
      }   // end catch
   }   // end main()

//~ ===========================================================================
}   // end Server
