package com.example.wom.appwom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wom.appwom.Adapter.GioHangAdapter;
import com.example.wom.appwom.Util.CheckConnection;

public class GiohangActivity extends AppCompatActivity {
    ListView lvGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThanhToan, btnTieptuc;
    Toolbar  toolbar;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        CheckData();
        EventUltil();
        EventButton();
        CatchOnItemListView();
    }

    private void CatchOnItemListView() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(HomeActivity.mangGiohang.size() <= 0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else{
                            HomeActivity.mangGiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if(HomeActivity.mangGiohang.size()<=0){
                                txtThongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void EventButton() {
        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.mangGiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),DonhangActivity.class);
                    startActivity(intent);
                }else{
                    CheckConnection.ShowToast_Short
                            (getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    public static void EventUltil() {
        long tongtien = 0;
        for(int i = 0; i < HomeActivity.mangGiohang.size();i++){
            tongtien += HomeActivity.mangGiohang.get(i).getGiasp();
        }
        txtTongtien.setText(tongtien + " VNĐ");
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

    public void Anhxa(){
        lvGiohang = (ListView)findViewById(R.id.listViewGiohang);
        txtThongbao = (TextView)findViewById(R.id.textViewThongbao);
        txtTongtien = (TextView)findViewById(R.id.textViewTongtien);
        btnThanhToan = (Button)findViewById(R.id.ButtonThanhtoan);
        btnTieptuc = (Button)findViewById(R.id.ButtonTieptucmua);
        gioHangAdapter = new GioHangAdapter( GiohangActivity.this,HomeActivity.mangGiohang);
        lvGiohang.setAdapter(gioHangAdapter);

    }
}
