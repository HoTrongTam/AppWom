package com.example.wom.appwom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.LaptopAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Laptop extends AppCompatActivity {


    ListView listLaptop;
    ArrayList<Sanpham> sanphamArrayList;
    LaptopAdapter adapter;
    int iddt = 0;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

      Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){

            Getidloaisp();
            Getdata(page);
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn vui lòng kiểm tra lại kết nối");
            finish();
        }
    }
    private void Getidloaisp() {
        iddt = getIntent().getIntExtra("id_loaisanpham", -1);
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
                            sanphamArrayList.add(new Sanpham(id, Tendt, Hinhanh, Mota, idspdt));
                            adapter.notifyDataSetChanged();

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
    public void Anhxa(){
        listLaptop = (ListView) findViewById(R.id.lvLaptop);
        sanphamArrayList = new ArrayList<>();
        adapter = new LaptopAdapter(getApplicationContext(),sanphamArrayList);
        listLaptop.setAdapter(adapter);
    }
}
