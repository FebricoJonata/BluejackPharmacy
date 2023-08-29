package com.example.mcs_lab.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mcs_lab.OnItemClickListener;
import com.example.mcs_lab.R;
import com.example.mcs_lab.model.Medicine;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private ArrayList<Medicine> medicines;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public MedicineAdapter(ArrayList<Medicine> medicines){
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(medicines.get(position).getName());
        holder.manu.setText(medicines.get(position).getManufacturer());
        holder.price.setText(medicines.get(position).getPrice().toString());
        String url = medicines.get(position).getImage();
        Picasso.get().load(url).into(holder.imgv);
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgv;
        TextView name, manu, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv = itemView.findViewById(R.id.imgv);
            name = itemView.findViewById(R.id.name);
            manu = itemView.findViewById(R.id.manu);
            price = itemView.findViewById(R.id.price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Medicine clickedItem = medicines.get(position);
                            listener.onItemClick(clickedItem);
                        }
                    }
                }
            });
        }
    }
}
