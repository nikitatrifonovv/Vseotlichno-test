package com.example.vseotlichno.screens.main;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vseotlichno.R;
import com.google.android.material.button.MaterialButton;

public class OrderFragment extends Fragment {

    private OrderViewModel mViewModel;
    private MaterialButton btnOrderComplete;
    private TextView tvOrderId;
    private TextView tvOrderDate;
    private TextView tvOrderPhone;
    private RecyclerView rvGoods;
    private GoodsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        tvOrderId = root.findViewById(R.id.tv_oredr_id);
        tvOrderDate = root.findViewById(R.id.tv_order_date);
        tvOrderPhone = root.findViewById(R.id.tv_order_phone);
        btnOrderComplete = root.findViewById(R.id.btn_order_complete);

        adapter = new GoodsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvGoods = root.findViewById(R.id.rv_goods);
        rvGoods.setLayoutManager(linearLayoutManager);
        rvGoods.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        btnOrderComplete.setOnClickListener(view -> {
            if (!mViewModel.loadNextOrderAndCompleteCurrent()) {
                Toast.makeText(requireActivity(), "Запоните все данные у измененых товаров!", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getOrderLiveData().observe(this, order -> {
            tvOrderId.setText(String.format(String.valueOf(getText(R.string.id_s)), order.getOrder_id()));
            tvOrderDate.setText(String.format(String.valueOf(getText(R.string.date_s)), order.getOrder_date()));
            tvOrderPhone.setText(String.format(String.valueOf(getText(R.string.phone_s)), order.getPhone()));
            adapter.setOrder(mViewModel.getOrderLiveData());
            Log.d(getTag(), mViewModel.getOrderLiveData().getValue().toString());
        });

        if (mViewModel.getOrderLiveData().getValue() == null) {
            mViewModel.loadNextOrder();
        }
    }
}