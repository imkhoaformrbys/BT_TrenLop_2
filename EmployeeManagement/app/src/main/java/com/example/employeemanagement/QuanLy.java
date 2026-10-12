package com.example.employeemanagement;

import java.io.Serializable;

public class QuanLy extends NhanVien implements Serializable {
    int capbac;
    public QuanLy(int mnv, String hoten, int capbac) {
        super(mnv, hoten);
        this.capbac = capbac;
    }

    public int getCapbac() {
        return capbac;
    }

    public void setCapbac(int capbac) {
        this.capbac = capbac;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\t\t" + "Quản lý trưởng" ;
    }
}
