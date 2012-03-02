package com.defuzeme.playqueue;

import java.util.ArrayList;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R.id;
import com.defuzeme.R.layout;
import com.defuzeme.R.string;
import com.defuzeme.communication.Communication;
import com.defuzeme.communication.StreamObject;

import android.R;
import android.R.drawable;
import android.app.ListActivity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * @author François Gaillard
 */

@SuppressWarnings("unused")
public class PlayQueue extends ListActivity {
	
	public			Updater						_updater	= null;
	public			QueueAdapter				_adapter	= null;
	
	public static	Gui							Gui			= null;
    public static	Tools						Tools		= null;
    public			ListView					listView	= null;
    
    public			ArrayAdapter<StreamObject>	items		= null;
    
    public static	int							itemNumber	= 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DefuzeMe._activity	= this;
        
        this.setContentView(layout.play_queue);
        
        PlayQueue.Gui		= new Gui(this);
        PlayQueue.Tools		= new Tools();
        this.listView		= getListView();
        this._updater		= new Updater();
        
        this.initialize();
        this._updater.execute(this);
    }
    
    public void initialize() {
    	this.setListeners();
    	
		items			= new ArrayAdapter<StreamObject>(this, DefuzeMe._queueTracks.size());
        
		for (int i = 1; i < DefuzeMe._queueTracks.size(); i++)
        	items.add(DefuzeMe._queueTracks.get(i));
        
		this._adapter	= new QueueAdapter(this, new int[]{layout.play_queue_item}, new int[]{id.TextView01, id.TextView02}, items);
		
        this.setListAdapter(this._adapter);
		this.listView	= (DnDList) getListView();
        
		this.setCurrentTrackInfos();
		if (DefuzeMe._playingStatus == true)
			((ImageView) findViewById(id.playPauseButton)).setImageResource(drawable.ic_media_pause);
		else
			((ImageView) findViewById(id.playPauseButton)).setImageResource(drawable.ic_media_play);

        if (this.listView instanceof DnDList) {
        	((DnDList) this.listView).setDropListener(mDropListener);
        	((DnDList) this.listView).setRemoveListener(mRemoveListener);
        	((DnDList) this.listView).setDragListener(mDragListener);
        }
        
        registerForContextMenu(this.listView);
    }
    
    private void setListeners() {
    	findViewById(id.currentSound).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showItemInfos(-1);
			}
		});
    	
    	findViewById(id.currentSoundTitle).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showItemInfos(-1);
			}
		});
    	
    	findViewById(id.currentSoundTime).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showItemInfos(-1);
			}
		});
    	
    	findViewById(id.nextButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listView.getCount() > 0) {
					Communication.Send.next();
					
					setFollowingTrackInfos();
					final View		firstChild	= listView.getChildAt(0);
					
					
			    	if (firstChild != null) {
						final AnimationSet set = new AnimationSet(true);
	
				        Animation animation = new AlphaAnimation(1.0f, 0.3f);
				        animation.setDuration(1000);
				        set.addAnimation(animation);
	
				        animation = new AlphaAnimation(1.0f, 1.0f);
				        animation.setDuration(6000);
				        set.addAnimation(animation);
				        
				        firstChild.startAnimation(set);
					}
				}
			}
		});
    	
    	findViewById(id.playPauseButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (DefuzeMe._playingStatus == true) {
					DefuzeMe._playingStatus = false;
					Communication.Send.pause();
				} else {
					DefuzeMe._playingStatus = true;
					Communication.Send.play();
				}
			}
		});
    }
    
    public void setCurrentTrackInfos() {
    	
    	if (DefuzeMe._queueTracks.size() > 0) {
    		final TextView currentTitle	= (TextView) findViewById(id.currentSoundTitle);
    		final TextView currentTime	= (TextView) findViewById(id.currentSoundTime);
    	
    		if ((DefuzeMe._queueTracks.get(0).data.content.title != null)
        		&& (DefuzeMe._queueTracks.get(0).data.content.artist != null))
        			currentTitle.setText(DefuzeMe._queueTracks.get(0).data.content.title + " - " + DefuzeMe._queueTracks.get(0).data.content.artist);
    		else if ((DefuzeMe._queueTracks.get(0).data.content.title != null)
        			&& (DefuzeMe._queueTracks.get(0).data.content.artist == null))
        			currentTitle.setText(DefuzeMe._queueTracks.get(0).data.content.title);
    		else if ((DefuzeMe._queueTracks.get(0).data.content.title == null)
        			&& (DefuzeMe._queueTracks.get(0).data.content.artist != null))
        			currentTitle.setText(DefuzeMe._queueTracks.get(0).data.content.artist);
    		if ((DefuzeMe._queueTracks.get(0).data.content.title == null)
        		&& (DefuzeMe._queueTracks.get(0).data.content.artist == null))
        			currentTitle.setText(com.defuzeme.R.string.NoPlayingItem);
    			
    		if (DefuzeMe._queueTracks.get(0).data.content.duration != null)
    			currentTime.setText(PlayQueue.Tools.secondsToTimeString(DefuzeMe._queueTracks.get(0).data.content.duration));
    	}
    }
    
    public void setFollowingTrackInfos() {
    	if (DefuzeMe._queueTracks.size() > 0) {
    		final View		followingSound		= findViewById(id.followingSound);
    		
	    	if (followingSound != null) {
	    		final TextView followingTitle	= (TextView) findViewById(id.followingSoundTitle);
	    		final TextView followingTime	= (TextView) findViewById(id.followingSoundTime);
	    		
	    		followingTitle.setText(DefuzeMe._queueTracks.get(1).data.content.title + " - " + DefuzeMe._queueTracks.get(1).data.content.artist);
	    		followingTime.setText(PlayQueue.Tools.secondsToTimeString(DefuzeMe._queueTracks.get(1).data.content.duration));
	    		
				final AnimationSet set = new AnimationSet(true);
		        final Animation animation = new AlphaAnimation(0.0f, 1.0f);
		        animation.setDuration(1400);
		        set.addAnimation(animation);
		        
		        followingSound.startAnimation(set);
		        followingSound.setVisibility(View.VISIBLE);
		        
		        followingTitle.startAnimation(set);
		        followingTitle.setVisibility(View.VISIBLE);
		        
		        followingTime.startAnimation(set);
		        followingTime.setVisibility(View.VISIBLE);
			}
    	}
    }

	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}

	private Drop mDropListener = new Drop() {
        public void onDrop(int from, int to)  {
    		if (from != to) {
    			PlayQueue.Gui.showUpdatingProgress();
    			Communication.Send.moveQueueElem(from, to);
    		}
        }
	};
    
    private Remove mRemoveListener = new Remove() {
        public void onRemove(int which) {
        	final ListAdapter ladapter = getListAdapter();
        	
        	if (ladapter instanceof QueueAdapter) {
        		((QueueAdapter) ladapter).onRemove(which);
        		getListView().invalidateViews();

                if (listView instanceof DnDList) {
                	((DnDList) listView).setRemoveListener(mRemoveListener);
                	((DnDList) listView).setDropListener(mDropListener);
                	((DnDList) listView).setDragListener(mDragListener);
                }
                _adapter.notifyDataSetChanged();
        	}
        }
    };
    
    private Drag mDragListener = new Drag() {
    	Drawable		defaultBackground	= null;
    	final Integer	background			= 0xff555555;

		public void onDrag(int x, int y, ListView listView) { }

		public void onStartDrag(View itemView) {
			if (itemView != null) {
				itemView.setVisibility(View.INVISIBLE);
				defaultBackground = itemView.getBackground();
				itemView.setBackgroundResource(com.defuzeme.R.drawable.play_queue_item_hover);
				
				final ImageView iv = (ImageView)itemView.findViewById(com.defuzeme.R.id.ImageView01);
				
				if (iv != null)
					iv.setVisibility(View.INVISIBLE);
			}
		}

		public void onStopDrag(View itemView) {
			if (itemView != null) {
				itemView.setVisibility(View.VISIBLE);
				itemView.setBackgroundDrawable(defaultBackground);
				
				final ImageView iv = (ImageView)itemView.findViewById(com.defuzeme.R.id.ImageView01);
				
				if (iv != null)
					iv.setVisibility(View.VISIBLE);
			}
		}
    };
	
	public Runnable updaterHandler = new Runnable() {
        public void run() {
    		initialize();

    		if (findViewById(id.followingSound) != null) {
    			findViewById(id.followingSound).setVisibility(View.INVISIBLE);
    			findViewById(id.followingSoundTitle).setVisibility(View.INVISIBLE);
    			findViewById(id.followingSoundTime).setVisibility(View.INVISIBLE);
    		}
    		
    		if ( (listView != null) && (listView.getChildAt(0) != null) ) {
    			if (listView.getAnimation() != null) {
    				listView.getAnimation().reset();
    				listView.getAnimation().cancel();
    			}
    			listView.getChildAt(0).setVisibility(View.INVISIBLE);
    		}
    		
    		_adapter.notifyDataSetChanged();
        }
    };
    
	public Runnable hideProgressHandler = new Runnable() {
        public void run() {
    		Gui.hideProgress();
        }
    };
    
    private void showItemInfos(int position) {
    	String	content				= null;
    	final StreamObject object	= DefuzeMe._queueTracks.get(position + 1);

    	if (object != null) {
	    	content	= new String(this.getString(string.Title) + " : " + object.data.content.title + "\r\n");
	    	content += this.getString(string.Artist) + " : " + object.data.content.artist + "\r\n";
	    	content += this.getString(string.Album) + " : " + object.data.content.album + "\r\n";
	    	content += this.getString(string.AlbumArtist) + " : " + object.data.content.album_artist + "\r\n";
	    	content += this.getString(string.Genre) + " : " + object.data.content.genre + "\r\n";
	    	content += this.getString(string.Duration) + " : " + PlayQueue.Tools.secondsToTimeString(object.data.content.duration) + "\r\n";
	    	content += this.getString(string.Track) + " : " + String.valueOf(object.data.content.track) + "\r\n";
	    	
	    	PlayQueue.Gui.showAlertDialog(string.Informations, content);
    	}
    }
	
	@Override
	public void onConfigurationChanged(Configuration config) {
	  super.onConfigurationChanged(config);
	}
	
	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	        return true;

	    return super.onKeyLongPress(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking() && !event.isCanceled()) {
    		this.finish();
	        return true;
	    }
	    return super.onKeyUp(keyCode, event);
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK)
    		event.startTracking();
    	
    	return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	this.showItemInfos(position);
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		initialize();
		
		_adapter.notifyDataSetChanged();
	}
    
    @Override
    public void finish() {
    	_updater.cancel(true);
    	super.finish();
    }

}