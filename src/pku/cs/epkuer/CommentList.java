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
	private View moreView; // 加载更多页面
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
		// 生成动态数组，加入数据
		listItem = new ArrayList<HashMap<String, Object>>();
		
		try {
			getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 生成适配器的Item和动态数组对应的元素
		//ListView lv = (ListView) findViewById(R.id.commentList);
		//MyAdapter adapter = new MyAdapter(this);

		listItemAdapter = new MySimpleAdapter(this, listItem,// 数据源
				R.layout.comment_item,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemTitle", "ItemText","ItemMark" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemTitle, R.id.ItemText,R.id.ItemRating });
		list.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		// 添加并且显示
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				//setTitle("点击第" + arg2 + "个项目" + arg3);
			}
		});
		list.setOnScrollListener(this); // 设置listview的滚动事件
	}

	private void loadMoreData() throws Exception { // 加载更多数据
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
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView

	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
		if (lastItem == count && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			moreView.setVisibility(View.VISIBLE);
			mHandler.sendEmptyMessage(0);
		}

	}

	// 声明Handler
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
				} // 加载更多数据，这里可以使用异步加载
				listItemAdapter.notifyDataSetChanged();
				moreView.setVisibility(View.GONE);

				if (cmtList.size()==0) {
					Toast.makeText(CommentList.this, "木有更多数据！", 3000)
							.show();
					list.removeFooterView(moreView); // 移除底部视图
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


