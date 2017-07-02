package com.example.wom.appwom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.wom.appwom.Model.Taikhoan;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {

    @BindView(R.id.txtEmailTT)
    TextView txtEmail;
    @BindView(R.id.edtHoTenTT)
    EditText edtHoten;
    @BindView(R.id.rdGroupTT)
    RadioGroup rdGroup;
    @BindView(R.id.rdNamTT)
    RadioButton rdNam;
    @BindView(R.id.rdNuTT)
    RadioButton rdNu;
    @BindView(R.id.txtNgaySinhTT)
    TextView txtNgaySinh;
    @BindView(R.id.btnDateTT)
    Button btnDate;
    @BindView(R.id.btnXacNhanTT)
    Button btnXacNhan;
    @BindView(R.id.btnXacNhan_NhapLaiTT)
    Button btnNhapLai;
    // Thông tin trên ThongtintaikhoanActivity
    ArrayList<Taikhoan> accList;
    private RadioButton radioButton;
    private Calendar calendar;
    private int year, month, day;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        ButterKnife.bind(this);
        accList = new ArrayList<>();

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

        txtNgaySinh.setText(new StringBuilder()
                .append(year).append("-").append(month + 1).append("-")
                .append(day).append(" "));


        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getThongTinTaiKhoan();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }
    @OnClick({R.id.btnDateTT, R.id.btnXacNhanTT, R.id.btnXacNhan_NhapLaiTT})
    public void OnClick(Button button)
    {
        if (button.getId()==R.id.btnDateTT)
        {
            showDialog(999);
        }else if (button.getId()==R.id.btnXacNhanTT)
        {
            String hoten = edtHoten.getText().toString();
            String ngaysinh = txtNgaySinh.getText().toString();
            if (hoten.isEmpty())
            {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập Họ tên");
                edtHoten.requestFocus();
                return;
            }

            // bắt sự kiện khi đã bắt lỗi thành công, sau đó thực hiện sửa thông tin
            mProgress.show();
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {

                    // xác nhận thay đổi
                    XacNhanThayDoiThongTin();
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Cập nhật thành công !!");
                    mProgress.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 2000);

        }else if (button.getId() == R.id.btnXacNhan_NhapLaiTT)
        {
            edtHoten.setText(null);
            rdNam.setChecked(true);
            txtNgaySinh.setText(new StringBuilder()
                    .append(year).append("-").append(month + 1).append("-")
                    .append(day).append(" "));
        }
    }
    private void getThongTinTaiKhoan(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getThongTinTaiKhoan2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    accList.clear();
                    int ID = 0;
                    String email = "";
                    String ngaysinh = "";
                    String hoten = "";
                    String anhdaidien = "";
                    String gioitinh = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id_tk");
                            email = jsonObject.getString("email");
                            hoten = jsonObject.getString("hoten");
                            ngaysinh = jsonObject.getString("ngaysinh");
                            anhdaidien = jsonObject.getString("anhdaidien");
                            gioitinh = jsonObject.getString("gioitinh");
                            accList.add(new Taikhoan(email, ID, hoten, ngaysinh, anhdaidien, gioitinh));

                            if (USER_LOGIN_ID.equals(ID+"")){
                                txtEmail.setText(email);
                                edtHoten.setText(hoten);
                                txtNgaySinh.setText(ngaysinh);
                                if (gioitinh.equals("1")){
                                    rdNam.setChecked(true);
                                }else{
                                    rdNu.setChecked(true);
                                }
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
                hashMap.put("id_tk", USER_LOGIN_ID);
                hashMap.put("hoten", edtHoten.getText().toString());
                hashMap.put("anhdaidien", "1");
                // kiểm tra dữ liệu từ RadioGroup
                int selectedId = rdGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                hashMap.put("gioitinh", ((radioButton.getText().equals("Nam")==true) ? "1" : "2"));
                hashMap.put("ngaysinh", txtNgaySinh.getText().toString());
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
                    txtNgaySinh.setText(new StringBuilder().append(year)
                            .append("-").append(month + 1).append("-").append(day)
                            .append(" "));
                }
            };
}
