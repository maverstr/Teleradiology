/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.ContentItem;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomOutputStream;
import com.pixelmed.dicom.StructuredReport;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INFO-H-400
 */
public class Report extends StructuredReport{

    public Report(AttributeList al, String text) throws DicomException { //choisir la methode pour construire un nouveau rapport
        super(al);
        System.out.println("Un nouveau dicom SR a été créé sur base d'une liste d'attributs \n");
        System.out.println(al.toString());
        
        //1.2.840.10008.5.1.4.1.1.88.11 sop uid d'un basic text struicturedreport
        try {
            FileOutputStream os = new FileOutputStream("D:\\Users\\INFO-H-400\\libraries\\dcm4che-5.14.0\\bin\\test.dcm");
            DicomOutputStream dos = new DicomOutputStream(os, null, "1.2.840.10008.1.2");   //Dicom output stream has transfert syntax uid :
                                                                                            //Implicit VR Endian: Default Transfer Syntax for DICOM
            al.write(dos);
            dos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveText(String text){
        
    }
    
    

    
}
