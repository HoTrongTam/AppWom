package com.example.wom.appwom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemloaispAdmin extends AppCompatActivity {

    @BindView(R.id.edtTenLoai)
    EditText edTen;
    @BindView(R.id.edtHinhLoai)
    EditText edHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themloaisp_admin);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnThemLoai, R.id.btnResetLoai})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnThemLoai:
                final String tenloai = edTen.getText().toString();
                final String hinhloai = edHinh.getText().toString();
                if (tenloai.length() > 0 && edHinh.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_insertLoai, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tensanpham", tenloai);
                            hashMap.put("anhsanpham", hinhloai);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(getApplicationContext(), "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SanPhamActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại dữ liệu");
                }
                break;
            case R.id.btnResetLoai:
                edTen.setText("");
                edHinh.setText("");
                break;
        }

    }

}
