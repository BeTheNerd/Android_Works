package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	// ���������
	public static final String KEY_ID = "_id";
	public static final String KEY_TASK = "task";
	public static final String KEY_CREATION_DATE = "creation_date";
		
	// �������ݿ�ͱ�
	public static final String DATABASE_NAME = "todoDatabase.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_TABLE = "todoItemTable";
	
	// ���������ݿ��SQL���
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE 
			+ "(" + KEY_ID + " interget primary key autoincrement, " 
			+ KEY_TASK + " text not null, "
			+ KEY_CREATION_DATE + " long);";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("TaskDBAdapter", "Upgrading from versino " 
				+ oldVersion + " to " + newVersion
				+ " which will destory all old data");
		// ɾ���ɱ������±�
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		this.onCreate(db);
	}

}
