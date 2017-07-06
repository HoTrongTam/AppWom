package com.example.wom.appwom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Util.CheckConnection;

import java.util.HashMap;
import java.util.Map;

public class ThemmoiSanphamAdmin extends AppCompatActivity {
    EditText edtTensp, edtGiasp, edtHinhsp, edtMotasp, edtLoaisp;
    Button btnThem, btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themmoi_sanpham_admin);
        Anhxa();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTensp.setText("");
                edtGiasp.setText("");
                edtHinhsp.setText("");
                edtMotasp.setText("");
                edtLoaisp.setText("");
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EnventButton();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void EnventButton() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tensp = edtTensp.getText().toString();
                final String giasp = edtGiasp.getText().toString();
                final String hinhsp = edtHinhsp.getText().toString();
                final String mota = edtMotasp.getText().toString();
                final String loaisp = edtLoaisp.getText().toString();
                if(tensp.length()>0 && giasp.length()>0 && hinhsp.length()>0&& edtMotasp.length()>0 && loaisp.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            APIConfig.URL_insertSanpham, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("masanpham",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tensanpham", tensp);
                            hashMap.put("giasanpham", giasp);
                            hashMap.put("anhsanpham", hinhsp);
                            hashMap.put("motasanpham", mota);
                            hashMap.put("loaisanpham", loaisp);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(ThemmoiSanphamAdmin.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        edtTensp = (EditText)findViewById(R.id.edtTensp);
        edtGiasp = (EditText)findViewById(R.id.edtGiasp);
        edtHinhsp = (EditText)findViewById(R.id.edtHinhsp);
        edtMotasp = (EditText)findViewById(R.id.edtMotasp);
        edtLoaisp = (EditText)findViewById(R.id.edtLoaisp);
        btnThem = (Button)findViewById(R.id.btnThemsp);
        btnReset = (Button)findViewById(R.id.btnReset);
    }
}
