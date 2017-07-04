package com.example.wom.appwom.Adapter;

import android.content.Context;
import android.text.TextUtils;
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
 * Created by hotrongtam on 7/2/2017.
 */

public class DienthoaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arrDienthoai;

    public DienthoaiAdapter(Context context, ArrayList<Sanpham> arrDienthoai) {
        this.context = context;
        this.arrDienthoai = arrDienthoai;
    }

    @Override
    public int getCount() {
        return arrDienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrDienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtTendt, txtMota, txtGia;
        public ImageView imgAnhdt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dienthoai, null);
            viewHolder.txtTendt = (TextView) convertView.findViewById(R.id.txtTenDienthoai);
            viewHolder.txtGia = (TextView)convertView.findViewById(R.id.txtGiaDienthoai);
            viewHolder.imgAnhdt = (ImageView) convertView.findViewById(R.id.imgDienthoai);
            viewHolder.txtMota = (TextView) convertView.findViewById(R.id.txtMotaDienthoai);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTendt.setText(sanpham.getTensanpham());
        viewHolder.txtGia.setText("Giá : "+sanpham.getGiasanpham()+" VNĐ");
        viewHolder.txtMota.setMaxLines(2);
        viewHolder.txtMota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMota.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.err)
                .into(viewHolder.imgAnhdt);


        return convertView;
    }
}
