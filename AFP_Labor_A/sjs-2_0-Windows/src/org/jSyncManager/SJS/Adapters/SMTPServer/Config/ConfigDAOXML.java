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

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sourceforge.c4j.ContractReference;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;


//~  ========================================================================
/** 
 * Implementation of the ConfigDAO with an xml datastore.
 * @author $author$
 * @version $Revision$
  *
 * @param <T> parameter value
 * @param <ID> parameter value
 */
public class ConfigDAOXML<T, ID extends Serializable> implements ConfigDAO {
//~  ========================================================================

   /** 
    * Loads the configuration from an XML file 
    *
    * @param configFile the path to the configuration file as a String
    *
    * @return A Configuration implementation encapsulating the details of the configuration file
    */
   public Configuration loadConfig(String configFile) {
      try {
         File file = new File(configFile);
         InputStream xml = new FileInputStream(file);

         SAXParserFactory spf = SAXParserFactory.newInstance();
         spf.setValidating(false);
         spf.setNamespaceAware(false);

         SAXParser sp = spf.newSAXParser();

         PlayerHandler handler = new PlayerHandler();

         sp.parse(xml, handler);

         return handler.getConfiguration();
      }   // end try
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }   // end catch
      catch (SAXException e) {
         e.printStackTrace();
      }   // end catch
      catch (IOException e) {
         e.printStackTrace();
      }   // end catch
      catch (ParserConfigurationException e) {
         e.printStackTrace();
      }   // end catch

      return null;
   }   // end loadConfig()

//~  ========================================================================

   /** 
    * Inner class which listens to SAX events to load a configuration from 
    * 
    * @author $author$
    * @version $Revision$
     */
   @ContractReference(contractClassName = "org.jSyncManager.SJS.Adapters.SMTPServer.Config.contracts.PlayerHandler")
   public static final class PlayerHandler extends DefaultHandler2 {
//~  =====================================================================

      /** Contains the attributes for a given XML element during parsing */
      private Attributes attrs;

      /** Character buffer used during parsing */
      private CharArrayWriter text = new CharArrayWriter();

      /** Configuration object to add parameter values to */
      private Configuration config = Configuration.INSTANCE;

//~  =====================================================================

      /** 
       * Opening tag of an XML element is encountered. Load the attributes and 
       * store them for use later. 
       *
       * @param namespaceURI namespace of element
       * @param lname local name of the element
       * @param qname qname of the element
       * @param attrs the attributes of the element
       */
      public void startElement(String namespaceURI, String lname, String qname,
         Attributes attrs) {
         this.attrs = attrs;
         text.reset();
      }   // end startElement()

//    ---------------------------------------------------------------------------
      /** 
       * Called when a closing tag is encountered. Uses the attributes 
       * loaded when the start tag is encountered.
       * @param uri namespace of the URI of the element
       * @param localName local name of the element
       * @param qname qname of the element
       */
      public void endElement(String uri, String localName, String qname) {
         if (qname.equalsIgnoreCase("user")) {
            User user = new User();
            user.setName(attrs.getValue("name"));
            user.setPath(attrs.getValue("path"));
            config.getUsers().put(user.getName(), user);
         }   // end if

         if (qname.equalsIgnoreCase("domain")) {
            Domain domain = new Domain();
            domain.setDomain(attrs.getValue("name"));
            config.getDomains().put(domain.getDomain(), domain);
         }   // end if
      }   // end endElement()

//    ---------------------------------------------------------------------------
      /** 
       * Load the text and trim the text string nicely 
       *
       * @return string type of the text 
       */
      public String getText() {
         return text.toString().trim();
      }   // end getText()

//    ---------------------------------------------------------------------------
      /** 
       * Convenience method used to write an array of characters to the text buffer
       *
       * @param ch the characters to write to the text buffer
       * @param start start offset of the data
       * @param length end offset of the data
       */
      public void characters(char[] ch, int start, int length) {
         text.write(ch, start, length);
      }   // end characters()

//    ---------------------------------------------------------------------------
      /** 
       * Gets the loaded configuration object 
       *
       * @return returns the loaded configuration
       */
      public Configuration getConfiguration() {
         return config;
      }   // end getConfiguration()
      
//    ~  ========================================================================
   }   // end PlayerHandeler
   
// ~  ========================================================================
}   // end ConfigDAOXML
