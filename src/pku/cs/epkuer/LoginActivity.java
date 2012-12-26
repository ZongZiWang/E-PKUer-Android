package pku.cs.epkuer;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

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
				Toast.makeText(this, "请输入完整的用户名和密码！", Toast.LENGTH_SHORT).show();
				break;
			}
			// TODO:验证登陆：若失败，则Toast，否则跳转ResList，记录密码
			try {
				String url = "10.0.2.2:3000/usr/login.json/";
				HttpPost request = new HttpPost(url);
				JSONObject account = new JSONObject();
				account.put("user_name", uname);
				account.put("password", psw);
				StringEntity se = new StringEntity(account.toString()); 
				request.setEntity(se);
				HttpResponse httpResponse = new DefaultHttpClient().execute(request);
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject result = new JSONObject(retSrc);
				String error_code = result.get("error_code").toString();
				if(error_code!=null) {
					Toast.makeText(this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
				}
				else {//成功则记录账号信息，并转入食堂列表界面
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
			// 跳转注册界面
//			i = new Intent(this, ResList.class);
			i = new Intent(this, SignupActivity.class);
			startActivity(i);
			break;
		}
	}

}