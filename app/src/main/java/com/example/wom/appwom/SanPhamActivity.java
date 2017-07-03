package com.example.wom.appwom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wom.appwom.Util.CheckConnection;

public class SanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        final String arr[] = {"Điện thoại", "Laptop"};
        ListView lv = (ListView) findViewById(R.id.lvSanpham);
        lv.setItemsCanFocus(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), Dienthoai.class);
                        startActivity(i);
                        break;
                    case 1:
                        Toast.makeText(SanPhamActivity.this, "2", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
