/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.network.IdentifierHandler;

/**
 *
 * @author INFO-H-400
 */
public class NewHandler extends IdentifierHandler {
    private String StudyInstanceUIDvalue;

    public NewHandler() {
        this.StudyInstanceUIDvalue = "";
    }
    

    public void setStudyInstanceUIDvalue(String StudyInstanceUIDvalue) {
        this.StudyInstanceUIDvalue = StudyInstanceUIDvalue;
    }

    public String getStudyInstanceUIDvalue() {
        return StudyInstanceUIDvalue;
    }

    @Override
    public void doSomethingWithIdentifier(AttributeList al) throws DicomException {
        System.out.println(al.get(TagFromName.StudyInstanceUID).getDelimitedStringValuesOrEmptyString());
        /*this.setStudyInstanceUIDvalue(al.get(TagFromName.StudyInstanceUID).getDelimitedStringValuesOrEmptyString());*/
    }
    
}
