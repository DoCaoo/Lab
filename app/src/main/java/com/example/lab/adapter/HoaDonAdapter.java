package com.example.lab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.R;
import com.example.lab.dao.HoaDonChiTietDAO;
import com.example.lab.dao.HoaDonDAO;
import com.example.lab.dao.SachDAO;
import com.example.lab.model.HoaDon;
import com.example.lab.model.Sach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends BaseAdapter implements Filterable {
    List<HoaDon> arrHoaDon;
    List<HoaDon> arrSortHoaDon;
    private Filter hoaDonFilter;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonDAO hoaDonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");

    public HoaDonAdapter(Activity context, List<HoaDon> arrayHoaDon){
        super();
        this.context = context;
        this.arrHoaDon = arrayHoaDon;
        this.arrSortHoaDon = arrayHoaDon;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonDAO = new HoaDonDAO(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView img;
        TextView txtMaHoaDon;
        TextView txtNgayMua;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_hoadon,null);
            holder.img = (ImageView)convertView.findViewById(R.id.ivIcon);
            holder.txtMaHoaDon = (TextView)convertView.findViewById(R.id.tvMaHoaDon);
            holder.txtNgayMua = (TextView)convertView.findViewById(R.id.tvNgayMua);
            holder.imgDelete = (ImageView)convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hoaDonChiTietDAO.checkHoaDon(arrHoaDon.get(position).getMaHoaDon())) {
                        Toast.makeText(context, "Bạn phải xóa hóa đơn chi tiết trc khi xóa hóa đơn",Toast.LENGTH_LONG).show();
                    }else {
                        hoaDonDAO.deleteHoaDonByID(arrHoaDon.get(position).getMaHoaDon());
                        arrHoaDon.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();

        HoaDon _entry = (HoaDon) arrHoaDon.get(position);
        holder.img.setImageResource(R.drawable.hdicon);
        holder.txtMaHoaDon.setText(_entry.getMaHoaDon());
        holder.txtNgayMua.setText(sdf.format(_entry.getNgayMua()));

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDon> items){
        this.arrHoaDon = items;
        notifyDataSetChanged();
    }

    public void resetData(){
        arrHoaDon = arrSortHoaDon;
    }

    @Override
    public Filter getFilter(){
        if (hoaDonFilter == null)
            hoaDonFilter = new CustomFilter();

            return hoaDonFilter;
    }

    private class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                results.values = arrSortHoaDon;
                results.count = arrSortHoaDon.size();
            }else{
                List<HoaDon> lsHoaDon = new ArrayList<HoaDon>();
                for (HoaDon p : arrHoaDon){
                    if (p.getMaHoaDon().toUpperCase().startsWith(constraint.toString().toUpperCase()))lsHoaDon.add(p);
                }
                results.values = lsHoaDon;
                results.count = lsHoaDon.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }else{
                arrHoaDon = (List<HoaDon>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
