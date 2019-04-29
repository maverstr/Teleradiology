/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.teleradiology_server;
import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.CodeStringAttribute;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.StoredFilePathStrategy;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.UniqueIdentifierAttribute;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.ReceivedObjectHandler;
import com.pixelmed.network.StorageSOPClassSCPDispatcher;
import controller.ImagingstudyJpaController;
import controller.InstanceJpaController;
import controller.PatientJpaController;
import controller.PatientdicomidentifierJpaController;
import controller.PersonJpaController;
import controller.PractitionerdicomidentifierJpaController;
import controller.SeriesJpaController;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Imagingstudy;
import model.Instance;
import model.Patient;
import model.Patientdicomidentifier;
import model.Person;
import model.Series;

/**
 *
 * @author INFO-H-400
 */
public class DICOMServer {

    
    public static void main(String args[]) throws IOException {
        System.out.println("in main Server");
        DICOMServer dicomServ = new DICOMServer();

        
    }
    
    public DICOMServer() throws IOException{
        startServer(443, "STORESCP109",new File("D:\\Users\\INFO-H-400\\Documents"), null);
    }
    
   private void startServer(int port, String aetitle, File storePath, StoredFilePathStrategy sfps) throws IOException{
       Thread t = new Thread(
               new StorageSOPClassSCPDispatcher(
                       port,
                       aetitle,
                       storePath,
                       StoredFilePathStrategy.BYSOPINSTANCEUIDINSINGLEFOLDER,
                       new StoreObjectHandler())
               
       );
       System.out.println("server started");
       t.start();

    }

    private class StoreObjectHandler extends ReceivedObjectHandler {
        private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("Teleradiology");
        private final PatientJpaController patientController = new PatientJpaController(emfac);
        private final PatientdicomidentifierJpaController pdiCtrl = new PatientdicomidentifierJpaController(emfac);
        private final PractitionerdicomidentifierJpaController prapdiCtrl = new PractitionerdicomidentifierJpaController(emfac);
        private final PatientJpaController patientCtrl = new PatientJpaController(emfac);
        private final PersonJpaController personCtrl = new PersonJpaController(emfac);
        private final PractitionerdicomidentifierJpaController praCtrl = new PractitionerdicomidentifierJpaController(emfac);
        private final ImagingstudyJpaController studyCtrl = new ImagingstudyJpaController(emfac);
        private final SeriesJpaController seriesCtrl = new SeriesJpaController(emfac);
        private final InstanceJpaController instanceCtrl = new InstanceJpaController(emfac);
         
        
             
        @Override
        public void sendReceivedObjectIndication(String dcmFilename, String transferSyntax, String callingAET) throws DicomNetworkException, DicomException,
                IOException {
            
            AttributeList al = new AttributeList();
            al.read(dcmFilename);

            
            //retrieve info
            String patientName = getTag(al, TagFromName.PatientName);
            String patientID = getTag(al, TagFromName.PatientID);
            int patientIDint = Integer.parseInt(patientID);
            String patientSex = getTag(al, TagFromName.PatientSex);
            String patientBirthdate = getTag(al, TagFromName.PatientBirthDate);
            String studyUID = getTag(al, TagFromName.StudyInstanceUID);
            String studyDescription = getTag(al, TagFromName.StudyDescription);
            String seriesUID = getTag(al, TagFromName.SeriesInstanceUID);
            String seriesModality = getTag(al, TagFromName.Modality);
            String seriesDescription = getTag(al, TagFromName.SeriesDescription);
            String instanceUID = getTag(al, TagFromName.SOPInstanceUID);
            int instanceUIDint = Integer.parseInt(instanceUID);
            
            String test= getTag(al, TagFromName.ValueType);
           
            System.out.println("value type"+test);

            
            Patientdicomidentifier pdi = pdiCtrl.findPatientdicomidentifier(patientIDint);
            Patient pat = null;
            if(pdi == null){
                pat = new Patient();
                pat.setActive(true);
                Person per = new Person();
                per.setNameFamily(patientName);
                per.setGender(patientSex);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyymmdd");
                try{
                    per.setBirthdate(fmt.parse(patientBirthdate));
                } catch(ParseException ex){
                }
                pdi = new Patientdicomidentifier();
                pdi.setPatient(pat);
                pdi.setDicomIdentifier(patientID);
                //pdi.setPerson(per);
                personCtrl.create(per);
                patientCtrl.create(pat);
                pdiCtrl.create(pdi);            
            }
            else{
            pat = pdi.getPatient();
            }
            
            Imagingstudy study = studyCtrl.findImagingstudyByUid(studyUID);
            if(study == null){
                study = new Imagingstudy();;
                study.setUid(studyUID);
                study.setPatient(pat);
                study.setDescription(studyDescription);
                studyCtrl.create(study);
        }
            
            Series series = seriesCtrl.findSeriesByUid(studyUID);
            if(series == null){
                series = new Series();
                series.setUid(studyUID);
                series.setModality(seriesModality);
                series.setDescription(studyDescription);
                series.setStudy(study);
                seriesCtrl.create(series);
        }
            
            Instance instance = instanceCtrl.findInstance(instanceUIDint);
            if (instance == null) {
                instance = new Instance();
                instance.setUid(studyUID);
                instance.setSeries(series);
                instanceCtrl.create(instance);
            }

                   
    }


    public String getTag(AttributeList list, AttributeTag tag){
        return Attribute.getDelimitedStringValuesOrNull(list, tag);
    }
    
    }   
}
