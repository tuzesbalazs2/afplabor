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

import org.jSyncManager.SJS.API.*;

import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;

import java.net.*;

import java.util.*;


//~ ===========================================================================
/** 
 * The ConnectionManager class. This class handles the incoming connections
 * for a single port, and passes them off to the associated Adapter class.
 * This class extends Runnable, in order to make it easily threadable
 * (although it's not a requirement, which may be useful if one were to use
 * this code to build a single-port server in the future).
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
class ConnectionManager implements Runnable, ConnectionManagerInterface {
//~  ========================================================================

   /**
    * A field to store the class name for the adapter class this
    * connection manager is associated with.
    */
   protected Class adapterClass = null;

   /** A field to store the Element object that created this instance, if any. */
   private Element element = null;

   /** A field to hold the IP address this connection manager should bind to. */
   protected InetAddress bindAddress = null;

   /** A field to store the log manager for this connection manager. */
   private LogManager log = null;

   /**
    * A field to store the properties associated with this connection
    * manager.
    */
   protected Properties props = null;

   /** A field to store the ServerSocket object. */
   private ServerSocket ss = null;

   /** A field to hold this connection managers service name. */
   protected String serviceName = null;

   /** A field to hold this connection managers ThreadGroup. */
   private ThreadGroup adapterThreadGroup = null;

   /**
    * A field to denote whether or not this connection manager is
    * listening to the specified port.
    */
   protected volatile boolean listening = false;

   /** A field to store the maximum number of allowable connections. */
   private int maxConnections = Integer.MAX_VALUE;

   /** A field to store the current number of connections. */
   private volatile int numConnections = 0;

   /** A field to store the TCP port to listen to for connections. */
   protected int port = 0;

   /** A field to store the timeout value, if any. */
   private int timeout = 0;

//~  ========================================================================

   /** Constructs a new instance of the ConnectionManager for the specified
     * adapter class and port.
     * @param serviceName the name of this service.
     * @param adapter the adapter class to use for communications.
     * @param bindAddress the address object to bind to.
     * @param port the port to listen to for incoming connections.
     * @param properties the properties to associate with this connection manager.
     */
   public ConnectionManager(String serviceName, Class adapter,
      InetAddress bindAddress, int port, Properties properties) {
      this.serviceName = serviceName;
      this.bindAddress = bindAddress;
      adapterClass = adapter;
      this.port = port;

      if (properties != null) {
         props = properties;
      }   // end if 
      else {
         props = new Properties();
      }   // end else
   }   // end ConnectionManager()


// ---------------------------------------------------------------------------
   /** Constructs a new instance of the ConnectionManager for the specified
     * adapter class and port, accepting connections from any/all adapters.
     * @param serviceName the name of this service.
     * @param adapter the adapter classname to use for communications.
     * @param port the port to listen to for incoming connections.
     * @exception ClassNotFoundException thrown if the specified adapter class cannot be found.
     */
   public ConnectionManager(String serviceName, Class adapter, int port) {
      this(serviceName, adapter, null, port, null);
   }   // end ConnectionManager()

// ---------------------------------------------------------------------------
   /** Constructs a new instance of the ConnectionManager for the specified
     * adapter class and port.
     * @param serviceName the name of this service.
     * @param adapter the adapter classname to use for communications.
     * @param bindAddress the address object to bind to.
     * @param port the port to listen to for incoming connections.
     * @param properties the properties to associate with this connection manager.
     * @exception ClassNotFoundException thrown if the specified adapter class cannot be found.
     */
   public ConnectionManager(String serviceName, String adapter,
      String bindAddress, int port, Properties properties)
      throws ClassNotFoundException, UnknownHostException {
      this(serviceName, Class.forName(adapter),
         InetAddress.getByName(bindAddress), port, properties);
   }   // end ConnectionManager()

// ---------------------------------------------------------------------------
   /** Constructs a new instance of the ConnectionManager for the specified
     * adapter class and port, acceptiing connections from any/all adapters.
     * @param serviceName the name of this service.
     * @param adapter the adapter classname to use for communications.
     * @param port the port to listen to for incoming connections.
     * @exception ClassNotFoundException thrown if the specified adapter class cannot be found.
     */
   public ConnectionManager(String serviceName, String adapter, int port)
      throws ClassNotFoundException {
      this(serviceName, Class.forName(adapter), null, port, null);
   }   // end ConnectionManager()

