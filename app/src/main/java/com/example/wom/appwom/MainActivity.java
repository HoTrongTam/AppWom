package com.example.wom.appwom;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wom.appwom.DBHelper.ConnectionClass;
import com.example.wom.appwom.Model.Taikhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText taikhoan;
    @BindView(R.id.editText4)
    EditText matkhau;

    @BindView(R.id.button)
    Button login;
    // arraylist cho adapter của listview
    private ArrayList<Taikhoan> alPF = new ArrayList<>();

    // URL dùng để truy xuất bảng PlatfForm
    private String URL_DISP_PLATFFORM = "http://192.168.1.16/webapi/display.php";

    @BindView(R.id.lvTaikhoan)
    ListView lvPlatfForm;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.button)
    void submitButton(View view) {
        if (view.getId() == R.id.button) {
            GetPlatfForms a = new GetPlatfForms();
            a.execute();
        }
    }

    class GetPlatfForms extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            ConnectionClass jsonParser = new ConnectionClass();
            String json = jsonParser.callService(URL_DISP_PLATFFORM, ConnectionClass.GET);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray taikhoan = jsonObj.getJSONArray("taikhoan");

                        for (int i = 0; i < taikhoan.length(); i++) {
                            JSONObject obj = (JSONObject) taikhoan.get(i);
                            Taikhoan pf = new Taikhoan(obj.getString("email"), obj.getString("matkhau"));
                            alPF.add(pf);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (pDialog.isShowing())
                pDialog.dismiss();
            getData();
        }
    }
    private void getData() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < alPF.size(); i++) {
            lables.add(((Taikhoan) alPF.get(i)).email);
            Toast.makeText(this, "" + alPF.size(), Toast.LENGTH_SHORT).show();
        }

//        // Tạo adapter cho listivew
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lables);
//
//        // Gắn adapter cho listview
//        lvPlatfForm.setAdapter(adapter);
    }
}
