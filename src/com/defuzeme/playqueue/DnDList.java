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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

public class DnDList extends ListView {
	
	boolean			mDragMode			= false;

	Integer 		mStartPosition		= 0;
	Integer 		mMovePosition		= 0;
	Integer	 		mEndPosition		= 0;
	Integer			mPos				= 176;
	Integer 		mDragPointOffset	= 0;
	Integer			mItemPosition		= 0;
	
	ImageView 		mDragView			= null;
	GestureDetector mGestureDetector	= null;
	
	Drop			mDropListener		= null;
	Remove			mRemoveListener		= null;
	Drag			mDragListener		= null;
	
	public DnDList(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setDropListener(Drop l) {
		mDropListener = l;
	}

	public void setRemoveListener(Remove l) {
		mRemoveListener = l;
	}
	
	public void setDragListener(Drag l) {
		mDragListener = l;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final int x = (int) ev.getX();
		final int y = (int) ev.getY();
		
		if (action == MotionEvent.ACTION_DOWN && x > (this.getWidth() - 60) )
			mDragMode = true;

		if (!mDragMode)
			return super.onTouchEvent(ev);

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mStartPosition			= pointToPosition(x,y);
				
				if (mStartPosition != INVALID_POSITION) {
					mItemPosition		= mStartPosition - getFirstVisiblePosition();
                    mDragPointOffset	= y - getChildAt(mItemPosition).getTop();
                    mDragPointOffset	-= ((int)ev.getRawY()) - y;
					startDrag(mItemPosition,y);
					drag(50,y);
				} else
					mDragMode			= false;
				break;
			case MotionEvent.ACTION_MOVE:		
				drag(50, y);
				
				final int mCurrentPosition	= pointToPosition(x,y);
				
				if ((y < 100) && (mCurrentPosition >= 0)) {
					this.smoothScrollToPosition(mCurrentPosition - 1);
					mDragListener.onStopDrag(getChildAt(mItemPosition));
				} else if (((y - getHeight()) < 100) && ((mCurrentPosition > 0) && (mCurrentPosition < this.getCount()))) {
					this.smoothScrollToPosition(mCurrentPosition + 1);
					mDragListener.onStopDrag(getChildAt(mItemPosition));
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
			default:
				mDragMode				= false;
				mEndPosition			= pointToPosition(x,y);
				stopDrag(mStartPosition - getFirstVisiblePosition());
				
				if (mDropListener != null && mStartPosition != INVALID_POSITION && mEndPosition != INVALID_POSITION)
	        		 mDropListener.onDrop(mStartPosition, mEndPosition);
				break;
		}
		
		return true;
	}	

	private void drag(int x, int y) {	
		if (mDragView != null) {
			final WindowManager.LayoutParams layoutParams	= (WindowManager.LayoutParams)mDragView.getLayoutParams();
			layoutParams.x									= x;
			layoutParams.y									= y - mDragPointOffset;
	        final WindowManager mWindowManager				= (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
	        
	        mWindowManager.updateViewLayout(mDragView, layoutParams);
	        
	        if (mDragListener != null)
				mDragListener.onDrag(x, y, this);
		}
	}

	private void startDrag(int itemIndex, int y) {
		stopDrag(itemIndex);

		final View item = getChildAt(itemIndex);
		
		if (item == null)
			return;
		item.setDrawingCacheEnabled(true);
		
		if (mDragListener != null)
			mDragListener.onStartDrag(item);
		
        final Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
        final WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
        
        mWindowParams.gravity				= Gravity.TOP;
        mWindowParams.x						= 0;
        mWindowParams.y						= y - mDragPointOffset;

        mWindowParams.height				= WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.width					= WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags					= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
							                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
							                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
							                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.windowAnimations = 0;
        
        final Context context				= getContext();
        final ImageView v					= new ImageView(context);
        v.setImageBitmap(bitmap);      

        final WindowManager mWindowManager	= (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(v, mWindowParams);
        mDragView							= v;
	}

	private void stopDrag(int itemIndex) {
		if (mDragView != null) {
			if (mDragListener != null)
				mDragListener.onStopDrag(getChildAt(itemIndex));

			mDragView.setVisibility(GONE);
			final WindowManager wm	= (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
			wm.removeView(mDragView);
			mDragView.setImageDrawable(null);
			mDragView			= null;
        }
	}

}
