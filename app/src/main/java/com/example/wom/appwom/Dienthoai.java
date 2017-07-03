package com.example.wom.appwom;

import android.net.http.RequestQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.DienthoaiAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dienthoai extends AppCompatActivity {

    Toolbar toolbar;
    ListView lv;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> sanphams;
    int iddt = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {

            Getdata(page);
            Getidloaisp();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
        }


    }

    private void Getidloaisp(){
        iddt = getIntent().getIntExtra("id_loaisanpham",-1);
    }
    private void Getdata(int Page) {

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = APIConfig.DuongdanDT + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                String Hinhanh = "";
                String Mota = "";
                int idspdt = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Mota = jsonObject.getString("motasanpham");
                            Hinhanh = jsonObject.getString("hinhsanpham");
                            idspdt = jsonObject.getInt("id_loaisanpham");
                            sanphams.add(new Sanpham(id, Tendt, Hinhanh, Mota, idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id_loaisanpham", String.valueOf(iddt));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void Anhxa() {
        lv = (ListView) findViewById(R.id.lvDienthoai);
        sanphams = new ArrayList<>();
        dienthoaiAdapter = new DienthoaiAdapter(getApplicationContext(), sanphams);
        lv.setAdapter(dienthoaiAdapter);
    }
}
