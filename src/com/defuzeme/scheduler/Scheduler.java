package com.defuzeme.scheduler;

import com.defuzeme.R;
import com.defuzeme.R.array;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Scheduler extends TabActivity {
	
	private Resources	_resources	= null;
	
	private	int			_tabHeight	= 60;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.scheduler);
		
	    final Intent intent			= new Intent().setClass(this, SchedulerDay.class);
	    final TabHost tabHost 		= getTabHost();  // The activity TabHost
		this._resources				= getResources();
		
	    final String[]	weekDays	= this._resources.getStringArray(array.WeekDays);
	    TabHost.TabSpec spec;
	    
	    for (int index = 0; index < weekDays.length; index += 1) {
		    spec = tabHost.newTabSpec(weekDays[index].substring(0, 3)).setIndicator(weekDays[index].substring(0, 3)).setContent(intent);
		    tabHost.addTab(spec);
		    tabHost.getTabWidget().getChildAt(index).getLayoutParams().height = this._tabHeight;
	    }

	    tabHost.setCurrentTab(0);
	}

}
