package com.yejianfeng.switchhost;

import java.util.ArrayList;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
	
	public static final String CUR_HOSTNAME = "CUR_HOSTNAME";
	public static final String DEFAULT_HOSTNAME = "Default";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ���ֳ�ʼ������
		this.operator = new HostFileOperator(this.getApplicationContext());
		ListView hostListView = (ListView) findViewById(R.id.HostListView);
		String[] hostNames = this.operator.GetHostsName();
		
		this.hosts = new ArrayList<String>();
		for (int i=0; i<hostNames.length; i++) {
			this.hosts.add(hostNames[i]);
		}
		
		// ��hosts�󶨵�listView
		this.arrayAdapter = new ArrayAdapter<String>(this, R.layout.hostitem, R.id.hostName, hosts);
		hostListView.setAdapter(this.arrayAdapter);
		
		registerForContextMenu(hostListView); 
		
		// ���û��default�ļ����򽫵�ǰhost����Ϊhost
		if (false == this.operator.isExistHost(this.DEFAULT_HOSTNAME)) {
			String curHostContent = this.operator.getActivityHost();
			this.operator.AddHost(this.DEFAULT_HOSTNAME, curHostContent);
		}
	}
	
	// ÿ��������ʱ�򶼼���һ������
	// TODO: �����ƺ����Ը���
	protected void onStart()
	{
		String[] hostNames = this.operator.GetHostsName();
		
		this.hosts = new ArrayList<String>();
		for (int i=0; i<hostNames.length; i++) {
			this.hosts.add(hostNames[i]);
		}
		this.arrayAdapter.notifyDataSetChanged();
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
		String curHostName;
		curHostName = item.getActionView().getContext().toString();
		switch(item.getItemId()) {
		case R.id.set:
			this.operator.setActivityHost(curHostName);
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
			prefs.getString(this.CUR_HOSTNAME, this.DEFAULT_HOSTNAME);
			// TODO: �����Ի�����ʾ���óɹ�
			return true;
		case R.id.detail:
			Intent intent = new Intent();
    		intent.setClass(this, ModifyHostActivity.class);
    		intent.putExtra("curHostName", curHostName);
    		startActivity(intent);
			return true;
		case R.id.delete:
			this.operator.DeleteHost(curHostName);
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
    		finish();
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    }
	
	private void SetCurHostImageView(String curHostName)
	{
		this.arrayAdapter.getViewTypeCount()
		this.arrayAdapter.getView(position, convertView, parent)
	}

}
