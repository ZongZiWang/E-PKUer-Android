package pku.cs.epkuer;

import pku.cs.epkuer.api.API;
import pku.cs.epkuer.util.Restaurant;
import pku.cs.epkuer.util.StreamTool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class ResInfo extends Activity implements OnClickListener {

	public Restaurant res;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resinfo);
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() throws Exception {
		Intent intent = getIntent();
		Integer id = intent.getIntExtra("id", 0);
		Integer img = intent.getIntExtra("img", R.drawable.xuewu);
		res = new API().getResInfo(id);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText(res.name);
		RatingBar rb = (RatingBar) this.findViewById(R.id.ratingBar);
		rb.setRating(res.evaluation);
		ImageView iv = (ImageView) this.findViewById(R.id.restaurant_view);
		if (res.image_url.contentEquals(""))
			iv.setImageResource(img);
		else {
			byte[] data = StreamTool.getImage(res.image_url);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// 生成位图
			// 显示图片
			iv.setImageBitmap(bitmap);
		}
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

			Button btn = (Button) findViewById(R.id.crowd_high);
			btn.setTextColor(Color.RED);
		}
		tv = (TextView) this.findViewById(R.id.user1);
		tv.setText("echo");
		tv = (TextView) this.findViewById(R.id.comment1);
		tv.setText("饭好难吃啊~~");
		rb = (RatingBar) this.findViewById(R.id.rating1);
		rb.setRating(2);

		tv = (TextView) this.findViewById(R.id.user2);
		tv.setText("山南水北");
		tv = (TextView) this.findViewById(R.id.comment2);
		tv.setText("肥牛饭和麻婆豆腐饭都很好吃！！饭点儿人很多，鸡腿饭总是抢不到T_T");
		rb = (RatingBar) this.findViewById(R.id.rating2);
		rb.setRating(4);

		Button bt = (Button) this.findViewById(R.id.myComment);
		bt.setOnClickListener(this);
		bt = (Button) this.findViewById(R.id.viewComment);
		bt.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (R.id.myComment == v.getId()) {
			Intent intent = new Intent();
			// intent.setClass(ResInfo.this, CommentActivity.class);
			startActivity(intent);
		}
		else if(R.id.viewComment==v.getId()){
			Intent intent = new Intent();
			Bundle bundle=new Bundle();
			bundle.putInt("res_id", res.id);
			bundle.putString("res_name", res.name);
			intent.putExtras(bundle);
			intent.setClass(ResInfo.this, CommentList.class);
			startActivity(intent);
		}

	}
}
