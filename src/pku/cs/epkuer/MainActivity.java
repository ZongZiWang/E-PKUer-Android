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

//��������ʱ���õĵ�һ��activity
public class MainActivity extends Activity {

	public static API api;
	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		try {
			prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ����½

	}

	private void prepare() throws ClientProtocolException, JSONException, IOException {

		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		String username = sp.getString("USERNAME", "null");
		String password = sp.getString("PASSWORD", "null");

		Intent i;
		// û�е�¼��¼
		if (username.equals("null") && password.equals("null")) {
			i = new Intent(this, LoginActivity.class);
			startActivity(i);
		} 
		// TODO:���Ե�½
		else { 
			if (api.login(username, password)) {// ��ʧ�� ת��login��Toast����
				i = new Intent(this, LoginActivity.class);
				Toast.makeText(this, "�û������������", Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
			else {// ���ɹ���ת��ʳ���б����
				i = new Intent(this, LoginActivity.class);
				startActivity(i);
			}
		}
	}

}