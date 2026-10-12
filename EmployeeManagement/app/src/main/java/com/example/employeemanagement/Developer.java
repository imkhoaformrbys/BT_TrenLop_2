package com.example.employeemanagement;

import java.io.Serializable;

public class Developer extends NhanVien implements Serializable {
    int sonam;
    public Developer(int mnv, String hoten, int sonam) {
        super(mnv, hoten);
        this.sonam = sonam;
    }

    public int getSonam() {
        return sonam;
    }

    public void setSonam(int sonam) {
        this.sonam = sonam;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\t\t"+ "Lập trình viên";
    }
}
