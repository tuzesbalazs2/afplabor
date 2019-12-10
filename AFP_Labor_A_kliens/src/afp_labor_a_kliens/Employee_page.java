/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */
public class Employee_page extends javax.swing.JFrame {

    /**
     * Creates new form List
     */
    public Employee_page() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(0, 153, 0) );  
        setTitle("Dolgozó oldal");
        betolt();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dolgozoneve_szoveg = new javax.swing.JTextPane();
        dolgozo_kep = new javax.swing.JLabel();
        Button_kepmodositas = new javax.swing.JButton();
        Button_modositas = new javax.swing.JButton();
        Button_export = new javax.swing.JButton();
        Button_kilepes = new javax.swing.JButton();
        copyright = new javax.swing.JTextPane();
        nev_szoveg = new javax.swing.JLabel();
        szulev_szoveg = new javax.swing.JLabel();
        fizetes_szoveg = new javax.swing.JLabel();
        varos_szoveg = new javax.swing.JLabel();
        utcahazszam_szoveg = new javax.swing.JLabel();
        nev_adat = new javax.swing.JTextField();
        szulev_adat = new javax.swing.JTextField();
        fizetes_adat = new javax.swing.JTextField();
        varos_adat = new javax.swing.JTextField();
        utcahazszam_adat = new javax.swing.JTextField();

        setResizable(false);

        dolgozoneve_szoveg.setBackground(new java.awt.Color(0, 0, 0));
        dolgozoneve_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        dolgozoneve_szoveg.setForeground(new java.awt.Color(255, 255, 255));
        dolgozoneve_szoveg.setText("Dolgozó Neve");
        dolgozoneve_szoveg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dolgozoneve_szoveg.setFocusable(false);

        dolgozo_kep.setText("kép");

        Button_kepmodositas.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Button_kepmodositas.setText("Módosítás");

        Button_modositas.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Button_modositas.setText("Módosítás");
        Button_modositas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_modositasActionPerformed(evt);
            }
        });

        Button_export.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Button_export.setText("Export");
        Button_export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Button_exportMousePressed(evt);
            }
        });
        Button_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_exportActionPerformed(evt);
            }
        });

        Button_kilepes.setBackground(new java.awt.Color(204, 0, 0));
        Button_kilepes.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Button_kilepes.setText("Kilépés");
        Button_kilepes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_kilepesActionPerformed(evt);
            }
        });

        copyright.setBackground(new java.awt.Color(0, 153, 0));
        copyright.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        copyright.setForeground(new java.awt.Color(255, 255, 255));
        copyright.setText("Copyright mindenség");
        copyright.setFocusable(false);

        nev_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        nev_szoveg.setText("Név:");

        szulev_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        szulev_szoveg.setText("Szül.év:");

        fizetes_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        fizetes_szoveg.setText("Fizetés:");

        varos_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        varos_szoveg.setText("Város:");

        utcahazszam_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        utcahazszam_szoveg.setText("Utca, házszám:");

        nev_adat.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        szulev_adat.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        fizetes_adat.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        varos_adat.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        utcahazszam_adat.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dolgozo_kep, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Button_kepmodositas)
                                .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(utcahazszam_szoveg)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(varos_szoveg)
                                    .addGap(198, 198, 198))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(szulev_szoveg)
                                        .addComponent(fizetes_szoveg))
                                    .addGap(181, 181, 181)))
                            .addComponent(nev_szoveg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(varos_adat)
                            .addComponent(nev_adat)
                            .addComponent(szulev_adat)
                            .addComponent(fizetes_adat)
                            .addComponent(utcahazszam_adat, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dolgozoneve_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 406, Short.MAX_VALUE)
                        .addComponent(Button_modositas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Button_export)))
                .addGap(18, 18, 18)
                .addComponent(Button_kilepes)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dolgozoneve_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Button_modositas)
                        .addComponent(Button_export)
                        .addComponent(Button_kilepes)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dolgozo_kep, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Button_kepmodositas))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nev_szoveg)
                                .addGap(64, 64, 64)
                                .addComponent(szulev_szoveg)
                                .addGap(64, 64, 64)
                                .addComponent(fizetes_szoveg)
                                .addGap(53, 53, 53)
                                .addComponent(varos_szoveg)))
                        .addGap(62, 62, 62)
                        .addComponent(utcahazszam_szoveg)
                        .addGap(55, 55, 55)
                        .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nev_adat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(szulev_adat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(fizetes_adat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(varos_adat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(utcahazszam_adat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Button_exportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_exportMousePressed
//System.out.println("PDF létrehozva");     
// TODO add your handling code here:

    }//GEN-LAST:event_Button_exportMousePressed

    private void Button_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_exportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Button_exportActionPerformed

    private void Button_kilepesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_kilepesActionPerformed
dispose();        
    }//GEN-LAST:event_Button_kilepesActionPerformed

    private void Button_modositasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_modositasActionPerformed
          try {
            
            PreparedStatement pstmt = AFP_Labor_A_Kliens.db.conn.prepareStatement("UPDATE dolgozo SET nev = ?, szul_ev = ?, fizetes = ?, varos = ?, utca_hsz = ? WHERE id = ?");
    pstmt.setString(1, nev_adat.getText());
    pstmt.setShort(2, Short.parseShort((szulev_adat.getText())));
    pstmt.setInt(3, Integer.parseInt(fizetes_adat.getText()));
    pstmt.setString(4, varos_adat.getText());
    pstmt.setString(5, utcahazszam_adat.getText());
    pstmt.setInt(6, Employee_list.index+1);
    pstmt.executeUpdate();
JOptionPane.showMessageDialog(null,"Sikeres módosítás!");
            } catch (Exception e) {
              System.out.println(e.getMessage());
              JOptionPane.showMessageDialog(null, "Hiba: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_Button_modositasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Employee_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee_page().setVisible(true);
            }
        });
    }
    
    public void betolt(){
        try {
            int ind = Employee_list.index+1;
    Statement st = AFP_Labor_A_Kliens.db.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, nev, szul_ev, fizetes, varos, utca_hsz FROM dolgozo WHERE id="+ind);
            rs.next();
            nev_adat.setText(rs.getString("nev"));
            szulev_adat.setText(Integer.toString(rs.getInt("szul_ev")));
            fizetes_adat.setText(Integer.toString(rs.getInt("fizetes")));
            varos_adat.setText(rs.getString("varos"));
            utcahazszam_adat.setText(rs.getString("utca_hsz"));
            } catch (Exception e) {
              System.out.println(e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_export;
    private javax.swing.JButton Button_kepmodositas;
    private javax.swing.JButton Button_kilepes;
    private javax.swing.JButton Button_modositas;
    private javax.swing.JTextPane copyright;
    private javax.swing.JLabel dolgozo_kep;
    private javax.swing.JTextPane dolgozoneve_szoveg;
    private javax.swing.JTextField fizetes_adat;
    private javax.swing.JLabel fizetes_szoveg;
    private javax.swing.JTextField nev_adat;
    private javax.swing.JLabel nev_szoveg;
    private javax.swing.JTextField szulev_adat;
    private javax.swing.JLabel szulev_szoveg;
    private javax.swing.JTextField utcahazszam_adat;
    private javax.swing.JLabel utcahazszam_szoveg;
    private javax.swing.JTextField varos_adat;
    private javax.swing.JLabel varos_szoveg;
    // End of variables declaration//GEN-END:variables
}
