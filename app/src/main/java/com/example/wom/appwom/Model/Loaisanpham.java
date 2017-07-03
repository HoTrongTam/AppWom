package com.example.wom.appwom.Model;

/**
 * Created by TamHT12 on 7/3/2017.
 */

public class Loaisanpham {
    public int idloaisp;
    public String tenloaisp;
    public String hinhanhloaisp;


    public Loaisanpham(int idloaisp, String tenloaisp, String hinhanhloaisp) {

        this.idloaisp = idloaisp;
        this.tenloaisp = tenloaisp;
        this.hinhanhloaisp = hinhanhloaisp;
    }
    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return hinhanhloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        this.hinhanhloaisp = hinhanhloaisp;
    }


}
