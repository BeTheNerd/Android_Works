package com.example.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private MediaRecorder mRecorder =null;
	private MediaPlayer   mPlayer = null;
	
	private static final String LOG_TAG = "AudioRecordTest";
	private String RAW_RECORD_FILE = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		this.RAW_RECORD_FILE = this.getApplicationContext().getFilesDir() + "/" + "record";
		return true;
	}

	public void onRecord(View v) {
		// ��ʼ��¼��ʱ��Ƶ�ļ�
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(this.RAW_RECORD_FILE);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		try {
			mRecorder.prepare();
		} catch(IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
		
		mRecorder.start();
	}
	
	public void onStop(View v) {
		// ������ʱ�ļ�
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		// ������¼�ɹ�����ʾ
		new Builder(this).setTitle("��ʾ").setMessage("����ɹ�").show();
	}
	
	public void onChange(View v) {
		// TODO:
		// ִ����Ƶ�޸�
		SoundPool sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		int StreamId = sp.load(this.RAW_RECORD_FILE, 1);
		sp.play(StreamId, 1, 1, 1, 0, (float) 0.5);
		
		// ����Ϊ��ʱ�ļ�2
	}
	
	public void onPlay(View v) {
		// TODO:
		// ������ʱ�ļ�2
	}
	
	public void onPlayRaw(View v) {
		// TODO:
		// ����ԭ������Ƶ�ļ�
		mPlayer = new MediaPlayer();
		try	{
			File file = new File(this.RAW_RECORD_FILE);
			FileInputStream fis = new FileInputStream(file);
			mPlayer.setDataSource(fis.getFD());
			mPlayer.prepare();
			mPlayer.start();
		} catch(IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}
}
