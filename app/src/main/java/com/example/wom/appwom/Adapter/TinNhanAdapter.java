package com.example.wom.appwom.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wom.appwom.DBHelper.APIConfig;
import com.example.wom.appwom.Model.TinNhan;
import com.example.wom.appwom.R;

import java.util.ArrayList;

/**
 * Created by Nhan on 7/4/2017.
 */

public class TinNhanAdapter extends BaseAdapter {
    ArrayList<TinNhan> list;
    Activity context;

    class view_cell {
        TextView tvNguoi, tvTinNhan,tvThoiGian;
        LinearLayout linearLayout;
    }

    public TinNhanAdapter(ArrayList<TinNhan> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view_cell cell;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        if (view == null) {
            cell = new view_cell();
            view = inflater.inflate(R.layout.item_tinnhan, null);
            cell.tvNguoi = (TextView) view.findViewById(R.id.textView_nguoi);
            cell.tvTinNhan = (TextView) view.findViewById(R.id.textView_tinnhan);
            cell.tvThoiGian = (TextView) view.findViewById(R.id.textView_thoigian);
            cell.linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            view.setTag(cell);
        } else {
            cell = (view_cell) view.getTag();
        }
        if (APIConfig.USER_LOGIN_ID.equals(list.get(i).getNguoigui())) {
            cell.tvNguoi.setText("Bạn:");
            cell.linearLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }else {
            cell.tvNguoi.setText("QTV:");
        }
        cell.tvThoiGian.setText(list.get(i).getThoigian().toString());
        cell.tvTinNhan.setText(list.get(i).getNoidung());
        return view;
    }
}
