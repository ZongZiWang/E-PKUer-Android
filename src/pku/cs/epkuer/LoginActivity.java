package pku.cs.epkuer;

<<<<<<< HEAD
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
=======
import pku.cs.epkuer.api.API;
>>>>>>> 实现了注册和登陆功能

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	public EditText mAccountsEditText, mPassEditText;
	Button mLoginButton, mSignupButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginpage);

		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		mSignupButton = (Button) findViewById(R.id.login_btn_signup);
		mAccountsEditText = (EditText) findViewById(R.id.login_edit_account);
		mPassEditText = (EditText) findViewById(R.id.login_edit_pwd);
		mLoginButton.setOnClickListener(this);
		mSignupButton.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {

		case R.id.login_btn_login:
			String uname = mAccountsEditText.getText().toString();
			String psw = mPassEditText.getText().toString();
			if (uname.equals("") || psw.equals("")) {
				Toast.makeText(this, "�������������û��������룡", Toast.LENGTH_SHORT).show();
				break;
			}
			try {
				if(!API.login(uname, psw)) {
					Toast.makeText(this, "�û������������!", Toast.LENGTH_SHORT).show();
				}
				else {//�ɹ����¼�˺���Ϣ����ת��ʳ���б�����
					SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("USERNAME", uname);
					editor.putString("PASSWORD", psw);
					editor.commit();
					i = new Intent(this, ResList.class);
					startActivity(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.login_btn_signup:
			// ��תע�����
			i = new Intent(this, SignupActivity.class);
			startActivity(i);
			break;
		}
	}

}