package pku.cs.epkuer;

import pku.cs.epkuer.api.API;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class ComplainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complain);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		Intent intent = getIntent();
		String resName = intent.getStringExtra("res_name");
		tv.setText(resName);
		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		final int uid = sp.getInt("USERID", -1);
		Button bt = (Button) this.findViewById(R.id.submit);
		bt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = getIntent();
				Integer resId = intent.getIntExtra("res_id", 0);

				EditText et = (EditText) findViewById(R.id.complaint);
				String content = et.getText().toString();

				try {
					if (API.makeComplaint(uid, resId, content)) {
						Toast.makeText(ComplainActivity.this, "投诉提交成功！",
								Toast.LENGTH_SHORT).show();
						finish();// 返回上个页面
					} else
						Toast.makeText(ComplainActivity.this, "投诉提交失败！",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST + 1, 1, "注销");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
		return true;
	}
}
