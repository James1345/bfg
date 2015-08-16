package com.mithos.bfg.loop;

import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.KEY_RELEASED;
import static java.awt.event.MouseEvent.MOUSE_DRAGGED;
import static java.awt.event.MouseEvent.MOUSE_MOVED;
import static java.awt.event.MouseEvent.MOUSE_PRESSED;
import static java.awt.event.MouseEvent.MOUSE_RELEASED;
import static java.awt.event.MouseEvent.MOUSE_WHEEL;
import static java.awt.event.WindowEvent.WINDOW_ACTIVATED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.awt.event.WindowEvent.WINDOW_DEACTIVATED;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import com.mithos.bfg.core.BFG;
import com.mithos.bfg.core.BFGWindow;

/**
 * This class holds an configured copy of the
 * BFG main loop.
 * 
 * As the loop is constructed from several parts, these have been split
 * off into interfaces to be implemented. The interfaces return boolean
 * values. If at any point they return false this is taken as the signal
 * to call the close method and exit.
 * @author James McMahon
 *
 */
public class MainLoop {

	private OnInit onInit = null;
	private OnEvent onEvent = null;
	private OnLoop onLoop = null;
	private OnClose onClose = null;
	
	private boolean running = true;
	
	public MainLoop(OnInit onInit, OnEvent onEvent, OnLoop onLoop, OnClose onClose){

		
		if(onInit == null) throw new NullPointerException("onInit may not be null!");
		if(onEvent == null)throw new NullPointerException("onEvent may not be null!");
		if(onLoop == null) throw new NullPointerException("onLoop may not be null!");
		if(onClose == null)throw new NullPointerException("onClose may not be null!");
			
		this.onInit = onInit;
		this.onEvent = onEvent;
		this.onLoop = onLoop;
		this.onClose = onClose;
	}
	
	/**
	 * Starts the main loop.
	 * 
	 * This method will start the main event/loop/render loop.
	 * 
	 * After a method returns false and {@link OnClose#close()} has returned
	 * this method also handles cleanly disposing of windows and
	 * stopping threads.
	 */
	public void run() {
		BFG.init();
		
		running = onInit.init();
		
		long oldTime = System.currentTimeMillis();
		long currentTime = 0;
		long interval = 0;
		
		while(running){
			
			// Process event
			AWTEvent ev = null;
			while((ev = BFG.pollEventQueue()) != null){
				running = running && onEvent(ev);
				// If too long has passed need to call loop
				currentTime = System.currentTimeMillis();
				interval = currentTime - oldTime;
				if(interval > onLoop.getMaxDelay()){
					running = running && onLoop.loop(interval);
					oldTime = currentTime;
				}
			}
			
			// loop
			currentTime = System.currentTimeMillis();
			interval = currentTime - oldTime;
			running = running && onLoop.loop(interval);
			oldTime = currentTime;
			
			// Re-render windows
			for(BFGWindow win : BFGWindow.getBFGWindows()){
				win.render();
			}
		}
		
		onClose.close();
	}
	
	// Selects the correct event type to fire and passes the event object to that method
	private boolean onEvent(AWTEvent ev){
		switch(ev.getID()){
		case KEY_PRESSED: return onEvent.keyPressed((KeyEvent)ev);
		case KEY_RELEASED: return onEvent.keyReleased((KeyEvent)ev);
		case MOUSE_MOVED: return onEvent.mouseMoved((MouseEvent)ev);
		case MOUSE_DRAGGED: return onEvent.mouseMoved((MouseEvent)ev);
		case MOUSE_PRESSED: return onEvent.mousePressed((MouseEvent)ev);
		case MOUSE_RELEASED: return onEvent.mouseReleased((MouseEvent)ev);
		case MOUSE_WHEEL: return onEvent.mouseWheel((MouseWheelEvent)ev);
		case WINDOW_ACTIVATED: 
			if(ev.getSource() instanceof LoopWindow)
				return ((LoopWindow)ev.getSource()).behaviour.windowActivated((WindowEvent)ev);
			else
				return true;
		case WINDOW_CLOSING:
			if(ev.getSource() instanceof LoopWindow)
				return ((LoopWindow)ev.getSource()).behaviour.windowClosed((WindowEvent)ev);
			else
				return true;
		case WINDOW_DEACTIVATED:
			if(ev.getSource() instanceof LoopWindow)
				return ((LoopWindow)ev.getSource()).behaviour.windowDeactivated((WindowEvent)ev);
			else
				return true;
			
		default : return true; // If it's an event we're not interested in just return true
		}
	}
	
	
}
