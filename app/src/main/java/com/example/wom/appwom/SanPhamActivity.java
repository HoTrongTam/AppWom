package com.example.wom.appwom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.LoaisanphamAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Loaisanpham;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_ROLE;

public class SanPhamActivity extends AppCompatActivity {

    ArrayList<Loaisanpham> loaisanphams;
    LoaisanphamAdapter loaisanphamAdapter;
    @BindView(R.id.lvSanpham)
    ListView listLoaisp;
    Dialog dialog;
    EditText tens, hinhs;
    int idloaisp = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    Button btSua, btRe;

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
            if (USER_ROLE.equals("0")) {
                listLoaisp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final int ide = loaisanphams.get(position).getIdloaisp();
                        final String ten = loaisanphams.get(position).getTenloaisp();
                        final String hinh = loaisanphams.get(position).getHinhanhloaisp();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SanPhamActivity.this);
                        builder.setTitle("Lựa chọn chức năng");
                        builder.setMessage("Bạn muốn xóa hay sửa");
                        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_deleteLoai, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("id_loaisanpham", String.valueOf(ide));
                                        return hashMap;
                                    }
                                };
                                requestQueue.add(stringRequest);
                                Toast.makeText(SanPhamActivity.this, "Xóa thành công" + ide, Toast.LENGTH_SHORT).show();
                                loaisanphamAdapter.notifyDataSetChanged();
                                finish();


                            }
                        });
                        builder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDialog();
                                tens.setText(ten);
                                hinhs.setText(hinh);


                                btSua.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.URL_updateLoai, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                loaisanphams.clear();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                                hashMap.put("id_loaisanpham", String.valueOf(ide));
                                                hashMap.put("tenloaisanpham", tens.getText().toString());
                                                hashMap.put("hinhloaisanpham", hinhs.getText().toString());


                                                return hashMap;
                                            }
                                        };
                                        //
                                        requestQueue.add(stringRequest);
                                        Toast.makeText(SanPhamActivity.this, "Sửa thành công" + tens.getText().toString() + "", Toast.LENGTH_SHORT).show();
                                        loaisanphamAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });


                        builder.show();

                        Toast.makeText(SanPhamActivity.this, "admin", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
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
                i.putExtra("ten",loaisanphams.get(position).getTenloaisp());
                startActivity(i);

            }
        });


    }

    public void showDialog() {
        dialog = new Dialog(SanPhamActivity.this);
        dialog.setContentView(R.layout.dialog_sualoai);
        dialog.show();
        tens = (EditText) dialog.findViewById(R.id.edSuaten);
        hinhs = (EditText) dialog.findViewById(R.id.edSuaHinh);
        btSua = (Button) dialog.findViewById(R.id.btnSuaLoai);
        btRe = (Button) dialog.findViewById(R.id.btnResetSua);


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
