package com.greenland.btsdk.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
	public final static int CONNECT_TIMEOUT = 60;
	public final static int READ_TIMEOUT = 100;
	public final static int WRITE_TIMEOUT = 60;
	public static final OkHttpClient client = new OkHttpClient.Builder()
	        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//���ö�ȡ��ʱʱ��
	        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//����д�ĳ�ʱʱ��
	        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//�������ӳ�ʱʱ��
	        .build();
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	public static String doPostJson(String url, String json) throws IOException {
	    RequestBody body = RequestBody.create(JSON, json);
	    Request request = new Request.Builder()
	            .url(url)
	            .post(body)
	            .build();
	    Response response = client.newCall(request).execute();
	    if (response.isSuccessful()) {
	        return response.body().string();
	    } else {
	        throw new IOException("Unexpected code " + response);
	    }
	}
	
    public static String doGet(String url) throws IOException {
	    Request request = new Request.Builder().url(url).get().build();
	    Response response = client.newCall(request).execute();
	    if (response.isSuccessful()) {
	        return response.body().string();
	    } else {
	        throw new IOException("Unexpected code " + response);
	    }
    }
}
