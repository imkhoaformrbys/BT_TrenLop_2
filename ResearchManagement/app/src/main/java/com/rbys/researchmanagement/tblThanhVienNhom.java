package com.rbys.researchmanagement;

import java.io.Serializable;

public class tblThanhVienNhom implements Serializable {
    private int memID;
    private String memName;
    private int groupID;
    private String role;

    public tblThanhVienNhom() {
    }

    public tblThanhVienNhom(int memID, String memName, int groupID, String role) {
        this.memID = memID;
        this.memName = memName;
        this.groupID = groupID;
        this.role = role;
    }

    public int getMemID() {
        return memID;
    }

    public void setMemID(int memID) {
        this.memID = memID;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return memID + "\t\t" + memName + "\t\t" + role;
    }
}
