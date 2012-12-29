package pku.cs.epkuer;

import java.util.HashMap;

import pku.cs.epkuer.api.API;
import pku.cs.epkuer.util.Restaurant;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

public class ResInfo extends Activity implements OnClickListener {

	public Restaurant res;
	private HashMap<String, Integer> images = new HashMap<String, Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resinfo);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() throws Exception {
		images.put("学五食堂", R.drawable.xuewu);
		images.put("学一食堂", R.drawable.xueyi);
		images.put("家园餐厅", R.drawable.jiayuan);
		images.put("艺园", R.drawable.yiyuan);
		images.put("农园", R.drawable.nongyuan);
		images.put("松林快餐", R.drawable.songlin);
		images.put("燕南美食", R.drawable.yannan);
		images.put("桂林米粉", R.drawable.guilin);
		images.put("康博思中餐厅", R.drawable.kangzhong);
		images.put("康博思西餐厅", R.drawable.kangxi);
		images.put("康博思饺子部", R.drawable.kangjiao);
		images.put("康博思面食部", R.drawable.mianshi);

		Intent intent = getIntent();
		Integer id = intent.getIntExtra("id", 0);

		res = API.getResInfo(id);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText(res.name);
		RatingBar rb = (RatingBar) this.findViewById(R.id.ratingBar);
		rb.setRating(res.evaluation);
		ImageView iv = (ImageView) this.findViewById(R.id.restaurant_view);
		iv.setImageResource(images.get(res.name));
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.recList);
		String recommendations = res.recommendations;
		String[] recList = recommendations.split("，|,| ");
		for (String str : recList) {
			tv = new TextView(this);
			tv.setText(str);
			tv.setTextColor(Color.BLACK);
			ll.addView(tv);
			tv = (TextView) this.findViewById(R.id.Fee);
			tv.setText(res.average_cost + "元");
		}

		Button busy_high = (Button) this.findViewById(R.id.crowd_high);
		Button busy_mid = (Button) this.findViewById(R.id.crowd_mid);
		Button busy_low = (Button) this.findViewById(R.id.crowd_low);
		busy_high.setOnClickListener(this);
		busy_mid.setOnClickListener(this);
		busy_low.setOnClickListener(this);
		if (res.busy.equals("拥挤"))
			busy_high.setTextColor(Color.RED);
		else if (res.busy.equals("还好"))
			busy_mid.setTextColor(Color.YELLOW);
		else if (res.busy.equals("宽松"))
			busy_low.setTextColor(Color.GREEN);

		if (res.rct_cmt1 != null) {
			tv = (TextView) this.findViewById(R.id.user1);
			tv.setText(res.rct_cmt1.user_name);
			tv = (TextView) this.findViewById(R.id.comment1);
			tv.setText(res.rct_cmt1.content);
			rb = (RatingBar) this.findViewById(R.id.rating1);
			rb.setRating(res.rct_cmt1.evaluation);
		}

		if (res.rct_cmt2 != null) {
			tv = (TextView) this.findViewById(R.id.user2);
			tv.setText(res.rct_cmt2.user_name);
			tv = (TextView) this.findViewById(R.id.comment2);
			tv.setText(res.rct_cmt2.content);
			rb = (RatingBar) this.findViewById(R.id.rating2);
			rb.setRating(res.rct_cmt2.evaluation);
		}

		Button bt = (Button) this.findViewById(R.id.myComment);
		bt.setOnClickListener(this);
		bt = (Button) this.findViewById(R.id.viewComment);
		bt.setOnClickListener(this);
		bt = (Button) this.findViewById(R.id.complain);
		bt.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle;
		Button busy_high = (Button) this.findViewById(R.id.crowd_high);
		Button busy_mid = (Button) this.findViewById(R.id.crowd_mid);
		Button busy_low = (Button) this.findViewById(R.id.crowd_low);
		switch (v.getId()) {
		case R.id.myComment:
			intent.setClass(ResInfo.this, CommentActivity.class);
			bundle = new Bundle();
			bundle.putInt("res_id", res.id);
			bundle.putString("res_name", res.name);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.viewComment:
			bundle = new Bundle();
			bundle.putInt("res_id", res.id);
			bundle.putString("res_name", res.name);
			intent.putExtras(bundle);
			intent.setClass(ResInfo.this, CommentList.class);
			startActivity(intent);
			break;
		case R.id.complain:
			bundle = new Bundle();
			bundle.putInt("res_id", res.id);
			bundle.putString("res_name", res.name);
			intent.putExtras(bundle);
			intent.setClass(ResInfo.this, ComplainActivity.class);
			startActivity(intent);
			break;
		case R.id.crowd_high:
			Toast.makeText(this, "已报告拥挤程度为：拥挤", Toast.LENGTH_SHORT).show();
			try {
				API.reportBusy(res.id, "拥挤");
			} catch (Exception e) {
				e.printStackTrace();
			}
			busy_high.setClickable(false);
			busy_mid.setClickable(false);
			busy_low.setClickable(false);
			break;
		case R.id.crowd_mid:
			Toast.makeText(this, "已报告拥挤程度为：还好", Toast.LENGTH_SHORT).show();
			try {
				API.reportBusy(res.id, "还好");
			} catch (Exception e) {
				e.printStackTrace();
			}
			busy_high.setClickable(false);
			busy_mid.setClickable(false);
			busy_low.setClickable(false);
			break;
		case R.id.crowd_low:
			Toast.makeText(this, "已报告拥挤程度为：宽松", Toast.LENGTH_SHORT).show();
			try {
				API.reportBusy(res.id, "宽松");
			} catch (Exception e) {
				e.printStackTrace();
			}
			busy_high.setClickable(false);
			busy_mid.setClickable(false);
			busy_low.setClickable(false);
			break;
		default:
			break;
		}
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