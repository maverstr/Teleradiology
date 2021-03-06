/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import client.ClientUpload;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomDirectory;
import com.pixelmed.dicom.DicomDirectoryRecord;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import model.Patient;
import controller.PatientJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import view.PersonListModel;

/**
 *
 * @author Adrien Foucart
 */
public class DICOMViewer extends javax.swing.JFrame {
    ClientUpload scu = new ClientUpload();
    ArrayList<Patient> patientsSearchResult = new ArrayList();
    File dicomdirPath;
    TreeModel dicomdir = new DefaultTreeModel(null);
    private String studyToMove;
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("Teleradiology");
    private final PatientJpaController patientController = new PatientJpaController(emfac);
    private PersonListModel<Patient> patientModel = null;
    
    /**
     * Creates new form DICOMViewer
     */
    public DICOMViewer() {
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

        jScrollPane2 = new javax.swing.JScrollPane();
        dicomAttributesTextPane = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        dicomdirTree = new javax.swing.JTree();
        dicomImageLabel = new javax.swing.JLabel();
        PatientNameLabel = new javax.swing.JLabel();
        doCFindButton1 = new javax.swing.JButton();
        moveSelectedStudyButton1 = new javax.swing.JButton();
        dcmPatientNameField = new javax.swing.JTextField();
        ImageLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        receivedUIDList = new javax.swing.JList<>();
        SelectDicomdir = new javax.swing.JButton();
        ReportLabel = new javax.swing.JLabel();
        ReportReadLabel = new javax.swing.JLabel();

        jScrollPane2.setViewportView(dicomAttributesTextPane);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dicomdirTree.setModel(this.dicomdir);
        dicomdirTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                dicomdirTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(dicomdirTree);

        PatientNameLabel.setText("Patient Name");

        doCFindButton1.setText("C-FIND");
        doCFindButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doCFindButton1doCFindButtonActionPerformed(evt);
            }
        });

        moveSelectedStudyButton1.setText("C-MOVE");
        moveSelectedStudyButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveSelectedStudyButton1moveSelectedStudyButtonActionPerformed(evt);
            }
        });

        ImageLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ImageLabel.setText("Image");

        jScrollPane3.setViewportView(receivedUIDList);

        SelectDicomdir.setText("Select DICOMDIR");
        SelectDicomdir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectDicomdirActionPerformed(evt);
            }
        });

        ReportLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ReportLabel.setText("Report");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PatientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SelectDicomdir)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dcmPatientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(doCFindButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moveSelectedStudyButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dicomImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(ReportReadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(81, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ImageLabel)
                        .addGap(344, 344, 344)
                        .addComponent(ReportLabel)
                        .addGap(314, 314, 314))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dcmPatientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PatientNameLabel)
                    .addComponent(doCFindButton1)
                    .addComponent(moveSelectedStudyButton1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SelectDicomdir)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ReportLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(dicomImageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(ReportReadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dicomdirTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_dicomdirTreeValueChanged
        Object selectedObject = dicomdirTree.getLastSelectedPathComponent();
        DicomDirectoryRecord ddr = (DicomDirectoryRecord) selectedObject;
        AttributeList al = ddr.getAttributeList();
        //dicomImageTextPane1.setText(al.toString());
        if( al.get(TagFromName.DirectoryRecordType).getSingleStringValueOrEmptyString().equals("STUDY") ){
            System.out.println("bla");
            studyToMove = al.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString();
            System.out.println(studyToMove);
        }
            
        
        if( al.get(TagFromName.DirectoryRecordType).getSingleStringValueOrEmptyString().equals("IMAGE") ){
            try {
                
                String imagePath = al.get(TagFromName.ReferencedFileID).getDelimitedStringValuesOrEmptyString();
                
                File imageFile = new File(dicomdirPath.getParent(), imagePath);
                
                System.out.println(imagePath);
                System.out.println(imageFile.getAbsolutePath());
                SourceImage sImg = new SourceImage(imageFile.getAbsolutePath()); // path = path to the DICOM file containing the image data. Note that the DICOMDIR doesn't have the image data!
                dicomImageLabel.setIcon(new ImageIcon(sImg.getBufferedImage())); // Shows image in a jLabel
            } catch (IOException | DicomException ex) {
                Logger.getLogger(DICOMViewer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_dicomdirTreeValueChanged

    private void doCFindButton1doCFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doCFindButton1doCFindButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Find appelé");
        String searchStudyUID = dcmPatientNameField.getText();
        ArrayList<String> receivedStudyInstanceUIDs = scu.doFindScu(searchStudyUID);
        if( receivedStudyInstanceUIDs != null ){
            System.out.println("Trouvé!");
            DefaultListModel<String> receivedListModel = new DefaultListModel();
            for( String uid : receivedStudyInstanceUIDs )
                receivedListModel.addElement(uid);
                System.out.println("000");
                /*patientModel = new PersonListModel(patientController.findPatientEntities());
                System.out.println("1");*/
                receivedUIDList.setModel(receivedListModel);
                System.out.println("2");
 
        }
        else{
            System.out.println("rien trouvé");
        }
    }//GEN-LAST:event_doCFindButton1doCFindButtonActionPerformed

    private void moveSelectedStudyButton1moveSelectedStudyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveSelectedStudyButton1moveSelectedStudyButtonActionPerformed
        // TODO add your handling code here:
        //String selectedUID = receivedUIDList.getSelectedValue();
        String selectedUID = studyToMove;
        /*Object selectedObject = dicomdirTree.getLastSelectedPathComponent().getSelectedValue();
        String selectedUID = selectedObject.getStudyInstanceUID();*/
        System.out.println(selectedUID);
        /*DicomDirectoryRecord ddr = (DicomDirectoryRecord) selectedObject;
        AttributeList al = ddr.getAttributeList();*/
        System.out.println("j'ai récupéré la valeur");
        scu.doMoveScu(selectedUID);
        System.out.println("Je l'ai envoyé sur l'ordi");
    }//GEN-LAST:event_moveSelectedStudyButton1moveSelectedStudyButtonActionPerformed

    private void SelectDicomdirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectDicomdirActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser("D:\\Users\\INFO-H-400\\Downloads\\DICOMDIR\\DICOMDIR");
        
        if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
            DicomInputStream dis = null;
            try {
                dicomdirPath = jfc.getSelectedFile();
                System.out.println(dicomdirPath.getAbsolutePath());
                dis = new DicomInputStream(dicomdirPath);
                AttributeList al = new AttributeList();
                al.read(dis);
                String x = al.get(TagFromName.TextValue).getSingleStringValueOrEmptyString();
                ReportReadLabel.setText(x);
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
    }//GEN-LAST:event_SelectDicomdirActionPerformed
public static void main(String args[]) {
    System.out.println("main appelé");
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
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DICOMViewer().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImageLabel;
    private javax.swing.JLabel PatientNameLabel;
    private javax.swing.JLabel ReportLabel;
    private javax.swing.JLabel ReportReadLabel;
    private javax.swing.JButton SelectDicomdir;
    private javax.swing.JTextField dcmPatientNameField;
    private javax.swing.JTextPane dicomAttributesTextPane;
    private javax.swing.JLabel dicomImageLabel;
    private javax.swing.JTree dicomdirTree;
    private javax.swing.JButton doCFindButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton moveSelectedStudyButton1;
    private javax.swing.JList<String> receivedUIDList;
    // End of variables declaration//GEN-END:variables
}