// ---------------------------------------------------------------------------
   /** Constructs a new instance of the ConnectionManager based on the data in the
     * specified DOM Element.  This element should conform to the "service" tag.
     * @param element the element containing the service information.
     */
   public ConnectionManager(Element element) throws Exception {
      if (! element.getTagName().toUpperCase().equals("SERVICE")) {
         throw new Exception("Not a SERVICE element!");
      }   // end if

      port = Integer.parseInt(element.getAttribute("port"));
      serviceName = element.getAttribute("name");

      String logPath = element.getAttribute("logpath");

      ClassLoader loader = null;
      String adapterClasspathString = element.getAttribute("classpath");

      if (adapterClasspathString != null) {
         URL url;

         if (adapterClasspathString.toLowerCase().startsWith("http") ||
               adapterClasspathString.toLowerCase().startsWith("file")) {
            // This is a URL already
            url = new URL(adapterClasspathString);
         }   // end if
         else {
            // This is a platform-specific path.
            url = new File(adapterClasspathString).toURL();
         }   // end else

         URL[] urls = new URL[1];
         urls[0] = url;
         loader = new URLClassLoader(urls);
      }   // end if

      String adapterClassString = element.getAttribute("adapter");
      adapterClass = Class.forName(adapterClassString, true, loader);

      if (logPath != null) {
         log = new LogManager(logPath, port + "." + adapterClassString,
               adapterClassString);
      }   // end if

      String ipAddr = element.getAttribute("ipaddress");

      if ((ipAddr != null) && ! ipAddr.equals("")) {
         // An IP address for binding was specified
         bindAddress = InetAddress.getByName(ipAddr);
      }   // end if

      String timeOutStr = element.getAttribute("timeout");

      if ((timeOutStr != null) && ! timeOutStr.equals("")) {
         try {
            timeout = Integer.parseInt(timeOutStr);
         }   // end try
         catch (NumberFormatException e) {
            // The value specified isn't a number.  Don't use any timeout.
            if (log != null) {
               log.writeWarningEntry("The specified timeout for the service \"" +
                  serviceName +
                  "\" is not a valid integer value.  Disabling timeout.");
            }   // end if
            timeout = 0;
         }   // end catch
      }   // end if

      String maxConnStr = element.getAttribute("maxconnections");

      if ((maxConnStr != null) && ! maxConnStr.equals("")) {
         try {
            maxConnections = Integer.parseInt(maxConnStr);
         }   // end try
         catch (NumberFormatException e) {
            // The value specified isn't a number.  Don't use any max connections.
            if (log != null) {
               log.writeWarningEntry(
                  "The specified max connections for the service \"" +
                  serviceName +
                  "\" is not a valid integer value.  Disabling max connections.");
            }   // end if
            maxConnections = Integer.MAX_VALUE;
         }   // end catch
      }   // end if

      // Parse the parameters
      NodeList nodes = element.getElementsByTagName("parameter");

      props = new Properties();

      if (nodes.getLength() != 0) {
         for (int i = 0; i < nodes.getLength(); i++) {
            Element next = (Element) nodes.item(i);
            String paramName = next.getAttribute("name");

            if (paramName == null) {
               continue;
            }   // end if

            Text text = (Text) next.getFirstChild();

            if (text == null) {
               continue;   // If the param doesn't even have a name, skip it completely.
            }   // end if

            String paramData = text.getNodeValue();
            props.setProperty(paramName, paramData);
         }   // end for
      }   // end if
      this.element = element;
   }   // end ConnectionManager()

// ---------------------------------------------------------------------------
   /** Constructs a new instance of the ConnectionManager based on the data in the
     * specified DOM Element.  This element should conform to the "service" tag.
     * @param element the element containing the service information.
     * @param parentTG the parent thread group for this object.
     */
   public ConnectionManager(Element element, ThreadGroup parentTG)
      throws Exception {
      this(element);
      adapterThreadGroup = new ThreadGroup(parentTG, getServiceName());
   }   // end ConnectionManager()

