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
