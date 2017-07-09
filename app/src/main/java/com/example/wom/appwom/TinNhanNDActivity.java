package com.example.wom.appwom;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.TinNhanAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.HoTen;
import com.example.wom.appwom.Model.TinNhan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class TinNhanNDActivity extends AppCompatActivity {
    EditText edtTinNhan;
    Button btnGui;
    ListView listView;
    ArrayList<TinNhan> list;
    public ArrayList<HoTen> listHoTen;
    TinNhanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_nhan_nd);
        setTitle("Tin nhắn");
        edtTinNhan = (EditText) findViewById(R.id.editText_tinnhan);
        btnGui = (Button) findViewById(R.id.button2);
        listView = (ListView) findViewById(R.id.listview);
        listHoTen = new ArrayList<>();
        list = new ArrayList<>();
        getTinNhan();
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTinNhan();
            }
        });
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;
            @Override
            public void run() {
                time += 2000;
                getTinNhan();
                h.postDelayed(this, 2000);
            }
        }, 2000);
    }

    private void postTinNhan() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_postTinNhan, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        edtTinNhan.setText("");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("noidung", edtTinNhan.getText().toString());
                hashMap.put("thoigian", "2017-07-08");
                hashMap.put("id_nguoinhan", "1");
                hashMap.put("id_nguoigui", USER_LOGIN_ID + "");

                return hashMap;

            }
        };
        requestQueue.add(stringRequest);
    }
    private void getTinNhan() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getTinNhan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    list.clear();
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


                            if ((nguoinhan.equals(USER_LOGIN_ID) && nguoigui.equals("1")) ||
                                    (nguoigui.equals(USER_LOGIN_ID) && nguoinhan.equals("1"))) {
                                TinNhan tinNhan = new TinNhan(noidung, thoigian,nguoinhan,nguoigui);
                                list.add(tinNhan);
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
        adapter = new TinNhanAdapter(list, TinNhanNDActivity.this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
