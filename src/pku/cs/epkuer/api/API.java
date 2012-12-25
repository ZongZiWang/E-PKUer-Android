package pku.cs.epkuer.api;

import pku.cs.epkuer.R;
import pku.cs.epkuer.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class API{

	private HashMap<Integer,Integer> images=new HashMap<Integer,Integer>();

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

	public ArrayList<ResListItem> getResList(int order) throws Exception {
		
		// TODO Auto-generated method stub
		images.put(2, R.drawable.nongyuan);
		images.put(3, R.drawable.xuewu);
		images.put(4, R.drawable.xueyi);
		ArrayList<ResListItem> resList = new ArrayList<ResListItem>();
		String path = "http://10.0.2.2:3000/restaurants.json";
//		String path = "http://162.105.146.21:3000/restaurants.json";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setReadTimeout(5*1000);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		byte[] data=StreamTool.ReadInputSream(inStream);
		String json = new String(data);
		Log.v("data", json);
		JSONArray array = new JSONArray(json);
		ResListItem resItem = null;
		for (int i = 0; i < array.length(); i++) {
			JSONObject item = array.getJSONObject(i);
			resItem= new ResListItem();
			resItem.id=item.getInt("id");
			resItem.name = item.getString("name");
			resItem.busy= item.getString("busy");
			resItem.evaluation= (float) item.getDouble("evaluation");
			resItem.dishes=item.getString("recommendations");
			if(images.get(resItem.id)==null)
				resItem.img=R.drawable.xuewu;
			else
				resItem.img=images.get(resItem.id);
			resList.add(resItem);
		}
		return	resList;
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