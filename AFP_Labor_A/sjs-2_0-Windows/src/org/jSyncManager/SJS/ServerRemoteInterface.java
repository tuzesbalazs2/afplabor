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
// $Id: Launcher.java,v 3.4 2004/08/17 01:23:34 yaztromo Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS;

import java.net.SocketAddress;
import java.net.UnknownHostException;

import java.rmi.*;


//~ ===========================================================================
   /** An interface to define the remotely accessable methods for manipulating the server.
  * This interface is implemented by the Server class in order to permit the specified
  * methods to be callable by an RMI-based client application.
  * @version 0.1
  * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
  */
public interface ServerRemoteInterface extends Remote {
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
         ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
         ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
         ConnectionManagerNotFoundException;

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
    */
   public void acceptIncomingRequests(SocketAddress address)
      throws RemoteException, ConnectionManagerNotFoundException,
         ServiceAlreadyRunningException;

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
    */
   public void acceptIncomingRequests(String serviceName)
      throws RemoteException, ConnectionManagerNotFoundException,
         ServiceAlreadyRunningException;

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
         ConnectionManagerNotFoundException, ServiceAlreadyRunningException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

// ---------------------------------------------------------------------------
   /** 
    * A method to query the status of the specified service.
    *
    * @param address the binding address of the service to process.
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
         ConnectionManagerNotFoundException;

// ---------------------------------------------------------------------------
   /** 
    * A method to shutdown the entire Internet Service Server
    * immediately.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void shutdownNow() throws RemoteException;

// ---------------------------------------------------------------------------
   /** 
    * A method to shutdown the entire Internet Service Server as soon as
    * all existing connections have ended.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void shutdownNicely() throws RemoteException;

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
         ConnectionManagerNotFoundException;

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
      String value) throws RemoteException, ConnectionManagerNotFoundException;

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
      String value) throws RemoteException, ConnectionManagerNotFoundException;

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
    * @exception UnknownHostException if the specified hostname is unknown.
    */
   public void createService(String name, String address, int port,
      String adapterName)
      throws RemoteException, ClassNotFoundException, UnknownHostException;

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
      String adapterName) throws RemoteException, ClassNotFoundException;

// ---------------------------------------------------------------------------
   /** 
    * Describes all the services running on this server.
    *
    * @return returned
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public String describeAllServices() throws RemoteException;

// ---------------------------------------------------------------------------
   /** 
    * Retrieves the status for all services running on this server.
    *
    * @return returned
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public String getAllStatus() throws RemoteException;

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
      throws RemoteException;

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
      throws RemoteException;

// ---------------------------------------------------------------------------
   /** 
    * Causes the server to deny all incoming requests in all services.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void denyAllIncomingRequests() throws RemoteException;

// ---------------------------------------------------------------------------
   /** 
    * Causes the server to accept all incoming requests in all services.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void acceptAllIncomingRequests() throws RemoteException;

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
         ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
         ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

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
      throws RemoteException, ConnectionManagerNotFoundException;

// ---------------------------------------------------------------------------
   /** 
    * Removes all services from the server.
    *
    * @exception RemoteException if an RMI communication problem is
    *            encountered.
    */
   public void removeAllServices() throws RemoteException;

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
      throws RemoteException;

//~ ===========================================================================
}   // end ServerRemoteInterface
