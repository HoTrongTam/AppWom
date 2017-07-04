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
import com.example.wom.appwom.Model.Sanpham;
import com.example.wom.appwom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DonHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<DonHang> listDonHang;

    public DonHangAdapter(Context context, ArrayList<DonHang> listDonHang) {
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
        public TextView txtTenSP, txtSoLuong, txtNguoiMua, txtTrangThai;
        public ImageView imgSanPham;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_donhang, null);
            viewHolder.txtSoLuong = (TextView) convertView.findViewById(R.id.txtSoLuong);
            viewHolder.txtTenSP = (TextView) convertView.findViewById(R.id.txtTenSanPham);
            viewHolder.txtNguoiMua = (TextView) convertView.findViewById(R.id.txtTenNguoiMua);
            viewHolder.txtTrangThai = (TextView) convertView.findViewById(R.id.txtTrangThai);
            viewHolder.imgSanPham = (ImageView) convertView.findViewById(R.id.imgSanPhamDonHang);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DonHang donHang = (DonHang) getItem(position);
        viewHolder.txtNguoiMua.setText(donHang.getNguoimua());
        viewHolder.txtNguoiMua.setMaxLines(2);
        viewHolder.txtNguoiMua.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtTenSP.setText("Tên SP: "+donHang.getTensanpham());
        viewHolder.txtSoLuong.setText("Số lượng: "+donHang.getSoluong());
        if (donHang.getTrangthai() == 1){
            viewHolder.txtTrangThai.setText("Đã duyệt");
            viewHolder.txtTrangThai.setTextColor(Color.BLUE);
        }else{
            viewHolder.txtTrangThai.setText("Chưa xác nhận đơn hàng");
            viewHolder.txtTrangThai.setTextColor(Color.GREEN);
        }
        Picasso.with(context).load(donHang.getAnhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.err)
                .into(viewHolder.imgSanPham);


        return convertView;
    }
}
