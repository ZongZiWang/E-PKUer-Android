package pku.cs.epkuer;

import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1;
import com.tencent.weibo.oauthv1.OAuthV1Client;
import com.tencent.weibo.utils.QHttpClient;
import com.tencent.weibo.webview.OAuthV1AuthorizeWebView;

import pku.cs.epkuer.api.API;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class CommentActivity extends Activity implements OnClickListener {

	private static String TAG = "CommentActivity.class";
	// !!!请根据您的实际情况修改!!! 认证成功后浏览器会被重定向到这个url中 本例子中不需改动
	private String oauthCallback = "null";
	// !!!请根据您的实际情况修改!!! 换为您为自己的应用申请到的APP KEY
	private String oauthConsumeKey = "801134885";
	// !!!请根据您的实际情况修改!!! 换为您为自己的应用申请到的APP SECRET
	private String oauthConsumerSecret = "7a6ca45ef222e2b1590c90b731e83abd";
	private OAuthV1 oAuth; // Oauth鉴权所需及所得信息的封装存储单元
	String[] recList;
	int n = 1;
	CheckBox[] cb = new CheckBox[3];
	private Integer resId;
	private int uid;
	private String content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		Intent intent = getIntent();
		String resName = intent.getStringExtra("res_name");
		resId = intent.getIntExtra("res_id", 0);
		tv.setText(resName);
		// TODO
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.recList);
		recList = intent.getStringArrayExtra("res_rec");

		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		uid = sp.getInt("USERID", -1);

		if (recList != null) {
			for (String str : recList) {
				cb[n - 1] = new CheckBox(this);
				cb[n - 1].setId(n++);
				cb[n - 2].setText(str);
				cb[n - 2].setTextColor(Color.BLACK);
				ll.addView(cb[n - 2]);
			}
		}
		Button bt = (Button) this.findViewById(R.id.submit);
		bt.setOnClickListener(this);
		Button cancel_btn = (Button) this.findViewById(R.id.cancle);
		cancel_btn.setOnClickListener(this);
	}

	private void publish() {
		TAPI tAPI;
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);
		try {
			String response = tAPI.add(oAuth, "json", content, "127.0.0.1");
			Toast.makeText(CommentActivity.this, response + "\n",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tAPI.shutdownConnection();
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// tencent
		if (requestCode == 1) {
			if (resultCode == OAuthV1AuthorizeWebView.RESULT_CODE) {
				// 从返回的Intent中获取验证码
				oAuth = (OAuthV1) data.getExtras().getSerializable("oauth");
				Log.i(TAG, "---------Step3: getAccessToken--------");
				try {
					oAuth = OAuthV1Client.accessToken(oAuth);
					/*
					 * 注意：此时oauth中的Oauth_token和Oauth_token_secret将发生变化，用新获取到的
					 * 已授权的access_token和access_token_secret替换之前存储的未授权的request_token
					 * 和request_token_secret.
					 */
					SharedPreferences sp = getSharedPreferences("USER_INFO",
							MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("tencent_access_token",
							oAuth.getOauthToken());
					editor.putString("tencent_access_token_secret",
							oAuth.getOauthTokenSecret());
					editor.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				publish();
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST + 1, 1, "注销");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.submit) {

			RatingBar rb = (RatingBar) findViewById(R.id.rating);
			float evaluation = rb.getRating();
			EditText et = (EditText) findViewById(R.id.comment);
			content = et.getText().toString();
			et = (EditText) findViewById(R.id.fee);
			float cost = Float.valueOf(et.getText().toString());
			et = (EditText) findViewById(R.id.recDish);
			String otherDishes = et.getText().toString();
			String recDishes = "";
			for (int j = 0; j < n - 1; j++) {
				if (cb[j].isChecked())
					recDishes = recDishes + cb[j].getText() + " ";
			}
			recDishes += otherDishes;
			try {
				if (API.makeComment(uid, resId, evaluation, content, recDishes,
						cost)) {
					Toast.makeText(CommentActivity.this, "评论提交成功！",
							Toast.LENGTH_SHORT).show();
					// finish();// 返回上个页面
				} else
					Toast.makeText(CommentActivity.this, "评论提交失败！",
							Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			CheckBox sync = (CheckBox) this.findViewById(R.id.sync);
			if (sync.isChecked()) {
				SharedPreferences sp = getSharedPreferences("USER_INFO",
						MODE_PRIVATE);
				String access_token = sp.getString("tencent_access_token",
						"null");
				String access_token_secret = sp.getString(
						"tencent_access_token_secret", "null");
				oAuth = new OAuthV1(oauthCallback);
				oAuth.setOauthConsumerKey(oauthConsumeKey);
				oAuth.setOauthConsumerSecret(oauthConsumerSecret);
				// 没有登录记录
				if (access_token.equals("null")
						&& access_token_secret.equals("null")) {

					oAuth = new OAuthV1(oauthCallback);
					oAuth.setOauthConsumerKey(oauthConsumeKey);
					oAuth.setOauthConsumerSecret(oauthConsumerSecret);

					// 关闭OAuthV1Client中的默认开启的QHttpClient。
					OAuthV1Client.getQHttpClient().shutdownConnection();

					// 为OAuthV1Client配置自己定义QHttpClient。
					OAuthV1Client.setQHttpClient(new QHttpClient());
					Intent intent;
					Log.i(TAG, "---------Step1: Get requestToken--------");
					try {
						// 向腾讯微博开放平台请求获得未授权的Request_Token
						oAuth = OAuthV1Client.requestToken(oAuth);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Log.i(TAG, "---------Step2: authorization--------");
					Log.i(TAG, "start WebView intent");
					intent = new Intent(CommentActivity.this,
							OAuthV1AuthorizeWebView.class);// 创建Intent，使用WebView让用户授权
					intent.putExtra("oauth", oAuth);
					startActivityForResult(intent, 1);
				} else {
					oAuth.setOauthToken(access_token);
					oAuth.setOauthTokenSecret(access_token_secret);
					publish();
				}
			}

		} else if (v.getId() == R.id.cancel) {
			finish();
		}

	}
}
