/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.ContentItem;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.StructuredReport;

/**
 *
 * @author INFO-H-400
 */
public class Report extends StructuredReport{

    public Report(AttributeList al) throws DicomException { //choisir la methode pour construire un nouveau rapport
        super(al);
        System.out.println("Un nouveau dicom SR a été créé sur base d'une liste d'attributs \n");
        System.out.println(al.toString());
        //System.out.println("Ce rapport possède la liste d'attributs suivante \n");
        //System.out.println(this.getAttributeList().toString()); (ca ne marche pas)
    }
    
    

    
}
