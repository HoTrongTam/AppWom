package com.example.wom.appwom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wom.appwom.DBHelper.ConnectionClass;
import com.example.wom.appwom.DBHelper.HttpHandler;
import com.example.wom.appwom.Model.Taikhoan;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wom.appwom.DBHelper.APIConfig.URL_Login;
import static com.example.wom.appwom.DBHelper.APIConfig.USERNAME;

public class DangNhapActivity extends AppCompatActivity {

    @BindView(R.id.edtUser)
    EditText edtUser;
    @BindView(R.id.edtMatKhau)
    EditText edtMatKhau;
    @BindView(R.id.chkLuuMatKhau)
    CheckBox chkLuuMatKhau;
    @BindView(R.id.btnDangNhap)
    Button btnDangNhap;
    @BindView(R.id.btnNhapLai)
    Button btnNhapLai;
    @BindView(R.id.txtQuenMatKhau)
    TextView txtQuenMatKhau;
    @BindView(R.id.txtTaoTaiKhoan)
    TextView txtTaoTaiKhoan;

    ProgressDialog mProgress;
    ArrayList<HashMap<String, String>> accList;

    private static final String TAG = "DangNhapActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        ButterKnife.bind(this);

        accList = new ArrayList<>();
                /*- Progress -*/
        mProgress =new ProgressDialog(this);
        String titleId="Đang đăng nhập...";
        mProgress.setTitle(titleId);
        mProgress.setCancelable(false);
        mProgress.setMessage("Vui lòng chờ trong giây lát...");

    }

    @OnClick({R.id.btnDangNhap ,R.id.btnNhapLai})
    public void submit(Button button) {
        String user = edtUser.getText().toString();
        String pass = edtMatKhau.getText().toString();
        if (button.getId() == R.id.btnDangNhap){
            if (user.isEmpty())
            {
                Toast("Không được bỏ trống Email đăng nhập");
                return;
            }else if (pass.isEmpty())
            {
             Toast("Không được bỏ trống mật khẩu đăng nhập");
                return;
            }else {
                new GetContacts().execute();
            }
        } else if (button.getId() == R.id.btnNhapLai){
            edtUser.setText(null);
            edtMatKhau.setText(null);
        }
    }

    private void Toast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.show();
           // Toast.makeText(DangNhapActivity.this,"Đang kiểm tra ",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(URL_Login);

           // Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObj.getJSONArray("taikhoan");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id_tk");
                        String matkhau = c.getString("matkhau");
                        String email = c.getString("email");

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("id", id);
                        contact.put("matkhau", matkhau);
                        contact.put("email", email);

                        accList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Kết nối bị lỗi: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Không thể kiểm tra dữ liệu: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Không thể kết nối với server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Vui lòng kiểm tra lại mạng!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast(""+accList);
//            Toast(accList.get(0).get("email"));
//            for (int i = 0; i < accList.size();i++){
//                if (accList.get(i).get("email")==username){
//
//                }
//            }
            mProgress.dismiss();
        }
    }
}
