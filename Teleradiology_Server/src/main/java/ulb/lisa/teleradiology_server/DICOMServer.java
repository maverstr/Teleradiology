/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.teleradiology_server;
import com.mysql.jdbc.PreparedStatement;
import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.AttributeTagAttribute;
import com.pixelmed.dicom.CodeStringAttribute;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.PersonNameAttribute;
import com.pixelmed.dicom.SetOfDicomFiles;
import com.pixelmed.dicom.SetOfDicomFiles.DicomFile;
import com.pixelmed.dicom.StoredFilePathStrategy;
import static com.pixelmed.dicom.StoredFilePathStrategy.BYSOPINSTANCEUIDCOMPONENTFOLDERS;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.UniqueIdentifierAttribute;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.NetworkApplicationInformation;
import com.pixelmed.network.ReceivedObjectHandler;
import com.pixelmed.network.StorageSOPClassSCPDispatcher;
import com.pixelmed.query.QueryResponseGenerator;
import com.pixelmed.query.QueryResponseGeneratorFactory;
import com.pixelmed.query.RetrieveResponseGenerator;
import com.pixelmed.query.RetrieveResponseGeneratorFactory;
import controller.ImagingstudyJpaController;
import controller.InstanceJpaController;
import controller.PatientJpaController;
import controller.PatientdicomidentifierJpaController;
import controller.PersonJpaController;
import controller.PractitionerdicomidentifierJpaController;
import controller.SeriesJpaController;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Imagingstudy;
import model.Instance;
import static model.Medicalstaff_.person;
import model.Patient;
import model.Patientdicomidentifier;
import model.Person;
import model.Series;

/**
 *
 * @author INFO-H-400
 */
public class DICOMServer {

