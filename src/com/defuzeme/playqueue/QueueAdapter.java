/*
 * Copyright (C) 2010 Eric Harlow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.defuzeme.playqueue;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R;
import com.defuzeme.communication.StreamObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public final class QueueAdapter extends BaseAdapter implements Remove, Drop
{
	private int[]						mIds		= null;
    private int[]						mLayouts	= null;
    
    private ArrayAdapter<StreamObject>	mItem		= null;
    private LayoutInflater				mInflater	= null;
    
    public QueueAdapter(Context context, int[] itemLayouts, int[] itemIDs, ArrayAdapter<StreamObject> item) {
    	init(context, itemLayouts, itemIDs, item);
    }
    
    private void init(Context context, int[] layouts, int[] ids, ArrayAdapter<StreamObject> item) {
    	this.mIds		= ids;
    	this.mItem		= item;
    	this.mLayouts	= layouts;
    	this.mInflater	= LayoutInflater.from(context);
    }

    public int getCount() {
        return mItem.getCount();
    }

    public StreamObject getItem(int position) {
        return mItem.getItem(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	final ViewHolder itemHolder;
    	
        if (convertView == null) {
        	itemHolder			= new ViewHolder();
            convertView			= mInflater.inflate(mLayouts[0], null);
            
            itemHolder.item		= (TextView) convertView.findViewById(mIds[0]);
            itemHolder.itemInfo	= (TextView) convertView.findViewById(mIds[1]);
            
            convertView.setTag(itemHolder);
        }
        else
        	itemHolder			= (ViewHolder) convertView.getTag();
        
        if ((mItem.getItem(position).data.content.title != null)) {
        	itemHolder.item.setText(mItem.getItem(position).data.content.title + " - " + mItem.getItem(position).data.content.artist);
        	itemHolder.itemInfo.setText(PlayQueue.Tools.secondsToTimeString(mItem.getItem(position).data.content.duration));
        } else {
        	itemHolder.item.setText(DefuzeMe._mainActivity.getString(R.string.QueueBreak));
    		itemHolder.itemInfo.setText("");
    	}
        
        return convertView;
    }

    static class ViewHolder {
        TextView item		= null;
        TextView itemInfo	= null;
    }

	public void onDrop(int from, int to) {	
		final StreamObject temp = mItem.getItem(from);
		
		mItem.remove(mItem.getItem(from));
		mItem.insert(temp, to);
	}

	public void onRemove(int which) {
		if (which < 0 || which > mItem.getCount())
			return;
		
		mItem.remove(mItem.getItem(which));
	}
	
}