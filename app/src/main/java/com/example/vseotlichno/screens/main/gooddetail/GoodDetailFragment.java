package com.example.vseotlichno.screens.main.gooddetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.vseotlichno.R;
import com.example.vseotlichno.s3.YandexStorageS3;
import com.example.vseotlichno.screens.main.OrderViewModel;
import com.example.vseotlichno.screens.main.model.Good;
import com.example.vseotlichno.screens.main.model.Order;
import com.example.vseotlichno.screens.main.model.RefundGood;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class GoodDetailFragment extends Fragment {

    int position;
    Order currentOrder;
    Good currentGood;
    RefundGood currentRefundGood;
    private OrderViewModel mViewModel;
    private GoodDetailViewModel gdViewModel;
    private TextView tvName;
    private TextView tvArticle;
    private TextView tvPrice;
    private TextInputEditText tiQuantity;
    private TextInputLayout tilQuantity;
    private TextInputEditText tiReason;
    private TextInputLayout tilReason;
    private TextView tvSumm;
    private ImageView imageView;
    private MaterialButton btnComplete;
    Uri lastImageUri;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultCallback<Boolean> cameraLauncherResult = result -> {
        if (result) {
            Picasso.get().load(lastImageUri).into(imageView);
            btnComplete.setVisibility(View.VISIBLE);
        }
        else {
            lastImageUri = null;
        }
    };

    private View.OnClickListener onClickListenerPicFromCamera = view -> {
        try {
            File f = File.createTempFile("tmp_image",".jpg", requireActivity().getCacheDir());
            lastImageUri = FileProvider.getUriForFile(requireActivity().getApplicationContext(),"com.example.vseotlichno.fileprovider",f);
            cameraLauncher.launch(lastImageUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    TextWatcher onChangeQuantity = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Integer newQ = null;
            try {
                newQ = Integer.valueOf(charSequence.toString());
            } catch (Exception ignore) {}
            if (newQ != null && !newQ.equals(currentGood.getQuantity())) {
                if (newQ < currentGood.getQuantity()) {
                    currentRefundGood.setQuantity(newQ);
                    gdViewModel.getRefundGoodMutableLiveData().setValue(currentRefundGood);
                    tilReason.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
                else {

                }
            }
            else if (newQ != null && newQ.equals(currentGood.getQuantity())) {
                currentRefundGood.setQuantity(newQ);
                gdViewModel.getRefundGoodMutableLiveData().setValue(currentRefundGood);
                tilReason.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
            }
            else {
                tilReason.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                tilQuantity.setErrorEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

    private final View.OnClickListener onClickListenerComplete = view -> {
        String imageKey = UUID.randomUUID().toString() + ".jpg";
        if (lastImageUri != null) {
            try {
                InputStream is = requireActivity().getContentResolver().openInputStream(lastImageUri);
                YandexStorageS3.getInstance().loadFileTask(imageKey, is);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            currentRefundGood.setImgUri(YandexStorageS3.getInstance().getUriFromKey(imageKey));
            updateData();
            currentOrder.getRefundGoods().add(currentRefundGood);
            Navigation.findNavController(requireView()).navigate(R.id.action_goodDetailFragment_to_mainFragment);
        }
        else if (currentRefundGood.getImgUri() != null) {
            updateData();
            Navigation.findNavController(requireView()).navigate(R.id.action_goodDetailFragment_to_mainFragment);
        }
        else {
            Toast.makeText(requireActivity(), "Необходимо прикрепить фото!", Toast.LENGTH_SHORT).show();
        }
    };

    private void updateData() {
        currentRefundGood.setReason(tiReason.getText().toString());
        currentRefundGood.setQuantity_refund(currentGood.getQuantity() - Integer.parseInt(tiQuantity.getText().toString()));
        currentGood.setQuantity(currentRefundGood.getQuantity());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cameraLauncher  = registerForActivityResult(new ActivityResultContracts.TakePicture(), cameraLauncherResult);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_good_detail, container, false);
        tvName = root.findViewById(R.id.tv_good_detail_name);
        tvArticle = root.findViewById(R.id.tv_good_detail_article);
        tvPrice = root.findViewById(R.id.tv_good_detail_price);
        tiQuantity = root.findViewById(R.id.ti_quantity);
        tilQuantity = root.findViewById(R.id.til_quantity);
        tiReason = root.findViewById(R.id.ti_reason);
        tilReason = root.findViewById(R.id.til_reason);
        tvSumm = root.findViewById(R.id.tv_good_detail_summ);
        imageView = root.findViewById(R.id.img_good_detail_pic);
        btnComplete = root.findViewById(R.id.btn_good_detail_complete);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        gdViewModel = new ViewModelProvider(requireActivity()).get(GoodDetailViewModel.class);

        gdViewModel.getRefundGoodMutableLiveData().observe(getViewLifecycleOwner(), refundGood -> {
            Log.d(getTag(), "RefundGood UPDATED: " + refundGood);
            tvName.setText(refundGood.getName());
            tvArticle.setText(String.valueOf(refundGood.getArticle()));
            tvPrice.setText(String.format(getString(R.string.good_detail_price_f),refundGood.getPrice()));
            tvSumm.setText(String.format(getString(R.string.good_detail_summ_2f), refundGood.getPrice() * refundGood.getQuantity()));
        });

        Bundle bundle = getArguments();
        position = bundle.getInt("position");

        currentOrder = mViewModel.getOrderLiveData().getValue();
        currentGood = currentOrder.getGoods().get(position);

        if (currentOrder.getRefundGoods().isEmpty() ||
                currentOrder.getRefundGoods()
                .stream()
                .noneMatch(refundGood -> refundGood.getArticle() == currentGood.getArticle())) {

            currentRefundGood = new RefundGood(currentGood);
            gdViewModel.getRefundGoodMutableLiveData().setValue(currentRefundGood);
            tiQuantity.setText(String.valueOf(currentRefundGood.getQuantity()));
        }
        else {
            currentRefundGood = currentOrder.getRefundGoods()
                    .stream()
                    .filter(rg -> rg.getArticle() == currentGood.getArticle())
                    .findFirst()
                    .get();

            tiQuantity.setText(String.valueOf(currentRefundGood.getQuantity()));
            imageView.setVisibility(View.VISIBLE);
            tilReason.setVisibility(View.VISIBLE);
            btnComplete.setVisibility(View.VISIBLE);

            gdViewModel.getRefundGoodMutableLiveData().setValue(currentRefundGood);
            Picasso.get().load(currentRefundGood.getImgUri()).into(imageView);
            tiReason.setText(currentRefundGood.getReason());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        tiQuantity.addTextChangedListener(onChangeQuantity);
        imageView.setOnClickListener(onClickListenerPicFromCamera);
        btnComplete.setOnClickListener(onClickListenerComplete);
    }
}