package com.example.lab.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab.NguoiDungActivity;
import com.example.lab.R;
import com.example.lab.dao.NguoiDungDAO;
import com.example.lab.model.NguoiDung;

import java.util.List;

public class NguoiDungRecAdapter extends RecyclerView.Adapter<NguoiDungRecAdapter.RecyclerViewHolder> {
    private Context context;
    private List<NguoiDung> arrNguoiDung;
    private LayoutInflater inflater;
    public NguoiDungDAO nguoiDungDAO;

    public NguoiDungRecAdapter(Context context, List<NguoiDung> arrNguoiDung){
        this.context = context;
        this.nguoiDungDAO = new NguoiDungDAO(context);
        this.arrNguoiDung = arrNguoiDung;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Tao view
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,final int i) {
        View view = inflater.inflate(R.layout.item_nguoi_dung,null);
        RecyclerViewHolder view1 = new RecyclerViewHolder(view);
        view1.ivIcon = (ImageView)view.findViewById(R.id.ivIcon);
        view1.tvName = (TextView) view.findViewById(R.id.tvName);
        view1.tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        view1.ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        view1.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xoa trong db
                String a = arrNguoiDung.get(i).getUserName();
                nguoiDungDAO.deleteNguoiDungByID(a);
                //xoa trong list
                NguoiDung nguoiDung = arrNguoiDung.get(i);
                arrNguoiDung.remove(nguoiDung);//xoa tren list
                //cap nhat thay doi
                notifyDataSetChanged();
            }
        });

        return view1;
    }

    //Gan du lieu
    @Override
    public void onBindViewHolder( RecyclerViewHolder holder,final int i) {
        NguoiDung nguoiDung = arrNguoiDung.get(i);
        holder.tvName.setText(nguoiDung.getUserName());
        holder.tvPhone.setText(nguoiDung.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NguoiDungActivity.class);

                // tao bundle chua du lieu
                Bundle bundle = new Bundle();
                bundle.putString("userName_key",arrNguoiDung.get(i).getUserName());
                bundle.putString("userPass_key",arrNguoiDung.get(i).getUserPass());
                bundle.putString("phone_key",arrNguoiDung.get(i).getPhone());
                bundle.putString("hoTen_key",arrNguoiDung.get(i).getHoTen());

                //day du lieu qua intent
                intent.putExtra("bun",bundle);
                context.startActivity(intent);
            }
        });
    }

    //tinh so luong item
    @Override
    public int getItemCount() {
        return arrNguoiDung.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvPhone;
        ImageView ivDelete;

        //phuong thuc khoi tao
        public RecyclerViewHolder(View itemView){
            super(itemView);
            this.ivIcon = ivIcon;
            this.tvName = tvName;
            this.tvPhone = tvPhone;
            this.ivDelete = ivDelete;
        }
    }
}
