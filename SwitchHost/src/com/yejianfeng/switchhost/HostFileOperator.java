package com.yejianfeng.switchhost;

public class HostFileOperator {
	
	// ��ȡ����host
	public String[] GetHostsName()
	{
		return null;
	}
	
	// ����һ��host
	public boolean AddHost(String hostName, String hostContent)
	{
		return true;
	}
	
	// ɾ��һ��host
	public boolean DeleteHost(String hostName)
	{
		return true;
	}
	
	// �޸�һ��host
	public boolean ModifyHost(String hostName, String hostContent)
	{
		this.DeleteHost(hostName);
		return this.AddHost(hostName, hostContent);
	}
}
