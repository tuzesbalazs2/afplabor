/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */
public class Employee_add extends javax.swing.JFrame {

    /**
     * Creates new form List
     */
    public Employee_add() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(0, 153, 0) );  
        setTitle("Dolgozó hozzáadása");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dolgozoadatfelvitel_szoveg = new javax.swing.JTextPane();
        dolgozo_kep = new javax.swing.JLabel();
        Button_feltolt = new javax.swing.JButton();
        Button_felvitel = new javax.swing.JButton();
        Button_kilepes = new javax.swing.JButton();
        copyright = new javax.swing.JTextPane();
        dolgozo_neve_szoveg = new javax.swing.JTextPane();
        dolgozo_neve_input = new javax.swing.JTextField();
        dolgozo_nev = new javax.swing.JTextField();
        dolgozo_szul_ev = new javax.swing.JTextField();
        dolgozo_fizetes = new javax.swing.JTextField();
        dolgozo_varos = new javax.swing.JTextField();
        dolgozo_utca_hsz = new javax.swing.JTextField();
        nev_szoveg = new javax.swing.JLabel();
        szulev_szoveg = new javax.swing.JLabel();
        fizetes_szoveg = new javax.swing.JLabel();
        varos_szoveg = new javax.swing.JLabel();
        utcahazszam_szoveg = new javax.swing.JLabel();

        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        dolgozoadatfelvitel_szoveg.setBackground(new java.awt.Color(0, 0, 0));
        dolgozoadatfelvitel_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        dolgozoadatfelvitel_szoveg.setForeground(new java.awt.Color(255, 255, 255));
        dolgozoadatfelvitel_szoveg.setText("Dolgozó adat felvitel");
        dolgozoadatfelvitel_szoveg.setFocusable(false);

        dolgozo_kep.setText("kép");

        Button_feltolt.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Button_feltolt.setText("Feltöltés");

        Button_felvitel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Button_felvitel.setText("Felvitel");
        Button_felvitel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_felvitelActionPerformed(evt);
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

        dolgozo_neve_szoveg.setBackground(new java.awt.Color(0, 0, 0));
        dolgozo_neve_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        dolgozo_neve_szoveg.setForeground(new java.awt.Color(255, 255, 255));
        dolgozo_neve_szoveg.setText("Dolgozó Neve");
        dolgozo_neve_szoveg.setFocusable(false);

        dolgozo_neve_input.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        dolgozo_neve_input.setText("Név");
        dolgozo_neve_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dolgozo_neve_inputActionPerformed(evt);
            }
        });

        dolgozo_nev.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        dolgozo_szul_ev.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        dolgozo_fizetes.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        dolgozo_varos.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

        dolgozo_utca_hsz.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(787, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dolgozoadatfelvitel_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Button_kilepes)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dolgozo_neve_input, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dolgozo_neve_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dolgozo_kep, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(Button_feltolt)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nev_szoveg)
                            .addComponent(szulev_szoveg)
                            .addComponent(fizetes_szoveg)
                            .addComponent(utcahazszam_szoveg)
                            .addComponent(varos_szoveg))
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_felvitel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dolgozo_varos)
                                .addComponent(dolgozo_nev)
                                .addComponent(dolgozo_szul_ev)
                                .addComponent(dolgozo_fizetes)
                                .addComponent(dolgozo_utca_hsz, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(122, 122, 122))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_kilepes)
                    .addComponent(dolgozoadatfelvitel_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dolgozo_kep, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_feltolt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(dolgozo_neve_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dolgozo_neve_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(307, 307, 307)
                        .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dolgozo_nev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nev_szoveg))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dolgozo_szul_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(szulev_szoveg))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dolgozo_fizetes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fizetes_szoveg))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dolgozo_varos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(varos_szoveg))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dolgozo_utca_hsz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(utcahazszam_szoveg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Button_felvitel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dolgozo_neve_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dolgozo_neve_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dolgozo_neve_inputActionPerformed

    private void Button_felvitelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_felvitelActionPerformed
    //System.out.println("az");
    String nev = dolgozo_nev.getText();
    short szulev = Short.parseShort((dolgozo_szul_ev.getText()));
    int fizetes = Integer.parseInt(dolgozo_fizetes.getText());
    String varos = dolgozo_varos.getText();
    String utca_hsz = dolgozo_utca_hsz.getText();
    
    try{
       int insert = AFP_Labor_A_Kliens.db.dolgozo_insert(nev, szulev, varos, fizetes, utca_hsz);
        
        if (insert > 0) {
           System.out.println("Sikeres feltöltés");
           JOptionPane.showMessageDialog(null, "Sikeres feltöltés", "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        }
    }
    catch(Exception e)
    {
        System.out.println("Hiba a feltöltéssel!");  
        JOptionPane.showMessageDialog(null, "Hiba a feltöltéssel!", "Hiba", JOptionPane.ERROR_MESSAGE);
    } 
    }//GEN-LAST:event_Button_felvitelActionPerformed

    private void Button_kilepesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_kilepesActionPerformed
dispose();
    }//GEN-LAST:event_Button_kilepesActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked


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
            java.util.logging.Logger.getLogger(Employee_add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee_add().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_feltolt;
    private javax.swing.JButton Button_felvitel;
    private javax.swing.JButton Button_kilepes;
    private javax.swing.JTextPane copyright;
    private javax.swing.JTextField dolgozo_fizetes;
    private javax.swing.JLabel dolgozo_kep;
    private javax.swing.JTextField dolgozo_nev;
    private javax.swing.JTextField dolgozo_neve_input;
    private javax.swing.JTextPane dolgozo_neve_szoveg;
    private javax.swing.JTextField dolgozo_szul_ev;
    private javax.swing.JTextField dolgozo_utca_hsz;
    private javax.swing.JTextField dolgozo_varos;
    private javax.swing.JTextPane dolgozoadatfelvitel_szoveg;
    private javax.swing.JLabel fizetes_szoveg;
    private javax.swing.JLabel nev_szoveg;
    private javax.swing.JLabel szulev_szoveg;
    private javax.swing.JLabel utcahazszam_szoveg;
    private javax.swing.JLabel varos_szoveg;
    // End of variables declaration//GEN-END:variables
}