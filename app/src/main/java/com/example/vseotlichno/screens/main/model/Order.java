package com.example.vseotlichno.screens.main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private int order_id;
    private String order_num;
    private String order_date;
    private String phone;
    private List<Good> goods;
    private List<RefundGood> refundGoods;

    public Order() {
        goods = new ArrayList<>();
        refundGoods = new ArrayList<>();
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public List<RefundGood> getRefundGoods() {
        return refundGoods;
    }

    public void setRefundGoods(List<RefundGood> refundGoods) {
        if (refundGoods != null) {
            this.refundGoods = refundGoods;
        }
        else {
            this.refundGoods = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_num='" + order_num + '\'' +
                ", order_date='" + order_date + '\'' +
                ", phone='" + phone + '\'' +
                ", goods=" + goods +
                ", refundGoods=" + refundGoods +
                '}';
    }
}
