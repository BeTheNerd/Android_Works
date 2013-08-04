package com.yejianfeng.switchhost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyHostActivity extends Activity {
	private String curHostName;
	private HostFileOperator operator;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyhost);
		
		Intent curIntent = getIntent();
		this.curHostName = curIntent.getStringExtra("curHostName");
		
		TextView hostName = (TextView) findViewById(R.id.curHostName);
		hostName.setText(this.curHostName);
		
		this.operator = new HostFileOperator(this.getApplicationContext());
		
		String Content = this.operator.getHostContent(this.curHostName);
		EditText hostContent = (EditText) findViewById(R.id.modifyHostContent);
		
		hostContent.setText(Content);
	}
	
	// ���水ť�����Ĳ���
	public void modifyHost(View v)
	{
		TextView hostContent = (TextView) findViewById(R.id.modifyHostContent);
		
		operator.ModifyHost(this.curHostName, hostContent.getText().toString());
		
		// ��ʾ����ɹ�
		AlertDialog.Builder builder = new Builder(this);
		new Builder(this).setTitle("��ʾ").setMessage("����ɹ�").show();
				
		// ���ص�SwithHostActivity
		Intent intent = new Intent();
		intent.setClass(this, SwitchHostActivity.class);
		startActivity(intent);
	}
}
