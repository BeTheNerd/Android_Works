package com.yejianfeng.switchhost;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

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
			if (hosts[i].equals(hostName)) {
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
	
	public String getHostContent(String hostName)
	{
		// ��ȡ��ǰ�ļ�
		if (this.isExistHost(hostName) == false) {
			return null;
		}
		String hostPath = context.getFilesDir() + "/" + hostName;
		String hostContent = this.getFileContent(hostPath);
		
		return hostContent;
	}
	
	// ��ȡ��ǰHost������
	public String getActivityHost()
	{
		return this.getFileContent("/etc/hosts");
	}
	
	public boolean setActivityHost(String hostName, Process process)
	{
		if (this.isExistHost(hostName) == false) {
			return false;
		}
				
	    DataOutputStream os = null;
	    try {
	    	String hostPath = context.getFilesDir() + "/" + hostName;
	        String cmd="/system/xbin/cp -f " + hostPath + " " + "/etc/hosts";
	        //process = Runtime.getRuntime().exec("su"); //�л���root�ʺ�
	        os = new DataOutputStream(process.getOutputStream());
	        os.writeBytes(cmd + "\n");
	        os.writeBytes("exit\n");
	        os.flush();
	        process.waitFor();
	    } catch (Exception e) {
	        return false;
	    } finally {
	        try {
	            if (os != null) {
	                os.close();
	            }
	            //process.destroy();
	        } catch (Exception e) {
	        }
	    }
	    return true;
	}
	
	public String getFileContent(String hostFile)
	{
		try {
			FileInputStream fin = new FileInputStream(hostFile);
			int length = fin.available();
			
			byte [] buffer = new byte[length];   
	        fin.read(buffer);
			
	        String res = EncodingUtils.getString(buffer, "UTF-8");   
	        fin.close();
	        return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setFileContent(String file, String content)
	{
		try {
			Runtime.getRuntime().exec("su");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			byte[] bytes = content.getBytes();
			
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
