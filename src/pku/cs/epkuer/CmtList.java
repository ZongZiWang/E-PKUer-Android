package pku.cs.epkuer;

import java.util.*;

import pku.cs.epkuer.util.MySimpleAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint({ "HandlerLeak", "ShowToast" })
public class CmtList extends Activity implements OnScrollListener {

	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem;
	MySimpleAdapter listItemAdapter;
	private int lastItem;
	private int count;
	private View moreView; // ���ظ���ҳ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText("����˹�в���");

		list = (ListView) findViewById(R.id.commentList);
		moreView = getLayoutInflater().inflate(R.layout.load, null);
		// ���ɶ�̬���飬��������
		listItem = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Username " + i);
			map.put("ItemText", "���ȷ��ó�");
			map.put("ItemMark", "4.5");
			listItem.add(map);
		}
		count = listItem.size();
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		//ListView lv = (ListView) findViewById(R.id.commentList);
		//MyAdapter adapter = new MyAdapter(this);

		listItemAdapter = new MySimpleAdapter(this, listItem,// ����Դ
				R.layout.comment_item,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemTitle", "ItemText","ItemMark" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemTitle, R.id.ItemText,R.id.ItemRating });
		list.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		// ��Ӳ�����ʾ
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				//setTitle("�����" + arg2 + "����Ŀ" + arg3);
			}
		});
		list.setOnScrollListener(this); // ����listview�Ĺ����¼�
	}

	private void loadMoreData() { // ���ظ�������
		count = listItemAdapter.getCount();
		for (int i = count; i < count + 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Username " + i);
			map.put("ItemText", "���ȷ��ó�");
			map.put("ItemMark", "4.5");
			listItem.add(map);
		}
		count = listItem.size();
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount - 1; // ��1����Ϊ������˸�addFooterView

	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// �����������ǣ������һ��item�����������ݵ�����ʱ�����и���
		if (lastItem == count && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			moreView.setVisibility(View.VISIBLE);
			mHandler.sendEmptyMessage(0);
		}

	}

	// ����Handler
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				loadMoreData(); // ���ظ������ݣ��������ʹ���첽����
				listItemAdapter.notifyDataSetChanged();
				moreView.setVisibility(View.GONE);

				if (count > 30) {
					Toast.makeText(CmtList.this, "ľ�и������ݣ�", 3000)
							.show();
					list.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				}
				break;
			default:
				break;
			}
		};
	};
	
}
