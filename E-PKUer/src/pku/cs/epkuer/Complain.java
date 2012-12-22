package pku.cs.epkuer;

import android.os.Bundle;
import android.widget.*;
import android.app.Activity;

public class Complain extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain);
        TextView tv = (TextView) this.findViewById(R.id.restaurant_name);
		tv.setText("康博斯中餐馆");
    }

}
