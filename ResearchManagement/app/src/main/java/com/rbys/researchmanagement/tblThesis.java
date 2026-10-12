package com.rbys.researchmanagement;

import java.io.Serializable;

public class tblThesis implements Serializable {
    private int groupID;
    private String thesisName;

    public tblThesis() {
    }

    public tblThesis(int groupID, String thesisName) {
        this.groupID = groupID;
        this.thesisName = thesisName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getThesisName() {
        return thesisName;
    }

    public void setThesisName(String thesisName) {
        this.thesisName = thesisName;
    }

    @Override
    public String toString() {
        return thesisName;
    }
}
