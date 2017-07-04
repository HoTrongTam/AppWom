package com.example.wom.appwom;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.DienthoaiAdapter;
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

public class LaptopAdmin extends AppCompatActivity {


    Toolbar toolbar;
    ListView listView;
    LaptopAdapter adapter;
    ArrayList<Sanpham> arrayList;
    int iddt = 0;
    int page = 1;
    View footerView;
    boolean isloading = false;
    mHandler handler;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_admin);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {

            Getdata(page);
            Getidloaisp();
            Loadmore();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
        }
    }

    private void Loadmore() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LaptopAdmin.this, ChitietSanphamLaptop.class);
                intent.putExtra("thongtinsanphamlaptop", arrayList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isloading == false && limitdata == false) {
                    isloading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void Getidloaisp() {
        iddt = getIntent().getIntExtra("id_loaisanpham", -1);
    }

    private void Getdata(int page) {
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
                if (response != null && response.length() != 2) {
                    listView.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Mota = jsonObject.getString("motasanpham");
                            Hinhanh = jsonObject.getString("hinhsanpham");
                            idspdt = jsonObject.getInt("id_loaisanpham");
                            arrayList.add(new Sanpham(id, Tendt, Hinhanh, Mota, idspdt));
                            adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitdata = true;
                    listView.removeFooterView(footerView);
                    Toast.makeText(getApplicationContext(), "Không còn dữ liệu", Toast.LENGTH_SHORT).show();

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

    private void Anhxa() {

        listView = (ListView) findViewById(R.id.lvLaptopAdmin);
        arrayList = new ArrayList<>();
        adapter = new LaptopAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(adapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        handler = new mHandler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAdd:
                Intent i = new Intent(LaptopAdmin.this, ThemmoiSanphamAdmin.class);
                startActivity(i);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listView.addFooterView(footerView);
                    break;
                case 1:
                    Getdata(++page);
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage(1);
            handler.sendMessage(message);
            super.run();
        }
    }
}
