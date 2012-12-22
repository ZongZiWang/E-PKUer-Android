package pku.cs.epkuer;

import pku.cs.util.Database;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

//��������ʱ���õĵ�һ��activity
public class MainActivity extends Activity {

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		prepare();// ����½

	}

	private void prepare() {

		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		String username = sp.getString("USERNAME", "null");
		String password = sp.getString("PASSWORD", "null");

		// û�е�¼��¼
		if (username.equals("null") && password.equals("null")) {
			//Intent i = new Intent(this, Login.class);
			Intent i = new Intent(this, CmtList.class);
			startActivity(i);
		} else { // ���Ե�½
			Database db = new Database(this);
			db.open();

			// ��ʧ�� ת��login��Toast����
			// TODO:
			// ���ɹ���ת��main
			// TODO:
		}
	}

}