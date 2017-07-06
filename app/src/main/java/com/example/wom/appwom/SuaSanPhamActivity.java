package com.example.wom.appwom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.Util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class SuaSanPhamActivity extends AppCompatActivity {
    EditText edtTensp, edtGiasp, edtHinhsp, edtMotasp, edtLoaisp;
    Button btnSuasp, btnXoasp;
    ImageView imgHinh;

    String tensp, hinhsp, motasp, idsp, giasp, idloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);

        Anhxa();
        GetInformation();
        EventButton();

    }

    private void EventButton() {
        btnSuasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tensp = edtTensp.getText().toString();
                giasp = edtGiasp.getText().toString();
                hinhsp = edtHinhsp.getText().toString();
                motasp = edtMotasp.getText().toString();
                idloai = edtLoaisp.getText().toString();
                if(tensp.length()>0 && giasp.length()>0 && hinhsp.length()>0&& motasp.length()>0 && idloai.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            APIConfig.URL_updateSanpham, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("id", idsp);
                            hashMap.put("tensanpham", tensp);
                            hashMap.put("giasanpham", giasp);
                            hashMap.put("hinhsanpham", hinhsp);
                            hashMap.put("motasanpham", motasp);
                            hashMap.put("id_loaisanpham", idloai);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(getApplicationContext(),"Cập nhật sản phẩm thành công",Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }
            }
        });

        btnXoasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        APIConfig.URL_deleteSanpham, new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("id", idsp);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
                Toast.makeText(getApplicationContext(),"Xóa sản phẩm thành công",Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        edtTensp = (EditText)findViewById(R.id.edtSuaTensp);
        edtGiasp = (EditText)findViewById(R.id.edtSuaGiasp);
        edtHinhsp = (EditText)findViewById(R.id.edtSuaHinhsp);
        edtMotasp = (EditText)findViewById(R.id.edtSuaMotasp);
        edtLoaisp = (EditText)findViewById(R.id.edtSuaLoaisp);
        imgHinh = (ImageView)findViewById(R.id.imgHinh);
        btnSuasp = (Button)findViewById(R.id.btnSuasp);
        btnXoasp = (Button)findViewById(R.id.btnXoasp);
    }


    private void GetInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");

        idsp = String.valueOf(sanpham.getId());
        tensp = sanpham.getTensanpham();
        giasp = String.valueOf(sanpham.getGiasanpham());
        hinhsp = sanpham.getHinhsanpham();
        motasp = sanpham.getMotasanpham();
        idloai = String.valueOf(sanpham.getIDSanpham());

        edtTensp.setText(tensp);
        edtGiasp.setText(giasp);
        edtHinhsp.setText(hinhsp);
        edtMotasp.setText(motasp);
        edtLoaisp.setText(idloai);
        Picasso.with(getApplicationContext()).load(hinhsp)
                .placeholder(R.drawable.load).error(R.drawable.err).into(imgHinh);
    }
}
