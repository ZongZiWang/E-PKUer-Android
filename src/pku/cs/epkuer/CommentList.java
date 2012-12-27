package pku.cs.epkuer;

import java.util.*;

import pku.cs.epkuer.api.API;
import pku.cs.epkuer.util.Comment;
import pku.cs.epkuer.util.MySimpleAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

@SuppressLint({ "HandlerLeak", "ShowToast" })
public class CommentList extends Activity implements OnScrollListener {

	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem;
	MySimpleAdapter listItemAdapter;
	private int lastItem;
	private int count=0;
	private View moreView; // ���ظ���ҳ��
	private int resId;
	private ArrayList<Comment> cmtList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);
		Intent intent = getIntent();
		resId = intent.getIntExtra("res_id", 0);
		String resName = intent.getStringExtra("res_name");
		count=0;
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText(resName);
		
		list = (ListView) findViewById(R.id.commentList);
		moreView = getLayoutInflater().inflate(R.layout.load, null);
		// ���ɶ�̬���飬��������
		listItem = new ArrayList<HashMap<String, Object>>();
		
		try {
			getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	private void loadMoreData() throws Exception { // ���ظ�������
		count = listItemAdapter.getCount();
		cmtList=new API().getComment(resId,count);
		Comment cmt=null;
		for (int i = 0; i < cmtList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			cmt=cmtList.get(i);
			map.put("ItemTitle", cmt.user_name);
			map.put("ItemText", cmt.content);
			map.put("ItemMark", ((Float)cmt.evaluation).toString());
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
				try {
					loadMoreData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // ���ظ������ݣ��������ʹ���첽����
				listItemAdapter.notifyDataSetChanged();
				moreView.setVisibility(View.GONE);

				if (cmtList.size()==0) {
					Toast.makeText(CommentList.this, "ľ�и������ݣ�", 3000)
							.show();
					list.removeFooterView(moreView); // �Ƴ��ײ���ͼ
				}
				break;
			default:
				break;
			}
		};
	};
	private void getData() throws Exception{

		cmtList=new API().getComment(resId,count);
		Comment cmt=null;
		for (int i = 0; i < cmtList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			cmt=cmtList.get(i);
			Log.v("data","click");
			map.put("ItemTitle", cmt.user_name);
			map.put("ItemText", cmt.content);
			map.put("ItemMark", ((Float)cmt.evaluation).toString());
			listItem.add(map);
		}
		count = listItem.size();
	}
}


