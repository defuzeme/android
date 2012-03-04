/*******************************************************************************
**  defuze.me - modern radio automation software suite
**  
**  Copyright © 2012
**    Athena Calmettes - Jocelyn De La Rosa - Francois Gaillard
**    Adrien Jarthon - Alexandre Moore - Luc Peres - Arnaud Sellier
**
**  website: http://defuze.me
**  contact: team@defuze.me
**
**  This program is free software: you can redistribute it and/or modify it
**  under the terms of the GNU Lesser General Public License as published by
**  the Free Software Foundation, either version 3 of the License, or
**  (at your option) any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU Lesser General Public License for more details.
**
**  You should have received a copy of the GNU Lesser General Public License
**  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*******************************************************************************/

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
