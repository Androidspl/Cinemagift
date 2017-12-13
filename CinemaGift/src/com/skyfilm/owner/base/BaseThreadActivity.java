package com.skyfilm.owner.base;


import com.skyfilm.owner.R;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqThreadFactory;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.widget.MyProgress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class BaseThreadActivity extends BaseActivity {

	public static final int OPERATION = -1; 
	public static final int GPS_COOR = 0;
	public static final int GCJ02_COOR = 1;

	protected abstract Object doInBackground(int operate, Object... objs) throws CsqException;
	protected abstract boolean handleResult(boolean result, int operate, Object obj);

	protected class CsqRunnable implements Runnable {	
		private int operate;
		private Object[] objs;

		public CsqRunnable(){}

		public CsqRunnable(int operate){
			this.operate = operate;
		}

		public CsqRunnable(int operate, Object... objs){
			this.operate = operate;
			this.objs = objs;
		}
		@Override
		public void run() {					
			Message msg = Message.obtain();
			try{
				msg.what = operate;
				msg.obj = doInBackground(operate, objs);
				msg.arg1 = CsqException.RET_NO_EXCEPTION;
			} catch (Exception e) {
				msg.obj = e.getMessage();
				if (e instanceof CsqException) {
					msg.arg2 = ((CsqException) e).getCode();
				}else{
					msg.arg2 = Integer.MIN_VALUE;
				}
				msg.arg1 = CsqException.RET_WITH_EXCEPTION;
				L.printStackTrace(e);
			}	
			OperationHandler.sendMessage(msg);			
		}
		public void start(){
			CsqThreadFactory.getExecutorService().execute(this);
		}
	}	

	protected final Handler OperationHandler = new Handler(){  
		public void handleMessage(Message msg) { 
			if(!isFinishing()){
				boolean dealed = handleResult(msg.arg1 == CsqException.RET_NO_EXCEPTION, msg.what, msg.obj);
				MyProgress.dismiss();
				if(!dealed && msg.arg1 != CsqException.RET_NO_EXCEPTION){	
					if(msg.arg2 == Integer.MIN_VALUE ){
						if(msg.obj != null && msg.obj instanceof String){
							CsqToast.show((String)msg.obj, BaseThreadActivity.this);
						}else{
							CsqToast.show(getResources().getString(R.string.default_error_msg), BaseThreadActivity.this);
						}
					}else{
						CsqToast.show(CsqException.getCsqMessage(msg.arg2)+"", BaseThreadActivity.this);
					}
				}

			}	
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		if(OperationHandler != null){
			OperationHandler.removeCallbacksAndMessages(null);
		}
	}
}
