package com.mithos.bfg.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mithos.bfg.core.BFGWindowProperties;
import com.mithos.bfg.loop.OnEvent;
import com.mithos.bfg.loop.OnLoop;

/**
 * Okay, so there's some magic going on with the helper ^_^
 * 
 * Basically, it's there so the interface doesn't change at all.
 * When the area is set as active in the window, the Helper is
 * set to handel loops and events. It does both in a similar way:
 * 
 * Loop:
 * Loop through entities and call loop on them
 * IF the area implements OnLoop THEN call its loop method
 * 
 * Event:
 * FOr each entity, and the area, IF OnEvent is implemented THEN call the correct event method
 * 
 * Yes the casts do go sideways in the inheritance tree. Yes, yes I am brilliant
 * 
 * @author James McMahon
 *
 */
public abstract class Area implements OnLoop {
	
	// Lists entities on each layer of the Area
	private final SortedMap<Integer, Set<Entity>> layers = new TreeMap<>();
	
	// Iterate over the entities in ascending layer order
	private class EntityIterable implements Iterable<Entity>{
		private class EntityIterator implements Iterator<Entity>{

			Iterator<Set<Entity>> iter;
			Iterator<Entity> currentSet;
			boolean hasNext = false;
			public EntityIterator(){
				iter = layers.values().iterator();
				if(iter.hasNext()){
					hasNext = true;
					currentSet = iter.next().iterator();
				}
			}
			
			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public Entity next() {
				if(!hasNext) throw new NoSuchElementException();
				
				Entity next = currentSet.next();
				testNext();
				return next;
			}
			
			private void testNext(){
				
				if(currentSet.hasNext()){
					return;
				}
				else if(iter.hasNext()){
					currentSet = iter.next().iterator();
					testNext();
				}
				else{
					hasNext = false;
				}
			}

			@Override
			public void remove() {
				currentSet.remove();
			}
		}
		
		@Override
		public Iterator<Entity> iterator(){
			return new EntityIterator();
		}
	}
	
	/**
	 * 
	 * @return Iterator to iterate through entities in ascending layer order.
	 */
	protected Iterable<Entity> eachEntity(){
		return new EntityIterable();
	}
	
	// Several backgrounds can be used for perspective mapping
	private final List<Background> backgrounds = new ArrayList<>();
	
	// A background struct
	// speed ratio for perspective
	// Needs more work - may need to use a layer system and let the user figure it out
	private static class Background{
		public Animation anim;
		public int speedRatio;
	}
		
	boolean _keyPressed(KeyEvent ev) {
		boolean _return = true;
		for(Entity e : eachEntity()){
			if(e instanceof OnEvent){
				_return = _return && ((OnEvent)e).keyPressed(ev);
			}
		}
		if(this instanceof OnEvent){
			_return = _return && ((OnEvent)this).keyPressed(ev);
		}
		return _return;
	}
	
	boolean _keyReleased(KeyEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

	boolean _mousePressed(MouseEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

	boolean _mouseMoved(MouseEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

	boolean _mouseReleased(MouseEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

	boolean _mouseWheel(MouseWheelEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean __loop(long milliseconds) {
		boolean running = true;
		for(Entity e : eachEntity()){ // for each entity in the area
			running = running && e.__loop(milliseconds); // call the system loop
			if(!running) break;
		}
		if(Area.this instanceof OnLoop){
			running = running && ((OnLoop)Area.this).loop(milliseconds);
		}
		return running;
	}
	
	@Override
	public long getMaxDelay() {
		long maxDelay = Long.MAX_VALUE;
		for(Entity e : eachEntity()){
			maxDelay = Math.min(maxDelay, e.getMaxDelay());
		}
		return maxDelay;
	}

	public abstract void onCollision(Entity e1, Entity e2, int l1, int l2);

	public void render(Graphics2D g, BFGWindowProperties properties) {
		// TODO Auto-generated method stub
		
	}
	
}
