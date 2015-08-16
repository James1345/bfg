package com.mithos.bfg.core;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


/**
 * The awt event listener with Linux keyboard fix
 * @author James McMahon
 *
 */
final class EventManager implements AWTEventListener {

	private Queue<AWTEvent> eventQueue;
	
	public EventManager(Queue<AWTEvent> eventQueue) {
		this.eventQueue = eventQueue;
	}

	/*
	 * We need to check that keyRelease/keyPress events are actually
	 * separate and not the result of a key being held down
	 * 
	 * In general, repeated key hits are >50ms apart, while key held
	 * events trigger at 0~2ms Therefore, 30ms has been chosen as the
	 * timer interval (variable name `DELAY`)
	 * 
	 * Note that while this code addresses a linux specific bug,
	 * it is run on all platforms (a 30ms delay in keyrelease events
	 * is not noticeable to the user)
	 */
	
	/*
	 * Here is the Map of the futures and the key they correspond to.
	 * 
	 * First, a customized ThreadFactory is created to create threads as daemons (so
	 * that they do not prolong the life of the app).
	 * A ScheduledExecutor is set up with this factory to check for the DELAY.
	 * The map is to hold the list of futures and also which keys are currently 'down'.
	 * Using this the executor can tell if an event is a repeat of the same key that is held
	 */
	ThreadFactory tf = new ThreadFactory() {
		private int threadnum = 0;
		
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "Keyboard-fix-thread-"+threadnum++);
			t.setDaemon(true);
			return t;
		}
	};
	private ScheduledExecutorService ex = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()*2,tf);
	private Map<Integer, Future<?>> futures = new HashMap<>();
	
	// Delay
	private static int DELAY = 30; // 30ms
	@Override
	public void eventDispatched(AWTEvent event) {
		
		if(event == null){
			throw new NullPointerException("Null event created by AWT!");
		}
		
		if(event.getID() == KeyEvent.KEY_RELEASED && event instanceof KeyEvent){
			
			/*
			 *  This is a key release, create a timer that will post the
			 *  event to the queue after DELAY ms and store the future
			 *  in the map with the keyCode
			 */
			final KeyEvent ke = (KeyEvent)event;
			Future<?> future = ex.schedule(new Runnable(){
				public void run(){
					eventQueue.offer(ke);
					futures.remove(ke.getKeyCode());
				}
			}, DELAY, TimeUnit.MILLISECONDS);
			
			futures.put(ke.getKeyCode(), future);
		}
		
		else if(event.getID() == KeyEvent.KEY_PRESSED && event instanceof KeyEvent){
			/*
			 * This is a keyPress.
			 * If there is a future waiting to execute, cancel it and do nothing
			 * as the key has been held. If not, this is a new event so we can
			 * post it to the queue
			 */
			
			KeyEvent ke = (KeyEvent)event;
			int code = ke.getKeyCode();
			if(futures.containsKey(code)){
				futures.get(code).cancel(false);
				futures.remove(code);
			}
			else {
				eventQueue.offer(ke);
			}
			
		}
		else if(event instanceof MouseEvent){
			/*
			 * This is a mouse event, we need to correct the mouse position
			 * to be the position in the canvas
			 */
			Object source = event.getSource();
			
			// Ignore  if not an AppWindow
			if(source instanceof BFGWindow){
				BFGWindow window = (BFGWindow)source;
				MouseEvent ev = (MouseEvent)event;
				
				// Correct the position by the difference in location
				Point difference = new Point(
						window.canvas.getLocationOnScreen().x - window.getLocationOnScreen().x,
						window.canvas.getLocationOnScreen().y - window.getLocationOnScreen().y
				);
				ev.translatePoint(-difference.x, -difference.y);
				eventQueue.offer(ev);
			}
		}
		else{
			/*
			 * This is an normal event,
			 * offer it to the event queue
			 */
			
			eventQueue.offer(event);
		}
	}
}
