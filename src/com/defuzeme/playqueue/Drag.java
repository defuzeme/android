package com.defuzeme.playqueue;

import android.view.View;
import android.widget.ListView;

/**
 * @author François Gaillard
 */
public interface Drag
{
	void onStartDrag(View itemView);
	void onDrag(int x, int y, ListView listView);
	void onStopDrag(View itemView);
}
