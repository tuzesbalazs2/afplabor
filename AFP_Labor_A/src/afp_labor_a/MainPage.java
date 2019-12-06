/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a;

import static afp_labor_a.SecureChatServerHandler.channels;
import io.netty.channel.Channel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tóth_Zsolt
 */
public class MainPage extends javax.swing.JFrame {

    /**
     * Creates new form MainPage
     */
    
    
    public MainPage() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(0, 153, 0) );
        setTitle("Főoldal");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jelszo_bejelentkezes = new javax.swing.JPasswordField();
        nev_bejelentkezes = new javax.swing.JTextField();
        Button_bejelentkezes = new javax.swing.JButton();
        Button_regisztracio = new javax.swing.JButton();
        rendszer_szoveg = new javax.swing.JTextPane();
        copyright = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(0, 153, 0));
        setForeground(java.awt.Color.white);
        setResizable(false);

        jelszo_bejelentkezes.setText("Jelszó");
        jelszo_bejelentkezes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jelszo_bejelentkezesActionPerformed(evt);
            }
        });

        nev_bejelentkezes.setText("Név");
        nev_bejelentkezes.setToolTipText("Felhasználónév");
        nev_bejelentkezes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nev_bejelentkezesActionPerformed(evt);
            }
        });

        Button_bejelentkezes.setText("Bejelentkezés");
        Button_bejelentkezes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_bejelentkezesActionPerformed(evt);
            }
        });

        Button_regisztracio.setText("Regisztráció");
        Button_regisztracio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_regisztracioActionPerformed(evt);
            }
        });

        rendszer_szoveg.setEditable(false);
        rendszer_szoveg.setBackground(new java.awt.Color(0, 0, 0));
        rendszer_szoveg.setFont(new java.awt.Font("Arial Black", 0, 21)); // NOI18N
        rendszer_szoveg.setForeground(new java.awt.Color(255, 255, 255));
        rendszer_szoveg.setText("Dolgozók Nyilvántartó Rendszere");
        rendszer_szoveg.setFocusable(false);

        copyright.setBackground(new java.awt.Color(0, 153, 0));
        copyright.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        copyright.setForeground(new java.awt.Color(255, 255, 255));
        copyright.setText("Copyright mindenség");
        copyright.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jelszo_bejelentkezes, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(nev_bejelentkezes))
                        .addGap(323, 323, 323))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rendszer_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(231, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Button_regisztracio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Button_bejelentkezes, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addGap(358, 358, 358))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(rendszer_szoveg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(nev_bejelentkezes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jelszo_bejelentkezes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(Button_bejelentkezes)
                .addGap(18, 18, 18)
                .addComponent(Button_regisztracio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nev_bejelentkezesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nev_bejelentkezesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nev_bejelentkezesActionPerformed

    private void Button_bejelentkezesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_bejelentkezesActionPerformed
        // TODO add your handling code here:
//Login hibaüzenet teszt
String username = nev_bejelentkezes.getText();
String password = jelszo_bejelentkezes.getText();


   try {
       for (Channel c: channels) {
           c.writeAndFlush("[you] " + '\n');
            //c.writeAndFlush("fakkkk");
        }
       
      //AFP_Labor_A.db.connect();
      //AFP_Labor_A.db.login(username, password);
      
   }
   catch(Exception e)
   {
    //  JOptionPane.showMessageDialog(this,"Hibás felhasználónév és/vagy jelszó!",
    //  "Hiba",JOptionPane.ERROR_MESSAGE);  
   System.out.println("Hibás felhasználónév és/vagy jelszó!");
   JOptionPane.showMessageDialog(null, "Hibás felhasználónév és/vagy jelszó!", "Hiba", JOptionPane.ERROR_MESSAGE);
   
   }
   


    }//GEN-LAST:event_Button_bejelentkezesActionPerformed

    private void Button_regisztracioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_regisztracioActionPerformed
        Registration r = new Registration();
        r.setVisible(true);
    }//GEN-LAST:event_Button_regisztracioActionPerformed

    private void jelszo_bejelentkezesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jelszo_bejelentkezesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jelszo_bejelentkezesActionPerformed

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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_bejelentkezes;
    private javax.swing.JButton Button_regisztracio;
    private javax.swing.JTextPane copyright;
    private javax.swing.JPasswordField jelszo_bejelentkezes;
    private javax.swing.JTextField nev_bejelentkezes;
    private javax.swing.JTextPane rendszer_szoveg;
    // End of variables declaration//GEN-END:variables
}
