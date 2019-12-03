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
package org.jSyncManager.SJS.API;

import org.jSyncManager.SJS.LogManager;

import java.io.*;

import java.net.*;

import java.util.Properties;


//~ ==========================================================================
/** 
 * The Abstract Adapter class. This class is the parent class of all
 * Adapter classes.  It provides the basic I/O and data mechanisms.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public abstract class AbstractAdapter extends Thread {
//~  ========================================================================

   /** A static field to hold the ThreadGroup to create new instances in. */
   private static ThreadGroup tg = null;

//~  ========================================================================

   /** A field to store the connection manager interface for this connection. */
   private ConnectionManagerInterface connMgr = null;

   /** A field to hold a handle to the log manager. */
   private LogManager log = null;

   /** The properties held by the connection manager instance. */
   protected Properties props = null;

   /** The socket connection underlying the adapter instance. */
   private Socket socket = null;

//~  ========================================================================

   /** Constructs a new instance of this abstract adapter.
     */
   public AbstractAdapter() {
      super(tg, "Adapter");
      setDaemon(false);
   }   // end AbstractAdapter()

//~  ========================================================================
   
   /** 
    * The processConnection method. This method is called when a
    * connection for this adapter type is received. Subclasses need to
    * implement this method in order to do anything useful.
    *
    * @param in the InputStream object for communications.
    * @param out the OutputStream object for communications.
    *
    * @exception Exception any checked exception not caught and handled by the
    *            adapter.
    */
   protected abstract void processConnection(InputStream in, OutputStream out)
      throws Exception;

// ---------------------------------------------------------------------------
   /** 
    * The processConnection method. This is the publically accessable
    * interface to the adapter. The connection manager will call this method
    * to initialize the communication session.
    *
    * @param socket the Socket for the connection.
    * @param props the Properties object for this adapter instance.
    * @param cm the ConnectionManagerInterface to use for connection manager
    *        message passing.
    */
   public void processConnection(Socket socket, Properties props,
      ConnectionManagerInterface cm) {
      this.props = props;
      this.socket = socket;
      this.connMgr = cm;

      if (connectionPermitted(socket.getInetAddress())) {
         start();
      } else {
         try {
            socket.close();
         }   // end try
         catch (IOException e) {
            // Socket is already closed.  Oh well...
         }   // end catch
      }   // end if
   }   // end processConnection()

// ---------------------------------------------------------------------------
   /** 
    * Runs the logic for this adapter in a seperate thread.
    *
    * @see java.lang.Thread
    */
   public void run() {
      try {
         processConnection(socket.getInputStream(), socket.getOutputStream());
      }   // end try
      catch (Exception e) {
         if (log != null) {
            log.writeCriticalEntry("Caught exception in processConnection:");
            log.writeExceptionEntry(e);
         }   // end if
      }   // end catch
      finally {
         try {
            socket.close();
         }   // end try
         catch (IOException ioe) {
            if (log != null) {
               log.writeWarningEntry(
                  "Caught IOException attempting to close socket.  Continuing...");
            }
         }   // end catch
      }   // end finally
   }   // end run()

// ---------------------------------------------------------------------------
   /** 
    * Gets the specified property from the properties object passed from
    * the Connection Manager.
    *
    * @param name the name of the property to retrieve.
    *
    * @return the value for the property, or <I>null</I> if it doesn't exist.
    */
   protected String getProperty(String name) {
      if (props == null) {
         return null;
      }   // end if

      return props.getProperty(name);
   }   // end getProperty()

// ---------------------------------------------------------------------------
   /** 
    * A method for testing wether or not the connection should be
    * permitted. Subclasses may implement this method to provide connection
    * control, such as from specific IPs. The default implementation permits
    * all connections.
    *
    * @param address the InetAddress object associated with this connection.
    *
    * @return <B>true</B> if this client is permitted to connect, <B>false</B>
    *         otherwise.  Default is <B>true</B>.
    */
   protected boolean connectionPermitted(InetAddress address) {
      return true;
   }   // end connectionPermitted()

// ---------------------------------------------------------------------------
   /** 
    * Statically set the thread group all new instances should be
    * created in.
    *
    * @param group the ThreadGroup to use for all instances of this class.
    */
   public static void setThreadGroup(ThreadGroup group) {
      tg = group;
   }   // end setThreadGroup()

// ---------------------------------------------------------------------------
   /** 
    * Set the log manager to use for loging messages.
    *
    * @param log the log manaer to use.
    */
   public void setLogManager(LogManager log) {
      this.log = log;
   }   // end setLogManager()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves a handle to the current log manager.
    *
    * @return a handle to the current log manager.
    */
   protected LogManager getLogManager() {
      return log;
   }   // end getLogManager()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves a handle to the configuration manager interface object.
    *
    * @return a handle to the configuration manager interface object.
    */
   protected ConnectionManagerInterface getConnectionManagerInterface() {
      return connMgr;
   }   // end getConnectionManagerInterface()

// ---------------------------------------------------------------------------
   /** 
    * Process any disconnection details.
    */
   protected void disconnect() {
      if (connMgr != null) {
         getConnectionManagerInterface().disconnectionAlert();
      }
   }   // end disconnect()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves the internet address object for the connected host. This
    * method acts as a proxy to Socket.getInetAddress().
    *
    * @return the internet address object for the connected host, or null if
    *         there is no socket connection (ie unit testing).
    */
   protected InetAddress getInetAddress() {
      return (socket != null) ? socket.getInetAddress() : null;
   }   // end getInetAddress()

//~ ===========================================================================
}   // end AbstractAdapter
