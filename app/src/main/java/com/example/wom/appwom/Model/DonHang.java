package com.example.wom.appwom.Model;

/**
 * Created by Eagle007 on 04/07/2017.
 */

public class DonHang {
    int id_donhang, id_sanpham, id_chitietdonhang, soluong, gia,trangthai;
    String nguoimua, tensanpham, anhsanpham;

    public DonHang() {
    }

    public DonHang(int id_donhang, int id_sanpham, int id_chitietdonhang, int soluong, int gia) {
        this.id_donhang = id_donhang;
        this.id_sanpham = id_sanpham;
        this.id_chitietdonhang = id_chitietdonhang;
        this.soluong = soluong;
        this.gia = gia;
    }

    public DonHang(int id_donhang, int id_sanpham, int id_chitietdonhang, int soluong, int gia, String nguoimua, String tensanpham, String anhsanpham, int trangthai) {
        this.id_donhang = id_donhang;
        this.id_sanpham = id_sanpham;
        this.id_chitietdonhang = id_chitietdonhang;
        this.soluong = soluong;
        this.gia = gia;
        this.nguoimua = nguoimua;
        this.tensanpham = tensanpham;
        this.anhsanpham = anhsanpham;
        this.trangthai = trangthai;
    }

    public DonHang(int id_donhang, int trangthai, String nguoimua, String anhsanpham) {
        this.id_donhang = id_donhang;
        this.trangthai = trangthai;
        this.nguoimua = nguoimua;
        this.anhsanpham = anhsanpham;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getNguoimua() {
        return nguoimua;
    }

    public void setNguoimua(String nguoimua) {
        this.nguoimua = nguoimua;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getAnhsanpham() {
        return anhsanpham;
    }

    public void setAnhsanpham(String anhsanpham) {
        this.anhsanpham = anhsanpham;
    }

    public int getId_donhang() {
        return id_donhang;
    }

    public void setId_donhang(int id_donhang) {
        this.id_donhang = id_donhang;
    }

    public int getId_sanpham() {
        return id_sanpham;
    }

    public void setId_sanpham(int id_sanpham) {
        this.id_sanpham = id_sanpham;
    }

    public int getId_chitietdonhang() {
        return id_chitietdonhang;
    }

    public void setId_chitietdonhang(int id_chitietdonhang) {
        this.id_chitietdonhang = id_chitietdonhang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
