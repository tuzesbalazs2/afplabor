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


//~ ===========================================================================
/** An interface for adapters to pass messages to their Connection Manager.
  * It is generally undesirable to permit adapters access to all the functionality in
  * the Connection Manager.  As such, this interface is used to allow controlled access
  * to the specific connection manager implementation in use for this adapter type.
  * @version 0.1
  * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
  */
public interface ConnectionManagerInterface {
//~  ========================================================================

   /** 
    * A method to inform the connection manager that a disconnection has
    * occurred.
    */
   public void disconnectionAlert();

//~ ===========================================================================
}   // end ConnectionManagerInterface
