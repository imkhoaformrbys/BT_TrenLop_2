package com.rbys.carbookingmanagement;

import java.io.Serializable;

public class Xe implements Serializable {
    private String bienSo;
    private int namSX;
    private double giaTri;
    private int maHang;
    private String tenHang;
    private String tenKhach;
    private String sdtKhach;

    public Xe() {
        this.bienSo = "";
        this.namSX = 0;
        this.giaTri = 0.0;
        this.maHang = 0;
        this.tenHang = "";
        this.tenKhach = "";
        this.sdtKhach = "";
    }

    public Xe(String bienSo, int namSX, double giaTri, int maHang) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = "";
        this.tenKhach = "";
        this.sdtKhach = "";
    }

    public Xe(String bienSo, int namSX, double giaTri, int maHang, String tenHang) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.tenKhach = "";
        this.sdtKhach = "";
    }

    public Xe(String bienSo, int namSX, double giaTri, int maHang, String tenHang, String tenKhach, String sdtKhach) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.tenKhach = tenKhach;
        this.sdtKhach = sdtKhach;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public int getNamSX() {
        return namSX;
    }

    public void setNamSX(int namSX) {
        this.namSX = namSX;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
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

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
    }

    public String getSdtKhach() {
        return sdtKhach;
    }

    public void setSdtKhach(String sdtKhach) {
        this.sdtKhach = sdtKhach;
    }

    @Override
    public String toString() {
        String hang = (tenHang != null && !tenHang.isEmpty()) ? tenHang : "";
        return hang + " " + bienSo + " " + giaTri;
    }
}
