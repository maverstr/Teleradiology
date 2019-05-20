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
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author INFO-H-400
 */
public class FindStudy extends javax.swing.JFrame {
    ClientUpload scu = new ClientUpload();
    AttributeList identifier;
    File DICOMPath;
    private String studyToMove;
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
        doCGetButton = new javax.swing.JButton();
        SelectDicomFile = new javax.swing.JButton();
        dicomImageLabel = new javax.swing.JLabel();
        ImageLabel = new javax.swing.JLabel();

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

        doCGetButton.setText("C-GET");
        doCGetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doCGetButtondoCFindButtonActionPerformed(evt);
            }
        });

        SelectDicomFile.setText("Select DICOM");
        SelectDicomFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectDicomFileActionPerformed(evt);
            }
        });

        ImageLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ImageLabel.setText("Image");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ReportLabel)
                        .addGap(590, 590, 590))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(doCGetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(SelectDicomFile)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dicomImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(284, 284, 284))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SearchPatientReportLabel)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(PatientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dcmPatientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(doCFindButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(670, 670, 670)
                                .addComponent(ImageLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(645, 645, 645)
                                .addComponent(ReportReadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(233, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dicomImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(ReportLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReportReadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SearchPatientReportLabel)
                        .addGap(117, 117, 117)
                        .addComponent(doCGetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SelectDicomFile))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PatientNameLabel)
                            .addComponent(dcmPatientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(doCFindButton1))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void doCGetButtondoCFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doCGetButtondoCFindButtonActionPerformed
        // TODO add your handling code here:
        String searchName = dcmPatientNameField.getText();
        scu.doGetScu(searchName);
    }//GEN-LAST:event_doCGetButtondoCFindButtonActionPerformed

    private void SelectDicomFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectDicomFileActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser("D:\\Users\\INFO-H-400\\Downloads\\DICOMDIR\\DICOMDIR");

        if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
            DicomInputStream dis = null;
            try {
                DICOMPath = jfc.getSelectedFile();
                System.out.println(DICOMPath.getAbsolutePath());
                System.out.println("a");
                dis = new DicomInputStream(DICOMPath);
                System.out.println("b");
                AttributeList al = new AttributeList();
                System.out.println("c");
                al.read(dis);
                System.out.println("d");
                String x = al.get(TagFromName.TextValue).getSingleStringValueOrEmptyString();
                System.out.println("e");
                ReportReadLabel.setText(x);
                System.out.println("f");
                if( al.get(TagFromName.DirectoryRecordType).getSingleStringValueOrEmptyString().equals("IMAGE") ){
            try {
                
                String imagePath = al.get(TagFromName.ReferencedFileID).getDelimitedStringValuesOrEmptyString();
                
                File imageFile = new File(DICOMPath.getParent(), imagePath);
                
                System.out.println(imagePath);
                System.out.println(imageFile.getAbsolutePath());
                SourceImage sImg = new SourceImage(imageFile.getAbsolutePath()); // path = path to the DICOM file containing the image data. Note that the DICOMDIR doesn't have the image data!
                dicomImageLabel.setIcon(new ImageIcon(sImg.getBufferedImage())); // Shows image in a jLabel
            } catch (IOException | DicomException ex) {
                Logger.getLogger(DICOMViewer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            } catch (IOException | DicomException ex) {
                Logger.getLogger(DICOMViewer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    dis.close();
                } catch (IOException ex) {
                    Logger.getLogger(DICOMViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_SelectDicomFileActionPerformed

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
    private javax.swing.JLabel ImageLabel;
    private javax.swing.JLabel PatientNameLabel;
    private javax.swing.JLabel ReportLabel;
    private javax.swing.JLabel ReportReadLabel;
    private javax.swing.JLabel SearchPatientReportLabel;
    private javax.swing.JButton SelectDicomFile;
    private javax.swing.JTextField dcmPatientNameField;
    private javax.swing.JLabel dicomImageLabel;
    private javax.swing.JButton doCFindButton1;
    private javax.swing.JButton doCGetButton;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> receivedUIDList;
    // End of variables declaration//GEN-END:variables

}
