package com.example.wom.appwom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.DonHangAdminAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.DonHang;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;
import static com.example.wom.appwom.DBHelper.APIConfig.USER_ROLE;

public class DonhangActivity extends AppCompatActivity {

    @BindView(R.id.lvDanhSachDonHang)
    ListView lvDanhSachDonHang;
    @BindView(R.id.inputSearch)
    EditText inputSearch;
    ArrayList<DonHang> listDonHang, list;
    DonHangAdminAdapter donHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);
        ButterKnife.bind(this);
        listDonHang = new ArrayList<>();
        list = new ArrayList<>();
        donHangAdapter = new DonHangAdminAdapter(getApplicationContext(), listDonHang);
        lvDanhSachDonHang.setAdapter(donHangAdapter);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {

            getDonHang();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
        }
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                list.clear();
                for (int i = 0; i < listDonHang.size(); i++) {
                    DonHang dh = listDonHang.get(i);
                    if (input.equalsIgnoreCase("" + listDonHang.get(i).getId_donhang())) {
                        list.add(dh);
                        donHangAdapter = new DonHangAdminAdapter(getApplicationContext(), list);
                    }
                    lvDanhSachDonHang.setAdapter(donHangAdapter);
                    donHangAdapter.notifyDataSetChanged();
                }
                if (input.isEmpty()) {
                    donHangAdapter = new DonHangAdminAdapter(getApplicationContext(), listDonHang);
                    lvDanhSachDonHang.setAdapter(donHangAdapter);
                    donHangAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvDanhSachDonHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DonHang dh = (DonHang) lvDanhSachDonHang.getItemAtPosition(position);

                // Xác nhận đơn hàng đã duyệt
                if (dh.getTrangthai()==1){
                    Toast.makeText(DonhangActivity.this, "Đơn hàng đã được phê duyệt từ trước", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nếu chưa duyệt thì bước tiếp theo
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DonhangActivity.this);
                alertDialogBuilder.setMessage("Xác nhận duyệt đơn hàng?");
                alertDialogBuilder.setPositiveButton("Có",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(DonhangActivity.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        lvDanhSachDonHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DonHang dh = (DonHang) lvDanhSachDonHang.getItemAtPosition(position);
                Intent intent = new Intent(DonhangActivity.this, LichSuDonHangActivity.class);
                intent.putExtra("id_donhang", "" + dh.getId_donhang());
                startActivity(intent);
                return false;
            }
        });
    }

    private void getDonHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getlichsudonhang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    listDonHang.clear();
                    int id_donhang = 0;
                    int id_tk = 0;
                    String anhdaidien = "";
                    String hoten = "";
                    int trangthai = 0;
                    String email = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            id_tk = jsonObject.getInt("id_tk");
                            id_donhang = jsonObject.getInt("id_donhang");
                            anhdaidien = jsonObject.getString("anhdaidien");
                            hoten = jsonObject.getString("hoten");
                            trangthai = jsonObject.getInt("tinhtrangdh");
                            email = jsonObject.getString("email");
                            if (USER_ROLE.equals("0")) {
                                DonHang donhang = new DonHang(id_donhang, trangthai, hoten, anhdaidien);
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
