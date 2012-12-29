package pku.cs.epkuer;

import pku.cs.epkuer.api.API;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class CommentActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		Intent intent = getIntent();
		String resName = intent.getStringExtra("res_name");
		tv.setText(resName);
		// TODO
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.recList);
		String[] recList = { "鸡腿饭", "肥牛饭", "土豆饼" };
		CheckBox cb = null;
		SharedPreferences sp = getSharedPreferences("USER_INFO", MODE_PRIVATE);
		final int uid = sp.getInt("USERID", -1);
		int n = 1;
		for (String str : recList) {
			cb = new CheckBox(this);
			cb.setId(n++);
			cb.setText(str);
			cb.setTextColor(Color.BLACK);
			ll.addView(cb);
		}
		Button bt = (Button) this.findViewById(R.id.submit);
		bt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = getIntent();
				Integer resId = intent.getIntExtra("res_id", 0);
				RatingBar rb = (RatingBar) findViewById(R.id.rating);
				float evaluation = rb.getRating();
				EditText et = (EditText) findViewById(R.id.comment);
				String content = et.getText().toString();
				et = (EditText) findViewById(R.id.fee);
				float cost = Float.valueOf(et.getText().toString());
				int[] recDishes = { 1, 2 };
				String otherDishes = "没有";
				try {
					if (API.makeComment(uid, resId, evaluation, content,
							recDishes, otherDishes, cost)) {
						Toast.makeText(CommentActivity.this, "评论提交成功！",
								Toast.LENGTH_SHORT).show();
						finish();// 返回上个页面
					} else
						Toast.makeText(CommentActivity.this, "评论提交失败！",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Button cancel_btn = (Button) this.findViewById(R.id.cancle);
		cancel_btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
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
