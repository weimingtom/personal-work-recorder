package chokoapp.imanani;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        displayToday();
    }
    
    private void displayToday() {
    	TextView v = (TextView)findViewById(R.id.todayView);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd (E)");
    	v.setText(df.format(Calendar.getInstance().getTime()));
    }
}