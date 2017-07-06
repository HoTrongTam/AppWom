package com.example.wom.appwom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.LoaisanphamAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Loaisanpham;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class SanphamAdminAcitivity extends AppCompatActivity {

    ArrayList<Loaisanpham> loaisanphams;
    LoaisanphamAdapter loaisanphamAdapter;
    //@BindView(R.id.lvLoaispAdmin)
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham_admin_acitivity);
        ButterKnife.bind(this);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            Anhxa();
            Getdataloaisp();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn vui lòng kiểm tra lại kết nối");
            finish();
        }
    }

    private void Getdataloaisp() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_LOAISP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String ten = "";
                    String hinh = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.optJSONObject(i);
                            id = jsonObject.getInt("id_loaisanpham");
                            ten = jsonObject.getString("tenloaisanpham");
                            hinh = jsonObject.getString("hinhloaisanpham");
                            loaisanphams.add(new Loaisanpham(id, ten, hinh));
                            loaisanphamAdapter.notifyDataSetChanged();
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

    public void Anhxa() {
        listView = (ListView)findViewById(R.id.lvLoaispAdmin);
        loaisanphams = new ArrayList<>();
        loaisanphamAdapter = new LoaisanphamAdapter(getApplicationContext(), loaisanphams);
        listView.setAdapter(loaisanphamAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), DienthoaiAdmin.class);
                        i.putExtra("id_loaisanpham", loaisanphams.get(position).getIdloaisp());
                        startActivity(i);
                        break;
                    case 1:

                        Intent intent = new Intent(getApplicationContext(), LaptopAdmin.class);
                        intent.putExtra("id_loaisanpham", loaisanphams.get(position).getIdloaisp());
                        startActivity(intent);
                        break;

                }
            }
        });
    }
}
