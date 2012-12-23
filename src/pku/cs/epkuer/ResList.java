package pku.cs.epkuer;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class ResList extends TabActivity{
	private TabHost myTabhost;
	protected int myTag = 2;
	private ArrayList<HashMap<String, Object>> mData;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myTabhost = this.getTabHost();
		final TabWidget tabWidget = myTabhost.getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.reslist,
				myTabhost.getTabContentView(), true);
		myTabhost.setBackgroundColor(Color.GRAY);

		myTabhost.addTab(myTabhost.newTabSpec("One").setIndicator("按距离")
				.setContent(R.id.list_dist));

		myTabhost.addTab(myTabhost.newTabSpec("Two").setIndicator("按评分")
				.setContent(R.id.list_rate));

		myTabhost.addTab(myTabhost.newTabSpec("Three").setIndicator("按拥挤程度")
				.setContent(R.id.list_status));

		// 初始化Tab选项卡背景色
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			View vvv = tabWidget.getChildAt(i);
			vvv.getLayoutParams().height = 45;
			final TextView tv = (TextView) vvv.findViewById(android.R.id.title);
			tv.setTextColor(this.getResources().getColorStateList(
					android.R.color.black));
			if (myTabhost.getCurrentTab() == i)
				vvv.setBackgroundColor(Color.WHITE);
			else
				vvv.setBackgroundColor(Color.GRAY);
		}

		// 当点击tab选项卡的时候，更改当前的背景，并装载对应的activity
		myTabhost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tagString) {
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					View vvv = tabWidget.getChildAt(i);
					if (myTabhost.getCurrentTab() == i) {
						vvv.setBackgroundColor(Color.WHITE);
					} else {
						vvv.setBackgroundColor(Color.GRAY);
					}
				}
				if (tagString.equals("One")) {
					myTag = 1;
				}
				if (tagString.equals("Two")) {
					myTag = 2;
				}
				if (tagString.equals("Three")) {
					myTag = 3;
				}
			}
		});
		initList();
	}

	// 获取填充ListView
	private void initList() {
		mData = getData();
		ListView lv = (ListView) findViewById(R.id.listView3);
		MyAdapter adapter = new MyAdapter(this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Toast.makeText(getApplicationContext(), (String) mData.get(position).get("resName"), Toast.LENGTH_SHORT)
				.show();
			}
		});
	}

	// 获取填充数据
	private ArrayList<HashMap<String, Object>> getData() {

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("resName", "学五食堂");
		map.put("favDish", "宫保鸡丁，孜然鸡块");
		map.put("status", "拥挤");
		map.put("img", R.drawable.xuewu);
		map.put("mark", "4.5");
		listItem.add(map);

		map = new HashMap<String, Object>();
		map.put("resName", "学一食堂");
		map.put("favDish", "地三鲜，辣子鸡，冬菜包");
		map.put("status", "拥挤");
		map.put("img", R.drawable.xueyi);
		map.put("mark", "4.0");
		listItem.add(map);

		map = new HashMap<String, Object>();
		map.put("resName", "康中食堂");
		map.put("favDish", "鸡腿饭，咖喱鸡肉饭");
		map.put("status", "还好");
		map.put("img", R.drawable.kangzhong);
		map.put("mark", "3.5");
		listItem.add(map);

		map = new HashMap<String, Object>();
		map.put("resName", "农园餐厅");
		map.put("favDish", "麻辣香锅，煲汤");
		map.put("status", "宽松");
		map.put("img", R.drawable.nongyuan);
		map.put("mark", "3.0");
		listItem.add(map);
		
		map = new HashMap<String, Object>();
		map.put("resName", "艺园餐厅");
		map.put("favDish", "红烧排骨");
		map.put("status", "宽松");
		map.put("img", R.drawable.yiyuan);
		map.put("mark", "2.5");
		listItem.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("resName", "燕南餐厅");
		map.put("favDish", "麻辣香锅，炒年糕");
		map.put("status", "拥挤");
		map.put("img", R.drawable.yannan);
		map.put("mark", "2.0");
		listItem.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("resName", "康博思饺子部");
		map.put("favDish", "韭菜饺子");
		map.put("status", "宽松");
		map.put("img", R.drawable.kangjiao);
		map.put("mark", "1.5");
		listItem.add(map);
		return listItem;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView resName;
		public TextView favDish;
		public TextView status;
		public TextView mark;
		public RatingBar rb;
	}
	
	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mData.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.listrow, null);
				holder.img = (ImageView) convertView
						.findViewById(R.id.list_image);
				holder.resName = (TextView) convertView
						.findViewById(R.id.restaurant);
				holder.favDish = (TextView) convertView
						.findViewById(R.id.fav_dish);
				holder.status = (TextView) convertView
						.findViewById(R.id.status);
				holder.mark = (TextView) convertView.findViewById(R.id.rate);
				holder.rb = (RatingBar) convertView
						.findViewById(R.id.ratingBar);
				convertView.setTag(holder);

			} else
				holder = (ViewHolder) convertView.getTag();

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));
			holder.resName.setText((String) mData.get(position).get("resName"));
			holder.favDish.setText((String) mData.get(position).get("favDish"));
			holder.status.setText((String) mData.get(position).get("status"));
			holder.mark.setText((String) mData.get(position).get("mark"));
			holder.rb.setRating(Float.parseFloat((String) mData.get(position)
					.get("mark")));

			return convertView;
		}
	}
}