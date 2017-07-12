package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wom.appwom.Model.DonHang;
import com.example.wom.appwom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DonHangAdminAdapter extends BaseAdapter {

    Context context;
    ArrayList<DonHang> listDonHang;

    public DonHangAdminAdapter(Context context, ArrayList<DonHang> listDonHang) {
        this.context = context;
        this.listDonHang = listDonHang;
    }

    @Override
    public int getCount() {
        return listDonHang.size();
    }

    @Override
    public Object getItem(int position) {
        return listDonHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtMaDonHang, txtTenNguoiMua, txtTrangThaiNguoiMua;
        public ImageView imgNguoiMuaHang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_donhangadmin, null);
            viewHolder.txtMaDonHang = (TextView) convertView.findViewById(R.id.txtMaDonHang);
            viewHolder.txtTenNguoiMua = (TextView) convertView.findViewById(R.id.txtTenNguoiMua);
            viewHolder.txtTrangThaiNguoiMua = (TextView) convertView.findViewById(R.id.txtTrangThaiNguoiMua);
            viewHolder.imgNguoiMuaHang = (ImageView) convertView.findViewById(R.id.imgNguoiMuaHang);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DonHang donHang = (DonHang) getItem(position);
        viewHolder.txtTenNguoiMua.setText(donHang.getNguoimua());
        viewHolder.txtTenNguoiMua.setMaxLines(2);
        viewHolder.txtTenNguoiMua.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMaDonHang.setText(""+donHang.getId_donhang());
        if (donHang.getTrangthai() == 1){
            viewHolder.txtTrangThaiNguoiMua.setText("Đã duyệt");
            viewHolder.txtTrangThaiNguoiMua.setTextColor(Color.BLUE);
        }else{
            viewHolder.txtTrangThaiNguoiMua.setText("Chưa xác nhận đơn hàng");
            viewHolder.txtTrangThaiNguoiMua.setTextColor(Color.GREEN);
        }
        if (donHang.getAnhsanpham().equals("R.mipmap.ic_launcher"))
        {
            Picasso.with(context).load(R.mipmap.ic_launcher)
                    .placeholder(R.drawable.load)
                    .error(R.drawable.err)
                    .into(viewHolder.imgNguoiMuaHang);
        }else{
            Picasso.with(context).load(donHang.getAnhsanpham())
                    .placeholder(R.drawable.load)
                    .error(R.drawable.err)
                    .into(viewHolder.imgNguoiMuaHang);
        }


        return convertView;
    }
}