    public static void main(String args[]) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //connecting to DB
        String driver = "com.mysql.jdbc.Driver";
        String userName = "student";
        String password = "1234";
        String url = "jdbc:mysql://192.168.3.109/teleradiology";
        Connection conn = null;
        Class.forName(driver).newInstance();
        conn = DriverManager.getConnection(url, userName, password);
        System.out.println("Connected to the database.");

        
        DICOMServer dicomServ = new DICOMServer();

        
    }
    
    public DICOMServer() throws IOException{ 
        //Starting server
        startServer(443, "STORESCP109",new File("D:\\Users\\INFO-H-400\\Documents"), BYSOPINSTANCEUIDCOMPONENTFOLDERS);
    }
    
   private void startServer(int port, String aetitle, File storePath, StoredFilePathStrategy sfps) throws IOException{
       NetworkApplicationInformation nai = new NetworkApplicationInformation();
        try {
            nai.add("LOCAL_STORESCP", "STORESCP109", "localhost", 11113, null, null);
        } catch (DicomNetworkException ex) {
            Logger.getLogger(DICOMServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       Thread t = new Thread(
               new StorageSOPClassSCPDispatcher(
                       port,
                       aetitle,
                       storePath,
                       StoredFilePathStrategy.BYSOPINSTANCEUIDCOMPONENTFOLDERS,
                       new StoreObjectHandler(),  new QueryResponseGeneratorFactoryHandler(), new RetrieveResponseHandler(), nai, false)
               
       );
       System.out.println("server started");
       t.start();

    }
   
    private class QueryResponseHandler implements QueryResponseGenerator {
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
        
        private AttributeList al = new AttributeList();
        private AttributeList patientList = new AttributeList();
        private AttributeList responseList = new AttributeList();
        
        private String nameToBeSearched;
        boolean stop = false;
        
        @Override
        public void performQuery(String affectedSOPClassUID, AttributeList al, boolean bln) {
            System.out.println("C-Find Received");
            System.out.println(affectedSOPClassUID);
            System.out.println(al);
            
            this.al = al;
            nameToBeSearched = Attribute.getDelimitedStringValuesOrEmptyString(this.al,TagFromName.PatientName);
            if(stop){
                System.out.println("closing");

                close();
            }
            
            
        }

        @Override
        public AttributeList next() {    
            if( stop ) return null;
            Patient patient = new Patient();
            String patientName=nameToBeSearched;
            System.out.println("searching for: "+ patientName);
            patient = patientController.findPatientByName(patientName);
            Imagingstudy study = studyCtrl.findImagingstudyByPatientID(patient.getIdPatient());
            System.out.println("found: " + patient);
                try {
                    patientList.putNewAttribute(TagFromName.PatientName).addValue(patient.getPerson().getNameGiven());
                    patientList.putNewAttribute(TagFromName.StudyInstanceUID).addValue(study.getUid());
                    patientList.putNewAttribute(TagFromName.PatientID).addValue(patient.getIdPatient());
                    patientList.putNewAttribute(TagFromName.ReferencedFileID).addValue("TEST FILE ID");
                    patientList.putNewAttribute(TagFromName.TextValue).addValue("TEXT VALUE TEST TEST TEST DE RAPPORT");
                    patientList.putNewAttribute(TagFromName.DirectoryRecordType).addValue("STUDY");
                } catch (DicomException ex) {
                    Logger.getLogger(DICOMServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            stop = true;
            System.out.println("patient list: "+ patientList);

            return patientList;
        }

        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public AttributeTagAttribute getOffendingElement() {
            return null;
        }

        @Override
        public String getErrorComment() {
            return null;
        }

        @Override
        public void close() {
            stop = false;
        }

        @Override
        public boolean allOptionalKeysSuppliedWereSupported() {
            return true;
        }
        
    }
    
    private class QueryResponseGeneratorFactoryHandler implements QueryResponseGeneratorFactory {

        @Override
        public QueryResponseHandler newInstance() {
            return new QueryResponseHandler();
        }
        
    }
    
    
    
    
    private class RetrieveResponseHandler implements RetrieveResponseGeneratorFactory {

        
        @Override
        public RetrieveResponseGenerator newInstance() {
            return new RetrieveResponseGenerator() {
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

                private AttributeList al = new AttributeList();
                private AttributeList patientList = new AttributeList();
                private SetOfDicomFiles responseList = new SetOfDicomFiles();

                private String nameToBeSearched;
                boolean stop = false;
                
        
                @Override
                public void performRetrieve(String affectedSOPClassUID, AttributeList al, boolean bln) {
                    System.out.println("C-MOVE Request received");
                    System.out.println(affectedSOPClassUID);
                    System.out.println(al);
                    this.al = al;
                    nameToBeSearched = Attribute.getDelimitedStringValuesOrEmptyString(this.al,TagFromName.PatientName);
                    if(stop){
                        System.out.println("closing");

                        close();
            }
            
                }

                @Override
                public SetOfDicomFiles getDicomFiles() {
                    if( stop ){
                        System.out.println("get STOPPED");
                        return null;
                        
                    }
                    System.out.println("in GET DICOM FILES");

                    
                    Patient patient = new Patient();
                    String patientName=nameToBeSearched;
                    System.out.println("searching for: "+ patientName);
                    patient = patientController.findPatientByName(patientName);
                    Imagingstudy study = studyCtrl.findImagingstudyByPatientID(patient.getIdPatient());
                    System.out.println("found: " + patient);
                    String studyUID = study.getUid();
                    System.out.println("Study UID: " + studyUID);                    
                    
                    File dir = new File("D:\\Users\\INFO-H-400\\Documents\\"+studyUID+"\\");
                    System.out.println("is a directory?: "+dir.isDirectory());

                    File[] directoryListing = dir.listFiles();
                    if (directoryListing != null) {
                      for (File child : directoryListing) {
                          try {
                              responseList.add(child);
                              System.out.println("file child exists ?: "+child.exists());

                          } catch (IOException ex) {
                              Logger.getLogger(DICOMServer.class.getName()).log(Level.SEVERE, null, ex);
                          }
                      }
                    } else {
                      System.out.println("pas un directory c'est la merde");

                      // Handle the case where dir is not really a directory.
                      // Checking dir.isDirectory() above would not be sufficient
                      // to avoid race conditions with another process that deletes
                      // directories.
                    }


                    stop = true;
                    System.out.println("response list : "+responseList.toString() + " stop");
                    return responseList;                
                }
                
                

                @Override
                public int getStatus() {
                    return 0;
                }

                @Override
                public AttributeTagAttribute getOffendingElement() {
                    return null;
                }

                @Override
                public String getErrorComment() {
                    return null;
                }

                @Override
                public void close() {
                    stop = false;
                }
            };
        }
        
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
            System.out.println(dcmFilename + "    " + callingAET);

            AttributeList al = new AttributeList();
            al.read(dcmFilename);
            
       
            //retrieve info
            String patientName = getTag(al, TagFromName.PatientName);
            String patientID = getTag(al, TagFromName.PatientID);
            String patientSex = getTag(al, TagFromName.PatientSex);
            String patientBirthdate = getTag(al, TagFromName.PatientBirthDate);
            String hospitalID = getTag(al, TagFromName.InstitutionName);
            String studyUID = getTag(al, TagFromName.StudyInstanceUID);
            String studyDescription = getTag(al, TagFromName.StudyDescription);
            String seriesUID = getTag(al, TagFromName.SeriesInstanceUID);
            String seriesModality = getTag(al, TagFromName.Modality);
            String seriesDescription = getTag(al, TagFromName.SeriesDescription);
            String instanceUID = getTag(al, TagFromName.SOPInstanceUID);
            String test= getTag(al, TagFromName.ValueType);
            //String Text = getTag(al, TagFromName.TextValue);
           
            System.out.println("value type"+test);

            Patientdicomidentifier pdi = pdiCtrl.findPatientdicomidentifierByDicomIdentifier(patientID);
            Patient pat = null;

            if(pdi == null){
                pat = new Patient();
                pat.setActive(true);
                Person per = new Person();
                per.setNameFamily(patientName);
                per.setGender(patientSex);
                per.setHospitalid(hospitalID);
                System.out.println("hospital : "+hospitalID);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyymmdd");
                try{
                    per.setBirthdate(fmt.parse(patientBirthdate));
                } catch(ParseException ex){
                }
                pdi = new Patientdicomidentifier();
                pdi.setPatient(pat);
                pdi.setDicomIdentifier(patientID);
                pat.setPerson(per);
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
            Instance instance = instanceCtrl.findInstanceByUid(instanceUID);
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
