package com.kishu.mousegrabberapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText editText;
	MouseView mouseView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.editText);
		mouseView = (MouseView) findViewById(R.id.mouseView);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void buttonClicked(View w)
	{
		
		String ip = editText.getText().toString();
		editText.setText("done");
		mouseView.setupIpaddress(ip);
				
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mouseView.close();
	}
	
	
			

}
