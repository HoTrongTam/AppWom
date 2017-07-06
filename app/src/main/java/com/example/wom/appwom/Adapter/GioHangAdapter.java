package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wom.appwom.GiohangActivity;
import com.example.wom.appwom.HomeActivity;
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
    class ViewHolder{
        public TextView txtTengiohang, txtGiagiohang;
        public ImageView imgGiohang;
        public Button btnTru, btnCong, btnGiatri;
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
            viewHolder.btnCong = (Button) view.findViewById(R.id.ButtonCong);
            viewHolder.btnTru = (Button) view.findViewById(R.id.ButtonTru);
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
        if(sl>=10){
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        }else if(sl>=1){
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                int slMoi = Integer.parseInt(finalViewHolder.btnGiatri.getText().toString())+1;

                int slHientai = HomeActivity.mangGiohang.get(position).getSoluongsp();
                long giaHientai = HomeActivity.mangGiohang.get(position).getGiasp();
                HomeActivity.mangGiohang.get(position).setSoluongsp(slMoi);
                long giaMoi = (giaHientai * slMoi)/slHientai;
                HomeActivity.mangGiohang.get(position).setGiasp(giaMoi);
                finalViewHolder.txtGiagiohang.setText(giaMoi + " VNĐ");

                GiohangActivity.EventUltil();

                if(slMoi > 9 ){
                    finalViewHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                }else{
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
          }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoi = Integer.parseInt(finalViewHolder1.btnGiatri.getText().toString())-1;
                int slHientai = HomeActivity.mangGiohang.get(position).getSoluongsp();
                long giaHientai = HomeActivity.mangGiohang.get(position).getGiasp();

                long giaMoi = (giaHientai * slMoi)/slHientai;
                HomeActivity.mangGiohang.get(position).setGiasp(giaMoi);
                HomeActivity.mangGiohang.get(position).setSoluongsp(slMoi);
                finalViewHolder1.txtGiagiohang.setText(giaMoi + " VNĐ");

                GiohangActivity.EventUltil();
                if(slMoi < 2 ){
                    finalViewHolder1.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnTru.setVisibility(View.INVISIBLE);
                }else{
                    finalViewHolder1.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnTru.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
