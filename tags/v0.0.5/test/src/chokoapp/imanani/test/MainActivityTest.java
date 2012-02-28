package chokoapp.imanani.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import chokoapp.imanani.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Calendar today;
	
	public MainActivityTest() {
		super("chokoapp.imanani", MainActivity.class);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		today = Calendar.getInstance();
	}


	public void testDisplayDateOnTheTitleBar() {
		TextView v = (TextView)getActivity().
				findViewById(chokoapp.imanani.R.id.todayView);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd (E)");
		assertEquals(df.format(today.getTime()), v.getText());
	}
}
