package com.example.wom.appwom.Model;

/**
 * Created by Nhan on 7/4/2017.
 */

public class TinNhan {
    String noidung,thoigian;
    String nguoinhan,nguoigui;

    public TinNhan() {
    }
    public TinNhan(String noidung, String thoigian, String nguoinhan, String nguoigui) {
        this.noidung = noidung;
        this.thoigian = thoigian;
        this.nguoinhan = nguoinhan;
        this.nguoigui = nguoigui;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getNguoinhan() {
        return nguoinhan;
    }

    public void setNguoinhan(String nguoinhan) {
        this.nguoinhan = nguoinhan;
    }

    public String getNguoigui() {
        return nguoigui;
    }

    public void setNguoigui(String nguoigui) {
        this.nguoigui = nguoigui;
    }
}
