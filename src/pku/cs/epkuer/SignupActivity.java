package pku.cs.epkuer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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

public class SignupActivity extends Activity implements OnClickListener {

	private static final int LENGTH_SHORT = 0;
	public EditText mAccountsEditText, mPassEditText, mPassEditText2;
	Button mSignupButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.signuppage);

		mSignupButton = (Button) findViewById(R.id.signup_btn_signup);
		mAccountsEditText = (EditText) findViewById(R.id.signup_edit_account);
		mPassEditText = (EditText) findViewById(R.id.signup_edit_pwd);
		mPassEditText2 = (EditText) findViewById(R.id.signup_edit_pwd2);
		mSignupButton.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.signup_btn_signup:
			
			String user_name = mAccountsEditText.getText().toString();
			String password = mPassEditText.getText().toString();
			String password2 = mPassEditText2.getText().toString();
			if(!user_name.equals("") && !password.equals("") && password.equals(password2)) {
				try {
					// TODO: 注册功能
					String url = "http://162.105.146.21:3000/users/signup.json/";
					HttpPost request = new HttpPost(url);
					JSONObject account = new JSONObject();
					account.put("name", user_name);
					account.put("password", password);
					account.put("password_confirmation", password2);
					StringEntity se = new StringEntity(account.toString()); 
					request.setEntity(se);
					HttpResponse httpResponse = new DefaultHttpClient().execute(request);
					if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
						String retSrc = EntityUtils.toString(httpResponse.getEntity());
						Toast.makeText(this, retSrc, Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(this, String.valueOf(httpResponse.getStatusLine().getStatusCode()), LENGTH_SHORT).show();
					}
//					String retSrc = EntityUtils.toString(httpResponse.getEntity());
//					JSONObject result = new JSONObject(retSrc);
//					String error_code = result.toString();
					
//					if(error_code!=null) {
//						Toast.makeText(this, "用户名已存在!", Toast.LENGTH_SHORT).show();
//					}
//					else {//成功则记录账号信息，并转入食堂列表界面
//						SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
//						Editor editor = sp.edit();
//						editor.putString("USERNAME", user_name);
//						editor.putString("PASSWORD", password);
//						editor.commit();
//						Intent i = new Intent(this, ResList.class);
//						startActivity(i);
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(user_name.equals("") || password.equals("")) {
				Toast.makeText(this,"用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
			}
			else if(!password.equals(password2)) {
				Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

}