package pku.cs.epkuer;

import java.util.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import android.app.Activity;

public class CmtList extends Activity implements OnScrollListener {

	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem;
	MySimpleAdapter listItemAdapter;
	private int lastItem;
	private int count;
	private View moreView; // 加载更多页面

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);
		TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText("康博斯中餐厅");

		list = (ListView) findViewById(R.id.commentList);
		moreView = getLayoutInflater().inflate(R.layout.load, null);
		// 生成动态数组，加入数据
		listItem = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Username " + i);
			map.put("ItemText", "鸡腿饭好吃");
			map.put("ItemMark", "4.5");
			listItem.add(map);
		}
		count = listItem.size();
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

	private void loadMoreData() { // 加载更多数据
		count = listItemAdapter.getCount();
		for (int i = count; i < count + 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Username " + i);
			map.put("ItemText", "鸡腿饭好吃");
			map.put("ItemMark", "4.5");
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
				loadMoreData(); // 加载更多数据，这里可以使用异步加载
				listItemAdapter.notifyDataSetChanged();
				moreView.setVisibility(View.GONE);

				if (count > 30) {
					Toast.makeText(CmtList.this, "木有更多数据！", 3000)
							.show();
					list.removeFooterView(moreView); // 移除底部视图
				}
				break;
			default:
				break;
			}
		};
	};
	
}
