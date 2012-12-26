package pku.cs.epkuer;

import java.util.*;
import pku.cs.epkuer.api.API;
import pku.cs.epkuer.util.ResListItem;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;

public class ResList extends TabActivity {
	private TabHost myTabhost;
	protected int myTag = 2;
	private ArrayList<HashMap<String, Object>> mData;
//	private HashMap<Integer,Integer> images=new HashMap<Integer,Integer>();

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
		try {
			initList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取填充ListView
	private void initList() throws Exception {
//		images.put(2, R.drawable.nongyuan);
//		images.put(3, R.drawable.xuewu);
//		images.put(4, R.drawable.xueyi);
		
		mData = getData();
		ListView lv = (ListView) findViewById(R.id.listView3);
		MyAdapter adapter = new MyAdapter(this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Toast.makeText(getApplicationContext(),
						(String) mData.get(position).get("resName"),
						Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), ResInfo.class);
				Bundle bundle=new Bundle();
				bundle.putInt("id", (Integer) mData.get(position).get("id"));
				bundle.putInt("img", (Integer) mData.get(position).get("img"));
				intent.putExtras(bundle);
				startActivity(intent);	
			}
		});
	}

	// 获取填充数据
	private ArrayList<HashMap<String, Object>> getData() throws Exception {

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		ArrayList<ResListItem> array=new API().getResList(2);
		HashMap<String, Object> map = null;
		for (int i = 0; i < array.size(); i++) {
						ResListItem item = array.get(i);
						map = new HashMap<String, Object>();
						map.put("id", item.id);
						map.put("resName", item.name);
						map.put("favDish", item.recommendations);
						map.put("status", item.busy);
						map.put("img", item.img);
						map.put("mark", ((Float)item.evaluation).toString());
						listItem.add(map);
		}
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