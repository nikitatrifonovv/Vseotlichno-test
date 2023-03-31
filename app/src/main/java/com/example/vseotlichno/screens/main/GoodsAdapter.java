package com.example.vseotlichno.screens.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vseotlichno.R;
import com.example.vseotlichno.screens.main.model.Good;
import com.example.vseotlichno.screens.main.model.Order;

import java.io.Serializable;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodViewHolder> {

    MutableLiveData<Order> order;

    public GoodsAdapter() {
        order = new MutableLiveData<Order>();
    }

    public void setOrder(MutableLiveData<Order> order) {
        this.order = order;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsAdapter.GoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_good_item, parent, false);
        return new GoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.GoodViewHolder holder, int position) {

        Good good = order.getValue().getGoods().get(position);

        holder.tvGoodName.setText(good.getName());
        holder.tvGoodArticle.setText(String.valueOf(good.getArticle()));
        holder.tvGoodPrice.setText(String.format("%.2f x ", good.getPrice()));
        holder.tvGoodQuantity.setText(String.valueOf(good.getQuantity()));
        holder.tvGoodSumm.setText(String.format("= %.2f", good.getQuantity() * good.getPrice()));

        holder.goodItemLayout.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putString("good_name", good.getName());
            bundle.putInt("good_article",good.getArticle());
            bundle.putDouble("good_price",good.getPrice());
            bundle.putInt("good_quantity",good.getQuantity());

            Navigation.findNavController(holder.goodItemLayout).navigate(R.id.action_mainFragment_to_goodDetailFragment, bundle);

        });

    }

    @Override
    public int getItemCount() {
        Order _order = order.getValue();
        return _order != null ? _order.getGoods().size() : 0;
    }

    class GoodViewHolder extends RecyclerView.ViewHolder {

        private View goodItemLayout;
        private TextView tvGoodName;
        private TextView tvGoodArticle;
        private TextView tvGoodPrice;
        private TextView tvGoodQuantity;
        private TextView tvGoodSumm;

        public GoodViewHolder(@NonNull View itemView) {
            super(itemView);
            goodItemLayout = itemView;
            tvGoodName = itemView.findViewById(R.id.tv_good_name);
            tvGoodArticle = itemView.findViewById(R.id.tv_good_article);
            tvGoodPrice = itemView.findViewById(R.id.tv_good_price);
            tvGoodQuantity = itemView.findViewById(R.id.tv_good_quantity);
            tvGoodSumm = itemView.findViewById(R.id.tv_good_summ);
        }
    }

}
