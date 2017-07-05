package com.example.wom.appwom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wom.appwom.Adapter.SanphamAdapter;
import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.Model.Taikhoan;
import com.example.wom.appwom.Util.CheckConnection;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wom.appwom.DBHelper.APIConfig.USER_LOGIN_ID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Cấu hình NavigationView
    @BindView(R.id.lvMainsp)
    RecyclerView recyclerView;
    ArrayList<Sanpham> sanphamArrayList;
    SanphamAdapter sanphamAdapter;
    TextView txtHoTenNV;
    TextView txtEmailNV;
    ImageView imgAvatar;
    ArrayList<Taikhoan> accList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        accList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        txtHoTenNV = (TextView) headerLayout.findViewById(R.id.txtNameMain);
        txtEmailNV = (TextView) headerLayout.findViewById(R.id.txtMailMain);
        imgAvatar = (ImageView) headerLayout.findViewById(R.id.imgAvatarMain);
        sanphamArrayList = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(), sanphamArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(sanphamAdapter);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getThongTinTaiKhoan();
            GetdataSanpham();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void GetdataSanpham() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_LoadProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String tensp = "";
                    int gia = 0;
                    String hinhanhsp = "";
                    String mota = "";
                    int idloai = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensanpham");
                            gia = jsonObject.getInt("giasanpham");
                            hinhanhsp = jsonObject.getString("hinhsanpham");
                            mota = jsonObject.getString("motasanpham");
                            idloai = jsonObject.getInt("id_loaisanpham");
                            sanphamArrayList.add(new Sanpham(id, tensp, gia, hinhanhsp, mota, idloai));
                            sanphamAdapter.notifyDataSetChanged();
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, ThongTinTaiKhoanActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery2) {
            Intent intent = new Intent(MainActivity.this, SanphamAdminAcitivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, LichSuDonHangActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, DoiMatKhauActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getThongTinTaiKhoan() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(APIConfig.URL_getThongTinTaiKhoan2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    accList.clear();
                    int ID = 0;
                    String email = "";
                    String anhdaidien = "";
                    String hoten = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id_tk");
                            email = jsonObject.getString("email");
                            hoten = jsonObject.getString("hoten");
                            anhdaidien = jsonObject.getString("anhdaidien");
                            accList.add(new Taikhoan(email, ID, hoten, "", anhdaidien, ""));
                            if (USER_LOGIN_ID.equals(ID + "")) {
                                txtHoTenNV.setText(hoten);
                                txtEmailNV.setText(email);
                                if (!anhdaidien.equals("R.mipmap.ic_launcher")) {
                                    Picasso.with(getApplicationContext()).load(anhdaidien).into(imgAvatar);
                                } else {
                                    Picasso.with(getApplicationContext()).load(R.mipmap.ic_launcher).into(imgAvatar);
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
    }
}
