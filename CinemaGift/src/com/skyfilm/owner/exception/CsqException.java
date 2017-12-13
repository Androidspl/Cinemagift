package com.skyfilm.owner.exception;

import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.R;
import com.skyfilm.owner.utils.PropertiesUtils;
import com.skyfilm.owner.utils.StringUtil;

public class CsqException extends Exception {

	private static final long serialVersionUID = -5897262547267384828L;
	public static final int RET_NO_EXCEPTION = 0;
	public static final int RET_WITH_EXCEPTION = 1;
	private int code = Integer.MIN_VALUE;
	public static final int BAD_NET_WORK = -1;
	public static final int STATUS_LINE_NOT_200 = -2;
	public static final int ERROR_DATA_FORMAT = -3;
	public static final int ERROR_USER_NOT_LOGIN = 113;
	private String msg = null;
	public CsqException(int code) {
		super();
		this.code = code;
	}
    public CsqException(String message) {
		super(message);
		this.msg = message;
	}
    
    public CsqException(){
    	super();
    }
    public CsqException(int code,String message) {
    	super();
    	msg = getCsqMessage(code);
    	if(StringUtil.isNull(msg)){
    		msg = message;
    	}else{
    		setCode(code);
    	}
	}
    public CsqException(Exception e){
    	super(e);
    	this.msg = e.getMessage();
    }
    
    @Override
    public String getMessage() {
    	String message = null;
    	if(code != Integer.MIN_VALUE){
    		message = getCsqMessage(code);
    	}
    	if(StringUtil.isNull(message) && !StringUtil.isNull(msg)){
    		message = msg;
    		code = Integer.MIN_VALUE;
    	}
    	return StringUtil.isNull(message)?MainApplication.getInstance().getResources().getString(R.string.default_error_msg):message;
    }
    
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public static String getCsqMessage(int code){
		String message = null;
		try {
			message = PropertiesUtils.getValue(MainApplication.getInstance(),"exception",code+"");
		} catch (Exception e) {
		}
		return message;
	}
}
