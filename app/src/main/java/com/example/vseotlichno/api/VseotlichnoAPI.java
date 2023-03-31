package com.example.vseotlichno.api;

import com.example.vseotlichno.screens.main.model.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VseotlichnoAPI {

    @GET("get_order.php")
    Call<Order> getOrder();

    @POST("set_order.php")
    Call<Void> setOrder(@Body Order order);

}
