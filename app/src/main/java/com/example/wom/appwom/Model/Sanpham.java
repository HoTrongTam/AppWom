package com.example.wom.appwom.Model;

import java.io.Serializable;

/**
 * Created by hotrongtam on 7/1/2017.
 */

public class Sanpham implements Serializable{
    public int id;
    public String Tensanpham;
    public int Giasanpham;
    public String Hinhsanpham;
    public String Motasanpham;
    public int IDSanpham;
    public Sanpham(int id, String tensanpham,int giasanpham, String hinhsanpham, String motasanpham, int IDSanpham) {
        this.id = id;
        Tensanpham = tensanpham;
        Giasanpham = giasanpham;
        Hinhsanpham = hinhsanpham;
        Motasanpham = motasanpham;
        this.IDSanpham = IDSanpham;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public int getGiasanpham(){ return Giasanpham;}

    public void setGiasanpham(int giasanpham){ Giasanpham = giasanpham;}

    public String getHinhsanpham() {
        return Hinhsanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        Hinhsanpham = hinhsanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public int getIDSanpham() {
        return IDSanpham;
    }

    public void setIDSanpham(int IDSanpham) {
        this.IDSanpham = IDSanpham;
    }


}
