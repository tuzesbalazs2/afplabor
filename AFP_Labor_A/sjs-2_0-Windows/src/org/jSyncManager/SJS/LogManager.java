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

import java.io.*;

import java.text.SimpleDateFormat;

import java.util.Date;


//~ ===========================================================================
/** 
 * A class for managing log files. This class will create a log file with
 * the current date/time as the filename, with the specified file extension.
 * When the log reaches a specified size (default of 2MB), a new log file will
 * be created.
 *
 * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
 * @version 0.1
 */
public class LogManager {
//~  ========================================================================

   /** Variable definition... */
   private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
         "yyyyMMddHHmmss");

   /** General log category string */
   private static final String GENERAL = "GEN";

   /** Critical log category string */
   private static final String CRITICAL = "CRT";

   /** Warning log category string */
   private static final String WARNING = "WRN";

   /** Exception log category string */
   private static final String EXCEPTION = "EXC";

//~  ========================================================================

   /** File object representing the log file */
   private File logFile = null;

   /** FileWriter for the log file */
   private FileWriter fw = null;

   /** PrintWriter for the log file */
   private PrintWriter pw = null;

   /** Log file extension */
   private String ext = null;

   /** Name of the module to be logged */
   private String moduleName = null;

   /** Path to the log file */
   private String path = null;

   /** Maximum log file size in Megabytes: Default to 2MB */
   private long maxLogSize = 2 * 1024 * 1024;

//~  ========================================================================

   /** Creates a new Log Manager instance.
     * @param filePath the path to write the log file to.
     * @param fileExtension the extension to use for the filename.
     * @param moduleName the name of the module to be logged.
     * @exception IOException if an I/O exception occurs during construction.
     */
   public LogManager(String filePath, String fileExtension, String moduleName)
      throws IOException {
      File temp = new File(filePath);

      if (! temp.exists()) {
         temp.mkdirs();
      }   // end if

      if (! temp.isDirectory()) {
         throw new IOException("\"filePath\" is not a directory!: " + filePath);
      }   // end if

      this.moduleName = moduleName;
      ext = fileExtension;
      path = filePath;

      makeLogFile();
   }   // end LogManager()

// ---------------------------------------------------------------------------
   /** Creates a new Log Manager instance.
     * @param filePath the path to write the log file to.
     * @param fileExtension the extension to use for the filename.
     * @param moduleName the name of the module to be logged.
     * @param maxSize the maximum size that the log file can reach before creating a new log file.
     * @exception IOException if an I/O excepttion occurs during construction.
     */
   public LogManager(String filePath, String fileExtension, String moduleName,
      long maxSize) throws IOException {
      this(filePath, fileExtension, moduleName);
      maxLogSize = maxSize;
   }   // end LogManager()

//~  ========================================================================

   /** 
    * Create the log file.
    */
   private void makeLogFile() {
      logFile = new File(path + File.separator +
            LogManager.dateFormatter.format(new Date()) + "." + ext);

      try {
         close();
         fw = new FileWriter(logFile);
         pw = new PrintWriter(fw, true);
      }   // end try
      catch (IOException e) {
         e.printStackTrace(System.err);
      }   // end catch
   }   // end makeLogFile()

// ---------------------------------------------------------------------------
   /** 
    * Writes a log file entry.
    *
    * @param type the entry type.
    * @param message message to log.
    */
   private void writeLogEntry(String type, String message) {
      if (logFile.length() > maxLogSize) {
         // Time to create a new log file.
         makeLogFile();
      }   // end if

      StringBuffer sb = new StringBuffer();
      sb.append(LogManager.dateFormatter.format(new Date()));
      sb.append(" : ");
      sb.append(type);
      sb.append(" : ");
      sb.append(moduleName);
      sb.append(" : ");
      sb.append(message);

      pw.println(sb.toString());
   }   // end writeLogEntry()

// ---------------------------------------------------------------------------
   /** 
    * Writes a general information log entry to the log file.
    *
    * @param message the message to be logged.
    */
   public void writeGeneralEntry(String message) {
      writeLogEntry(LogManager.GENERAL, message);
   }   // end writeGeneralEntry()

// ---------------------------------------------------------------------------
   /** 
    * Writes a critical information log entry to the log file.
    *
    * @param message the message to be logged.
    */
   public void writeCriticalEntry(String message) {
      writeLogEntry(LogManager.CRITICAL, message);
   }   // end writeCriticalEntry()

// ---------------------------------------------------------------------------
   /** 
    * Writes a warning information log entry to the log file.
    *
    * @param message the message to be logged.
    */
   public void writeWarningEntry(String message) {
      writeLogEntry(LogManager.WARNING, message);
   }   // end writeWarningEntry()

// ---------------------------------------------------------------------------
   /** 
    * Writes an exception information log entry to the log file.
    *
    * @param message the message to be logged.
    */
   public void writeExceptionEntry(String message) {
      writeLogEntry(LogManager.EXCEPTION, message);
   }   // end writeExceptionEntry()

// ---------------------------------------------------------------------------
   /** 
    * Writes a throwable (exception/error) object to the log file. Calls
    * to this method will cause the throwables text and stack trace to be
    * written to the log file.
    *
    * @param exception the throwable to be logged.
    */
   public void writeExceptionEntry(Throwable exception) {
      writeLogEntry(LogManager.EXCEPTION, exception.toString());
      exception.printStackTrace(pw);
   }   // end writeExceptionEntry()

// ---------------------------------------------------------------------------
   /** 
    * Closes the log file.
    */
   public void close() {
      try {
         if (pw != null) {
            pw.close();
         }   // end if

         if (fw != null) {
            fw.close();
         }   // end if
      }   // end try
      catch (IOException e) {
         writeWarningEntry("We were unable to close the log file properly.");
      }   // end catch
   }   // end close()

// ---------------------------------------------------------------------------
   /** 
    * Finalizes the log file.
    */
   protected void finalize() {
      close();
   }   // end finalize()

//~ ===========================================================================
}   // end LogManager
