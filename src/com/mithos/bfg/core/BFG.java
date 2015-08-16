package com.mithos.bfg.core;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_WHEEL_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the main class of BFG.
 * 
 * 
 * This class handles a lot of the setup and teardown process for BFG, leaving the
 * user free to concentrate on game logic.
 * 
 * @author James McMahon
 * 
 */
public final class BFG {
	
	private static long eventMask = MOUSE_EVENT_MASK | MOUSE_MOTION_EVENT_MASK | MOUSE_WHEEL_EVENT_MASK | KEY_EVENT_MASK | WINDOW_EVENT_MASK;
	
	private static BFG instance = null; // singleton
	
	// Event queue variables
	private Queue<AWTEvent> eventQueue;
	private EventManager eventManager;
	
	/**
	 * Initialize the framework
	 */
	public static void init(){
		if(instance == null) // only initialize once!
			instance = new BFG();
	}
	
	/**
	 * Returns the next event, or null if there are no events to process.
	 * 
	 * @return the next event.
	 * 
	 * @throws NullPointerException if {@link #init()} has not been called.
	 */
	public static AWTEvent pollEventQueue(){
		if(instance == null){
			throw new NullPointerException("BFG has not been initialized!");
		}
		return instance.eventQueue.poll();
	}
	
	/**
	 * Creates a new App with the given parameters and sets it up to be ready to run.
	 * 
	 * Setup does not run any of the methods included in the parameter objects, instead
	 * it includes things like registering for events and setting internal variable values.

	 */
	private BFG(){
		// Link up event queue and event manager
		eventQueue = new ConcurrentLinkedQueue<AWTEvent>();
		eventManager = new EventManager(eventQueue);
		Toolkit.getDefaultToolkit().addAWTEventListener(eventManager, eventMask);
	}
	
	
	
	
}
