package com.example.wom.appwom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Taikhoan;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class DoiMatKhauActivity extends AppCompatActivity {

    @BindView(R.id.txtMaXacNhanDMK)
    TextView txtMaXacNhan;
    @BindView(R.id.edtMatKhauCu)
    EditText edtMatKhauCu;
    @BindView(R.id.edtMatKhauMoi)
    EditText edtMatKhauMoi;
    @BindView(R.id.edtMatKhauMoi_NhapLai)
    EditText edtMatKhauMoi_NhapLai;
    @BindView(R.id.edtMaXacNhanDMK)
    EditText edtMaXacNhan;
    @BindView(R.id.btnXacNhanDMK)
    Button btnXacNhan;
    @BindView(R.id.btnNhapLaiDMK)
    Button btnNhapLai;


    ArrayList<Taikhoan> accList;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        ButterKnife.bind(this);

        accList = new ArrayList<>();

        /* Progress */
        mProgress =new ProgressDialog(this);
        String titleId="Đang xử lý dữ liệu...";
        mProgress.setTitle(titleId);
        mProgress.setCancelable(false);
        mProgress.setMessage("Vui lòng chờ trong giây lát...");

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getThongTinTaiKhoan();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }
    @OnClick({R.id.btnXacNhanDMK, R.id.btnNhapLaiDMK})
    public void onClick(Button button){
        if (button.getId() == R.id.btnNhapLaiDMK)
        {
            edtMatKhauCu.setText(null);
            edtMatKhauMoi.setText(null);
            edtMatKhauMoi_NhapLai.setText(null);
            edtMaXacNhan.setText(null);
            txtMaXacNhan.setText(new APIConfig().randomChu());
        }else if (button.getId() == R.id.btnXacNhanDMK) {
            String matkhau = edtMatKhauCu.getText().toString();
            String matkhaumoi = edtMatKhauMoi.getText().toString();
            String nhaplai = edtMatKhauMoi_NhapLai.getText().toString();
            String maxacnhan = txtMaXacNhan.getText().toString();
            String nhaplai_MXN = edtMaXacNhan.getText().toString();
            if (matkhau.isEmpty()) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng nhập mật khẩu cũ");
                edtMatKhauCu.requestFocus();
                return;
            }
            if (!matkhau.equals(accList.get(0).getMatkhau())) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Mật khẩu cũ không đúng");
                edtMatKhauCu.requestFocus();
                return;
            }
            if (matkhaumoi.isEmpty()) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng nhập mật khẩu mới");
                edtMatKhauMoi.requestFocus();
                return;
            }
            if (matkhaumoi.length() < 6) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Mật khẩu lớn hơn 6 kí tự");
                edtMatKhauMoi.requestFocus();
                return;
            }
            if (matkhaumoi.equals(matkhau)) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Mật khẩu mới trùng với mật khẩu cũ");
                edtMatKhauMoi.requestFocus();
                return;
            }
            if (nhaplai.isEmpty()) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng nhập lại mật khẩu mới");
                edtMatKhauMoi_NhapLai.requestFocus();
                return;
            }
            if (!matkhaumoi.equals(nhaplai)) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Nhập mật khẩu mới không chính xác !!");
                edtMatKhauMoi_NhapLai.requestFocus();
                return;
            }
            if (nhaplai_MXN.isEmpty()) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng nhập mã xác nhận");
                edtMaXacNhan.requestFocus();
                return;
            }
            if (!maxacnhan.equals(nhaplai_MXN)) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Mã xác nhận không chính xác");
                edtMaXacNhan.requestFocus();
                return;
            }

            // bắt sự kiện khi đã bắt lỗi thành công, sau đó thực hiện sửa thông tin
            mProgress.show();
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {

                    // xác nhận thay đổi
                    XacNhanThayDoiThongTin();
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đổi mật khẩu thành công !!");
                    mProgress.cancel();
                    Intent intent = new Intent(DoiMatKhauActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 2000);
        }
    }
    @Override
    protected void onStart() {
        txtMaXacNhan.setText(new APIConfig().randomChu());
        super.onStart();
    }
    private void getThongTinTaiKhoan(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getTaikhoan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    accList.clear();
                    int ID = 0;
                    String email = "";
                    String matkhau = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            email = jsonObject.getString("email");
                            matkhau = jsonObject.getString("matkhau");

                            if (USER_LOGIN_ID.equals(ID+""))
                            {
                                Taikhoan taikhoan = new Taikhoan();
                                taikhoan.setId_tk(ID);
                                taikhoan.setEmail(email);
                                taikhoan.setMatkhau(matkhau);
                                accList.add(taikhoan);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    // thay đổi xác nhận thông tin tài khoản, điền thông tin đầy đủ cho tài khoản
    private void XacNhanThayDoiThongTin(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_DoiMatKhau, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast(""+ response); // trả về id thông tin tài khoản

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
                hashMap.put("matkhau", edtMatKhauMoi_NhapLai.getText().toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
