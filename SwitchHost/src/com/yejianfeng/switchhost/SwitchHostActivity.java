package com.yejianfeng.switchhost;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SwitchHostActivity extends Activity {

	// ��������host
	private ArrayList<String> hosts;
	private ArrayAdapter<String> arrayAdapter;
	private HostFileOperator operator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ���ֳ�ʼ������
		ListView hostListView = (ListView) findViewById(R.id.HostListView);
		String[] hostNames = this.operator.GetHostsName();
		
		hosts = new ArrayList<String>();
		for (int i=0; i<hostNames.length; i++) {
			hosts.add(hostNames[i]);
		}
		
		// ��hosts�󶨵�listView
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.hostitem, R.id.hostName, hosts);
		hostListView.setAdapter(arrayAdapter);
		
		registerForContextMenu(hostListView); 
	}
	
	public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.host_menu, menu);
	}
	
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()) {
		case R.id.set:
			// TODO: ����Ϊ��ǰ��host
			return true;
		case R.id.detail:
			Intent intent = new Intent();
    		intent.setClass(this, ModifyHostActivity.class);
    		startActivity(intent);
			return true;
		case R.id.delete:
			String hostName = item.getActionView().getContext().toString();
			this.operator.DeleteHost(hostName);
			// TODO: �����Ի�����ʾɾ���ɹ�
			// TODO: ����ˢ�µ�ǰҳ��
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_preference, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.add:
    		Intent intent = new Intent();
    		intent.setClass(this, AddHostActivity.class);
    		startActivity(intent);
    		return true;
    	case R.id.quit:
    		// TODO: �˳�����
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    }

}
