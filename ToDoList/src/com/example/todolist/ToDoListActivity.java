package com.example.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.app.LoaderManager;
import com.example.todolist.ToDoItem;

public class ToDoListActivity extends Activity
	implements NewItemFragment.OnNewItemAddedListener, 
	LoaderManager.LoaderCallbacks<Cursor>{
	
	//��ö�UIС���������
    private ArrayList<ToDoItem> todoItems;
    private ToDoItemAdapter aa;
	
    @Override
    // onCreate�Ǵ������activity��ʱ�����õ�
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ��ȡ��Fragment������
        FragmentManager fm = getFragmentManager();
        ToDoListFragment todoListFragment = (ToDoListFragment)fm.findFragmentById(R.id.TodoListFragment);
        
        todoItems = new ArrayList<ToDoItem>();
        
        // TODO: ������Ҫ�����ݿ��д洢�Ķ�������ȡ����
        int resID = R.layout.todolist_item;
        
        aa = new ToDoItemAdapter(this, resID, todoItems);
        
        todoListFragment.setListAdapter(aa);
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().enableDebugLogging(true);
    }

    @Override
    // onResume����ͣ�Ժ������������Activityʱ�����
    protected void onResume() {
      super.onResume();
      getLoaderManager().restartLoader(0, null, this);
    }
    
    @Override
	public void onNewItemAdded(String newItem) {
    	ContentResolver cr = getContentResolver();
    
    	ContentValues values = new ContentValues();
    	values.put(ToDoContentProvider.KEY_TASK, newItem);
    	
    	cr.insert(ToDoContentProvider.CONTENT_URI, values);
    	getLoaderManager().restartLoader(0, null, this);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.manu_reference:
    		Intent intent = new Intent();
    		intent.setClass(this, ReferenceActivity.class);
    		startActivity(intent);
    	}
    	return true;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, ToDoContentProvider.CONTENT_URI,
				null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// ��loader��ѯ��ɵ�ʱ��Cursor�᷵�ص�onLoadFinished�������
		int keyTaskIndex = cursor.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);
		
		todoItems.clear();
		while (cursor.moveToNext()) {
			ToDoItem newItem = new ToDoItem(cursor.getString(keyTaskIndex));
			todoItems.add(newItem);
		}
		aa.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
