package com.example.todolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ToDoContentProvider extends ContentProvider {
	private MySQLiteOpenHelper myOpenHelper;
	
	// �����ֲ�ѯ����
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.example.todolist.todocontentprovider", "todoitems", ALLROWS);
		uriMatcher.addURI("com.example.todolist.todocontentprovider", "todoitems/#", SINGLE_ROW);
	}
	
	// ����һ��URI��ַ
	public static final Uri CONTENT_URI = 
			Uri.parse("content://com.example.todolist.todocontentprovider/todoitems");

	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// �����ݿ�
		SQLiteDatabase db = this.myOpenHelper.getWritableDatabase();

		switch(uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = MySQLiteOpenHelper.KEY_ID + "=" + rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		default:
			break;
		}
		
		// ��Ҫ����ɾ������������������ƶ�һ��where�Ӿ䣬Ҫɾ�����е��У�������һ��ֵ������"1";
		if (selection == null) {
			selection = "1";
		}
		
		// ִ��ɾ��
		int deleteCount = db.delete(MySQLiteOpenHelper.DATABASE_TABLE, selection, selectionArgs);
		
		// ֪ͨ�۲��ߣ����ݼ��Ѿ��ı���
		this.getContext().getContentResolver().notifyChange(uri, null);
		
		return deleteCount;
	}

	@Override
	public String getType(Uri uri) {
		// Ϊһ��uri����MIME����
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.example.todos";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.example.todos";
		default:
			throw new IllegalArgumentException("Unsupported URI:" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// �����ݿ�
		SQLiteDatabase db = this.myOpenHelper.getWritableDatabase();
		
		String nullColumnHack = null;
		long id = db.insert(MySQLiteOpenHelper.DATABASE_TABLE, nullColumnHack, values);
		if (id > -1) {
			// ���췵�ص�uri
			Uri insertId = ContentUris.withAppendedId(this.CONTENT_URI, id);
			
			// ֪ͨ�۲���
			this.getContext().getContentResolver().notifyChange(insertId, null);
			return insertId;
		} else {
			return null;
		}
	}

	@Override
	public boolean onCreate() {
		// ����ײ�����ݿ�
		this.myOpenHelper = new MySQLiteOpenHelper(getContext(), MySQLiteOpenHelper.DATABASE_NAME, null, 
				MySQLiteOpenHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// �����ݿ�
		SQLiteDatabase db = this.myOpenHelper.getWritableDatabase();
		
		String groupBy = null;
		String having = null;
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);
		
		switch(uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(MySQLiteOpenHelper.KEY_ID + "=" + rowID);
		default:
			break;
		}
		
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
		
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// �����ݿ�
		SQLiteDatabase db = this.myOpenHelper.getWritableDatabase();
		
		switch(uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = MySQLiteOpenHelper.KEY_ID + "=" + rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		default:
			break;
		}
		
		int updateCount = db.update(MySQLiteOpenHelper.DATABASE_TABLE, values, selection, selectionArgs);
		
		this.getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}

}
