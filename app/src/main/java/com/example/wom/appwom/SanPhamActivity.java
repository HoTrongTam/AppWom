package com.example.wom.appwom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wom.appwom.Util.CheckConnection;

public class SanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        final String arr[] = {"Điện thoại","Laptop"};
        ListView lv = (ListView)findViewById(R.id.lvSanpham);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0){
              // Intent intent = new Intent(SanPhamActivity.this, Dienthoai.class);
                //startActivity(intent);
                CheckConnection.ShowToast_Short(getApplicationContext(),"AAA");
            }else if(position == 1){
                //Intent intent = new Intent(SanPhamActivity.this,Laptop.class);
                //startActivity(intent);
                CheckConnection.ShowToast_Short(getApplicationContext(),"BBB");
            }
            }
        });
    }
}