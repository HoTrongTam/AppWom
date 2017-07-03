package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TamHT12 on 7/3/2017.
 */

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arrSanpham;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arrSanpham) {
        this.context = context;
        this.arrSanpham = arrSanpham;
    }

    @Override
    public int getCount() {
        return arrSanpham.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSanpham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_laptop,null);

            viewHolder.txtTenLaptop = (TextView)convertView.findViewById(R.id.txtTenlaptop);
            viewHolder.txtMotaLaptop = (TextView)convertView.findViewById(R.id.txtMotalaptop);
            viewHolder.imgAnhLaptop = (ImageView)convertView.findViewById(R.id.imgLaptop);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Sanpham sanpham = (Sanpham)getItem(position);
        viewHolder.txtTenLaptop.setText(sanpham.getTensanpham());
        viewHolder.txtMotaLaptop.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.err)
                .into(viewHolder.imgAnhLaptop);

        return convertView;
    }
    public class ViewHolder{
        ImageView imgAnhLaptop;
        TextView txtTenLaptop;
        TextView txtMotaLaptop;
    }
}
