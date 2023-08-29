package com.example.mcs_lab.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mcs_lab.OnTransactionClick;
import com.example.mcs_lab.R;
import com.example.mcs_lab.helper.SessionManager;
import com.example.mcs_lab.model.Medicine;
import com.example.mcs_lab.model.Transaction;
import com.example.mcs_lab.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private ArrayList<Transaction> transactions;
    private OnTransactionClick listener;
    SessionManager sm;

    public TransactionAdapter(ArrayList<Transaction> transactions, OnTransactionClick listener) {
        this.transactions = transactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Medicine medicine = transactions.get(position).getMedicine();
        holder.name.setText(medicine.getName());
        holder.date.setText(transactions.get(position).getDate());
        holder.price.setText("Price: " + medicine.getPrice().toString());
        holder.quantity.setText("Quantity: " + transactions.get(position).getQuantity().toString());
        String url = medicine.getImage();
        Picasso.get().load(url).into(holder.imgv);
        User user = sm.getUser();

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int id = transactions.get(position).getMedicineId();
                    int quantity = Integer.parseInt(holder.quantityedt.getText().toString());
                    listener.onUpdate(user.getUserId(), id, quantity);
                }
//                int id = transactions.get(position).getMedicineId();
//                listener.onUpdate(user.getUserId(), id, Integer.parseInt(holder.quantityedt.getText().toString()));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int id = transactions.get(position).getMedicineId();
                    listener.onDelete(user.getUserId(), id);
                }
//                int id = transactions.get(position).getMedicineId();
//                listener.onDelete(user.getUserId(), id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgv;
        TextView name, price, date, quantity;
        Button update, delete;
        EditText quantityedt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv = itemView.findViewById(R.id.imgv);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
            quantityedt = itemView.findViewById(R.id.quantityedt);

            sm = SessionManager.getInstance(itemView.getContext());
        }
    }
}
