package com.example.wom.appwom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wom.appwom.Adapter.GioHangAdapter;
import com.example.wom.appwom.Model.Giohang;

public class GiohangActivity extends AppCompatActivity {
    ListView lvGiohang;
    TextView txtThongbao, txtTongtien;
    Button btnThanhToan, btnTieptuc;
    Toolbar toolbarGiohang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
    }

    private void CheckData() {
        if(HomeActivity.mangGiohang.size() <=0){
            lvGiohang.deferNotifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }else{
            lvGiohang.deferNotifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Anhxa(){
        lvGiohang = (ListView)findViewById(R.id.listViewGiohang);
        txtThongbao = (TextView)findViewById(R.id.textViewThongbao);
        txtTongtien = (TextView)findViewById(R.id.textViewTongtien);
        btnThanhToan = (Button)findViewById(R.id.ButtonThanhtoan);
        btnTieptuc = (Button)findViewById(R.id.ButtonTieptucmua);
        toolbarGiohang = (Toolbar)findViewById(R.id.toolbarGiohang);
        gioHangAdapter = new GioHangAdapter( GiohangActivity.this,HomeActivity.mangGiohang);
        lvGiohang.setAdapter(gioHangAdapter);

    }
}
