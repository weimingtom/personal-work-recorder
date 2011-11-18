package chokoapp.imanani.test;

import chokoapp.imanani.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super("chokoapp.imanani", MainActivity.class);
	}

	public void testShowHelloMessage() {
		TextView v = (TextView)getActivity().findViewById(chokoapp.imanani.R.id.dailyButton);
		assertEquals("Daily", v.getText());
	}
}
