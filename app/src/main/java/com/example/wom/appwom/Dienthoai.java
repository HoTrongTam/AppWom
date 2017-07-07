package com.example.wom.appwom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.Util.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_ROLE;


public class Dienthoai extends AppCompatActivity {

    Toolbar toolbar;
    ListView lv;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> sanphams;
    int iddt = 0;
    int page = 1;
    View footerView;
    boolean isloading = false;
    mHandler handler;
    boolean limitdata = false;
    ActionBar actionBar;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
        ButterKnife.bind(this);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {

            Getdata(page);
            Getidloaisp();
            Loadmore();
            title = getIntent().getStringExtra("ten");
            actionBar = getSupportActionBar();
            actionBar.setTitle(Html.fromHtml("<small>" + title + "</small>"));

        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
        }


    }

    private void Loadmore() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(USER_ROLE.equals("1")) {
                    Intent intent = new Intent(Dienthoai.this, ChitietSanpham.class);
                    intent.putExtra("thongtinsanpham", sanphams.get(position));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Dienthoai.this, SuaSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham", sanphams.get(position));
                    startActivity(intent);
                }
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    private void Getdata(int Page) {

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = APIConfig.DuongdanDT + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                int Gia = 0;
                String Hinhanh = "";
                String Mota = "";
                int idspdt = 0;
                if (response != null && response.length() != 2) {
                    lv.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Gia = jsonObject.getInt("giasanpham");
                            Mota = jsonObject.getString("motasanpham");
                            Hinhanh = jsonObject.getString("hinhsanpham");
                            idspdt = jsonObject.getInt("id_loaisanpham");
                            sanphams.add(new Sanpham(id, Tendt, Gia, Hinhanh, Mota, idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitdata = true;
                    lv.removeFooterView(footerView);
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


    public void Anhxa() {
        lv = (ListView) findViewById(R.id.lvDienthoai);
        sanphams = new ArrayList<>();
        dienthoaiAdapter = new DienthoaiAdapter(getApplicationContext(), sanphams);
        lv.setAdapter(dienthoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        handler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lv.addFooterView(footerView);
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
                Intent i = new Intent(Dienthoai.this, ThemmoiSanphamAdmin.class);
                i.putExtra("id_loai", iddt);
                startActivity(i);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
