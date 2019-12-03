// --------------------------------------------------------------------------
// jSyncManager.org Simple Java Server -- Source File.
// Copyright (c) 2007 Torben WERNER <teisler@uvic.ca>
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
// $Id: SMTPUtils.java,v 1.0 2007/10/31 23:00:00 torbenwerner Exp $
// --------------------------------------------------------------------------
package org.jSyncManager.SJS.Adapters.SMTPServer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.c4j.ContractReference;

//~  ========================================================================
/**
 * Utility class to assist with some commonly performed SMTP functionality. Should
 * probably be static.
 *
 * @author Torben Werner
 */
@ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.util.SMTPUtilsContract")
public class SMTPUtils {

//	~  ========================================================================
	/** Simple email expression. Doesn't allow numbers in the domain name and doesn't allow
    * for top level domains that are less than 2 or more than 3 letters.
	 * TO-DO: Check with Brad for what we consider an acceptable regex for valid e-mail addresses
	 */
	public static final String addressRegex = "^(\\w+)@([a-zA-Z_]+?\\.[a-zA-Z]{2,3})$";

	/** The same as addressRegex except that it puts the user in group 1 and the domain in group 2
	 * E.G. for some address of the form "xyz@example.com" group 1 in the regex will contain "xyz"
	 * and group 2 will contain "example.com"
	 */
	public static final String groupedAddressRegex = "^(\\w+)@([a-zA-Z_]+?\\.[a-zA-Z]{2,3})$";

//	~  ========================================================================

	/**
	 * Checks whether an e-mail address is correct (regex might need to change according to our standard)
	 * @param address the address string to be validated
	 * @return true if the address is valid, false otherwise
	 */
	public static boolean isValidAddress(String address){

		// Check for some basic problems first (maybe save us from doing regex)
		if (address.contains("@") && !address.contains(" ")) {


	         Pattern pattern = Pattern.compile(addressRegex);
	         Matcher matcher = pattern.matcher(address);

	         // ONLY return true if the address matches the regex
	         if(matcher.matches()){
	        	 return true;
	         }   // end if

	      return false;
		}   // end if

		return false;
	}   // end isValidAddress()

//	~  ========================================================================
}   // end SMTPUtils
