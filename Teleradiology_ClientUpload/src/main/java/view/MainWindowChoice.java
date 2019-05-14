/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author INFO-H-400
 */
public class MainWindowChoice extends javax.swing.JFrame {

    /**
     * Creates new form MainWindowChoice
     */
    public MainWindowChoice() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pageTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        SendStudyButton = new javax.swing.JButton();
        ReceiveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pageTitle.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        pageTitle.setText("Teleradiology");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Task to perform");

        SendStudyButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SendStudyButton.setText("Send a study");
        SendStudyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendStudyButtonActionPerformed(evt);
            }
        });

        ReceiveButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ReceiveButton.setText("Find a study");
        ReceiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReceiveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pageTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SendStudyButton)
                        .addGap(18, 18, 18)
                        .addComponent(ReceiveButton)))
                .addGap(79, 79, 79))
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pageTitle)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SendStudyButton)
                    .addComponent(ReceiveButton))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendStudyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendStudyButtonActionPerformed
        // TODO add your handling code here:
        SendStudy sstudy = new SendStudy();
        sstudy.setVisible(true);
    }//GEN-LAST:event_SendStudyButtonActionPerformed

    private void ReceiveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReceiveButtonActionPerformed
        // TODO add your handling code here:
        FindStudy fstudy = new FindStudy();
        fstudy.setVisible(true);
    }//GEN-LAST:event_ReceiveButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MainWindowChoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindowChoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindowChoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindowChoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindowChoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ReceiveButton;
    private javax.swing.JButton SendStudyButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel pageTitle;
    // End of variables declaration//GEN-END:variables
}
