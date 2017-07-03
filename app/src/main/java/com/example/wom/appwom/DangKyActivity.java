package com.example.wom.appwom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Util.CheckConnection;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DangKyActivity extends AppCompatActivity {

    // Tab đăng ký
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
    @BindView(R.id.tab1)
    LinearLayout tabDangKy;
    @BindView(R.id.tab2)
    LinearLayout tabThongTin;

    // Tab Thông tin
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.edtHoTenDK)
    EditText edtHoTenDK;
    @BindView(R.id.rdGroupDK)
    RadioGroup rdGroupDK;
    @BindView(R.id.rdNamDK)
    RadioButton rdNam;
    @BindView(R.id.rdNuDK)
    RadioButton rdNuDK;
    @BindView(R.id.txtNgaySinhDK)
    TextView dateView;
    @BindView(R.id.btnDateDK)
    Button btnDateDK;
    @BindView(R.id.btnXacNhanDK)
    Button btnXacNhanDK;
    @BindView(R.id.btnXacNhan_NhapLaiDK)
    Button btnXacNhan_NhapLaiDK;
    @BindView(R.id.btnLoadAnhDK)
    Button btnLoadAnh;
    @BindView(R.id.edtURLDK)
    EditText edtURL;
    @BindView(R.id.imgAnhDaiDienDK)
    ImageView imgAvatar;
    @BindView(R.id.btnClearLoadAnhDK)
    Button btnClearURL;
    // Thông tin trong Activity
    ArrayList<String> listMail;
    private DatePicker datePicker;
    private Calendar calendar;
    private RadioButton radioButton;
    private int year, month, day;
    String REGISTER_ID = "";
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        // Khởi tạo butter knife
        ButterKnife.bind(this);
        listMail = new ArrayList<>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        /* Progress */
        mProgress =new ProgressDialog(this);
        String titleId="Đang xử lý dữ liệu...";
        mProgress.setTitle(titleId);
        mProgress.setCancelable(false);
        mProgress.setMessage("Vui lòng chờ trong giây lát...");

        dateView.setText(new StringBuilder()
                .append(year).append("-").append(month + 1).append("-")
                .append(day).append(" "));
        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            GetMail();
        }else{
            Toast("Vui lòng kiểm tra lại kết nối");
        }
    }
    @OnClick({R.id.btnDangKy, R.id.btnNhapLaiDK, R.id.btnDateDK, R.id.btnXacNhanDK, R.id.btnXacNhan_NhapLaiDK, R.id.btnLoadAnhDK, R.id.btnClearLoadAnhDK})
    public void btnDangKy(Button button) {
        if (button.getId() == R.id.btnDangKy)
        {
            Pattern p = null;
            Matcher m = null;
            final String email = edtEmailDK.getText().toString();
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
            if (matkhau.length() < 6){
                Toast("Mật khẩu lớn hơn 6 kí tự");
                edtMatKhau.requestFocus();
                return;
            }
            if (nhaplaimatkhau.isEmpty()){
                Toast("Vui lòng nhập lại Mật khẩu");
                edtMatKhauNhapLai.requestFocus();
                return;
            }
            if (!nhaplaimatkhau.equals(matkhau)){
                Toast("Mật khẩu xác nhận không hợp lệ");
                edtMatKhauNhapLai.requestFocus();
                return;
            }
            if (nhaplaimaxacnhan.isEmpty()){
                Toast("Vui lòng nhập Mã xác nhận");
                edtMaXacNhanDK.requestFocus();
                return;
            }
            if (!maxacnhan.equals(nhaplaimaxacnhan)){
                Toast("Mã xác nhận không hợp lệ");
                edtMaXacNhanDK.requestFocus();
                return;
            }

            // bắt sự kiện khi đã bắt lỗi thành công, sau đó thực hiện thêm dữ liệu vào bảng tài khoản
            mProgress.show();
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    tabDangKy.setVisibility(View.INVISIBLE);
                    tabThongTin.setVisibility(View.VISIBLE);
                    txtEmail.setText(email);
                    // xác nhận đăng ký
                    DangKyTaiKhoan();

                    rdNam.setChecked(true);
                    mProgress.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 2000);

        }else if (button.getId() == R.id.btnNhapLaiDK){
            edtEmailDK.setText(null);
            edtMatKhau.setText(null);
            edtMatKhauNhapLai.setText(null);
            edtMaXacNhanDK.setText(null);
            txtMaXacNhan.setText(new APIConfig().randomChu());
        }else if (button.getId() == R.id.btnXacNhanDK){
            if (edtHoTenDK.getText().toString().isEmpty()){
                Toast("Vui lòng nhập họ tên");
                edtHoTenDK.requestFocus();
                return;
            }

            // Xử lý lại mảng accList để truy vấn Mã xác nhận
            mProgress.show();
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    XacNhanDangKy();
                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                    mProgress.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 3000);


        }else if (button.getId() == R.id.btnDateDK){
            showDialog(999);
        }else if (button.getId() == R.id.btnXacNhan_NhapLaiDK){
            edtHoTenDK.setText(null);
            rdNam.setChecked(true);
        }else if (button.getId() == R.id.btnLoadAnhDK){
            String txtURL = edtURL.getText().toString();
            if (txtURL.isEmpty()){
                Picasso.with(getApplicationContext()).load(R.mipmap.ic_launcher).into(imgAvatar);
            }else{
                Picasso.with(getApplicationContext()).load(txtURL).into(imgAvatar);
            }
        }else if (button.getId() == R.id.btnClearLoadAnhDK){
            edtURL.setText(null);
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
    // Đăng ký trên bảng TaiKhoan
    private void DangKyTaiKhoan(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast(""+ response); // trả về ID tài khoản
                REGISTER_ID = ""+response;
                // POST Thông tin mặc định cho tài khoản
                POST_THONGTIN();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("email", txtEmail.getText().toString());
                hashMap.put("matkhau", edtMatKhauNhapLai.getText().toString());
                hashMap.put("vaitro", "1");
                hashMap.put("trangthai", "0");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    // THông tin mặc định cho tài khoản vừa tạo thành công
    private void POST_THONGTIN(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_Register_Confirm, new Response.Listener<String>() {
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
                hashMap.put("id_tk", REGISTER_ID);
                hashMap.put("hoten", "WOM");
                hashMap.put("anhdaidien", "R.mipmap.ic_launcher");
                hashMap.put("gioitinh", "1");
                hashMap.put("ngaysinh", dateView.getText().toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    // thay đổi xác nhận thông tin tài khoản, điền thông tin đầy đủ cho tài khoản
    private void XacNhanDangKy(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_CapNhatThongTinTaiKhoan, new Response.Listener<String>() {
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
                hashMap.put("id_tk", REGISTER_ID);
                hashMap.put("hoten", edtHoTenDK.getText().toString());
                hashMap.put("anhdaidien", edtURL.getText().toString());
                // kiểm tra dữ liệu từ RadioGroup
               int selectedId = rdGroupDK.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                hashMap.put("gioitinh", ((radioButton.getText().equals("Nam")==true) ? "1" : "2"));
                hashMap.put("ngaysinh", dateView.getText().toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    year = arg1;
                    month = arg2;
                    day = arg3;
                    dateView.setText(new StringBuilder().append(year)
                            .append("-").append(month + 1).append("-").append(day)
                            .append(" "));
                }
            };
}
