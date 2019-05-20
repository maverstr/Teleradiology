/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.CodeStringAttribute;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.SOPClass;
import com.pixelmed.dicom.SpecificCharacterSet;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.UniqueIdentifierAttribute;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.FindSOPClassSCU;
import com.pixelmed.network.GetSOPClassSCU;
import com.pixelmed.network.IdentifierHandler;
import com.pixelmed.network.MoveSOPClassSCU;
import com.pixelmed.network.StorageSOPClassSCU;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.DICOMViewer;

/**
 *
 * @author Adrien Foucart
 */
public class ClientUpload {
    
    private class FindScuIdentifierHandler extends IdentifierHandler {
        
        //public ArrayList<String> receivedStudyInstanceUIDs = new ArrayList();
        public HashMap<String, AttributeList> receivedIdentifiers = new HashMap();
        //public ArrayList<AttributeList> receivedIdentifiers = new ArrayList();
        public ArrayList<String> receivedStudyInstanceUIDs = new ArrayList();
        //public ArrayList<String> receivedReport = new ArrayList();

        
        @Override
        public void doSomethingWithIdentifier(AttributeList identifier) throws DicomException {
            
            System.out.println("Received C-FIND response:");
            Set<AttributeTag> receivedTags = identifier.keySet();
            System.out.println("Je vais imprimer");
            for( AttributeTag tag: receivedTags ){
                System.out.println(tag.toString() + " :: " + identifier.get(tag).getSingleStringValueOrEmptyString());
            }
            receivedIdentifiers.put(identifier.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString(),identifier);
            //receivedIdentifiers.add(identifier);
            //receivedStudyInstanceUIDs.add(identifier.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString());//là qu'on choisit quoi afficher après avoir recherché le patient
            //receivedReport.add(identifier.get(TagFromName.TextValue).getSingleStringValueOrEmptyString());
            System.out.println("j'ai imprimé");
        }
            //public ArrayList<ArrayList> receivedStudyAndReport = new Arraylist();
            //receivedStudyAndReport.append(receivedStudyInstanceUIDs);
            
    }
    
    public HashMap<String,AttributeList> doFindScu(String searchPatientName){
        try {
            SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet((String[])null);
            AttributeList identifier = new AttributeList();
            FindScuIdentifierHandler handler = new FindScuIdentifierHandler();
            
            //specify attributes to retrieve and pass in any search criteria
            //query root of "study" to retrieve studies
            identifier.putNewAttribute(TagFromName.ReferencedFileID);
            identifier.putNewAttribute(TagFromName.QueryRetrieveLevel).addValue("STUDY");
            identifier.putNewAttribute(TagFromName.PatientName,specificCharacterSet).addValue(searchPatientName);
            identifier.putNewAttribute(TagFromName.PatientID,specificCharacterSet);
            identifier.putNewAttribute(TagFromName.PatientBirthDate);
            identifier.putNewAttribute(TagFromName.PatientSex);
            identifier.putNewAttribute(TagFromName.StudyInstanceUID);
            identifier.putNewAttribute(TagFromName.SOPInstanceUID);
            identifier.putNewAttribute(TagFromName.StudyDescription);
            identifier.putNewAttribute(TagFromName.StudyDate);
            
            //retrieve all studies belonging to patient with name 'Bowen'
            new FindSOPClassSCU("192.168.3.109",
                    443,
                    "STORESCP109",
                    "FINDSCU",
                    SOPClass.StudyRootQueryRetrieveInformationModelFind,
                    identifier,
                    handler);
                return handler.receivedIdentifiers;
        
        } catch (DicomException | DicomNetworkException | IOException ex) {
            Logger.getLogger(DICOMViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void doStoreScu(String ImagePath) {
        try {
            AttributeList identifier = new AttributeList();
            System.out.println("A");
            { AttributeTag t = TagFromName.QueryRetrieveLevel; Attribute a = new CodeStringAttribute(t); a.addValue("STUDY"); identifier.put(t,a); }
            System.out.println("B");
            System.out.println("C");
            new StorageSOPClassSCU("192.168.3.109",443,"STORESCP109","STORESCU",ImagePath,SOPClass.StudyRootQueryRetrieveInformationModelGet,identifier.get(TagFromName.QueryRetrieveLevel).getDelimitedStringValuesOrEmptyString(),0);
            System.out.println("D");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
    /*public void doStore(String ImagePath) throws DicomException, DicomNetworkException, IOException{
        AttributeList identifier = new AttributeList();
        {
            AttributeTag t = TagFromName.QueryRetrieveLevel;
            Attribute a = new CodeStringAttribute(t);
            a.addValue("STUDY");
            identifier.put(t, a);
        }
        {
            AttributeTag t = TagFromName.StudyInstanceUID;
            Attribute a = new CodeStringAttribute(t);
            a.addValue(ImagePath);
            identifier.put(t, a);
        }
        new StorageSOPClassSCU("192.168.3.109",443,"STORESCP109","STORESCU",ImagePath,SOPClass.StudyRootQueryRetrieveInformationModelGet,identifier.get(TagFromName.StudyInstanceUID).getDelimitedStringValuesOrEmptyString(),0);
    }*/
    public void doGetScu(String studyInstanceUID) { //A NE PAS UTILISER ? ou bien il faut modifier !!
        /*try {
            AttributeList identifier = new AttributeList();
            { AttributeTag t = TagFromName.QueryRetrieveLevel; Attribute a = new CodeStringAttribute(t); a.addValue("STUDY"); identifier.put(t,a); }
            { AttributeTag t = TagFromName.StudyInstanceUID; Attribute a = new UniqueIdentifierAttribute(t); a.addValue(studyInstanceUID); identifier.put(t,a); }
            { AttributeTag t = TagFromName.PatientName; Attribute a = new UniqueIdentifierAttribute(t); a.addValue(studyInstanceUID); identifier.put(t,a); }
            new GetSOPClassSCU("192.168.3.109",443,"STORESCP109","MOVESCU","STORESCP",SOPClass.StudyRootQueryRetrieveInformationModelMove,identifier,0);
        }
        catch (DicomException | DicomNetworkException | IOException | ClassCastException | NullPointerException e) {
            System.err.println(e);
        }*/
    }
}