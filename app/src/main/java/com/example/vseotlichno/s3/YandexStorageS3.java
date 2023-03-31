package com.example.vseotlichno.s3;

import android.net.Uri;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.vseotlichno.api.ApiClientVseotlichno;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.Executors;

public class YandexStorageS3 {
    private static final String ACCESS_KEY = "you_s3_access_key";
    private static final String SECRET_KEY = "you_s3_secret_key";

    private static final String BUCKET_NAME = "vseotlichno";
    private static final String STORAGE_ENDPOINT  = "storage.yandexcloud.net";
    private static YandexStorageS3 mInstance;
    AmazonS3 s3;
    public static YandexStorageS3 getInstance() {
        if (mInstance == null) {
            mInstance = new YandexStorageS3();
        }
        return mInstance;
    }

    private YandexStorageS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        s3 = new AmazonS3Client(credentials);
        s3.setEndpoint(STORAGE_ENDPOINT);
    }

    public AmazonS3 getClient() {
        return s3;
    }

    public void loadFileTask(String key, InputStream inputStream) {
        Executors.newSingleThreadExecutor().execute(() -> {
            s3.putObject(BUCKET_NAME, key, inputStream, new ObjectMetadata());
        });
    }

    public String getUriFromKey(String key) {
        return String.format("https://%s/%s/%s", STORAGE_ENDPOINT, BUCKET_NAME, key);
    }
}
