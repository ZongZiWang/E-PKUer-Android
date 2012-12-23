package pku.cs.epkuer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

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

		Intent i;
		// û�е�¼��¼
		if (username.equals("null") && password.equals("null")) {
			i = new Intent(this, LoginActivity.class);
			//i = new Intent(this, CmtList.class);
			startActivity(i);
		} 
		// TODO:���Ե�½
		else { 
			if (1==2) {// ��ʧ�� ת��login��Toast����
				i = new Intent(this, LoginActivity.class);
				Toast.makeText(this, "�û������������", Toast.LENGTH_SHORT).show();
				startActivity(i);
			}
			else {// ���ɹ���ת��ʳ���б����
				i = new Intent(this, ResList.class);
				startActivity(i);
			}
		}
	}

}