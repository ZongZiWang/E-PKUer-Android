package pku.cs.epkuer;

import pku.cs.util.Database;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

//程序启动时调用的第一个activity
public class MainActivity extends Activity {

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		prepare();// 检查登陆

	}

	private void prepare() {

		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		String username = sp.getString("USERNAME", "null");
		String password = sp.getString("PASSWORD", "null");

		// 没有登录记录
		if (username.equals("null") && password.equals("null")) {
			//Intent i = new Intent(this, Login.class);
			Intent i = new Intent(this, CmtList.class);
			startActivity(i);
		} else { // 尝试登陆
			Database db = new Database(this);
			db.open();

			// 若失败 转到login，Toast提醒
			// TODO:
			// 若成功，转到main
			// TODO:
		}
	}

}