package com.example.mcs_lab.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcs_lab.DetailActivity;
import com.example.mcs_lab.OnItemClickListener;
import com.example.mcs_lab.R;
import com.example.mcs_lab.adapter.MedicineAdapter;
import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.model.Medicine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicineListFragment extends Fragment {

    private RecyclerView rcv;
    private MedicineAdapter ma;
    private ArrayList<Medicine> medicines = new ArrayList<>();
    Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medicine_list, container, false);
        rcv = root.findViewById(R.id.rcv);
        db = Database.getInstance(requireContext());
        getData();
        medicines = db.getMedicine();
        ma = new MedicineAdapter(medicines);
        ma.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Medicine medicine) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("image", medicine.getImage());
                intent.putExtra("name", medicine.getName());
                intent.putExtra("manu", medicine.getManufacturer());
                intent.putExtra("price", medicine.getPrice().toString());
                intent.putExtra("desc", medicine.getDescription());
                intent.putExtra("medicineid", medicine.getMedicineId().toString());
                startActivityForResult(intent, 1);
            }
        });

        GridLayoutManager lm = new GridLayoutManager(requireContext(), 2);
        rcv.setLayoutManager(lm);
        rcv.setAdapter(ma);
        ma.notifyDataSetChanged();
        return root;
    }

    private void getData(){
        String url = "https://mocki.io/v1/ae13b04b-13df-4023-88a5-7346d5d3c7eb";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("medicines");
                            parseData(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void parseData(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String manufacturer = jsonObject.getString("manufacturer");
                int price = jsonObject.getInt("price");
                String imageUrl = jsonObject.getString("image");
                String desc = jsonObject.getString("description");
                db.addMedicine(name, manufacturer, price, imageUrl, desc);
//                medicines.add(new Medicine(i+1, name, manufacturer, price, imageUrl, desc));
            }
            ma.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
