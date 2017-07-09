package com.example.wom.appwom.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wom.appwom.Model.HoTen;
import com.example.wom.appwom.R;

import java.util.ArrayList;

/**
 * Created by Nhan on 7/8/2017.
 */

public class NguoiNhanTinAdapter extends BaseAdapter {
    ArrayList<HoTen> list;
    Activity context;


    public NguoiNhanTinAdapter(ArrayList<HoTen> list, Activity context) {
        this.list = list;
        this.context = context;
    }
    class view_cell {
        TextView tvNguoi;
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
            view = inflater.inflate(R.layout.item_nhantin, null);
            cell.tvNguoi = (TextView) view.findViewById(R.id.textView);

            view.setTag(cell);
        } else {
            cell = (view_cell) view.getTag();
        }

        cell.tvNguoi.setText(list.get(i).getHoten().toString());

        return view;
    }
}
