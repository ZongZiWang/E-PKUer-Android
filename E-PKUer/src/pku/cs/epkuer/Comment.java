package pku.cs.epkuer;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.*;

public class Comment extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        TextView tv=(TextView)this.findViewById(R.id.restaurant_name);
        tv.setText("康博斯中餐厅");
        LinearLayout ll = (LinearLayout) this.findViewById(R.id.recList);
		String[] recList = { "鸡腿饭", "肥牛饭", "土豆饼" };
		CheckBox cb=null;
		int n=1;
		for (String str : recList) {
			cb = new CheckBox(this);
			cb.setId(n++);
			cb.setText(str);
			cb.setTextColor(Color.BLACK);
			ll.addView(cb);
		}
		Button bt=(Button)this.findViewById(R.id.submit);
		bt.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			}
			
		});
				
    }

}
