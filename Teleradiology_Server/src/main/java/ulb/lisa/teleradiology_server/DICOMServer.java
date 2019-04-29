/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.teleradiology_server;
import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.CodeStringAttribute;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.StoredFilePathStrategy;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.UniqueIdentifierAttribute;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.ReceivedObjectHandler;
import com.pixelmed.network.StorageSOPClassSCPDispatcher;
import controller.PatientJpaController;
import java.io.File;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author INFO-H-400
 */
public class DICOMServer {

    
    public static void main(String args[]) throws IOException {
        DICOMServer dicomServ = new DICOMServer();

        
    }
    
    public DICOMServer() throws IOException{
        System.out.println("in");
        startServer(11112, "STORESCP109",new File("D:\\Users\\INFO-H-400\\Documents"), null);
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
       System.out.println("done");
       t.start();

    }

    private class StoreObjectHandler extends ReceivedObjectHandler {
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("HIS");
        PatientJpaController patientController = new PatientJpaController(emfac);
        
        @Override
        public void sendReceivedObjectIndication(String dcmFilename, String transferSyntax, String callingAET) throws DicomNetworkException, DicomException,
                IOException {
            
            

                   System.out.println(dcmFilename + "    " + callingAET);
                   
                   
        }

    
    
    }   
}

import com.pixelmed.network.StorageSOPClassSCPDispatcher;

/**
 *
 * @author INFO-H-400
 */
public class DICOMServer {

    Thread t = new Thread(
            new StorageSOPClassSCPDispatcher(
                    port,
                    aetitle,
                    storePath,
                    StoredFilePathStrategy.BYSOPINSTANCEUIDINSINGLEFOLDER,
                    new StoreObjectHandler())
    );

    t.start ();

    private class StoreObjectHandler extends ReceivedObjectHandler {

        @Override
        public void sendReceivedObjectIndication(String dcmFilename, String transferSyntax, String callingAET) throws DicomNetworkException, DicomException,
                IOException {

        }
    }

    
}
