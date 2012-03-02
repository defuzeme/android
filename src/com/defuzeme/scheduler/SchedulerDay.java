package com.defuzeme.scheduler;

import com.defuzeme.R.array;
import com.defuzeme.R.drawable;
import com.defuzeme.R.id;
import com.defuzeme.R.layout;
import android.app.Activity;
import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("unused")
public class SchedulerDay extends Activity {

	private Resources	_resources		= null;
	
	private TabActivity	_tabActivity	= null;
	private TabHost		_tabHost		= null;
	
	private	int			_currentTab		= 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(layout.scheduler_day);
		
		this._resources			= getResources();
		this._tabActivity		= (TabActivity) this.getParent();
		this._tabHost			= this._tabActivity.getTabHost();
		
		this._currentTab		= this._tabHost.getCurrentTab();
		
		final String[] weekDays	= this._resources.getStringArray(array.WeekDays);
		
		/*
		for (int index = 0; index < 10; index += 1) {
			this.addRow();
		}
		*/
	}
	
	public void addRow() {
		final TableRow	tr		= new TableRow(this);
		final TextView	tTime	= new TextView(this);
		final TextView	tDesc	= new TextView(this);
		
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		tTime.setText("Time");
		tTime.setTextColor(0xFFFFFFFF);
		tTime.setId(id.scheduler_time);
		
		final LinearLayout.LayoutParams	tTimeParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tTimeParams.setMargins(10, 0, 0, 0);
		tTime.setLayoutParams(tTimeParams);

		tDesc.setText("Description");
		tDesc.setTextColor(0xFFFFFFFF);
		tDesc.setId(id.scheduler_description);
		tDesc.setBackgroundResource(drawable.scheduler_task);

		tr.addView(tTime);
		tr.addView(tDesc);
		
		((TableLayout) findViewById(id.schedulerLayout)).addView(tr,new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	}

}
