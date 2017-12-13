package com.skyfilm.owner.pay;

import java.io.IOException;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.activity.PayStyleActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {
	
	private Context context;

	public PaymentTask (Context context) {
		this.context = context;
	}

	@Override
    protected void onPreExecute() {
        //按键点击之后的禁用，防止重复点击
        //wechatButton.setOnClickListener(null);
        //alipayButton.setOnClickListener(null);
    }

    @Override
    protected String doInBackground(PaymentRequest... pr) {

        PaymentRequest paymentRequest = pr[0];
        String data = null;
        String json = new Gson().toJson(paymentRequest);
        try {
            //向Your Ping++ Server SDK请求数据
            data = postJson(Const.PINGPP_URL, json);
            Log.d("Tag", "doInBackground"+data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获得服务端的charge，调用ping++ sdk。
     */
    @Override
    protected void onPostExecute(String data) {
    	if(null == data){
    		showMsg("请求出错", "请检查URL", "URL无法获取charge");
    		return;
    	}
    	Log.d("charge", data);
        Pingpp.createPayment((Activity) context, data);
    }
    
    private static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
    
    public void showMsg(String title, String msg1, String msg2) {
    	String str = title;
    	if (null !=msg1 && msg1.length() != 0) {
    		str += "\n" + msg1;
    	}
    	if (null !=msg2 && msg2.length() != 0) {
    		str += "\n" + msg2;
    	}
    	AlertDialog.Builder builder = new Builder(context);
    	builder.setMessage(str);
    	builder.setTitle("提示");
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }

}
