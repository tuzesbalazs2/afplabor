// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) YEAR AUTHOR_NAME <AUTHOR_EMAIL>
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
// $Id: FILE_NAME.java,v 0.1 CREATION_DATE CREATION_TIME SOURCEFORGE_ID Exp $
// --------------------------------------------------------------------------

package org.jSyncManager.SJS.Adapters.SMTPServer.Config;


//~  ========================================================================
/** 
 * This class represents an email user used for sending, 
 * receiving, and storing messages
 * 
 * @author $author$
 * @version $Revision$
  */
public class User {
//~  ========================================================================

   /** the name of the user being created */
   private String name;

   /** the path to the user's mbox directory */
   private String path;

//~  ========================================================================

   /**
    * Constructor, default and empty
    */
   public User(){
	   
   }
   
   /**
    * Constructor
    * @param name the name of the user being created
    * @param path the path to the user's mbox directory
    */
   public User(String name, String path){
	   this.name = name;
	   this.path = path;
   }
   
// ~  ========================================================================
   /** 
    * gets the name of the user 
    *
    * @return string of the name  
    */
   public String getName() {
      return name;
   }   // end getName()

// ---------------------------------------------------------------------------
   /** 
    * sets the user name to the given parameter 
    *
    * @param name string type 
    */
   public void setName(String name) {
      this.name = name;
   }   // end setName()

// ---------------------------------------------------------------------------
   /** 
    * returns the path to user's mbox 
    *
    * @return string type of path 
    */
   public String getPath() {
      return path;
   }   // end getPath()

// ---------------------------------------------------------------------------
   /** 
    * sets the path to user's mbox path 
    *
    * @param path parameter value
    */
   public void setPath(String path) {
      this.path = path;
   }   // end setPath()

// ---------------------------------------------------------------------------
   /** 
    * Returns the user's name.
    *
    * @return returned
    */
   public String toString() {
      return this.name;
   }   // end toString()
   
// ~  ========================================================================
}   // end User
