package pku.cs.epkuer;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

public class ResInfo extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resinfo);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText("康博斯中餐馆");
		RatingBar rb = (RatingBar) this.findViewById(R.id.ratingBar);
		rb.setRating(3);
		ImageView iv = (ImageView) this.findViewById(R.id.restaurant_view);
		iv.setImageResource(R.drawable.kangzhong);
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.recList);
		String[] recList = { "鸡腿饭", "肥牛饭", "土豆饼" };
		for (String str : recList) {
			tv = new TextView(this);
			tv.setText(str);
			tv.setTextColor(Color.BLACK);
			ll.addView(tv);
		}
		tv = (TextView) this.findViewById(R.id.Fee);
		tv.setText(8 + "元");

		Button btn = (Button) findViewById(R.id.crowd_high);
		btn.setTextColor(Color.RED);
		
		tv = (TextView) this.findViewById(R.id.user1);
		tv.setText("echo");
		tv= (TextView) this.findViewById(R.id.comment1);
		tv.setText("饭好难吃啊~~");
		rb = (RatingBar) this.findViewById(R.id.rating1);
		rb.setRating(2);
		
		tv = (TextView) this.findViewById(R.id.user2);
		tv.setText("山南水北");
		tv= (TextView) this.findViewById(R.id.comment2);
		tv.setText("肥牛饭和麻婆豆腐饭都很好吃！！饭点儿人很多，鸡腿饭总是抢不到T_T");
		rb = (RatingBar) this.findViewById(R.id.rating2);
		rb.setRating(4);		
		
		Button bt = (Button) this.findViewById(R.id.myComment);
		bt.setOnClickListener(this);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (R.id.myComment == v.getId()) {
			Intent intent = new Intent();
			//intent.setClass(ResInfo.this, CommentActivity.class);
			startActivity(intent);
		}

	}
}
