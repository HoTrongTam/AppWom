package com.example.wom.appwom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.DonHangAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.DonHang;
import com.example.wom.appwom.Model.Taikhoan;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class LichSuDonHangActivity extends AppCompatActivity {

    @BindView(R.id.lvDonHang)
    ListView lvDonHang;

    ArrayList<DonHang> listDonHang;
    DonHangAdapter donHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_don_hang);
        ButterKnife.bind(this);
        listDonHang = new ArrayList<>();

        donHangAdapter = new DonHangAdapter(getApplicationContext(), listDonHang);
        lvDonHang.setAdapter(donHangAdapter);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {

            getDonHang();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
        }

    }

    private void getDonHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getDonHang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int giasanpham = 0;
                    int soluong = 0;
                    int id_tk = 0;
                    int id_sanpham = 0;
                    int id_donhang = 0;
                    String tensanpham = "";
                    String hinhsanpham = "";
                    String hoten = "";
                    int trangthai = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            giasanpham = jsonObject.getInt("giasanpham");
                            soluong = jsonObject.getInt("soluong");
                            id_tk = jsonObject.getInt("id_tk");
                            id_sanpham = jsonObject.getInt("id_sanpham");
                            id_donhang = jsonObject.getInt("id_donhang");
                            tensanpham = jsonObject.getString("tensanpham");
                            hinhsanpham = jsonObject.getString("hinhsanpham");
                            hoten = jsonObject.getString("hoten");
                            trangthai = jsonObject.getInt("tinhtrangdh");

                            if (USER_LOGIN_ID.equals(id_tk + "")) {
                                DonHang donhang = new DonHang(id_donhang,id_sanpham,0,soluong,giasanpham,hoten,tensanpham,hinhsanpham, trangthai);
                                listDonHang.add(donhang);
                                donHangAdapter.notifyDataSetChanged();
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
}