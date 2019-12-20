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
//        String username = "";
//        String password = "";
//        Database instance = null;
//        String expResult = "";
//        String result = instance.login(username, password);
//        assertEquals(expResult, result);
        Database test = new Database("jdbc:mysql://85.10.205.173:3306/afplabor_rft?useSSL=false", "afplabor_rft", "felhasznalo1", "abcdefgh");
        assertEquals("bejelentkezesjo", test.login("a", "a"));
        assertEquals("bejelentkezeshiba", test.login("ááááááááááá", "ááááááááááá"));        

        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    /**
//     * Test of select method, of class Database.
//     */
//    @Test
//    public void testSelect() throws Exception {
//        System.out.println("select");
//        String q = "";
//        Database instance = null;
//        instance.select(q);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of list method, of class Database.
//     */
//    @Test
//    public void testList() throws Exception {
//        System.out.println("list");
//        Database instance = null;
//        String expResult = "";
//        String result = instance.list();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of employee_page method, of class Database.
//     */
//    @Test
//    public void testEmployee_page() throws Exception {
//        System.out.println("employee_page");
//        int ind = 0;
//        Database instance = null;
//        String expResult = "";
//        String result = instance.employee_page(ind);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of dolgozo_delete method, of class Database.
//     */
//    @Test
//    public void testDolgozo_delete() throws Exception {
//        System.out.println("dolgozo_delete");
//        int ind = 0;
//        Database instance = null;
//        String expResult = "";
//        String result = instance.dolgozo_delete(ind);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of dolgozo_insert method, of class Database.
//     */
//    @Test
//    public void testDolgozo_insert() throws Exception {
//        System.out.println("dolgozo_insert");
//        String nev = "";
//        short szul_ev = 0;
//        int fizetes = 0;
//        String varos = "";
//        String utca_hsz = "";
//        Database instance = null;
//        String expResult = "";
//        String result = instance.dolgozo_insert(nev, szul_ev, fizetes, varos, utca_hsz);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of dolgozomodosit method, of class Database.
//     */
//    @Test
//    public void testDolgozomodosit() throws Exception {
//        System.out.println("dolgozomodosit");
//        String[] msgsplit = null;
//        Database instance = null;
//        String expResult = "";
//        String result = instance.dolgozomodosit(msgsplit);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of reg_insert method, of class Database.
//     */
//    @Test
//    public void testReg_insert() throws Exception {
//        System.out.println("reg_insert");
//        String username = "";
//        String password = "";
//        Database instance = null;
//        String expResult = "";
//        String result = instance.reg_insert(username, password);
//        assertEquals(expResult, result);
//    }
//    
}
