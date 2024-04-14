package com.example.lap3.model;

import java.io.Serializable;

public class ThiSinh implements Serializable {
    private String sbd;
    private String hoten;
    private double dt,dl,dh;

    public ThiSinh(String sbd, String hoten, double dt, double dl, double dh) {
        this.sbd = sbd;
        this.hoten = hoten;
        this.dt = dt;
        this.dl = dl;
        this.dh = dh;
    }

    public ThiSinh() {
    }

    public double tinhTongDiem(){
        return dh+dl+dt;
    }



    public double tinhDiemTrungBinh(){
        return tinhTongDiem()/3;
    }

    public String getSbd() {
        return sbd;
    }

    public void setSbd(String sbd) {
        this.sbd = sbd;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getDl() {
        return dl;
    }

    public void setDl(double dl) {
        this.dl = dl;
    }

    public double getDh() {
        return dh;
    }

    public void setDh(double dh) {
        this.dh = dh;
    }
}
