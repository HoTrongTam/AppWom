package com.example.wom.appwom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DangKyActivity extends AppCompatActivity {
    @BindView(R.id.btnDangKy)
    Button btnDangKy;
    @BindView(R.id.btnNhapLaiDK)
    Button btnNhapLaiDK;
    @BindView(R.id.edtEmailDK)
    EditText edtEmailDK;
    @BindView(R.id.edtMatKhauDK)
    EditText edtMatKhau;
    @BindView(R.id.edtMatKhauDK_NhapLai)
    EditText edtMatKhauNhapLai;
    @BindView(R.id.edtMaXacNhanDK)
    EditText edtMaXacNhanDK;
    @BindView(R.id.txtMaXacNhanDK)
    TextView txtMaXacNhan;
    ArrayList<String> listMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        // Khởi tạo butter knife
        ButterKnife.bind(this);
         listMail = new ArrayList<>();

        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            GetMail();
        }else{
            Toast("Vui lòng kiểm tra lại kết nối");
        }
    }
    @OnClick({R.id.btnDangKy,R.id.btnNhapLaiDK})
    public void btnDangKy(Button button) {
        if (button.getId() == R.id.btnDangKy)
        {
            Pattern p = null;
            Matcher m = null;
            String email = edtEmailDK.getText().toString();
            String matkhau = edtMatKhau.getText().toString();
            String nhaplaimatkhau = edtMatKhauNhapLai.getText().toString();
            String maxacnhan = txtMaXacNhan.getText().toString();
            String nhaplaimaxacnhan = edtMaXacNhanDK.getText().toString();
            if (email.isEmpty()){
                Toast("Vui lòng nhập Email");
                edtEmailDK.requestFocus();
                return;
            }
            do {
                p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                m = p.matcher(email);
                if (!m.matches()) {
                    Toast("Nhập sai định dạng Email");
                    return;
                }
                break;
            } while (true);
            for (int i = 0; i < listMail.size();i++)
            {
                if (email.equals(listMail.get(i)))
                {
                    Toast("Email đã được sử dụng");
                    return;
                }
            }
            if (matkhau.isEmpty()){
                Toast("Vui lòng nhập Mật khẩu");
                edtMatKhau.requestFocus();
                return;
            }
            if (nhaplaimatkhau.isEmpty()){
                Toast("Vui lòng nhập lại Mật khẩu");
                edtMatKhauNhapLai.requestFocus();
                return;
            }
            if (nhaplaimaxacnhan.isEmpty()){
                Toast("Vui lòng nhập Mã xác nhận");
                edtMaXacNhanDK.requestFocus();
                return;
            }
            if (maxacnhan.equals(nhaplaimaxacnhan)){
                Toast("Mã xác nhận không hợp lệ");
                edtMaXacNhanDK.requestFocus();
                return;
            }


        }
    }
    /* Toast ở trang Đăng Ký*/
    private void Toast(String toast){
        Toast.makeText(DangKyActivity.this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        txtMaXacNhan.setText(new APIConfig().randomChu());
        super.onStart();
    }
    private void GetMail(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getTaikhoan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listMail.clear();
                if (response != null) {
                    String email = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            email = jsonObject.getString("email");
                            listMail.add(email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast("không có dữ liệu: " + error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