//~  ========================================================================

   /** 
    * Runs this connection manager. Note that if you're running an
    * instance of this object within a thread, you shouldn't call this
    * directly.  Instead, construct a new instance of java.lang.Thread with
    * this object as its constructor parameter, and call your Thread instances
    * start() method.  Thread.start() automatically calls this Runnable's
    * run() method.
    */
   public void run() {
      listening = true;

      if (log != null) {
         log.writeGeneralEntry("Service \"" + getServiceName() +
            "\" starting on port " + port);
      }   // end if

      try {
         ss = new ServerSocket(port, 0, bindAddress);

         while (listening) {
            while (numConnections >= maxConnections) {
               // Close the socket so as not to block acccepting connections.
               // Not closing the socket when maxconnections is reached would
               // provide a vector for a denial-of-service attack.
               ss.close();

               synchronized (this) {
                  try {
                     // Wait for one second
                     wait(1000L);
                  }   // end try
                  catch (InterruptedException e) {
                     // We don't care, as we'll loop back into the wait if necessary.
                  }   // end catch
               }   // end synchronized
            }   // end while

            // If we closed the socket while waiting for the number of connections to decrease, re-bind now.
            if (ss.isClosed()) {
               ss = new ServerSocket(port, 0, bindAddress);
            }   // end if

            Socket s = ss.accept();

            if (log != null) {
               log.writeGeneralEntry("Received connection from " +
                  s.getInetAddress().toString());
            }   // end if
            numConnections++;

            if ((props != null) && (props.getProperty("timeout") != null)) {
               s.setSoTimeout(timeout);
            }   // end if

            try {
               AbstractAdapter adapterInstance = (AbstractAdapter) adapterClass.newInstance();

               if (log != null) {
                  adapterInstance.setLogManager(log);
               }   // end if
               adapterInstance.processConnection(s, props, this);
            }   // end try
            catch (Exception e0) {
               if (log != null) {
                  log.writeWarningEntry("Service \"" + getServiceName() +
                     "\" on port " + port + " encountered an exception:");
               }   // end if

               if (log != null) {
                  log.writeExceptionEntry(e0);
               }   // end if

               if (log != null) {
                  log.writeWarningEntry("Connection Manager is continuing...");
               }   // end if

               try {
                  s.close();
               }   // end try
               catch (IOException e01) {
                  if (log != null) {
                     log.writeWarningEntry(
                        "We were unable to close the socket.  Continuing anyway...");
                  }   // end if
               }   // end catch
            }   // end catch
            catch (OutOfMemoryError e1) {
               // This is serious.  We couldn't create the new adapter because we're out of memory.
               if (log != null) {
                  log.writeCriticalEntry(
                     "Unable to allocate new adapter -- out of memory!");
               }   // end if

               try {
                  s.close();
               }   // end try
               catch (IOException e01) {
                  if (log != null) {
                     log.writeWarningEntry(
                        "We were unable to close the socket.  Continuing anyway...");
                  }   // end if
               }   // end catch
            }   // end catch
         }   // end while

         // Mark the server socket as being null.
         ss = null;
         log.close();
      }   // end try
      catch (SocketException e2) {
         if (log != null) {
            log.writeGeneralEntry("Service \"" + getServiceName() +
               "\" is closed.");
         }   // end if

         return;
      }   // end catch
      catch (Exception e1) {
         if (log != null) {
            log.writeCriticalEntry("Service \"" + getServiceName() +
               "\" on port " + port + " encountered an exception:");
         }   // end if

         if (log != null) {
            log.writeExceptionEntry(e1);
         }   // end if

         if (log != null) {
            log.writeCriticalEntry("Service \"" + getServiceName() +
               "\" is aborting...");
         }   // end if

         return;
      }   // end catch
   }   // end run()

// ---------------------------------------------------------------------------
   /** 
    * Tests to see if this connection manager instance is listening on
    * its port.
    *
    * @return <B>true</B> if this instance is actively accepting connections,
    *         <B>false</B> otherwise.
    */
   public boolean isListening() {
      return listening;
   }   // end isListening()

// ---------------------------------------------------------------------------
   /** 
    * Tells this connection manager to stop listening on its port.
    */
   public void stopListening() {
      listening = false;

      if (ss == null) {
         return;
      }   // end if

      try {
         ss.close();
      }   // end try
      catch (Exception e) {
         // Log the error
         e.printStackTrace(System.err);
      }   // end catch
   }   // end stopListening()

// ---------------------------------------------------------------------------
   /** 
    * Returns this connection managers service name.
    *
    * @return this connection managers service name.
    */
   public String getServiceName() {
      return serviceName;
   }   // end getServiceName()

// ---------------------------------------------------------------------------
   /** 
    * Returns this object as a human-readable descriptive String.
    *
    * @return this object as a human-readable descriptive String.
    */
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(
         "================================================================\n");
      sb.append("  Service Name:     ");
      sb.append(serviceName);
      sb.append("\n");
      sb.append("  Port:             ");
      sb.append(port);
      sb.append("\n");
      sb.append("  Adapter Name:     ");
      sb.append(adapterClass.getName());
      sb.append("\n");

      if (bindAddress != null) {
         sb.append("  Bind Address:     ");
         sb.append(bindAddress.getHostAddress());
         sb.append("\n");
      }   // end if
      else if (ss != null) {
         sb.append("  Bind Address:     ");
         sb.append(ss.getInetAddress().getHostAddress());
         sb.append("\n");
      }   // end else if

      if (props != null) {
         sb.append("  Properties:\n");

         for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
            String next = (String) e.nextElement();
            sb.append("    ");
            sb.append(next);
            sb.append(" = ");
            sb.append(props.getProperty(next));
            sb.append("\n");
         }   // end for
      }   // end if

      return sb.toString();
   }   // end toString()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves the specified property from this connection manager.
    *
    * @param property the property name to retrieve.
    *
    * @return the associated property, or null if this property doesn't exist.
    */
   public String getProperty(String property) {
      return props.getProperty(property);
   }   // end getProperty()

