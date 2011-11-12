package jp.co.jsol.chokoapp.test;

import jp.co.jsol.chokoapp.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super("jp.co.jsol.chokoapp", MainActivity.class);
	}

	public void testShowHelloMessage() {
		TextView v = (TextView)getActivity().findViewById(jp.co.jsol.chokoapp.R.id.helloView);
		assertTrue(v.getText().toString().startsWith("Hello World"));
	}
}
