package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hotrongtam on 7/1/2017.
 */

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.MyViewHolder> {

    Context context;
    ArrayList<Sanpham> arrSanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arrSanpham) {
        this.context = context;
        this.arrSanpham = arrSanpham;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAnhsp;
        public TextView txtTensp;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgAnhsp = (ImageView) itemView.findViewById(R.id.imgSanpham);
            txtTensp = (TextView) itemView.findViewById(R.id.txtTensanpham);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sanphammoi, null);
        MyViewHolder myholder = new MyViewHolder(v);


        return myholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Sanpham sanpham = arrSanpham.get(position);
        holder.txtTensp.setText(sanpham.getTensanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.err)
                .into(holder.imgAnhsp);
    }

    @Override
    public int getItemCount() {
        return arrSanpham.size();
    }


}
