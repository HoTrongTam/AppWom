package com.example.wom.appwom;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wom.appwom.Model.Giohang;
import com.example.wom.appwom.Model.Sanpham;
import com.squareup.picasso.Picasso;

public class ChitietSanpham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtTen, txtGia, txtMota;
    Spinner spinner;
    Button btnDatmua;

    int id = 0;
    String tenChiTiet;
    int giaChiTiet = 0;
    String hinhAnhChiTiet;
    String moTaChiTiet;
    int idSanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);
        Anhxa();
        AcctionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    private void EventButton() {
        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.mangGiohang.size() > 0){
                    boolean exit = false;
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    for(int i = 0; i < HomeActivity.mangGiohang.size() ;i++){
                        if(HomeActivity.mangGiohang.get(i).getIdsp() == id){
                            HomeActivity.mangGiohang.get(i)
                                    .setSoluongsp(HomeActivity.mangGiohang.get(i).getSoluongsp()+sl);
                            if(HomeActivity.mangGiohang.get(i).getSoluongsp()>=10){
                                HomeActivity.mangGiohang.get(i).setSoluongsp(10);
                            }
                            HomeActivity.mangGiohang.get(i).
                                    setGiasp(giaChiTiet * HomeActivity.mangGiohang.get(i).getSoluongsp());
                            exit = true;
                        }
                    }
                    if(exit == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long tong = soluong * giaChiTiet;
                        HomeActivity.mangGiohang.add(new Giohang(id,tenChiTiet,tong,hinhAnhChiTiet,soluong));
                    }
                 }else{
                    int a = Integer.parseInt(spinner.getSelectedItem().toString());
                    long tong = a * giaChiTiet;
                    HomeActivity.mangGiohang.add(new Giohang(id,tenChiTiet,tong,hinhAnhChiTiet,a));
                }
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter =
                new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");

        id = sanpham.getId();
        tenChiTiet = sanpham.getTensanpham();
        giaChiTiet = sanpham.getGiasanpham();
        hinhAnhChiTiet = sanpham.getHinhsanpham();
        moTaChiTiet = sanpham.getMotasanpham();
        idSanpham = sanpham.getIDSanpham();

        txtTen.setText(tenChiTiet);
        txtGia.setText("Giá : " + giaChiTiet + " VNĐ");
        txtMota.setText(moTaChiTiet);
        Picasso.with(getApplicationContext()).load(hinhAnhChiTiet)
                .placeholder(R.drawable.load).error(R.drawable.err).into(imgChitiet);
    }

    private void AcctionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa(){

        toolbarChitiet = (Toolbar) findViewById(R.id.toolbarChitietsanpham);
        imgChitiet = (ImageView) findViewById(R.id.imageViewChitietsanpham);
        txtTen = (TextView) findViewById(R.id.textViewTenchitietsanpham);
        txtGia = (TextView) findViewById(R.id.textViewGiachitietsanpham);
        txtMota = (TextView) findViewById(R.id.textViewMotasanpham);
        spinner = (Spinner) findViewById(R.id.spinnerChitietsanpham);
        btnDatmua = (Button) findViewById(R.id.buttonMua);
    }
}
