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
		initList();
	}

	// ��ȡ���ListView
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

	// ��ȡ�������
	private ArrayList<HashMap<String, Object>> getData() {

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("resName", "ѧ��ʳ��");
		map.put("favDish", "������������Ȼ����");
		map.put("status", "ӵ��");
		map.put("img", R.drawable.xuewu);
		map.put("mark", "4.5");
		listItem.add(map);

		map = new HashMap<String, Object>();
		map.put("resName", "ѧһʳ��");
		map.put("favDish", "�����ʣ����Ӽ������˰�");
		map.put("status", "ӵ��");
		map.put("img", R.drawable.xueyi);
		map.put("mark", "4.0");
		listItem.add(map);

		map = new HashMap<String, Object>();
		map.put("resName", "����ʳ��");
		map.put("favDish", "���ȷ�����଼��ⷹ");
		map.put("status", "����");
		map.put("img", R.drawable.kangzhong);
		map.put("mark", "3.5");
		listItem.add(map);

		map = new HashMap<String, Object>();
		map.put("resName", "ũ԰����");
		map.put("favDish", "�������������");
		map.put("status", "����");
		map.put("img", R.drawable.nongyuan);
		map.put("mark", "3.0");
		listItem.add(map);
		
		map = new HashMap<String, Object>();
		map.put("resName", "��԰����");
		map.put("favDish", "�����Ź�");
		map.put("status", "����");
		map.put("img", R.drawable.yiyuan);
		map.put("mark", "2.5");
		listItem.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("resName", "���ϲ���");
		map.put("favDish", "��������������");
		map.put("status", "ӵ��");
		map.put("img", R.drawable.yannan);
		map.put("mark", "2.0");
		listItem.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("resName", "����˼���Ӳ�");
		map.put("favDish", "�²˽���");
		map.put("status", "����");
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