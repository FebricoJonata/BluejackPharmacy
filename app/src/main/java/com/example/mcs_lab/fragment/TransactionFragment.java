package com.example.mcs_lab.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mcs_lab.OnItemClickListener;
import com.example.mcs_lab.OnTransactionClick;
import com.example.mcs_lab.R;
import com.example.mcs_lab.adapter.TransactionAdapter;
import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.helper.SessionManager;
import com.example.mcs_lab.model.Transaction;
import com.example.mcs_lab.model.User;

import java.util.ArrayList;


public class TransactionFragment extends Fragment implements OnTransactionClick {

    private RecyclerView rcv;
    private TransactionAdapter ta;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    Database db;
    int userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medicine_list, container, false);
        rcv = root.findViewById(R.id.rcv);
        db = Database.getInstance(requireContext());
        SessionManager sm = SessionManager.getInstance(requireContext());
        User user = sm.getUser();
        this.userid = user.getUserId();
        transactions = db.getAllTransaction(userid);
        ta = new TransactionAdapter(transactions, this);
        LinearLayoutManager lm = new LinearLayoutManager(requireContext());
        rcv.setLayoutManager(lm);
        rcv.setAdapter(ta);
        return root;
    }

    @Override
    public void onUpdate(int userid, int medicineid, int quantity) {
        db.updateTransaction(userid, medicineid, quantity);
        transactions = db.getAllTransaction(userid);
        ta = new TransactionAdapter(transactions, this);
        rcv.setAdapter(ta);
        ta.notifyDataSetChanged();
    }

    @Override
    public void onDelete(int userid, int medicineid) {
        db.deleteTransaction(userid, medicineid);
        transactions = db.getAllTransaction(userid);
        ta = new TransactionAdapter(transactions, this);
        rcv.setAdapter(ta);
        ta.notifyDataSetChanged();
    }
}