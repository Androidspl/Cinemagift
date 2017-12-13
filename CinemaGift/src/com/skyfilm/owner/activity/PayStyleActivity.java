package com.skyfilm.owner.activity;

import java.math.BigDecimal;

import com.alipay.sdk.app.PayTask;
import com.pingplusplus.android.Pingpp;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.biz.StoreBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.pay.PayResult;
import com.skyfilm.owner.pay.PaymentRequest;
import com.skyfilm.owner.pay.PaymentTask;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PayStyleActivity extends BaseThreadActivity implements OnClickListener {

	private TextView confirm_pay,amount;
	private String user_id = "user_id";
	private CheckBox cb_wechent, cb_zhifubao;
	private IWXAPI msgApi;
	private String Address;
	private String Order_id;
	private StoreBiz storeBiz;
	// 支付宝支付
	/** 获取支付的标识 */
	private static final int GENERATE_TAG = 0x0201;
	/** 生成账单 */
	private static final int GENERATE_BILL = 0x0202;
	/** 支付账单 */
	private static final int PAY_BILL = 0x0203;
	/** 返回服务器验证账单 */
	private static final int CONFIRM_BILL = 0x0204;
	// 微信支付
	/** 获取支付的标识 */
	private static final int GENERATE_TAG_WECHEAT = 0x0205;
	/** 生成账单 */
	private static final int GENERATE_BILL_WECHEAT = 0x0206;
	/** 支付账单 */
	private static final int PAY_BILL_WECHEAT = 0x0207;
	/** 返回服务器验证账单 */
	private static final int CONFIRM_BILL_WECHEAT = 0x0208;
	/** 订单地址绑定 */
	private static final int ORDER_ADDRES_BIND = 0x0209;
	public static int temp = -1;
	/**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_style);
		initView();
		initData();
	}

	private void initView() {
		storeBiz = (StoreBiz) CsqManger.getInstance().get(Type.STOREBIZ);
		Intent intent = getIntent();
		Address = intent.getStringExtra("Address");
		Order_id = intent.getStringExtra("Order_id");
		new CsqRunnable(ORDER_ADDRES_BIND).start();
		confirm_pay = (TextView) findViewById(R.id.confirm_pay);
		cb_wechent = (CheckBox) findViewById(R.id.cb_wechent);
		cb_zhifubao = (CheckBox) findViewById(R.id.cb_zhifubao);
		amount = (TextView) findViewById(R.id.amount);
		confirm_pay.setOnClickListener(this);
		// 做个标记
		// cb_wechent.setId(groupPosition);
		// checkbox监听
		cb_wechent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 这段代码来实现单选功能
					if (temp != -1) {
						// CheckBox tempButton = (CheckBox)
						// PayStyleActivity.this.findViewById(temp);
						if (cb_zhifubao != null) {
							cb_zhifubao.setChecked(false);
						}
					}
					// 得到当前的position
					temp = buttonView.getId();
				} else {
					temp = -1;
				}

			}
		});
		cb_zhifubao.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 这段代码来实现单选功能
					if (temp != -1) {
						// CheckBox tempButton = (CheckBox)
						// PayStyleActivity.this.findViewById(temp);
						if (cb_wechent != null) {
							cb_wechent.setChecked(false);
						}
					}
					// 得到当前的position
					temp = buttonView.getId();
				} else {
					temp = -1;
				}

			}
		});
	}

	private void initData() {
		// 商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID，代码如下：
		msgApi = WXAPIFactory.createWXAPI(getApplicationContext(), null);
		// 将该app注册到微信
		msgApi.registerApp("wxd930ea5d5a258f4f");
		new CsqRunnable(GENERATE_TAG, "user_id").start();// 房子id,当前房产在data中的位置
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("支付");
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		switch (operate) {
		case GENERATE_TAG:
			user_id = objs[0].toString();// user_id,用户id
			// return pc.getPropertyFeeBill(objs[0].toString(),
			// data.get(place).getPksmallarea());
			return user_id;

		case GENERATE_BILL:
			if (user_id != null) {
				return user_id;
			} else {
				CsqToast.show("账单生成失败", getApplicationContext());
			}
			break;

		case PAY_BILL:
			PayTask alipay = new PayTask(PayStyleActivity.this);
			// String result = alipay.pay((String) objs[0], true);
			String result = alipay.pay("Sign", true);
			// PayResult payResult = new PayResult(result);
			PayResult payResult = new PayResult(result);
			// payResult.setNo((String) objs[1]);
			payResult.setNo("Out_trade_no");
			return payResult;

		case CONFIRM_BILL:
			// confirtPayCom.confirtPay(objs.toString());
			break;

		case PAY_BILL_WECHEAT:
			// IWXAPI api;
			PayReq request = new PayReq();
			request.appId = "wxd930ea5d5a258f4f";
			request.partnerId = "1900000109";
			request.prepayId = "1101000000140415649af9fc314aa427";
			request.packageValue = "Sign=WXPay";
			request.nonceStr = "1101000000140429eb40476f8896f4c9";
			request.timeStamp = "1398746574";
			request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
			msgApi.sendReq(request);
			return msgApi.sendReq(request);
			
		case ORDER_ADDRES_BIND:
			return storeBiz.bindOrderAddress(Address, Order_id);
			
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		switch (operate) {
		case GENERATE_TAG:

			break;
		case GENERATE_BILL:
			if (result && obj != null) {
				if (GENERATE_BILL == 0x0202) {
					user_id = (String) obj;
					if (user_id != null)
						new CsqRunnable(PAY_BILL, "Sign", "Out_trade").start();// 拿到sign，out_trade_no去发送支付请求
				}
			}
			break;

		case PAY_BILL:
			if (result && obj != null) {
				PayResult payResult = (PayResult) obj;
				String resultStatus = payResult.getResultStatus();
				if (TextUtils.equals(resultStatus, "9000")) {
					CsqToast.show("支付成功", getApplicationContext());
					new CsqRunnable(4, payResult.getNo());// 服务器验证
				} else {
					if (TextUtils.equals(resultStatus, "8000")) {
						CsqToast.show("支付结果确认中", getApplicationContext());
					} else {
						CsqToast.show("支付失败", getApplicationContext());
					}
				}
			}
			break;
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.confirm_pay:
			String amountText = amount.getText().toString();
	        if (amountText.equals("")) return;
	        int amount = Integer.valueOf(new BigDecimal(amountText).toString());
			if (cb_zhifubao.isChecked()) { 
				// 支付：1：线程2获取支付所需的（sign，out_trade_no）2：线程3调用支付宝完成支付3:服务器验证
				//new CsqRunnable(GENERATE_BILL).start();
				// 支付宝支付按键的点击响应处理
				new PaymentTask(PayStyleActivity.this).execute(new PaymentRequest(CHANNEL_ALIPAY, amount));
			} else if (cb_wechent.isChecked()) { 
				//new CsqRunnable(PAY_BILL_WECHEAT).start();
				// 微信支付按键的点击响应处理
				new PaymentTask(PayStyleActivity.this).execute(new PaymentRequest(CHANNEL_WECHAT, amount));
			}else {
				CsqToast.show("请选择支付方式", getApplicationContext());
			}
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
	 * 最终支付成功根据异步通知为准
	 */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }
    
    public void showMsg(String title, String msg1, String msg2) {
    	String str = title;
    	if (null !=msg1 && msg1.length() != 0) {
    		str += "\n" + msg1;
    	}
    	if (null !=msg2 && msg2.length() != 0) {
    		str += "\n" + msg2;
    	}
    	AlertDialog.Builder builder = new Builder(PayStyleActivity.this);
    	builder.setMessage(str);
    	builder.setTitle("提示");
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }
    
}
