package com.skyfilm.owner.communication;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.BindUser;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.user.UserCom;
import com.skyfilm.owner.utils.StringUtil;
import com.umeng.message.proguard.T;
import com.umeng.socialize.utils.Log;

public class UserComImpl extends AbstractCom implements UserCom, String2Object<User> {
	private Gson dateGson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new DateSerializer())
			.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).create();

	@Override
	public User login(String Mobile, String pwd) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();

		JSONObject obj = new JSONObject();
		try {
			obj.put("Mobile", Mobile);
			obj.put("Pwd", pwd);
		} catch (JSONException e) {
		}
		pairs.add(new BasicNameValuePair("A", "User_Mobile_Login"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return string2Object(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)));
	}
	
	@Override
	public User bindlogin(String Open_id, String Open_type) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();

		JSONObject obj = new JSONObject();
		try {
			obj.put("Open_id", Open_id);
			obj.put("Open_type", Open_type);
		} catch (JSONException e) {
		}
		pairs.add(new BasicNameValuePair("A", "User_Bind_Login"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String data = webClient.doPost(Const.EXEC_URL, pairs);
		String bind_type = null;
		String user_type = null;
		try {
			JSONObject jsonObject = new JSONObject(data);
			JSONObject object = jsonObject.getJSONObject("Data");
			bind_type = object.getString("type");
			user_type = object.getString("user_type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		User user = string2Object(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)));
		user.setBind_type(bind_type);
		user.setType(user_type);
		return user;
	}

	@Override
	public boolean logout(String userId) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("userId", userId));
		GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs));
		return true;
	}

	@Override
	public User valideUser(String Mobile, String Pwd, String Sure_pwd, String Valid_code, String Open_id,
			String Open_type, String Expires_in, String Access_token, String Refresh_token, String Open_pic, String User_name)
					throws CsqException {

		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try {
			obj.put("Mobile", Mobile);
			obj.put("Pwd", Pwd);
			obj.put("Sure_pwd", Sure_pwd);
			obj.put("Valid_code", Valid_code);
			obj.put("Open_id", Open_id);
			obj.put("Open_type", Open_type);
			obj.put("Expires_in", Expires_in);
			obj.put("Access_token", Access_token);
			obj.put("Refresh_token", Refresh_token);
			obj.put("Open_pic", Open_pic);
			obj.put("User_name", User_name);
		} catch (JSONException e) {
		}
		pairs.add(new BasicNameValuePair("A", "User_Mobile_Register"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		Log.d("Tag",Open_type+"三方登陆授权成功："+GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)));
		return string2Object(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)));
	}

	@Override
	public User getUserById(String userId, String rKey, String sKey) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();

		JSONObject obj = new JSONObject();
		try {
			obj.put("user_id", userId);
			obj.put("rand_str", rKey);
			obj.put("code", sKey);
		} catch (JSONException e) {
		}
		pairs.add(new BasicNameValuePair("A", "get_user_info"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));

		return string2Object(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)));
	}

	@Override
	public String getValidCode(String Mobile) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "User_Mobile_Code"));
		JSONObject object = new JSONObject();
		try {
			object.put("Mobile", Mobile);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pairs.add(new BasicNameValuePair("P", object.toString()));
		String data = GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs));
		return data;
	}

	@Override
	public boolean bindUser(String bid, String cid, String userId) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("bid", bid));
		pairs.add(new BasicNameValuePair("cid", cid));
		pairs.add(new BasicNameValuePair("userId", userId));
		GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs));
		return true;
	}

	@Override
	public boolean modifyUserPwd(String code, String newPwd, String userId) throws CsqException {

		JSONObject obj = new JSONObject();
		try {
			obj.put("auth_code", code);
			obj.put("pwd", newPwd);
			obj.put("mobile", userId);
		} catch (JSONException e) {
		}
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "user_change_password"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs));
		return true;
	}

	// TODO:未做处理
	@Override
	public boolean modifyUserInfo(User u) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		return false;
	}

	@Override
	public boolean setAdvice(String phone, String content, String community_id, String name, List<String> files)
			throws CsqException {
		JSONObject obj = new JSONObject();
		try {
			obj.put("phone", phone);
			obj.put("content", content);
			obj.put("community_id", community_id);
		} catch (JSONException e) {
		}
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "set_advice"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs, files));
		return true;
	}

	@Override
	public User editUserInfo(HashMap<String, String> changes) throws CsqException {

		/*
		 * JSONObject obj = new JSONObject(); try { obj.put("nick_name",
		 * nick_name); obj.put("name", name); obj.put("gender", gender);
		 * obj.put("birth_date", birth_date); obj.put("default_address",
		 * default_address); obj.put("id_number", id_number);
		 * 
		 * } catch (JSONException e) { }
		 * 
		 * HashMap<String, String> map = new HashMap<String, String>();
		 * 
		 * if(!StringUtil.isNull(head_pic)){ map.put("head_pic",head_pic ); }
		 */

		ArrayList<NameValuePair> pairs = new ArrayList<>();
		ArrayList<String> paths = null;
		if (changes.containsKey("head_pic")) {
			paths = new ArrayList<>();
			paths.add(changes.get("head_pic"));
			changes.remove("head_pic");
		}
		pairs.add(new BasicNameValuePair("A", "edit_user_info"));
		pairs.add(new BasicNameValuePair("P", new JSONObject(changes).toString()));

		return string2Object(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs, paths)));
	}

	@Override
	public User string2Object(String data) throws CsqException {
		if (StringUtil.isNull(data))
			return null;
		// return dateGson.fromJson(data, User.class);
		return gson.fromJson(data, User.class);
	}

	public class DateDeserializer implements JsonDeserializer<java.util.Date> {

		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			try {
				String time = json.getAsJsonPrimitive().getAsString();
				if (StringUtil.isNull(time))
					return null;
				return formator.parse(time);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public class DateSerializer implements JsonSerializer<Date> {
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(formator.format(src));
		}
	}

	@Override
	public void setDeviceToken(String userId, String token) throws CsqException {
		JSONObject obj = new JSONObject();
		try {
			obj.put("device_token", token);
			obj.put("plat_form", "android");
		} catch (JSONException e) {
		}
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "set_device"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs));
	}

	@Override
	public boolean isValidCode(String Mobile, String code) throws CsqException {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mobile", Mobile);
			obj.put("auth_code", code);
		} catch (JSONException e) {
		}
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "is_auth_code"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs));
		return true;
	}

	@Override
	public User resetUserPwd(String Mobile, String Pwd, String Sure_pwd, String Valid_code) throws CsqException {
		JSONObject obj = new JSONObject();
		try {
			obj.put("Mobile", Mobile);
			obj.put("Pwd", Pwd);
			obj.put("Sure_pwd", Sure_pwd);
			obj.put("Valid_code", Valid_code);
		} catch (JSONException e) {
		}
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("A", "User_Reset_Pwd"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return string2Object(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)));
	}

}
