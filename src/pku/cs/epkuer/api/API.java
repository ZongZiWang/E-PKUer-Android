package pku.cs.epkuer.api;

import pku.cs.epkuer.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class API {

	// public static String basicURL="http://162.105.146.21:3000/";
	public static String basicURL = "http://10.0.2.2:3000/";

	/**
	 * ע�����˺�
	 * 
	 * @param uname
	 *            �û���
	 * @param psw
	 *            ����
	 * @return ע��ɹ�����true��ʧ�ܷ���false
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static int signup(String uname, String psw) throws JSONException,
			ClientProtocolException, IOException {
		String url = basicURL + "users/signup.json/";
		HttpPost request = new HttpPost(url);
		List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
		postParametres.add(new BasicNameValuePair("name", uname));
		postParametres.add(new BasicNameValuePair("password", psw));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				postParametres);
		request.setEntity(formEntity);
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);

		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		JSONObject result;
		int uid = -1;
		try {
			result = new JSONObject(retSrc);
			uid = (Integer) result.get("id");
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
		return uid;
	}

	/**
	 * ��½
	 * 
	 * @param uname
	 *            �û���
	 * @param psw
	 *            ����
	 * @return �ɹ�Ϊtrue��ʧ��Ϊfalse
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static int login(String uname, String psw)
			throws ClientProtocolException, IOException {
		String url = basicURL + "users/login.json/";
		HttpPost request = new HttpPost(url);
		List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
		postParametres.add(new BasicNameValuePair("name", uname));
		postParametres.add(new BasicNameValuePair("password", psw));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				postParametres);
		request.setEntity(formEntity);
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);

		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		JSONObject result;
		int uid = -1;
		try {
			result = new JSONObject(retSrc);
			uid = (Integer) result.get("id");
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
		return uid;
	}

	/**
	 * ��ȡʳ���б�
	 * 
	 * @param order
	 *            ����������ʽ
	 * @return �����б�
	 * @throws Exception
	 */
	public static ArrayList<ResListItem> getResList(int order) throws Exception {

		ArrayList<ResListItem> resList = new ArrayList<ResListItem>();
		String path = "http://10.0.2.2:3000/restaurants.json";
		// String path = "http://162.105.146.21:3000/restaurants.json";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		byte[] data = StreamTool.ReadInputSream(inStream);
		String json = new String(data);
		JSONArray array = new JSONArray(json);
		ResListItem resItem = null;

		for (int i = 0; i < array.length(); i++) {
			JSONObject item = array.getJSONObject(i);
			resItem = new ResListItem();
			resItem.id = item.getInt("id");
			resItem.name = item.getString("name");
			resItem.busy = item.getString("busy");
			resItem.evaluation = (float) item.getDouble("evaluation");
			resItem.recommendations = item.getString("recommendations");
			resList.add(resItem);
		}

		if (order == 2) {
			// �Ӹߵ���
			Collections.sort(resList, new Comparator<ResListItem>() {
				public int compare(ResListItem r1, ResListItem r2) {
					if (r1.evaluation < r2.evaluation)
						return 1;
					else
						return -1;
				}
			});
		} else if (order == 3) {
			// �ӿ��ɵ�ӵ��
			Collections.sort(resList, new Comparator<ResListItem>() {
				public int compare(ResListItem r1, ResListItem r2) {
					int b1, b2;
					if (r1.busy.equals("ӵ��"))
						b1 = 3;
					else if (r1.busy.equals("����"))
						b1 = 2;
					else
						b1 = 1;
					if (r2.busy.equals("ӵ��"))
						b2 = 3;
					else if (r2.busy.equals("����"))
						b2 = 2;
					else
						b2 = 1;

					if (b1 > b2)
						return 1;
					else
						return -1;
				}
			});
		}
		// �������������ResList.java��ʵ��
		return resList;
	}

	/**
	 * ��ȡʳ�þ�����Ϣ
	 * 
	 * @param resId
	 *            ����Id
	 * @return �����ľ�����Ϣ
	 * @throws Exception
	 */
	public static Restaurant getResInfo(int resId) throws Exception {
		String path = "http://10.0.2.2:3000/restaurants/" + resId + ".json";
		// String path =
		// "http://162.105.146.21:3000/restaurants/"+resId+".json";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		byte[] data = StreamTool.ReadInputSream(inStream);
		String json = new String(data);
		JSONObject item = new JSONObject(json);
		Restaurant restaurant = new Restaurant();
		restaurant.id = resId;
		restaurant.name = item.getString("name");
		restaurant.busy = item.getString("busy");
		restaurant.average_cost = (float) item.getDouble("average_cost");
		restaurant.recommendations = item.getString("recommendations");
		restaurant.evaluation = (float) item.getDouble("evaluation");
		JSONArray jsonObjs = item.getJSONArray("partial_comments");
		if (jsonObjs.length() > 0) {
			JSONObject jsonObj = ((JSONObject) jsonObjs.opt(0));
			restaurant.rct_cmt1 = new Comment();
			restaurant.rct_cmt1.content = jsonObj.getString("content");
			restaurant.rct_cmt1.evaluation = (float) jsonObj
					.getDouble("evaluation");
			restaurant.rct_cmt1.user_name = jsonObj.getString("user_name");
		}
		if (jsonObjs.length() == 2) {
			JSONObject jsonObj = ((JSONObject) jsonObjs.opt(1));
			restaurant.rct_cmt2 = new Comment();
			restaurant.rct_cmt2.content = jsonObj.getString("content");
			restaurant.rct_cmt2.evaluation = (float) jsonObj
					.getDouble("evaluation");
			restaurant.rct_cmt2.user_name = jsonObj.getString("user_name");
		}
		return restaurant;
	}

	/**
	 * ��ȡ��start��ʼ������10������
	 * 
	 * @param resId
	 *            ʳ��id
	 * @param start
	 *            ��ʼindex
	 * @return ������������
	 * @throws Exception
	 */
	public static ArrayList<Comment> getComment(int resId, int start) throws Exception {
		ArrayList<Comment> cmtList = new ArrayList<Comment>();
		String path = "http://10.0.2.2:3000/restaurants/" + (resId)
				+ "/comments.json";
		// String path =
		// "http://162.105.146.21:3000/restaurants/"+resId+"/comments.json";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("start", "" + start);
		InputStream inStream = conn.getInputStream();
		byte[] data = StreamTool.ReadInputSream(inStream);
		String json = new String(data);
		Log.v("data", json);
		JSONArray array = new JSONArray(json);
		Comment comment = null;
		for (int i = 0; i < array.length(); i++) {
			JSONObject item = array.getJSONObject(i);
			comment = new Comment();
			comment.id = item.getInt("id");
			comment.content = item.getString("content");
			comment.cost = (float) item.getDouble("cost");
			comment.user_name = item.getString("user_name");
			comment.evaluation = (float) item.getDouble("evaluation");
			comment.res_id = item.getInt("restaurant_id");
			comment.time = item.getString("created_at");
			comment.user_id = item.getInt("user_id");
			cmtList.add(comment);
		}
		return cmtList;
	}

	/**
	 * ����ӵ��״��
	 * 
	 * @param resId
	 *            ʳ��id
	 * @param busy
	 *            ӵ��״������ӵ�����������á��������ɡ�
	 * @return ����ɹ�����true�����򷵻�false
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean reportBusy(int resId, String busy)
			throws ClientProtocolException, IOException {
		String url = basicURL + "restaurants/" + resId + "/busy.json/";
		HttpPut request = new HttpPut(url);
		List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
		postParametres.add(new BasicNameValuePair("busy", busy));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				postParametres);
		request.setEntity(formEntity);
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT)
			return true;
		else
			return false;
	}

	/**
	 * ��ʳ�ý��е���
	 * 
	 * @param uid
	 *            �û�id
	 * @param resId
	 *            ʳ��id
	 * @param evaluation
	 *            ����
	 * @param content
	 *            ��������
	 * @param recDishes
	 *            �Ƽ���id����
	 * @param otherDishes
	 *            �����Ƽ�����
	 * @param cost
	 *            �˾�����
	 * @return �ύ�ɹ�����true�����򷵻�false
	 * @throws Exception
	 */
	public static boolean makeComment(int uid, int resId, float evaluation,
			String content, int[] recDishes, String otherDishes, float cost)
			throws Exception {
		String url = basicURL + "restaurants/" + (resId)
				+ "/comments/upload.json/";
		HttpPost request = new HttpPost(url);

		List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
		postParametres.add(new BasicNameValuePair("content", content));
		postParametres
				.add(new BasicNameValuePair("cost", String.valueOf(cost)));
		postParametres.add(new BasicNameValuePair("evaluation", String
				.valueOf(evaluation)));
		postParametres.add(new BasicNameValuePair("user_id", String
				.valueOf(uid)));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				postParametres);
		request.setEntity(formEntity);
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		JSONObject result;
		try {
			result = new JSONObject(retSrc);
			result.get("content");
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ��ʳ��Ͷ��
	 * 
	 * @param uid
	 *            �û�id
	 * @param resId
	 *            ʳ��id
	 * @param content
	 *            Ͷ������
	 * @return Ͷ�߳ɹ�����true�����򷵻�false
	 * @throws Exception
	 */
	public static boolean makeComplaint(int uid, int resId, String content)
			throws Exception {
		String url = basicURL + "restaurants/" + (resId)
				+ "/complaints/upload.json/";
		HttpPost request = new HttpPost(url);

		List<NameValuePair> postParametres = new ArrayList<NameValuePair>();
		postParametres.add(new BasicNameValuePair("content", content));
		postParametres.add(new BasicNameValuePair("user_id", String
				.valueOf(uid)));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				postParametres);
		request.setEntity(formEntity);
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);

		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		JSONObject result;
		try {
			result = new JSONObject(retSrc);
			result.get("content");
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}