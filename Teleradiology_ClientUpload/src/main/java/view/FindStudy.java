/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import client.ClientUpload;
import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomDirectoryRecord;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.DefaultListModel;

/**
 *
 * @author INFO-H-400
 */
public class FindStudy extends javax.swing.JFrame {
    ClientUpload scu = new ClientUpload();
    AttributeList identifier;
    
    HashMap<String, AttributeList> identifiers = new HashMap();
    
    /** Creates new form FindStudy */
    public FindStudy() {
        initComponents();
        
        //identifiers.put(studyUID,identifier);
        //identifiers.get(studyUID);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PatientNameLabel = new javax.swing.JLabel();
        dcmPatientNameField = new javax.swing.JTextField();
        doCFindButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        receivedUIDList = new javax.swing.JList<>();
        SearchPatientReportLabel = new javax.swing.JLabel();
        ReportLabel = new javax.swing.JLabel();
        ReportReadLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PatientNameLabel.setText("Patient Name");

        dcmPatientNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dcmPatientNameFieldActionPerformed(evt);
            }
        });

        doCFindButton1.setText("C-FIND");
        doCFindButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doCFindButton1doCFindButtonActionPerformed(evt);
            }
        });

        receivedUIDList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                receivedUIDListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(receivedUIDList);

        SearchPatientReportLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        SearchPatientReportLabel.setText("Search for Patient Report");

        ReportLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ReportLabel.setText("Report");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(ReportReadLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(SearchPatientReportLabel))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PatientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcmPatientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(doCFindButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                        .addComponent(ReportLabel)
                        .addGap(176, 176, 176))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchPatientReportLabel)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PatientNameLabel)
                    .addComponent(dcmPatientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doCFindButton1)
                    .addComponent(ReportLabel))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ReportReadLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void doCFindButton1doCFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doCFindButton1doCFindButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Find appelé");
        //String searchStudyUID = dcmPatientNameField.getText();
        String searchName = dcmPatientNameField.getText();
        identifiers = scu.doFindScu(searchName);//récupère une hashmap <studyInstanceUID,identifier>
        
        //System.out.println("------IDENTIFIER-----");
        //System.out.println(identifier);
        //System.out.println(identifier.get(TagFromName.StudyInstanceUID));
        /*if(identifier.get(TagFromName.StudyInstanceUID) != null ){
            System.out.println("Trouvé!");
            ArrayList<String> receivedStudyInstanceUIDs = new ArrayList();
            //System.out.println("Bla");
            DefaultListModel<String> receivedListModel = new DefaultListModel();
            //System.out.println("Bla Bla");
            Set<AttributeTag> receivedTags = identifier.keySet();
            System.out.println("Je vais imprimer");
            for( AttributeTag tag: receivedTags ){
                System.out.println(tag.toString() + " :: " + identifier.get(tag).getSingleStringValueOrEmptyString());
            }
            receivedStudyInstanceUIDs.add(identifier.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString());//là qu'on choisit quoi afficher après avoir recherché le patient
            //identifiers.put(identifier.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString(), identifier);
            System.out.println(identifier.get(TagFromName.StudyInstanceUID));
            System.out.println("Bla Bla Bla");
            
            for(String uid : receivedStudyInstanceUIDs){
                receivedListModel.addElement(uid);
                System.out.println("000");
                /*patientModel = new PersonListModel(patientController.findPatientEntities());
                System.out.println("1");
                receivedUIDList.setModel(receivedListModel);
                System.out.println("2");
            }
        }
        
        else{
            System.out.println("rien trouvé");
        }*/
        Set<String> uids = identifiers.keySet();
        DefaultListModel<String> receivedListModel = new DefaultListModel();
        for( String uid : uids ){
            receivedListModel.addElement(uid);
                System.out.println("000");
                //patientModel = new PersonListModel(patientController.findPatientEntities());
                System.out.println("1");
                receivedUIDList.setModel(receivedListModel);
                System.out.println("2");
        }
    }//GEN-LAST:event_doCFindButton1doCFindButtonActionPerformed

    private void receivedUIDListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_receivedUIDListValueChanged
        // TODO add your handling code here:
        Object selectedObject = receivedUIDList.getSelectedValue();
        System.out.println(selectedObject);
        //AttributeList al = new AttributeList();
        //DicomDirectoryRecord ddr = (DicomDirectoryRecord) selectedObject;
        System.out.println("12");
        //AttributeList al = ddr.getAttributeList();
        //System.out.println("13");
        //al.read(evt);
        Set<String> uids = identifiers.keySet();
        for(String uid : uids){
            if(uid == selectedObject){
                System.out.println("14");
                String x = identifiers.get(uid).get(TagFromName.TextValue).getSingleStringValueOrEmptyString();
                System.out.println("15");
                ReportReadLabel.setText(x);
                System.out.println("16");
            }
        }
        System.out.println("17");
        /*if(identifier.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString().equals(selectedObject)){
            System.out.println("14");
            String x = identifier.get(TagFromName.TextValue).getSingleStringValueOrEmptyString();
            System.out.println("15");
            ReportReadLabel.setText(x);
            System.out.println("16");
        }
        System.out.println("17");*/
        //Object selectedObject = receivedUIDList.getSelectedValue();
        //String x = selectedObject.GetReport();
        //ReportReadLabel.setText(x);
    }//GEN-LAST:event_receivedUIDListValueChanged

    private void dcmPatientNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dcmPatientNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dcmPatientNameFieldActionPerformed

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
            java.util.logging.Logger.getLogger(FindStudy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FindStudy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FindStudy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FindStudy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FindStudy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PatientNameLabel;
    private javax.swing.JLabel ReportLabel;
    private javax.swing.JLabel ReportReadLabel;
    private javax.swing.JLabel SearchPatientReportLabel;
    private javax.swing.JTextField dcmPatientNameField;
    private javax.swing.JButton doCFindButton1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> receivedUIDList;
    // End of variables declaration//GEN-END:variables

}
