package com.example.vseotlichno.screens.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vseotlichno.api.ApiClientVseotlichno;
import com.example.vseotlichno.screens.main.model.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {
    MutableLiveData<Order> orderLiveData;


    public OrderViewModel() {
        orderLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Order> getOrderLiveData() {
        if (orderLiveData == null) {
            orderLiveData = new MutableLiveData<>();
        }
        return orderLiveData;
    }

    public void loadNextOrder() {
        ApiClientVseotlichno.getInstance().getJSONApi().getOrder().enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                orderLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public boolean loadNextOrderAndCompleteCurrent() {

        orderLiveData.getValue().getRefundGoods().forEach(refundGood -> Log.i("TAG", "loadNextOrderAndCompleteCurrent: " + refundGood.getReason().isEmpty()));

        if (orderLiveData.getValue().getRefundGoods()
                .stream()
                .anyMatch(refundGood -> refundGood.getReason().isEmpty() || refundGood.getImgUri().isEmpty())) {
            return false;
        }

        ApiClientVseotlichno.getInstance().getJSONApi().setOrder(orderLiveData.getValue()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(getClass().getName(), "Saved?");
                loadNextOrder();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(getClass().getName(), t.getLocalizedMessage());
            }
        });
        return true;
    }
}