package pku.cs.epkuer;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import pku.cs.epkuer.api.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

//程序启动时调用的第一个activity
public class MainActivity extends Activity {

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		try {
			prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepare() throws ClientProtocolException, JSONException,
			IOException {

		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		String username = sp.getString("USERNAME", "null");
		String password = sp.getString("PASSWORD", "null");

		Intent i;

		// 没有登录记录
		if (username.equals("null") && password.equals("null")) {
			i = new Intent(this, LoginActivity.class);
			startActivity(i);
		} else {
			// 若失败 转到login，Toast提醒
			if (API.login(username, password)==-1) {
				i = new Intent(this, LoginActivity.class);
				Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
			// 若成功，转到食堂列表界面
			else {
				i = new Intent(this, ResList.class);
				startActivity(i);
			}
		}
	}
}