package pku.cs.epkuer;

import pku.cs.epkuer.api.API;

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
			if (!user_name.equals("") && !password.equals("")
					&& password.equals(password2)) {
				try {
					int uid = API.signup(user_name, password);
					if (uid != -1) {
						SharedPreferences sp = getSharedPreferences(
								"USER_INFO", MODE_PRIVATE);
						Editor editor = sp.edit();
						editor.putString("USERNAME", user_name);
						editor.putString("PASSWORD", password);
						editor.putInt("USERID", uid);
						editor.commit();
						Intent i = new Intent(this, ResList.class);
						startActivity(i);
					} else
						Toast.makeText(this, "用户名已经存在！", LENGTH_SHORT).show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (user_name.equals("") || password.equals("")) {
				Toast.makeText(this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
			} else if (!password.equals(password2)) {
				Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
}