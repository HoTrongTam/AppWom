package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wom.appwom.Model.Giohang;
import com.example.wom.appwom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Quang on 7/4/2017.
 */

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayGiohang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrayGiohang) {
        this.context = context;
        this.arrayGiohang = arrayGiohang;
    }

    @Override
    public int getCount() {
        return arrayGiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTengiohang, txtGiagiohang;
        public ImageView imgGiohang;
        public Button btnGiatri;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
         ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txtTengiohang = (TextView) view.findViewById(R.id.textViewTengiohang);
            viewHolder.txtGiagiohang = (TextView) view.findViewById(R.id.textViewGiagiohang);
            viewHolder.imgGiohang = (ImageView) view.findViewById(R.id.imageViewGiohang);
            viewHolder.btnGiatri = (Button) view.findViewById(R.id.ButtonGiatri);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txtTengiohang.setText(giohang.getTensp());
        viewHolder.txtGiagiohang.setText(giohang.getGiasp() + " VNĐ");
        Picasso.with(context).load(giohang.getHinhsp()).placeholder(R.drawable.load)
                .error(R.drawable.err).into(viewHolder.imgGiohang);
        viewHolder.btnGiatri.setText(giohang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewHolder.btnGiatri.getText().toString());


        return view;
    }
}
