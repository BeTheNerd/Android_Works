package com.example.switchhost;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SwitchHostActivity extends Activity {

	// ��������host
	private ArrayList<String> hosts;
	private ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ���ֳ�ʼ������
		ListView hostListView = (ListView) findViewById(R.id.HostListView);
		hosts = new ArrayList<String>();
		hosts.add("res");
		
		// ��hosts�󶨵�listView
		arrayAdapter = new ArrayAdapter<String>(this, R.id.hostItem, hosts);
		hostListView.setAdapter(arrayAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.switch_host, menu);
		return true;
	}

}
