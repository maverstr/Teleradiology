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
import com.pixelmed.dicom.SpecificCharacterSet;
import com.pixelmed.dicom.StructuredReport;
import com.pixelmed.dicom.TagFromName;
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
    private String path;
    
    
    public Report(AttributeList al, String text) throws DicomException { //choisir la methode pour construire un nouveau rapport
        super(al);
        System.out.println("Un nouveau dicom SR a été créé sur base d'une liste d'attributs \n");
        System.out.println(al.toString());
        SpecificCharacterSet scs = new SpecificCharacterSet((String[])null); //créé un scs vide qui sera rempli à la ligne suivante
        al.putNewAttribute(TagFromName.TextValue, scs).addValue(text); //rempli l'attribut TEXTVALUE avec du text
        path ="D:\\Users\\INFO-H-400\\libraries\\dcm4che-5.14.0\\bin\\DICOMDIR\\"+al.get(TagFromName.ReferencedFileID).getDelimitedStringValuesOrEmptyString()+"Report.dcm";
        try {
            FileOutputStream os = new FileOutputStream(path);
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
    
    public String getReportPath(){
        
        return path;
    }
}
