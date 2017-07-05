package com.example.wom.appwom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.GioHangAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Giohang;
import com.example.wom.appwom.Util.CheckConnection;

import java.util.HashMap;
import java.util.Map;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class GiohangActivity extends AppCompatActivity {
    ListView lvGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThanhToan, btnTieptuc;
    Toolbar  toolbar;
    GioHangAdapter gioHangAdapter;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        CheckData();
        EventUltil();
        EventButton();
        CatchOnItemListView();

                /* Progress */
        mProgress =new ProgressDialog(this);
        String titleId="Đang thực hiện giao dịch...";
        mProgress.setTitle(titleId);
        mProgress.setCancelable(false);
        mProgress.setMessage("Vui lòng chờ trong giây lát...");
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

                    POST_DONHANG();
                    // bắt sự kiện khi đã bắt lỗi thành công, sau đó thực hiện sửa thông tin
                    mProgress.show();

                    Runnable progressRunnable = new Runnable() {
                        @Override
                        public void run() {
                            // xác nhận post
                            Toast.makeText(GiohangActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                            mProgress.cancel();
                            HomeActivity.mangGiohang.clear();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 2000);


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
    // Xác nhận mua hàng

    String DONHANG_ID;

    private void POST_DONHANG(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_insertDonHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(GiohangActivity.this, ""+response, Toast.LENGTH_SHORT).show(); // trả về id đơn hàng
                DONHANG_ID = response;

                for (int i = 0; i < HomeActivity.mangGiohang.size();i++)
                {
                    Giohang giohang = HomeActivity.mangGiohang.get(i);
                    POST_CHITIETDONHANG(""+giohang.getIdsp(),""+giohang.getSoluongsp());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("id_tk", USER_LOGIN_ID);
                hashMap.put("tinhtrangdh", "0");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void POST_CHITIETDONHANG(final String id_sanpham, final String soluong){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_insertChiTietDonHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText(GiohangActivity.this, ""+response, Toast.LENGTH_SHORT).show(); // trả về id chi tiet đơn hàng
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("id_sanpham", id_sanpham);
                hashMap.put("id_donhang", DONHANG_ID);
                hashMap.put("soluong", soluong);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
