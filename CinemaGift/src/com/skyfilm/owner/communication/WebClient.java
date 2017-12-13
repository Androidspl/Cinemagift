package com.skyfilm.owner.communication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.BitmapUtil;
import com.skyfilm.owner.utils.DataFolder;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.MD5;
import com.skyfilm.owner.utils.SharedPrefUtil;
import com.skyfilm.owner.utils.StringUtil;
import com.skyfilm.owner.utils.TelePhoneInfoUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class WebClient {

	private static DefaultHttpClient httpClient;
	private static final String TAG = "WebClient";
	private static final int RE_TRY_COUNT = 5;
	private int count = 0;
	private CookieStore myCookieStore;

	private static class WebCilentHolder{
		private static final WebClient INSTANCE = new WebClient();
	}

	private WebClient() {
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);
		HttpConnectionParams.setSoTimeout(params, 30 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpClientParams.setRedirecting(params, false);
		HttpClientParams.setCookiePolicy(params, CookiePolicy.BROWSER_COMPATIBILITY);

		HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				params, schemeRegistry);

		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 3) {
					return false;
				}
				if (exception instanceof org.apache.http.NoHttpResponseException) {
					L.i(TAG, "retry times:" + executionCount);
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					return false;
				}
				HttpRequest request = (HttpRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					return true;
				}
				return false;
			}
		};
		httpClient = new DefaultHttpClient(manager, params);
		myCookieStore=MainApplication.getInstance().getCookieStore();
		httpClient.setHttpRequestRetryHandler(retryHandler);
	}

	public static WebClient getInstance(){
		return WebCilentHolder.INSTANCE;
	}


	private void useCookie(){
		httpClient.setCookieStore(myCookieStore);
	}

	private void saveCookie() {
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();  
		for (Cookie cookie:cookies){  
			myCookieStore.addCookie(cookie);  
		}
	}

	public List<Cookie> getCookie(){
		return myCookieStore.getCookies(); 
	}

	public String doJsonPost(String url,JSONObject data) throws CsqException{
		//		StringEntity entity = null;
		//		try {
		//			entity = new StringEntity(data.toString(),HTTP.UTF_8);
		//			entity.setContentEncoding(HTTP.UTF_8);
		//			entity.setContentType("application/json");
		//		} catch (UnsupportedEncodingException e) {
		//			e.printStackTrace();
		//		}
		//		
		//		String strResp = "";
		//		HttpPost httpPost = new HttpPost(url);
		//		try {
		//			httpPost.setEntity(entity);
		//			addHeader2Request(httpPost, "longguang", "1234", entity.getContentLength());
		//			HttpResponse resp = new DefaultHttpClient().execute(httpPost);
		//			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
		//				strResp = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
		//			} else {
		//				throw (new CsqException(CsqException.STATUS_LINE_NOT_200));
		//			}
		//		} catch (Exception e) {
		//		} finally {
		//			count = 0;
		//			httpPost.abort();
		//		}
		//		return strResp;
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "get_request_info"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("method", "post");
			obj.put("request_url", url);
			url = Const.EXEC_URL;
			obj.put("format", "json");
			obj.put("request_params", data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return doPost(url,pairs);
	}


	public String doPost(String url,List<NameValuePair> pairs)throws CsqException{

		return doPost(url, pairs, null);
	}
	public String doPost(String url)throws CsqException{

		return doPost(url, null, null);
	}


	public String doGet(String url) throws CsqException{
		if(StringUtil.isNull(TelePhoneInfoUtil.getNetwork(MainApplication.getInstance()))){
			throw new CsqException(CsqException.BAD_NET_WORK);
		}
		if(!url.startsWith(Const.EXEC_URL)){//对非本服务期请求做转发
			ArrayList<NameValuePair> pairs = new ArrayList<>();
			pairs.add(new BasicNameValuePair("A", "get_request_info"));
			JSONObject obj = new JSONObject();
			try {
				obj.put("method", "get");
				obj.put("request_url", url);
				url = Const.EXEC_URL;
				obj.put("format", "json");
				//				obj.put("request_params", data);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pairs.add(new BasicNameValuePair("P", obj.toString()));
			return doPost(url, pairs);
		}
		count++;
		String strResp = "";
		HttpGet get = new HttpGet(url);
		L.v(TAG, "HTTP Post: " + url);
		long currentTimeMillis = System.currentTimeMillis();
		try {
			useCookie();
			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
				saveCookie(); 
				if(Const.DEBUG){
					SharedPrefUtil.Log("responce:"+strResp);
				}else{
					L.w("responce:"+strResp);
				}
			} else {
				SharedPrefUtil.Log("responce:"+resp.getStatusLine().getStatusCode()+"reason:"+resp.getStatusLine().getReasonPhrase());
				throw (new CsqException(CsqException.STATUS_LINE_NOT_200));
			}
		} catch (Exception e) {
			if(e instanceof CsqException){
				throw (CsqException)e;
			}
			if (e.getMessage() != null && e.getMessage().contains("EPIPE")) {
				count++;
				if (count > RE_TRY_COUNT) {
					throw (new CsqException(CsqException.BAD_NET_WORK));
				}
				return doGet(url);
			} else {
				if(e instanceof IOException){
					throw new CsqException(CsqException.BAD_NET_WORK);
				}
				throw new CsqException(e);
			}
		} finally {
			count = 0;
			get.abort();
		}
		L.i(TAG, "time:" + (System.currentTimeMillis() - currentTimeMillis));
		L.i(TAG, "response:" + strResp);
		return strResp;
	}

	public String doPost(String url,List<NameValuePair> pairs,List<String>files) throws CsqException{
//		String name = "";
//		if(files != null && files.size()>1){
//			name = "File[]";
//		}else{
//			name = "File";
//		}
		if(StringUtil.isNull(TelePhoneInfoUtil.getNetwork(MainApplication.getInstance()))){
			throw new CsqException(CsqException.BAD_NET_WORK);
		}
		if(!url.startsWith(Const.EXEC_URL)){
			List<NameValuePair> convertPairs = new ArrayList<NameValuePair>();
			convertPairs.add(new BasicNameValuePair("A", "get_request_info"));
			JSONObject obj = new JSONObject();

			try {
				obj.put("method", "post");
				obj.put("request_url", url);
				url = Const.EXEC_URL;
				obj.put("format", "key_value");
				HashMap<String,String> map = converNVPList2Map(pairs);
				if(map != null && !map.isEmpty()){
					obj.put("request_params", new JSONObject(map));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			convertPairs.add(new BasicNameValuePair("P", obj.toString()));
			pairs = convertPairs;
		}

		List<File> fileList = null;
		count++;
		StringBody strentity;
		try {
			pairs.add(new BasicNameValuePair("platform", "android"));
			pairs.add(new BasicNameValuePair("App_id", AppUtils.getAppId()));
		} catch (Exception e2) {
			L.printStackTrace(e2);
		}
		HttpEntity entity = null;
		if(files!=null&&files.size()>0){

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);// 设置浏览器兼容模式

			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
					HTTP.UTF_8);
			for(NameValuePair pair:pairs){
				strentity = new StringBody(pair.getValue(), contentType);
				builder.addPart(pair.getName(),strentity );
			}
			File file;
			if (files != null) {
				fileList = new ArrayList<File>();
				for (String filename : files) {
					file = scalePic(filename);
					if(file != null){
						try {
							fileList.add(file);
							builder.addPart("File[]", new FileBody(file));
						} catch (Exception e) {
							SharedPrefUtil.Log("doPost file err:"+e.getMessage());
						}
					}
				}
			}
			entity = builder.build();
		}else{
			try {
				entity = new UrlEncodedFormEntity(pairs,HTTP.UTF_8);
			} catch (Exception ue) {
				SharedPrefUtil.Log("doPost err1:"+ue.getMessage());
				L.printStackTrace(ue);
			}
		}

		String strResp = "";
		HttpPost httpPost = new HttpPost(url);
		L.v(TAG, "HTTP Post: " + url);
		if(pairs!=null){
			L.v(TAG, "HTTP Post: " + Arrays.toString(pairs.toArray()));
			if(Const.DEBUG){
				SharedPrefUtil.Log("HTTP Post:"+url+"\r\n"+Arrays.toString(pairs.toArray()));
			}else{
				L.w("HTTP Post:"+url+"\r\n"+Arrays.toString(pairs.toArray()));
			}
		}
		long currentTimeMillis = System.currentTimeMillis();
		try {
			if(entity != null)
				httpPost.setEntity(entity);
			useCookie();
			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
				saveCookie(); 
				if(Const.DEBUG){
					SharedPrefUtil.Log("responce:"+strResp);
				}else{
					L.w("responce:"+strResp);
				}
				if(fileList!=null && !fileList.isEmpty())
					for(int i = 0 ;i<fileList.size();i++){
						try {
							fileList.get(i).delete();
						} catch (Exception e) {
						}
					}
			} else {
				if(Const.DEBUG)
					System.out.println(EntityUtils.toString(resp.getEntity()));
				SharedPrefUtil.Log("responce:"+resp.getStatusLine().getStatusCode()+"reason:"+resp.getStatusLine().getReasonPhrase());
				throw (new CsqException(CsqException.STATUS_LINE_NOT_200));
			}
		} catch (Exception e) {
			if(e instanceof CsqException){
				throw (CsqException)e;
			}
			if (e.getMessage() != null && e.getMessage().contains("EPIPE")) {
				count++;
				if (count > RE_TRY_COUNT) {
					throw (new CsqException(CsqException.BAD_NET_WORK));
				}
				return doPost(url, pairs, files);
			} else {
				if(e instanceof IOException){
					throw new CsqException(CsqException.BAD_NET_WORK);
				}
				throw new CsqException(e);
			}
		} finally {
			count = 0;
			httpPost.abort();
		}
		L.i(TAG, "time:" + (System.currentTimeMillis() - currentTimeMillis));
		L.i(TAG, "response:" + strResp);
		return strResp;
	}
	private HashMap<String, String> converNVPList2Map(List<NameValuePair> pairs) {
		if(pairs == null || pairs.isEmpty()){
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for(NameValuePair pair:pairs){
			map.put(pair.getName(), pair.getValue());
		}
		return map;
	}

	public String doGet(String url,List<NameValuePair> pairs) throws CsqException{
		if(StringUtil.isNull(TelePhoneInfoUtil.getNetwork(MainApplication.getInstance()))){
			throw new CsqException(CsqException.BAD_NET_WORK);
		}
		StringBuilder builder = new StringBuilder(url);
		boolean isFirst= true;
		for(NameValuePair pair:pairs){
			if(isFirst){
				isFirst = false;
				if(!url.contains("?")){
					builder.append("?");
				}else{
					builder.append("&");
				}
			}else{
				builder.append("&");
			}
			builder.append(pair.getName()).append("=").append(pair.getValue());
		}

		return doGet(builder.toString());
	}


	@SuppressWarnings("unused")
	public String doPost(String url,List<NameValuePair> pairs,Map<String, String> files,boolean isNeedScale) throws CsqException{
		if(StringUtil.isNull(TelePhoneInfoUtil.getNetwork(MainApplication.getInstance()))){
			throw new CsqException(CsqException.BAD_NET_WORK);
		}
		if(!url.startsWith(Const.EXEC_URL)){
			List<NameValuePair> convertPairs = new ArrayList<NameValuePair>();
			convertPairs.add(new BasicNameValuePair("A", "get_request_info"));
			JSONObject obj = new JSONObject();

			try {
				obj.put("method", "post");
				obj.put("request_url", url);
				url = Const.EXEC_URL;
				obj.put("format", "key_value");
				HashMap<String,String> map = converNVPList2Map(pairs);
				if(map != null && !map.isEmpty()){
					obj.put("request_params", new JSONObject(map));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			convertPairs.add(new BasicNameValuePair("P", obj.toString()));
			pairs = convertPairs;
		}

		List<File> fileList = null;
		count++;
		StringBody strentity;
		try {
			pairs.add(new BasicNameValuePair("platform", "android"));
		} catch (Exception e2) {
			L.printStackTrace(e2);
		}
		HttpEntity entity = null;
		if(files!=null&&files.size()>0){

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);// 设置浏览器兼容模式

			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
					HTTP.UTF_8);
			for(NameValuePair pair:pairs){
				strentity = new StringBody(pair.getValue(), contentType);
				builder.addPart(pair.getName(),strentity );
			}
			File file;
			if (files != null) {
				fileList = new ArrayList<File>();
				Set<String> keys = files.keySet();
				for (String key : keys) {
					if(isNeedScale)
						file = scalePic(files.get(key));
					else
						file = new File(files.get(key));
					if(file != null){
						try {
							fileList.add(file);
							if(key.startsWith("item")){
								builder.addPart("items[]", new FileBody(file));
							}else if(key.startsWith("certificate")){
								builder.addPart("certificate[]", new FileBody(file));
							}else{
								builder.addPart(key, new FileBody(file));
							}
						} catch (Exception e) {
							SharedPrefUtil.Log("doPost file err:"+e.getMessage());
						}
					}
				}
			}
			entity = builder.build();
		}else{
			try {
				entity = new UrlEncodedFormEntity(pairs,HTTP.UTF_8);
			} catch (UnsupportedEncodingException ue) {
				SharedPrefUtil.Log("doPost err1:"+ue.getMessage());
				L.printStackTrace(ue);
			}
		}

		String strResp = "";
		HttpPost httpPost = new HttpPost(url);
		long currentTimeMillis = System.currentTimeMillis();
		if(Const.DEBUG){
			L.v(TAG, "HTTP Post: " + url);
			L.v(TAG, "HTTP Post: " + Arrays.toString(pairs.toArray()));
			SharedPrefUtil.Log("HTTP Post:"+url+"\r\n"+Arrays.toString(pairs.toArray()));
		}
		try {
			httpPost.setEntity(entity);
			useCookie();
			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection
					.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
				saveCookie(); 
				if(!Const.DEBUG && strResp.contains("error")){
					L.v(TAG, "HTTP Post: " + url);
					L.v(TAG, "HTTP Post: " + Arrays.toString(pairs.toArray()));
					SharedPrefUtil.Log("HTTP Post:"+url+"\r\n"+Arrays.toString(pairs.toArray()));
					SharedPrefUtil.Log("responce:"+strResp);
				}else if(Const.DEBUG){
					SharedPrefUtil.Log("responce:"+strResp);
				}
				if(isNeedScale && fileList!=null && !fileList.isEmpty())
					for(int i = 0 ;i<fileList.size();i++){
						try {
							fileList.get(i).delete();
						} catch (Exception e) {
						}
					}
			} else {
				if(!Const.DEBUG){
					L.v(TAG, "HTTP Post: " + url);
					L.v(TAG, "HTTP Post: " + Arrays.toString(pairs.toArray()));
					SharedPrefUtil.Log("HTTP Post:"+url+"\r\n"+Arrays.toString(pairs.toArray()));
				}
				L.v(TAG, resp.getStatusLine().getReasonPhrase());
				L.v(TAG, resp.getStatusLine().getStatusCode()+"");
				SharedPrefUtil.Log("responce:"+resp.getStatusLine().getStatusCode());
				throw (new CsqException(CsqException.STATUS_LINE_NOT_200));
			}
		} catch (Exception e) {
			if(e instanceof CsqException){
				throw (CsqException)e;
			}
			if (e.getMessage() != null && e.getMessage().contains("EPIPE")) {
				count++;
				if (count > RE_TRY_COUNT) {
					throw (new CsqException(CsqException.BAD_NET_WORK));
				}
				return doPost(url, pairs, files,isNeedScale);
			} else {
				if(e instanceof IOException){
					throw new CsqException(CsqException.BAD_NET_WORK);
				}
				throw new CsqException(e);
			}
		} finally {
			count = 0;
			httpPost.abort();
		}
		L.i(TAG, "time:" + (System.currentTimeMillis() - currentTimeMillis));
		L.i(TAG, "response:" + strResp);
		return strResp;
	}



	private File scalePic(String path){
		if(StringUtil.isNull(path))return null;
		if(path.endsWith("mp3")){
			return new File(path);
		}
		File scaleFile = new File(DataFolder.getAppDataRoot()+System.currentTimeMillis()+".jpg");
		Bitmap bitmap = null;
		try {
			bitmap = BitmapUtil.comPressBitmapFromFile(new File(path), 480, 800, 100);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, new FileOutputStream(scaleFile));
		} catch (Exception e) {
			try {
				if(scaleFile != null && scaleFile.exists()) scaleFile.delete();
			} catch (Exception e1) {
			}
			return new File(path);
		}catch (OutOfMemoryError e) {
			try {
				if(scaleFile != null && scaleFile.exists()) scaleFile.delete();
			} catch (Exception e1) {
			}
			return new File(path);
		}finally{
			if(bitmap != null && !bitmap.isRecycled()){
				bitmap.recycle();
				System.gc();
			}
		}
		return scaleFile;
	}

	private HttpUriRequest addHeader2Request (HttpUriRequest request,String userName,String pwd,long length){
		long timestap = System.currentTimeMillis()/1000;
		StringBuilder builder = new StringBuilder();
		builder.append(request.getMethod())
		.append(request.getURI())
		.append(timestap)
		.append(length)
		.append(MD5.getMD5(pwd));

		String sign = MD5.getMD5(builder.toString());

		request.addHeader("date", String.valueOf(timestap));
		request.addHeader("sign",userName+":"+sign);

		return request;

	}

}