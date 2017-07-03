package com.example.wom.appwom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class SanPhamActivity extends AppCompatActivity {

    ArrayList<Loaisanpham> loaisanphams;
    LoaisanphamAdapter loaisanphamAdapter;
    @BindView(R.id.lvSanpham)
    ListView listLoaisp;

    int idloaisp=0;
    String tenloaisp ="";
    String hinhanhloaisp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        ButterKnife.bind(this);
                        /*Intent i = new Intent(getApplicationContext(), Dienthoai.class);
                        startActivity(i);*/
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetdulieuLoaisp();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }
    public void Anhxa() {
        loaisanphams = new ArrayList<>();
        loaisanphamAdapter = new LoaisanphamAdapter(getApplicationContext(), loaisanphams);
        listLoaisp.setAdapter(loaisanphamAdapter);
        listLoaisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), Dienthoai.class);
                        i.putExtra("id_loaisanpham",loaisanphams.get(position).getIdloaisp());
                        startActivity(i);
                        break;
                    case 1:

                        Intent intent = new Intent(getApplicationContext(),Laptop.class);
                        intent.putExtra("id_loaisanpham",loaisanphams.get(position).getIdloaisp());
                       startActivity(intent);
                        break;

                }
            }
        });

    }
    private void GetdulieuLoaisp() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_LOAISP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response!=null){
                    for (int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idloaisp = jsonObject.getInt("id_loaisanpham");
                            tenloaisp = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp = jsonObject.getString("hinhloaisanpham");
                            loaisanphams.add(new Loaisanpham(idloaisp,tenloaisp,hinhanhloaisp));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);


    }


}
