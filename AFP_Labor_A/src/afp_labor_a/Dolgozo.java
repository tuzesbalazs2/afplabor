/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a;

/**
 *
 * @author Tóth_Zsolt
 */
public class Dolgozo {

    private String nev;
    private short szul_ev;
    private String varos;
    private int fizetes;
    private String utca_hsz;

    private String getNev() {
        return this.nev;
    }

    private void setNev(String value) {
        try {
        if (value.length() < 1 || !value.contains(" ") || !value.matches("[a-zA-Z]+"))
            throw new Exception();
        this.nev = value;
    }
        catch (Exception e) {System.out.println("Rossz név");};
    }

    private short getSzul_ev() {
        return this.szul_ev;
    }

    private void setSzul_ev(short value) {
        //TODO..
        try {
        if (value < 1000 || value > 2000)
            throw new Exception();
        this.szul_ev = value;
    }
        catch (Exception e) {System.out.println("Rossz szülrtzési év");};
    }

    private String getVaros() {
        return this.varos;
    }

    private void setVaros(String value) {
        try {
        if (value.length() < 1)
            throw new Exception();
        this.varos = value;
    }
        catch (Exception e) {System.out.println("Rossz Város");}
    }

    private int getFizetes() {
        return this.fizetes;
    }

    private void setFizetes(int value) {
        //TODO..
        try {
        if (value < 138000)
            throw new Exception();
        this.fizetes = value;
    }
        catch (Exception e) {System.out.println("Rossz fizetés érték");};
    }

    private String getUtca_hsz() {
        return this.utca_hsz;
    }

    private void setUtca_hsz(String value) {
        try {
        if (value.length() < 1 || !value.matches(".*\\d+.*"))
            throw new Exception();
        this.utca_hsz = value;
    }
        catch (Exception e) {System.out.println("Rossz utca/házszám");}
    }

    public Dolgozo(String nev, short szul_ev, String varos, int fizetes, String utca_hsz) {
        this.setNev(nev);
        this.setSzul_ev(szul_ev);
        this.setVaros(varos);
        this.setFizetes(fizetes);
        this.setUtca_hsz(utca_hsz);
    }
}
