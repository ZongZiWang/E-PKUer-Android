package pku.cs.epkuer;

import java.util.*;

import pku.cs.epkuer.api.API;
import pku.cs.epkuer.util.ResListItem;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;

public class ResList extends TabActivity {
	private TabHost myTabhost;
	protected int myTag = 2;
	private ArrayList<HashMap<String, Object>> mData1, mData2, mData3;
	private HashMap<String, Integer> images = new HashMap<String, Integer>();
	private HashMap<String, Double> locations = new HashMap<String, Double>();
	LocationManager locMan;  
    Location location; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myTabhost = this.getTabHost();
		final TabWidget tabWidget = myTabhost.getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.reslist,
				myTabhost.getTabContentView(), true);
		myTabhost.setBackgroundColor(Color.GRAY);

		myTabhost.addTab(myTabhost.newTabSpec("One").setIndicator("������")
				.setContent(R.id.list_dist));

		myTabhost.addTab(myTabhost.newTabSpec("Two").setIndicator("������")
				.setContent(R.id.list_rate));

		myTabhost.addTab(myTabhost.newTabSpec("Three").setIndicator("��ӵ���̶�")
				.setContent(R.id.list_status));

		// ��ʼ��Tabѡ�����ɫ
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

		// �����tabѡ���ʱ�򣬸��ĵ�ǰ�ı�������װ�ض�Ӧ��activity
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
			e.printStackTrace();
		}
	}

	// ��ȡ���ListView
	private void initList() throws Exception {
		images.put("ѧ��ʳ��", R.drawable.xuewu);
		images.put("ѧһʳ��", R.drawable.xueyi);
		images.put("��԰����", R.drawable.jiayuan);
		images.put("��԰", R.drawable.yiyuan);
		images.put("ũ԰", R.drawable.nongyuan);
		images.put("���ֿ��", R.drawable.songlin);
		images.put("������ʳ", R.drawable.yannan);
		images.put("�����׷�", R.drawable.guilin);
		images.put("����˼�в���", R.drawable.kangzhong);
		images.put("����˼������", R.drawable.kangxi);
		images.put("����˼���Ӳ�", R.drawable.kangjiao);
		images.put("����˼��ʳ��", R.drawable.mianshi);
		
		locations.put("ѧ��ʳ�þ���", 116.313351);locations.put("ѧ��ʳ��γ��", 39.994971);
		locations.put("ѧһʳ�þ���", 116.314321);locations.put("ѧһʳ��γ��", 39.993532);
		locations.put("��԰��������", 116.313994);locations.put("��԰γ��", 39.994025);
		locations.put("��԰����", 116.313414);locations.put("��԰γ��", 39.994356);	
		locations.put("ũ԰����", 116.318746);locations.put("ũ԰γ��", 39.99617);	
		locations.put("���ֿ�;���", 116.314833);locations.put("���ֿ��γ��", 39.99376);		
		locations.put("������ʳ����", 116.316774);locations.put("������ʳγ��", 39.996571);		
		locations.put("�����׷۾���", 116.314371);locations.put("�����׷�γ��", 39.994201);		
		locations.put("����˼�в�������", 116.314793);locations.put("����˼�в���γ��", 39.994194);		
		locations.put("����˼����������", 116.314784);locations.put("����˼������γ��", 39.994329);		
		locations.put("����˼���Ӳ�����", 116.314789);locations.put("����˼���Ӳ�γ��", 39.994422);
		locations.put("����˼��ʳ������", 116.314699);locations.put("����˼��ʳ��γ��", 39.994532);
		
		mData1 = getData(1);
		ListView lv1 = (ListView) findViewById(R.id.listView2);
		MyAdapter1 adapter1 = new MyAdapter1(this);
		lv1.setAdapter(adapter1);
		lv1.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ResInfo.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", (Integer) mData1.get(position).get("id"));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		mData2 = getData(2);
		ListView lv2 = (ListView) findViewById(R.id.listView3);
		MyAdapter2 adapter2 = new MyAdapter2(this);
		lv2.setAdapter(adapter2);
		lv2.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ResInfo.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", (Integer) mData2.get(position).get("id"));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		mData3 = getData(3);
		ListView lv3 = (ListView) findViewById(R.id.listView4);
		MyAdapter3 adapter3 = new MyAdapter3(this);
		lv3.setAdapter(adapter3);
		lv3.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ResInfo.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", (Integer) mData3.get(position).get("id"));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	// ��ȡ�������
	private ArrayList<HashMap<String, Object>> getData(int order)
			throws Exception {

		
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		ArrayList<ResListItem> array = API.getResList(order);
		if (order == 1) {
			locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
			if(location == null)
				location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		     
	        if(location!=null) {
	        	Collections.sort(array, new Comparator<ResListItem> () {
					public int compare(ResListItem r1, ResListItem r2) {
						int abs_lon1 = (int)((locations.get(r1.name+"����")-location.getLongitude())*1000000);
						int abs_lat1 = (int)((locations.get(r1.name+"γ��")-location.getLatitude())*1000000);
						int dist1 = abs_lon1*abs_lon1+abs_lat1*abs_lat1;
						
						int abs_lon2 = (int)((locations.get(r2.name+"����")-location.getLongitude())*1000000);
						int abs_lat2 = (int)((locations.get(r2.name+"γ��")-location.getLatitude())*1000000);
						int dist2 = abs_lon2*abs_lon2+abs_lat2*abs_lat2;
						
						if (dist1>dist2)
							return 1;
						else
							return -1;
					}
				});
	        }
	        else Toast.makeText(this, "��λʧ�ܣ�", Toast.LENGTH_SHORT).show();
		}
		HashMap<String, Object> map = null;
		for (int i = 0; i < array.size(); i++) {
			ResListItem item = array.get(i);
			map = new HashMap<String, Object>();
			map.put("id", item.id);
			map.put("resName", item.name);
			map.put("favDish", item.recommendations);
			map.put("status", item.busy);
			map.put("img", images.get(item.name));
			map.put("mark", ((Float) item.evaluation).toString());
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

	public class MyAdapter1 extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter1(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mData1.size();
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

			holder.img.setBackgroundResource((Integer) mData1.get(position)
					.get("img"));
			holder.resName
					.setText((String) mData1.get(position).get("resName"));
			holder.favDish
					.setText((String) mData1.get(position).get("favDish"));
			holder.status.setText((String) mData1.get(position).get("status"));
			holder.mark.setText((String) mData1.get(position).get("mark"));
			holder.rb.setRating(Float.parseFloat((String) mData1.get(position)
					.get("mark")));
			return convertView;
		}
	}

	public class MyAdapter2 extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter2(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mData2.size();
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

			holder.img.setBackgroundResource((Integer) mData2.get(position)
					.get("img"));
			holder.resName
					.setText((String) mData2.get(position).get("resName"));
			holder.favDish
					.setText((String) mData2.get(position).get("favDish"));
			holder.status.setText((String) mData2.get(position).get("status"));
			holder.mark.setText((String) mData2.get(position).get("mark"));
			holder.rb.setRating(Float.parseFloat((String) mData2.get(position)
					.get("mark")));
			return convertView;
		}
	}

	public class MyAdapter3 extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter3(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mData3.size();
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

			holder.img.setBackgroundResource((Integer) mData3.get(position)
					.get("img"));
			holder.resName
					.setText((String) mData3.get(position).get("resName"));
			holder.favDish
					.setText((String) mData3.get(position).get("favDish"));
			holder.status.setText((String) mData3.get(position).get("status"));
			holder.mark.setText((String) mData3.get(position).get("mark"));
			holder.rb.setRating(Float.parseFloat((String) mData3.get(position)
					.get("mark")));
			return convertView;
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST + 1, 1, "ע��");
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