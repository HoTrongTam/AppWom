package com.example.wom.appwom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wom.appwom.DBHelper.APIConfig;

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
    @BindView(R.id.txtMaXacNhanDK)
    TextView txtMaXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        // Khởi tạo butter knife
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btnDangKy,R.id.btnNhapLaiDK})
    public void btnDangKy(Button button) {
        if (button.getId() == R.id.btnDangKy)
        edtEmailDK.setText("AAA"); txtMaXacNhan.setText(new APIConfig().randomChu());
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
}
