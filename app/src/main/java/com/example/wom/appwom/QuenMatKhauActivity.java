package com.example.wom.appwom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.Model.Taikhoan;
import com.example.wom.appwom.Util.CheckConnection;
import com.example.wom.appwom.Util.SendMailTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuenMatKhauActivity extends AppCompatActivity {

    // Tab xác nhận thông tin đổi mật khẩu
    @BindView(R.id.txtMaXacNhanQMK)
    TextView txtMaXacNhanQMK;
    @BindView(R.id.edtEmailQMK)
    EditText edtEmail;
    @BindView(R.id.edtMaXacNhanQMK)
    EditText  edtMaXacNhan;
    @BindView(R.id.btnQuenMatKhau)
    Button btnQuenMatKhau;
    @BindView(R.id.btnNhapLaiQMK)
    Button btnNhapLaiQMK;
    @BindView(R.id.tabQuenMatKhau1)
    LinearLayout tabQMK1;
    @BindView(R.id.tabQuenMatKhau2)
    LinearLayout tabQMK2;

    // Thông tin tab Xác nhận mật khẩu mới
    @BindView(R.id.txtEmailQMK123)
    TextView txtQuenMatKhau;
    @BindView(R.id.btnXacNhanThongTinQMK)
    Button btnXacNhan;
    @BindView(R.id.btnXacNhan_NhapLaiQMK)
    Button btnNhapLai;
    @BindView(R.id.edtMatKhauMoiQMK)
    EditText edtMatKhauMoiQMK;
    @BindView(R.id.edtMatKhauMoiQMK_XacNhan)
    EditText edtMatKhauMoiQMK_XacNhan;
    @BindView(R.id.edtMaXacNhanMail)
    EditText edtMaXacNhanMail;

    // Thông tin trên Activity Quên mật khẩu
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    ArrayList<Taikhoan> accList;
    String UPDATE_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        // Khởi động thư viên Butter Knife
        ButterKnife.bind(this);
        accList = new ArrayList<>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            QuenMatKhau();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }
    @OnClick({R.id.btnQuenMatKhau, R.id.btnNhapLaiQMK, R.id.btnXacNhanThongTinQMK, R.id.btnXacNhan_NhapLaiQMK})
    public void onClick(Button button){
        if (button.getId() == R.id.btnQuenMatKhau)
        {
            Pattern p = null;
            Matcher m = null;
            String email = edtEmail.getText().toString();
            String maxacnhan = edtMaXacNhan.getText().toString();
            String MXN = txtMaXacNhanQMK.getText().toString();
            if (email.isEmpty()){
                CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập Email");
                edtEmail.requestFocus();
                return;
            }
            do {
                p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                m = p.matcher(email);
                if (!m.matches()) {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Nhập sai định dạng Email");
                    return;
                }
                break;
            } while (true);
            if (maxacnhan.isEmpty()){
                CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập mã xác nhận");
                edtMaXacNhan.requestFocus();
                return;
            }
            if (!maxacnhan.equals(MXN)){
                CheckConnection.ShowToast_Short(getApplicationContext(),"Mã xác nhận không đúng");
                edtMaXacNhan.requestFocus();
                return;
            }

            // bắt sự kiện khi đã bắt lỗi thành công, sau đó thực hiện thêm dữ liệu vào bảng tài khoản
            XuLyDuLieuMail(email);

        }else if (button.getId() == R.id.btnNhapLaiQMK)
        {
            // làm trống tab_quenmatkhau 1
            edtEmail.setText(null);
            txtMaXacNhanQMK.setText(new APIConfig().randomChu());
            edtMaXacNhan.setText(null);
        }else if (button.getId() == R.id.btnXacNhanThongTinQMK)
        {
            // Xác nhận thay đổi mật khẩu
            String maMail = edtMaXacNhanMail.getText().toString();
            String makhau1 = edtMatKhauMoiQMK.getText().toString();
            String makhau2 = edtMatKhauMoiQMK_XacNhan.getText().toString();

            if (maMail.isEmpty())
            {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập mã xác nhận");
                edtMaXacNhanMail.requestFocus();
                return;
            }

            QuenMatKhau();

            if (makhau1.isEmpty())
            {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập mật khẩu mới");
                edtMatKhauMoiQMK.requestFocus();
                return;
            }
            if (makhau2.isEmpty())
            {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập lại mật khẩu");
                edtMatKhauMoiQMK_XacNhan.requestFocus();
                return;
            }
            if (!makhau2.equals(makhau1))
            {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Nhập lại mật khẩu không chính xác");
                edtMatKhauMoiQMK_XacNhan.requestFocus();
                return;
            }

            for (int i = 0; i < accList.size(); i++) {
                Taikhoan taikhoan = accList.get(i);
                if (taikhoan.getMaxacnhan().equals(maMail) && taikhoan.getEmail().equals(txtQuenMatKhau.getText().toString()))
                {
                    // Thay đổi mật khẩu
                    POST_CHANGEPASSWORD(maMail,UPDATE_ID,makhau2);
                    CheckConnection.ShowToast_Short(getApplicationContext()," Đã thay đổi mật khẩu thành công");
                    Intent intent = new Intent(QuenMatKhauActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                    return;
                }
            }
            CheckConnection.ShowToast_Short(getApplicationContext(),"Mã xác nhận không chính xác");
            edtMaXacNhanMail.requestFocus();


        }else if (button.getId() == R.id.btnXacNhan_NhapLaiQMK)
        {
            // làm trống tab_quenmatkhau 2
            edtMaXacNhanMail.setText(null);
            edtMatKhauMoiQMK.setText(null);
            edtMatKhauMoiQMK_XacNhan.setText(null);
        }
    }
    private void QuenMatKhau(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getThongTinTaiKhoan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    accList.clear();
                    int ID = 0;
                    String email = "";
                    String maxacnhan = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id_tk");
                            email = jsonObject.getString("email");
                            maxacnhan = jsonObject.getString("maxacnhan");
                            accList.add(new Taikhoan(email,ID,maxacnhan));

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


    private void XuLyDuLieuMail(String email){
        for (int i =0; i< accList.size();i++){
            Taikhoan tk = accList.get(i);
            if (tk.getEmail().equals(email)){
                tabQMK1.setVisibility(View.INVISIBLE);
                tabQMK2.setVisibility(View.VISIBLE);
                txtQuenMatKhau.setText(email);
                UPDATE_ID = ""+tk.getId_tk();
                String maxacnhan =  new APIConfig().randomChu();
                String fromEmail ="chimotminhem1993@gmail.com";
                String fromPassword="nguyenminhduyen";
                String toEmails = email;
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                String emailSubject = "Xác nhận thay đổi mật khẩu từ WOM";
                String emailBody = "Chào "+accList.get(i).getHoten()+","
                        +"<br><br>Vui long nhap ma xac nhan: <b>"+ maxacnhan+"</b> de thay doi mat khau cua ban."
                        +"<br>Tran trong cam on!!"
                        +"<br>----------------------";

                // Gửi về Mail
                new SendMailTask(QuenMatKhauActivity.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);
                // cập nhật mã xác nhận cho tài khoản
                POST_KEY(maxacnhan, ""+tk.getId_tk());

                return;
            }
        }
        CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn cung cấp không đúng thông tin !!");
    }
    @Override
    protected void onStart() {
        txtMaXacNhanQMK.setText(new APIConfig().randomChu());
        super.onStart();
    }

    private void POST_KEY(final String maxacnhan, final String UPDATE_ID){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_CapNhatMaXacNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //CheckConnection.ShowToast_Short(getApplicationContext(),""+ response); // trả về ID tài khoản
                //UPDATE_ID = ""+response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("email", edtEmail.getText().toString());
                hashMap.put("maxacnhan", maxacnhan);
                hashMap.put("id_tk", UPDATE_ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void POST_CHANGEPASSWORD(final String maxacnhan, final String UPDATE_ID, final String matkhau){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_CapNhatMatKhau, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //CheckConnection.ShowToast_Short(getApplicationContext()," Đã thay đổi mật khẩu thành công");
                // trả về ID tài khoản
                //UPDATE_ID = ""+response;
                // thay đổi Key
                POST_KEY(new APIConfig().randomChu(), UPDATE_ID);
                QuenMatKhau();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("matkhau", matkhau);
                hashMap.put("maxacnhan", maxacnhan);
                hashMap.put("id_tk", UPDATE_ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
