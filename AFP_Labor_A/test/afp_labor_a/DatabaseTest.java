/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tüzes
 */
public class DatabaseTest {
    Database test = new Database("jdbc:mysql://85.10.205.173:3306/afplabor_rft?useSSL=false", "afplabor_rft", "felhasznalo1", "abcdefgh");
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

//    /**
//     * Test of connect method, of class Database.
//     */
//    @Test
//    public void testConnect() throws Exception {
//        System.out.println("connect");
//        Database instance = null;
//        instance.connect();
//    }
//
//    /**
//     * Test of close method, of class Database.
//     */
//    @Test
//    public void testClose() {
//        System.out.println("close");
//        Database instance = null;
//        instance.close();
//    }


    
     /**
     * Test of login method, of class Database.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        
//        test = new Database("jdbc:mysql://85.10.205.173:3306/afplabor_rft?useSSL=false", "afplabor_rft", "felhasznalo1", "abcdefgh");
        assertEquals("bejelentkezesjo", test.login("a", "a"));
        assertEquals("bejelentkezeshiba", test.login("ááááááááááá", "ááááááááááá"));        

        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }


//    /**
//     * Test of list method, of class Database.
//     */
    @Test
    public void testList() throws Exception {
        System.out.println("list");
        //Database test = new Database("jdbc:mysql://85.10.205.173:3306/afplabor_rft?useSSL=false", "afplabor_rft", "felhasznalo1", "abcdefgh");
        String[] msgsplit = {""};
        msgsplit = test.list().split("\\$\\$\\$");
        assertEquals("listajo", msgsplit[0]);
        //assertEquals("bejelentkezeshiba", test.list());   
    }
//
//    /**
//     * Test of employee_page method, of class Database.
//     */
    @Test
    public void testEmployee_page() throws Exception {
        System.out.println("employee_page");
        
            String[] msgsplit = {""};
            msgsplit = test.employee_page(0).split("\\$\\$\\$");
            assertEquals("dolgozoadatjo", msgsplit[0]);
    }
//
//    /**
//     * Test of dolgozo_delete method, of class Database.
//     */
    @Test
    public void testDolgozo_delete() throws Exception {
        System.out.println("dolgozo_delete");
        
            String[] msgsplit = {""};
            msgsplit = test.dolgozo_delete(0).split("\\$\\$\\$");
            assertEquals("dolgozotoroljo", msgsplit[0]);
    }
//
//    /**
//     * Test of dolgozo_insert method, of class Database.
//     */
    @Test
    public void testDolgozo_insert() throws Exception {
        System.out.println("dolgozo_insert");
        
Short sdzulev = 1990;
         String[] msgsplit = {""};
            msgsplit = test.dolgozo_insert("béla", sdzulev, 0, "vártalan", "utsz_hsz").split("\\$\\$\\$");
            assertEquals("dolgozofelviteljo", msgsplit[0]);
    }

    /**
     * Test of dolgozomodosit method, of class Database.
     */
    @Test
    public void testDolgozomodosit() throws Exception {
        System.out.println("dolgozomodosit");
         String[] stringthing = {"dolgozomodosit", "Tóth Béla", "1990", "20000", "Budapest", "József utca 10.", "1"};
         String[] msgsplit = test.dolgozomodosit(stringthing).split("\\$\\$\\$");
            assertEquals("dolgozomodositjo", msgsplit[0]);
         String[] stringthing2 = {"dolgozomodosit", "Tóth Béla", "szülév", "fizetés", "Budapest", "József utca 10.", "1"};
         String[] msgsplit2 = test.dolgozomodosit(stringthing2).split("\\$\\$\\$");
            assertEquals("dolgozomodosíthiba", msgsplit2[0]);
    }

    /**
     * Test of reg_insert method, of class Database.
     */
    @Test
    public void testReg_insert() throws Exception {
        System.out.println("reg_insert");

        assertEquals("regisztraciorossz", test.reg_insert("d", "d"));
    }
    
}
