package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wom.appwom.Model.Loaisanpham;
import com.example.wom.appwom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TamHT12 on 7/3/2017.
 */

public class LoaisanphamAdapter extends BaseAdapter{

    Context context;
    ArrayList<Loaisanpham> arrloaisp;

    public LoaisanphamAdapter(Context context, ArrayList<Loaisanpham> arrloaisp) {
        this.context = context;
        this.arrloaisp = arrloaisp;
    }

    @Override
    public int getCount() {
        return arrloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_loaisanpham,null);
            viewHolder.txtTenloaisp = (TextView)convertView.findViewById(R.id.txtTenLoaisp);
            viewHolder.imgHinhloaisp = (ImageView)convertView.findViewById(R.id.imgLoaisp);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Loaisanpham loaisanpham  = (Loaisanpham) getItem(position);
        viewHolder.txtTenloaisp.setText(loaisanpham.getTenloaisp());
        Picasso.with(context).load(loaisanpham.getHinhanhloaisp())
                .placeholder(R.drawable.load)
                .error(R.drawable.err)
                .into(viewHolder.imgHinhloaisp);
        return convertView;
    }
    public class ViewHolder{
        TextView txtTenloaisp;
        ImageView imgHinhloaisp;
    }
}
