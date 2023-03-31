package com.example.vseotlichno.screens.main.gooddetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vseotlichno.screens.main.model.Good;
import com.example.vseotlichno.screens.main.model.Order;
import com.example.vseotlichno.screens.main.model.RefundGood;

public class GoodDetailViewModel extends ViewModel {
    private MutableLiveData<RefundGood> refundGoodMutableLiveData;

    public MutableLiveData<RefundGood> getRefundGoodMutableLiveData() {
        if (refundGoodMutableLiveData == null) {
            refundGoodMutableLiveData = new MutableLiveData<>();
        }
        return refundGoodMutableLiveData;
    }
}