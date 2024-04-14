package com.example.lap3.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lap3.R;
import com.example.lap3.db.MyDB;
import com.example.lap3.model.ThiSinh;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThiSinhAdapter extends RecyclerView.Adapter<ThiSinhAdapter.ThiSinhViewHolder> {
    private List<ThiSinh> thiSinhs;


    public void setThiSinhs(List<ThiSinh> thiSinhs) {
        this.thiSinhs = thiSinhs;
    }

    public void sapXepTangTheoTongDiem(){
        thiSinhs.sort(Comparator.comparingDouble(ThiSinh::tinhTongDiem));
        notifyDataSetChanged();

    }

    public void sapXepGiamTheoTongDiem(){
        thiSinhs.sort((ts1, ts2) -> Double.compare(ts2.tinhTongDiem(), ts1.tinhTongDiem()));
        notifyDataSetChanged();

    }
    public ThiSinh getItem(int position) {
        return thiSinhs.get(position);
    }

    public ThiSinhAdapter(List<ThiSinh> thiSinhs) {
        this.thiSinhs = thiSinhs;
    }

    @NonNull
    @Override
    public ThiSinhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);





        return new ThiSinhViewHolder(view);
    }

    public static String formatDouble(double number) {
        // Kiểm tra xem số có phải là số nguyên không
        if (number == (int) number) {
            // Nếu là số nguyên, định dạng với một chữ số sau dấu thập phân
            return String.format("%.1f", number);
        } else {
            // Nếu không phải là số nguyên, định dạng bình thường
            return String.format("%.2f", number);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ThiSinhViewHolder holder, int position) {
        ThiSinh thiSinh=thiSinhs.get(position);
        holder.tvSBD.setText(thiSinh.getSbd());
        holder.tvHoTen.setText(thiSinh.getHoten());
        holder.tvTongDiem.setText(formatDouble(thiSinh.tinhTongDiem()));


    }
    public ThiSinh getThiSinh(int position){
        return thiSinhs.get(position);



    }



    @Override
    public int getItemCount() {
        return thiSinhs.size();
    }

    public static class ThiSinhViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tvSBD,tvHoTen,tvTongDiem;

        public ThiSinhViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSBD=itemView.findViewById(R.id.tvSBD);
            tvHoTen=itemView.findViewById(R.id.tvName);
            tvTongDiem=itemView.findViewById(R.id.tvTotal);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), R.id.edit, 0, "Sửa");
            menu.add(getAdapterPosition(), R.id.delete, 1, "Xóa");
        }


    }
}
