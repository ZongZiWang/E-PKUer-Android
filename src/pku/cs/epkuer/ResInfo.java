package pku.cs.epkuer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import pku.cs.epkuer.util.StreamTool;
import android.os.Bundle;
import android.util.Log;
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
		Intent intent=getIntent(); 
        Integer id=intent.getIntExtra("id", 0); 
        Integer img=intent.getIntExtra("img", R.drawable.xuewu); 
        String path = "http://10.0.2.2:3000/restaurants/"+id+".json";
//		String path = "http://162.105.146.21:3000/restaurants.json";
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setReadTimeout(5*1000);
			conn.setRequestMethod("GET");
			InputStream inStream = conn.getInputStream();
			byte[] data=StreamTool.ReadInputSream(inStream);
			String json = new String(data);
			Log.v("data", json);
			JSONObject item = new JSONObject(json);
			TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
			tv.setText(item.getString("name"));
			RatingBar rb = (RatingBar) this.findViewById(R.id.ratingBar);
			rb.setRating((float) item.getDouble("evaluation"));
			ImageView iv = (ImageView) this.findViewById(R.id.restaurant_view);
			iv.setImageResource(img);
			LinearLayout ll = (LinearLayout) this.findViewById(R.id.recList);
			String recommendations=item.getString("recommendations");
			String[] recList=recommendations.split("，|,| ");
			for (String str : recList) {
				tv = new TextView(this);
				tv.setText(str);
				tv.setTextColor(Color.BLACK);
				ll.addView(tv);
				tv = (TextView) this.findViewById(R.id.Fee);
				tv.setText(item.getString("average_cost") + "元");

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
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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
