package com.example.wom.appwom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

import static com.example.wom.appwom.DBHelper.APIConfig.USER_ROLE;

public class SanPhamActivity extends AppCompatActivity {

    ArrayList<Loaisanpham> loaisanphams;
    LoaisanphamAdapter loaisanphamAdapter;
    @BindView(R.id.lvSanpham)
    ListView listLoaisp;

    int idloaisp = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        ButterKnife.bind(this);
                        /*Intent i = new Intent(getApplicationContext(), Dienthoai.class);
                        startActivity(i);*/
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetdulieuLoaisp();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
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

                Intent i = new Intent(getApplicationContext(), Dienthoai.class);
                i.putExtra("id_loaisanpham", loaisanphams.get(position).getIdloaisp());
                startActivity(i);

            }
        });



    }

    private void GetdulieuLoaisp() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_LOAISP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idloaisp = jsonObject.getInt("id_loaisanpham");
                            tenloaisp = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp = jsonObject.getString("hinhloaisanpham");
                            loaisanphams.add(new Loaisanpham(idloaisp, tenloaisp, hinhanhloaisp));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (USER_ROLE.equals("0")) {
            inflater.inflate(R.menu.add, menu);
        } else {

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAdd:
                Intent i = new Intent(SanPhamActivity.this, ThemloaispAdmin.class);
                startActivity(i);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
