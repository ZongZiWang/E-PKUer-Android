package pku.cs.epkuer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity implements OnClickListener {

	public EditText mAccountsEditText, mPassEditText, mPassEditText2;
	Button mSignupButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.signuppage);

		mSignupButton = (Button) findViewById(R.id.signup_btn_signup);
		mAccountsEditText = (EditText) findViewById(R.id.signup_edit_account);
		mPassEditText = (EditText) findViewById(R.id.signup_edit_pwd);
		//mPassEditText2 = (EditText) findViewById(R.id.signup_edit_pwd2);
		mSignupButton.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.signup_btn_signup:
			// TODO:验证注册，成功则跳转到main，否则toast
			break;
		}
	}

}