// ---------------------------------------------------------------------------
   /** 
    * Sets the specified property to the specified value.
    *
    * @param property the property to set.
    * @param value the value to set the property to.
    */
   public void setProperty(String property, String value) {
      // Add the new XML element.
      if (getElement() != null) {
         if (getProperty(property) != null) {
            // Property already exists
            try {
               NodeList nodes = getElement().getElementsByTagName("parameter");

               if (nodes.getLength() != 0) {
                  for (int i = 0; i < nodes.getLength(); i++) {
                     Element next = (Element) nodes.item(i);
                     String paramName = next.getAttribute("name");

                     if ((paramName != null) && paramName.equals(property)) {
                        // Found what we're looking for...
                        Text text = (Text) next.getFirstChild();
                        next.removeChild(text);

                        Text newText = next.getOwnerDocument()
                              .createTextNode(value);
                        next.appendChild(newText);

                        continue;
                     }   // end if
                  }   // end for
               }   // end if
            }   // end try
            catch (DOMException e) {
               // A DOM Exception occurred.
               if (log != null) {
                  log.writeWarningEntry(
                     "We encountered a DOM Exception in ConnectionManager.setProperty():");
               }   // end if

               if (log != null) {
                  log.writeExceptionEntry(e);
               }   // end if
            }   // end catch
         }   // end if
         else {
            // This is a new property
            try {
               Element newElement = getElement().getOwnerDocument()
                     .createElement("parameter");
               newElement.setAttribute("name", property);

               Text newText = getElement().getOwnerDocument()
                     .createTextNode(value);
               newElement.appendChild(newText);
               getElement().appendChild(newElement);
            }   // end try
            catch (DOMException e) {
               // A DOM Exception occurred.
               if (log != null) {
                  log.writeWarningEntry(
                     "We encountered a DOM Exception in ConnectionManager.setProperty():");
               }   // end if

               if (log != null) {
                  log.writeExceptionEntry(e);
               }   // end if
            }   // end catch
         }   // end else
      }   // end if
      props.setProperty(property, value);
   }   // end setProperty()

// ---------------------------------------------------------------------------
   /** 
    * Removes the specified property.
    *
    * @param prop the property to be removed.
    */
   public void removeProperty(String prop) {
      if (getElement() != null) {
         try {
            NodeList nodes = getElement().getElementsByTagName("parameter");

            for (int i = 0; i < nodes.getLength(); i++) {
               Element next = (Element) nodes.item(i);
               String paramName = next.getAttribute("name");
               System.out.println("Found param \"" + paramName + "\"");

               if ((paramName != null) && paramName.equals(prop)) {
                  // Found what we're looking for...
                  System.out.println("Removing...");
                  getElement().removeChild(next);

                  continue;
               }   // end if
            }   // end for
         }   // end try
         catch (DOMException e) {
            // A DOM Exception occurred.
            if (log != null) {
               log.writeWarningEntry(
                  "We encountered a DOM Exception in ConnectionManager.removeProperty():");
            }   // end if

            if (log != null) {
               log.writeExceptionEntry(e);
            }   // end if
         }   // end catch
      }   // end if
      props.remove(prop);
   }   // end removeProperty()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves the socket address the ServerSocket is bound to.
    *
    * @return the socket address the ServerSocket is bound to.
    */
   public SocketAddress getBindAddress() {
      if (ss != null) {
         return ss.getLocalSocketAddress();
      }   // end if
      else {
         return new InetSocketAddress(bindAddress, port);
      }   // end else
   }   // end getBindAddress()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves a string describing the state of this connection
    * manager.
    *
    * @return a string describing the state of this connection manager.
    */
   public String getStatus() {
      if (! ss.isClosed()) {
         return "Service \"" + getServiceName() + "\" is bound to " +
         ss.getLocalSocketAddress().toString() +
         " and is accepting connections.";
      }   // end if
      else {
         return "Service \"" + getServiceName() +
         "\" is not currently accepting connections.";
      }   // end else
   }   // end getStatus()

// ---------------------------------------------------------------------------
   /** 
    * Retrieves the DOM Element that was used to create this object.
    * This method returns null if this object wasn't instantiated via a DOM
    * Element.
    *
    * @return the DOM Element that was used to create this object, or null if
    *         no Element was used in the creation of this object.
    */
   public Element getElement() {
      return element;
   }   // end getElement()

// ---------------------------------------------------------------------------
   /** 
    * A method to inform the connection manager that a disconnection has
    * occurred.
    */
   public synchronized void disconnectionAlert() {
      --numConnections;
   }   // end disconnectionAlert()

//~ ===========================================================================
}   // end ConnectionManager
