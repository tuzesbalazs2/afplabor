package org.jSyncManager.SJS.installtools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class PropertyConverter {
	private static final String INDENT = "   ";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static String TAG = "TAG";
	private static String CHILDREN = "CHILDREN";
	private static String ARRAY_IDENTIFIER = "@";
	private static String ARRAY_DELIMITER = "\\s*,\\s*";
	private static String VALUE = "VALUE";
	
	private static void convertFile(File propertyFile, File xmlFile) throws IOException {
		HashSet<String> childNodes = new HashSet<String>();
		ArrayList<Element> elements = new ArrayList<Element>();
		
		createElementMap(propertyFile,elements,childNodes);
		writeElementMap(xmlFile,elements,childNodes);
	}
	
	private static class Element implements Cloneable{
		String name;
		HashMap<String,String> params = new HashMap<String,String>();
		Element(String name){
			this.name = name;
		}
		
		public boolean equals(Object o){
			if (o instanceof Element){
				Element that = (Element)o;
				return (this.name.equalsIgnoreCase(that.name));
			} else if (o instanceof String){
				String that = (String)o;
				return (this.name.equalsIgnoreCase(that));
			}
			return false;
		}
		
		public Element clone() {
			Element copy = new Element(name);
			copy.params.putAll(params);
			return copy;
		}
		
		public String toString(){
			return name + "{" + params.toString() + "} ";
		}
	}
	
	private static void createElementMap(File propertyFile, ArrayList<Element> elements, HashSet<String> childNodes)  throws IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(propertyFile));
		
		Iterator<Object> propItr = props.keySet().iterator();
		while(propItr.hasNext()){
			String key = (String)propItr.next();
			String value = props.getProperty(key);
			String[] keyparts = key.split("\\.");
			if (keyparts.length != 2){
				throw new IllegalArgumentException("All property file entries must be  of the form element.param=value");
			}
			String name = keyparts[0];
			String param = keyparts[1];
			
			Element newelement = new Element(name);
			if (!elements.contains(newelement)){
				// first sighting of this element name
				elements.add(newelement);
			}
			
			if (param.startsWith(ARRAY_IDENTIFIER)){
				param = param.substring(ARRAY_IDENTIFIER.length()); // remove the identifier
				
				String[] values = value.split(ARRAY_DELIMITER);
				// get the reference to the first one
				Element original = elements.get(elements.indexOf(new Element(name)));
				for(int i=1; i<values.length; i++){
					Element copy = original.clone();
					copy.params.put(param, values[i]);
					elements.add(copy);
				}
				original.params.put(param, values[0]);
				
			} else {
				Iterator<Element> elementItr = elements.iterator();
				while(elementItr.hasNext()){
					Element elem = elementItr.next();
					if (!elem.equals(name)){ continue; }
					elem.params.put(param, value);
				}
			}
			
			if (param.equalsIgnoreCase(CHILDREN)){
				// store all children for use later in finding out which are the root elements (never children)
				List<String> children = Arrays.asList(getChildren(value));
				childNodes.addAll(children);
			}
		}
	}
	
	private static String[] getChildren(String s){
		return s.split("\\s*,\\s*");
	}

	private static void writeElementMap(File xmlFile, ArrayList<Element>elements, HashSet<String> childNodes) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(xmlFile));
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		//bw.write("Writing to standard out\n");
		
		bw.write(XML_HEADER + "\n");
		
		Iterator<Element> elementItr = elements.iterator();
		while(elementItr.hasNext()){
			String elementName = elementItr.next().name;
			if (!childNodes.contains(elementName)){
				// write only top level elements (non children)
				writeElement(bw,"",elementName,elements);
			}
		}
		
		bw.flush();
		bw.close();
	}
	
	private static void writeElement(BufferedWriter bw, String indent, String name, ArrayList<Element> elements) throws IOException {
		if (!elements.contains(new Element(name))){
			throw new IllegalArgumentException("Missing tag definition for " + name);
		}
		
		Iterator<Element> elementItr = elements.iterator();
		while(elementItr.hasNext()){
			Element element = elementItr.next();
			if (!element.equals(name)){ continue; }
			
			writeElement(bw,indent,element,elements);
		}
	}

	private static void writeElement(BufferedWriter bw, String indent, Element element, ArrayList<Element> elements) throws IOException {
		HashMap<String,String> params = element.params;

		String tag = params.get(TAG);
		
		bw.write(indent + "<" + tag + " ");
		Iterator<String> paramItr = params.keySet().iterator();
		while(paramItr.hasNext()){
			String paramName = paramItr.next();
			if (!paramName.equalsIgnoreCase(TAG) && !paramName.equalsIgnoreCase(CHILDREN) && !paramName.equalsIgnoreCase(VALUE)){
				String paramValue = params.get(paramName);
				bw.write(paramName + "=\"" + paramValue + "\" ");
			}
		}
		bw.write(">\n");
		
		String value = params.get(VALUE);
		if (value != null){
			// value should be indented one level more
			bw.write(INDENT + indent + value + "\n");
		}
		
		String children = params.get(CHILDREN);
		if (children != null && children.length() > 0){
			String[] childrenParts = getChildren(children);
			for(int i=0; i<childrenParts.length; i++){
				writeElement(bw,indent + INDENT, childrenParts[i], elements);
			}
		}
		
		bw.write(indent + "</" + tag + ">\n");
	}

	public static void main(String args[]) throws IOException {
		if (args.length != 2){
			System.out.println("Usage: PropertyConverter propertyfile xmlfile");
			System.exit(1);
		}
		
		File propertyFile = new File(args[0]);
		File xmlFile = new File(args[1]);
		
		if (!propertyFile.exists()){
			System.out.println("Error: input properties file " + args[0] + " does not exist");
			System.exit(2);
		}
		
		convertFile(propertyFile,xmlFile);
	}
	
	
}
