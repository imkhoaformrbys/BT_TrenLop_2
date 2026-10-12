package com.example.employeemanagement;

import java.io.Serializable;

public class NhanVienKiemThu extends NhanVien implements Serializable {
    String loaikt;
    public NhanVienKiemThu(int mnv, String hoten,String loaikt) {
        super(mnv, hoten);
        this.loaikt = loaikt;
    }

    public String getLoaikt() {
        return loaikt;
    }

    public void setLoaikt(String loaikt) {
        this.loaikt = loaikt;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\t\t" + "Nhân viên kiểm thử";
    }
}
