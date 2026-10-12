package com.example.employeemanagement;

import java.io.Serializable;

public class NhanVien implements Serializable {
    int mnv;
    String hoten;

    public NhanVien(int mnv, String hoten) {
        this.mnv = mnv;
        this.hoten = hoten;
    }

    public int getMnv() {
        return mnv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setMnv(int mnv) {
        this.mnv = mnv;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    @Override
    public String toString() {
        return new String(getMnv() + "\t\t\t" + getHoten());
    }
}
