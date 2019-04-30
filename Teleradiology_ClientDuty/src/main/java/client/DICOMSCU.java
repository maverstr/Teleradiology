/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author INFO-H-400
 */

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.CodeStringAttribute;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.SOPClass;
import com.pixelmed.dicom.SpecificCharacterSet;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.UniqueIdentifierAttribute;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.FindSOPClassSCU;
import com.pixelmed.network.GetSOPClassSCU;
import com.pixelmed.network.IdentifierHandler;
import com.pixelmed.network.MoveSOPClassSCU;
import com.pixelmed.network.ReceivedObjectHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.MainWindow;
//import ulb.lisa.his.client.view.MainWindow;

/**
 *
 * @author LVdP & RR
 * 
 * TO DO :
 *  - adapte au serveur
 */
public class DICOMSCU {
    private final String SCPadress = "192.168.3.109";
    private final String SCPname = "STORESCP109";
    private final File directory;

    public DICOMSCU() {
        this.directory = new File("D:\\Users\\INFO-H-400\\libraries\\dcm4che-5.14.0\\bin");
    }
    
    private class FindScuIdentifierHandler extends IdentifierHandler {
        
        public ArrayList<String> receivedStudyInstanceUIDs = new ArrayList();
        
        @Override
        public void doSomethingWithIdentifier(AttributeList identifier) throws DicomException {
            System.out.println("Received C-FIND response:");
            Set<AttributeTag> receivedTags = identifier.keySet();
            for( AttributeTag tag: receivedTags ){
                System.out.println(tag.toString() + " :: " + identifier.get(tag).getSingleStringValueOrEmptyString());
            }
            
            receivedStudyInstanceUIDs.add(identifier.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString());
        }
    }
    
    private class OurReceivedObjectHandler extends ReceivedObjectHandler {

        @Override
        public void sendReceivedObjectIndication(String dicomFileName, String transferSyntax, String callingAETitle) throws DicomNetworkException, DicomException, IOException {
            if (dicomFileName != null) {
                System.err.println("Received: " + dicomFileName + " from " + callingAETitle + " in " + transferSyntax);
                try {
                    AttributeList list;
                    try (DicomInputStream i = new DicomInputStream(new BufferedInputStream(new FileInputStream(dicomFileName)))) {
                        list = new AttributeList();
                        list.read(i, TagFromName.PixelData);             // no need to read pixel data (much faster if one does not)
                    }
                    //databaseInformationModel.insertObject(list, dicomFileName);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }
    
    public ArrayList<String> doFindScu(String searchPatientName){
        try {
            SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet((String[])null);
            AttributeList identifier = new AttributeList();
            
            FindScuIdentifierHandler handler = new FindScuIdentifierHandler();
            
            //specify attributes to retrieve and pass in any search criteria
            //query root of "study" to retrieve studies
            
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
            new FindSOPClassSCU(SCPadress,
                    443,
                    SCPname,
                    "FINDSCU",
                    SOPClass.StudyRootQueryRetrieveInformationModelFind,
                    identifier,
                    handler);
            
            return handler.receivedStudyInstanceUIDs;
        } catch (DicomException | DicomNetworkException | IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void doMoveScu(String studyInstanceUID) {
        try {
            AttributeList identifier = new AttributeList();
            { AttributeTag t = TagFromName.QueryRetrieveLevel; Attribute a = new CodeStringAttribute(t); a.addValue("STUDY"); identifier.put(t,a); }
            { AttributeTag t = TagFromName.StudyInstanceUID; Attribute a = new UniqueIdentifierAttribute(t); a.addValue(studyInstanceUID); identifier.put(t,a); }
            new MoveSOPClassSCU("192.168.3.109",443,"STORESCP109","MOVESCU","STORESCP",SOPClass.StudyRootQueryRetrieveInformationModelMove,identifier);
        }
        catch (DicomException | DicomNetworkException | IOException | ClassCastException | NullPointerException e) {
            System.err.println(e);
        }
    }
    
    /*public void doGetScu(String studyInstanceUID){
        try {
            AttributeList identifier = new AttributeList();
            {
                AttributeTag t = TagFromName.QueryRetrieveLevel;
                Attribute a = new CodeStringAttribute(t);
                a.addValue("IMAGE");
                identifier.put(t, a);
            }
            {
                AttributeTag t = TagFromName.StudyInstanceUID;
                Attribute a = new UniqueIdentifierAttribute(t);
                a.addValue(studyInstanceUID);
                identifier.put(t, a);
            }// on récupère toutes les study d'un patient, on fera un report à partir de toutes ces données après
            new GetSOPClassSCU("192.168.3.109", 443, "CP109", "GETSCU", SOPClass.StudyRootQueryRetrieveInformationModelGet, identifier, new IdentifierHandler(), directory,  , new OurReceivedObjectHandler(), SOPClass.getSetOfStorageSOPClasses(), 1, true, false, false);
        } catch (DicomException | ClassCastException | NullPointerException e) {
            System.err.println(e);
        }
    }*/
    
}
