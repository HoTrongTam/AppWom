package com.example.wom.appwom.Model;

/**
 * Created by hotrongtam on 7/1/2017.
 */

public class Sanpham {
    public int id;
    public String Tensanpham;
    public String Hinhsanpham;
    public String Motasanpham;
    public int IDSanpham;
    public Sanpham(int id, String tensanpham, String hinhsanpham, String motasanpham, int IDSanpham) {
        this.id = id;
        Tensanpham = tensanpham;
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
