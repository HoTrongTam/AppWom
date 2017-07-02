package com.example.wom.appwom.Model;

/**
 * Created by MinhDuyen on 12/06/2017.
 */

public class Taikhoan {
    public String email;
    public String matkhau;
    public int id_tk;
    public String hoten;
    public String ngaysinh;
    public String vaitro;
    public String trangthai_tk;
    public String maxacnhan;

    public Taikhoan(String email, String matkhau) {
        this.email = email;
        this.matkhau = matkhau;
    }

    public Taikhoan(String email, int id_tk, String maxacnhan) {
        this.email = email;
        this.id_tk = id_tk;
        this.maxacnhan = maxacnhan;
    }

    public Taikhoan(String email, int id_tk, String hoten, String maxacnhan) {
        this.email = email;
        this.id_tk = id_tk;
        this.hoten = hoten;
        this.maxacnhan = maxacnhan;
    }

    public String getMaxacnhan() {
        return maxacnhan;
    }

    public void setMaxacnhan(String maxacnhan) {
        this.maxacnhan = maxacnhan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
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

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getTrangthai_tk() {
        return trangthai_tk;
    }

    public void setTrangthai_tk(String trangthai_tk) {
        this.trangthai_tk = trangthai_tk;
    }
}
