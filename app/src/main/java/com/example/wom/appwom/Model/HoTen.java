package com.example.wom.appwom.Model;

/**
 * Created by Nhan on 7/8/2017.
 */

public class HoTen {
    int id_tk;
    String hoten;

    public HoTen() {
    }

    public HoTen(int id_tk, String hoten) {
        this.id_tk = id_tk;
        this.hoten = hoten;
    }

    public int getId_tk() {
        return id_tk;
    }

    public void setId_tk(int id_tk) {
        this.id_tk = id_tk;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
