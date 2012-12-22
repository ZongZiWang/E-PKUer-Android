package pku.cs.epkuer;

import pku.cs.util.Database;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	public EditText mAccountsEditText, mPassEditText;
	Button mLoginButton, mSignupButton;

	/** Called when the activity is first created. */
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

		switch (v.getId()) {

		case R.id.login_btn_login:
			if (mAccountsEditText.getText().toString().equals("")) {
				break;
			}
			String uname = mAccountsEditText.getText().toString();
			String psw = mPassEditText.getText().toString();
			Database db = new Database(Login.this);
			db.open();
			// TODO:验证登陆：若失败，则Toast，否则跳转ResList，记录密码
			break;
		case R.id.login_btn_signup:
			// 跳转Signup
			Intent i = new Intent(this, Signup.class);
			startActivity(i);
			break;
		}
	}

}