// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Ahmad H ahmadh@uvic.ca
// --------------------------------------------------------------------------
// OSI Certified Open Source Software
// --------------------------------------------------------------------------//
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 2 of the License, or (at your option)
// any later version.//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// more details.//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA//
// --------------------------------------------------------------------------
// $Id: DAOFactoryContract.java,v 1.0
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts;
import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;


/**
 * Contract for User class
 * @author Wenfeng Su
 * @version v1.0
**/
public class UserContract extends ContractBase<User>{

	User user;
	
	/**
	 * Constructor for the User class
	 * 
	 * @param user the user
	 */
	public UserContract(User user){
		super(user);
		this.user = user;
	}

	/**
	 * The class invariant for User class
	 *  
	 */
	public void classInvariant(){
		assert super.getTargetField("name") != null;
		assert super.getTargetField("path") != null;
	}

	/**
	 * The pre condition for setName method
	 * @param name string type of name
	 */
	public void pre_setName(String name){
		assert name != null;
	}

	/**
	 * The post condition for getPath method
	 */
	public void post_getPath(){
		String output = (String)super.getReturnValue();
		assert output != null;
	}

	/**
	 * The pre condition for setPath method
	 * @param path string type of path
	 */
	public void pre_setPath(String path){
		assert path != null;
	}

	/**
	 * The post condition for toString method
	 */
	public void post_toString(){	
		String output = (String)super.getReturnValue();
		assert output != null;
	}

}
