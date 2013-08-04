package com.yejianfeng.switchhost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddHostActivity extends Activity {
	private HostFileOperator operator;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addhost);
		
		this.operator = new HostFileOperator(this.getApplicationContext());
	}
	
	// ������水ť��������
	public void addHost(View v)
	{
		// ��ȡhost����
		EditText hostNameText = (EditText) findViewById(R.id.hostName);
		String hostName = hostNameText.getText().toString();
		
		// TODO�� ���������Ƿ��ظ�
		
		// ��ȡhost����
		EditText hostContentText = (EditText) findViewById(R.id.hostContent);
		String hostContent = hostContentText.getText().toString();
		
		// ����host�ļ�
		this.operator.AddHost(hostName, hostContent);
		Log.v("Add Host Save", hostName);
		
		// ��ʾ����ɹ�
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("����ɹ�");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		// ���ص�SwithHostActivity
		Intent intent = new Intent();
		intent.setClass(this, SwitchHostActivity.class);
		startActivity(intent);
	}
}
