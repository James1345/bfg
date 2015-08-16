package com.mithos.bfg.loop;

import java.awt.Graphics2D;
import java.awt.event.WindowEvent;

import com.mithos.bfg.core.BFGWindowProperties;

/**
 * This interface is used to define the behaviour of a window.
 * @author James McMahon
 *
 */
public interface LoopWindowBehaviour {
	
	/**
	 * Called to draw on the window's canvas.
	 * @param g The graphics object to draw on.
	 * @param properties A read-only copy of the window properties.
	 */
	public void render(Graphics2D g, BFGWindowProperties properties);
	
	/**
	 * Called when the close button of the window is clicked
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean windowClosed(WindowEvent e);
	
	/**
	 * Called when the Window is selected
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean windowActivated(WindowEvent e);
	
	/**
	 * Called when the window is deselected
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean windowDeactivated(WindowEvent e);
	
}
