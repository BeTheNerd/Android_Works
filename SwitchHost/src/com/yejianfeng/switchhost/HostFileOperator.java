package com.yejianfeng.switchhost;

import java.io.FileOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class HostFileOperator {
	private Context context;
	
	public HostFileOperator(Context context) 
	{
		this.context = context;
	}
	
	// ��ȡ����host
	public String[] GetHostsName()
	{
		return this.context.fileList();
	}
	
	// ����һ��host
	public boolean AddHost(String hostName, String hostContent)
	{
		FileOutputStream outputStream;
		
		try	{
			outputStream = this.context.openFileOutput(hostName, Context.MODE_PRIVATE);
			outputStream.write(hostContent.getBytes());
			outputStream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// �ж��Ƿ����host
	public boolean isExistHost(String hostName)
	{
		String[] hosts = this.GetHostsName();
		for	(int i=0; i<hosts.length; i++) {
			if (hosts[i] == hostName) {
				return true;
			}
		}
		return false;
	}
	
	// ɾ��һ��host
	public boolean DeleteHost(String hostName)
	{
		return this.context.deleteFile(hostName);
	}
	
	// �޸�һ��host
	public boolean ModifyHost(String hostName, String hostContent)
	{
		this.DeleteHost(hostName);
		return this.AddHost(hostName, hostContent);
	}
	
	// TODO: ����hostNameΪ��ǰ��Host
	public boolean setActivityHost(String hostName)
	{
		return true;
	}
	
	// TODO: ��ȡ��ǰHost������
	public String getActivityHost()
	{
		return null;
	}
}
