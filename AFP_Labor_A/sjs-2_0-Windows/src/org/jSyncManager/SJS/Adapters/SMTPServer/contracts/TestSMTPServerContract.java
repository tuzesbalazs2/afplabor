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

package org.jSyncManager.SJS.Adapters.SMTPServer.contracts;

import net.sourceforge.c4j.ContractBase;

import org.jSyncManager.SJS.Adapters.SMTPServer.TestSMTPServer;


/** 
 * Contract for TestSMTPServer class This class will inherit all the the
 * contracts from AbstractSMTPServer and add the below.
 *
 * @author Wenfeng Su
 * @version v1.0
 *
 * @see AbstractSMTPServerContract
 */
public class TestSMTPServerContract extends ContractBase<TestSMTPServer> {
   //~  ========================================================================

/**
    * Constructor for TestSMTPServer class
    * @param testserver the server object
    */
   public TestSMTPServerContract(TestSMTPServer testserver) {
      super(testserver);
   }   // end TestSMTPServerContract()

   //~  ========================================================================

   /** 
    * Class invariants for TestSMTPServer class
    */
   public void classInvariant() {
      assert super.getTargetField("domain") != null;

      //the server cannot be null
      assert super.getTargetField("server") != null;

      //the server has to be a self reference
      assert super.getTargetField("server") != super.getTargetField("server");
   }   // end classInvariant()
}   // end TestSMTPServerContract
