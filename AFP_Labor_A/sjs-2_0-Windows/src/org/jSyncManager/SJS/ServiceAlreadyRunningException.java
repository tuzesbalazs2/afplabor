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


//~ ===========================================================================
/** 
 * The ServiceAlreadyRunningException class. Instances of this class are
 * thrown when a ServerRemoteInterface instance are unable to find the
 * requested service.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public class ServiceAlreadyRunningException extends Exception {
//~  ========================================================================

   /** Constructs a new ServiceAlreadyRunningException.
     */
   public ServiceAlreadyRunningException() {
      super();
   }   // end ServiceAlreadyRunningException()

// ---------------------------------------------------------------------------
   /** Constructs a new ServiceAlreadyRunningException with the specified message.
     * @param msg the message to attach to this exception.
     */
   public ServiceAlreadyRunningException(String msg) {
      super(msg);
   }   // end ServiceAlreadyRunningException()

//~ ===========================================================================
}   // end ServiceAlreadyRunningException
