package com.example.wom.appwom.DBHelper;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Eagle007 on 30/06/2017.
 */

public class APIConfig {
    public static final String locahost = "192.168.1.7";
    public static final String URL_Login = "http://"+locahost+":8081/WOM/getTaiKhoan.php";
    public static final String URL_getTaikhoan = "http://"+locahost+":8081/WOM/getTaiKhoan2.php";
    public static final String URL_getThongTinTaiKhoan = "http://"+locahost+":8081/WOM/getThongTinTaiKhoan.php";
    public static final String URL_getThongTinTaiKhoan2 = "http://"+locahost+":8081/WOM/getThongTinTaiKhoan2.php";
    public static final String URL_Register = "http://"+locahost+":8081/WOM/postTaiKhoan.php";
    public static final String URL_Register_Confirm = "http://"+locahost+":8081/WOM/insertThongtintaikhoan.php";
    public static final String URL_LoadProduct = "http://"+locahost+":8081/WOM/getsanpham.php";
    public static final String URL_CapNhatMaXacNhan = "http://"+locahost+":8081/WOM/updateMaXacNhan.php";
    public static final String URL_CapNhatMatKhau = "http://"+locahost+":8081/WOM/updateMatKhau.php";
    public static final String URL_DoiMatKhau = "http://"+locahost+":8081/WOM/updateDoiMatKhau.php";
    public static final String URL_CapNhatTrangThai = "http://"+locahost+":8081/WOM/updateTrangThai.php";
    public static final String URL_CapNhatThongTinTaiKhoan = "http://"+locahost+":8081/WOM/updateThongTinTaiKhoan.php";
    public static final String DuongdanDT = "http://"+locahost+":8081/WOM/getlvsanpham.php?page=";
    public static final String URL_LOAISP = "http://"+locahost+":8081/WOM/getLoaisanpham.php";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static String USER_LOGIN_ID = "USER_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_HOTEN = "USER_HOTEN";
    public static String USER_ROLE = "USER_ROLE";
    public static final String URL_insertSanpham = "http://"+locahost+":8081/WOM/insertSanPham.php";
    public static final String URL_updateSanpham = "http://"+locahost+":8081/WOM/updateSanPham.php";
    public static final String URL_deleteSanpham = "http://"+locahost+":8081/WOM/deleteSanPham.php";
    public static final String URL_insertLoai = "http://"+locahost+":8081/WOM/insertLoaisanpham.php";
    public static final String URL_deleteLoai = "http://"+locahost+":8081/WOM/deleteLoai.php";
    public static final String URL_updateLoai = "http://"+locahost+":8081/WOM/updateLoaisp.php";
    public static final String QUANGCAO_01 = "https://tinhte.cdnforo.com/store/2014/08/2572609_Hinh_2.jpg";
    public static final String QUANGCAO_02 = "http://znews-photo-td.zadn.vn/w480/Uploaded/OFH_oazszstq/2017_05_18/cothemthongtinveiphone801.jpg";
    public static final String QUANGCAO_03 = "https://cdn.mediamart.vn/News/mua-vaio-rinh-qua-xperia-cung-media-mart-924201273231am.jpg";
    public static final String QUANGCAO_04 = "http://cdn.images.express.co.uk/img/dynamic/59/590x/blackberry-keyone-uk-price-release-date-mwc-2017-772697.jpg";
    public static final String URL_getDonHang = "http://"+locahost+":8081/WOM/getDonHang.php";
    public static final String URL_insertDonHang = "http://"+locahost+":8081/WOM/insertDonHang.php";
    public static final String URL_insertChiTietDonHang = "http://"+locahost+":8081/WOM/insertChiTietDonHang.php";
    public static final String URL_postTinNhan = "http://"+locahost+":8081/WOM/postTinNhan.php";
    public static final String URL_getTinNhan = "http://"+locahost+":8081/WOM/getTinNhan.php";
    public static final String URL_getHoTen = "http://"+locahost+":8081/WOM/getHoTen.php";
    public static final String URL_getlichsudonhang = "http://"+locahost+":8081/WOM/getlichsudonhang.php";
    public static final String URL_postTinhTrangDH = "http://"+locahost+":8081/WOM/updatexacnhandonhang.php";
    // Random kiểu chữ và số
    SecureRandom random = new SecureRandom();
    public String randomChu() {
        return new BigInteger(28, random).toString(32).toUpperCase();
    }
}