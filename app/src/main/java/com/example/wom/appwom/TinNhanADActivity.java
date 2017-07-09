package com.example.wom.appwom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.NguoiNhanTinAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.HoTen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class TinNhanADActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listTen;
    ArrayList<HoTen> getListTen;
    ArrayList<HoTen> listTam;

    String gui = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_nhan_ad);
        setTitle("Tin Nhắn");
        listView = (ListView) findViewById(R.id.listview);
        listTen = new ArrayList<>();
        listTam = new ArrayList<>();
        getListTen = new ArrayList<>();
        getHoTen();
        getTinNhan();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(TinNhanADActivity.this, TinNhanADGuiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nguoinhan", String.valueOf(listTam.get(i).getId_tk()));

                intent.putExtra("nn", bundle);
                startActivity(intent);
            }
        });
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run() {
                time += 1000;
                getTinNhan();
                h.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void getTinNhan() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getTinNhan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    listTen.clear();
                    String noidung = "";
                    String thoigian = "";
                    String nguoinhan = "";
                    String nguoigui = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            noidung = jsonObject.getString("noidung");
                            thoigian = jsonObject.getString("thoigian");
                            nguoinhan = jsonObject.getString("nguoinhan");
                            nguoigui = jsonObject.getString("nguoigui");
                            Boolean flag = true;
                            if (!nguoigui.equals(USER_LOGIN_ID)) {
                                for (int j = 0; j < listTen.size(); j++) {
                                    if (listTen.get(j).toString().equals(nguoigui)) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    listTen.add(nguoigui);
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
        listTam.clear();
        for (int j = 0 ; j < listTen.size() ; j++){
            for (int i = 0 ; i < getListTen.size() ; i++){

                if(listTen.get(j).toString().equals(String.valueOf(getListTen.get(i).getId_tk()))){
                    HoTen hoTen = new HoTen(getListTen.get(i).getId_tk(),getListTen.get(i)
                            .getHoten());
                    listTam.add(hoTen);
                }
            }
        }

        NguoiNhanTinAdapter adapter1 = new NguoiNhanTinAdapter(listTam,TinNhanADActivity.this);

        listView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }

    private void getHoTen() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getHoTen, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    getListTen.clear();
                    String id_tk;
                    String hoten = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_tk = jsonObject.getString("id_tk");
                            hoten = jsonObject.getString("hoten");
                            HoTen hoTen = new HoTen(Integer.parseInt(id_tk), hoten);

                            getListTen.add(hoTen);
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
