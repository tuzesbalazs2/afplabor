package org.jSyncManager.SJS.Adapters.SMTPServer;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;

import net.sourceforge.c4j.ContractReference;


/**
 * Checks the MX record and returns the follow 3 possibilities:
 * mybb.ca has 0 mail servers
 * mybb.ca has 2 mail servers:  mail1.backbonesystems.org 
 * tangwenfans.com : DNS name not found [response code 3]
 * 
 * @author tianfan
 * 
 */
@ContractReference(contractClassName="org.jSyncManager.SJS.Adapters.SMTPServer.contracts.DNSUtilContract")
public class DNSUtil {
//~  ========================================================================

   /**
	 * calls doLookup function, and gets the mx record info
	 * @param domain
	 * @return the string of mx record
	 */
	public static String MXLookup( String domain ) {

		String return_meg;	

		try {
			return_meg = doLookup( domain );
		}
		catch( Exception e ) {
			return_meg = null;
		}

		return return_meg;

	}	

// ---------------------------------------------------------------------------

	/**
	 * gets the mx record info and format it into string 
	 * @param hostName
	 * @return the string of mx record
	 * @throws NamingException
	 */
	static String doLookup( String hostName ) throws NamingException {
		
	    String return_str;	
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext( env );
		Attributes attrs = 
			ictx.getAttributes( hostName, new String[] { "MX" });
		Attribute attr = attrs.get( "MX" );
		if( attr == null ) {
			return_str = null;
		} else if ( attr.size() == 1 ) {
			String tmp = attr.toString();
			tmp = tmp.substring( 6, tmp.length() - 1  );
			return_str = tmp.replace(" ", "");

		} else {
			String tmp = attr.toString();
			tmp = tmp.substring( 6, tmp.indexOf(",")-1 );
			return_str = tmp.replace(" ", "");
		}
		return return_str;
	}
//~  ========================================================================
	
	public static void main(String[] args) {
		System.out.println(MXLookup("mybbs.ca"));
	}
}

