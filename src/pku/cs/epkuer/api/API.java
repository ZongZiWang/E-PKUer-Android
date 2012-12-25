package pku.cs.epkuer.api;

import pku.cs.epkuer.util.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class API{

	/**
	 * 登陆
	 * @param uname	用户名
	 * @param psw	密码
	 * @return	成功为true，失败为false
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean login(String uname, String psw) throws JSONException, ClientProtocolException, IOException {
		String url = "10.0.2.2:3000/usr/login.json";
		HttpPost request = new HttpPost(url);
		JSONObject account = new JSONObject();
		account.put("user_name", uname);
		account.put("password", psw);
		StringEntity se = new StringEntity(account.toString()); 
		request.setEntity(se);
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		JSONObject result = new JSONObject(retSrc);
		String error = result.get("error").toString();
		if(error!=null)
			return false;
		else return true;
	}

	public boolean logout(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ResListItem> getResList(int order) {
		
		// TODO Auto-generated method stub
		return null;
	}

	public Restaurant getResInfo(int resId) {
		// TODO Auto-generated method stub
		return null;
	}

	public int makeComment(int uid, int resId, float evaluation,
			String content, int[] recDishes, String otherDishes, int cost) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Comment> getComment(int resId, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reportBusy(int uid, int resId, int busy) {
		// TODO Auto-generated method stub
		
	}

	public void makeComplaint(int uid, int resId, String content) {
		// TODO Auto-generated method stub
		
	}
//String url = "10.0.2.2:3000/usr/login.json/";
//				HttpPost request = new HttpPost(url);
//				JSONObject account = new JSONObject();
//				account.put("user_name", uname);
//				account.put("password", psw);
//				StringEntity se = new StringEntity(account.toString()); 
//				request.setEntity(se);
//				HttpResponse httpResponse = new DefaultHttpClient().execute(request);
//				String retSrc = EntityUtils.toString(httpResponse.getEntity());
//				JSONObject result = new JSONObject(retSrc);
//				String error_code = result.get("error_code").toString();
//				if(error_code!=null) {
//				}
}