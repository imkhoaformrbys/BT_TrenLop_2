package com.rbys.carbookingmanagement;

import java.io.Serializable;

public class HangXe implements Serializable {
    private int maHang;
    private String tenHang;

    public HangXe() {
    }

    public HangXe(int maHang, String tenHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    @Override
    public String toString() {
        return tenHang;
    }
}
