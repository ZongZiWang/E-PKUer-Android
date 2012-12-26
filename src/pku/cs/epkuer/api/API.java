package pku.cs.epkuer.api;

import pku.cs.epkuer.R;
import pku.cs.epkuer.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class API{

	private HashMap<Integer,Integer> images=new HashMap<Integer,Integer>();

	// public static String basicURL="http://162.105.146.21:3000/";
		public static String basicURL = "http://10.0.2.2:3000/";

		/**
		 * 注册新账号
		 * 
		 * @param uname
		 *            用户名
		 * @param psw
		 *            密码
		 * @return 注册成功返回true，失败返回false
		 * @throws JSONException
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public static boolean signup(String uname, String psw)
				throws JSONException, ClientProtocolException, IOException {
			String url = basicURL + "user/signup.json/";
			HttpPost request = new HttpPost(url);
			List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
			postParametres.add(new BasicNameValuePair("name", uname));
			postParametres.add(new BasicNameValuePair("password", psw));
			postParametres
					.add(new BasicNameValuePair("password_confirmation", psw));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParametres);
			request.setEntity(formEntity);
			HttpResponse httpResponse = new DefaultHttpClient().execute(request);
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED)
				return true;
			else
				return false;
		}

		/**
		 * 登陆
		 * 
		 * @param uname
		 *            用户名
		 * @param psw
		 *            密码
		 * @return 成功为true，失败为false
		 * @throws JSONException
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		public static boolean login(String uname, String psw) throws JSONException,
				ClientProtocolException, IOException {
			String url = basicURL + "user/login.json/";
			HttpPost request = new HttpPost(url);
			List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
			postParametres.add(new BasicNameValuePair("name", uname));
			postParametres.add(new BasicNameValuePair("password", psw));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParametres);
			request.setEntity(formEntity);
			HttpResponse httpResponse = new DefaultHttpClient().execute(request);

			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			if (retSrc.equals("0"))
				return true;
			else
				return false;
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
//		Log.v("data", json);
		JSONArray array = new JSONArray(json);
		ResListItem resItem = null;
		for (int i = 0; i < array.length(); i++) {
			JSONObject item = array.getJSONObject(i);
			resItem= new ResListItem();
			resItem.id=item.getInt("id");
			resItem.name = item.getString("name");
			resItem.busy= item.getString("busy");
			resItem.evaluation= (float) item.getDouble("evaluation");
			resItem.recommendations=item.getString("recommendations");
			if(images.get(resItem.id)==null)
				resItem.img=R.drawable.xuewu;
			else
				resItem.img=images.get(resItem.id);
			resList.add(resItem);
		}
		return	resList;
	}

	public Restaurant getResInfo(int resId) throws Exception {
		// TODO Auto-generated method stub
		 String path = "http://10.0.2.2:3000/restaurants/"+resId+".json";
//			String path = "http://162.105.146.21:3000/restaurants/"+resId+".json";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setReadTimeout(5*1000);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		byte[] data=StreamTool.ReadInputSream(inStream);
		String json = new String(data);
		JSONObject item = new JSONObject(json);
		Restaurant restaurant=new Restaurant();
		restaurant.id=item.getInt("id");
		restaurant.average_cost=(float) item.getDouble("average_cost");
		restaurant.busy=item.getString("busy");
		restaurant.category=item.getString("category");
		restaurant.description=item.getString("description");
		restaurant.dishes=item.getString("dishes");
		restaurant.evaluation=(float) item.getDouble("evaluation");
//		restaurant.evaluation_service=item.getInt("evaluation_service");
//		restaurant.evaluation_taste=item.getInt("evaluation_taste");
		restaurant.image_url=item.getString("image_url");
		restaurant.info_summary=item.getString("info_summary");
		restaurant.info_tel=item.getString("info_tel");
		restaurant.info_time=item.getString("info_time");
//		restaurant.location_latitude=item.getDouble("location_latitude");
//		restaurant.location_longitude=item.getDouble("location_longitude");
		restaurant.location_name=item.getString("name");
		restaurant.location_zone=item.getString("location_zone");
		restaurant.name=item.getString("name");
		restaurant.recommendations=item.getString("recommendations");
		return restaurant;
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
}