package com.mithos.bfg.loop;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

/**
 * The interface to implement to tell the App how to respond to Events.
 * 
 * Events are triggered when the user interacts with the App in some way.
 * @author James McMahon
 *
 */
public interface OnEvent {

	/**
	 * Called when a key is pressed
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.). The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean keyPressed(KeyEvent e);
	
	/**
	 * Called when a key is released
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean keyReleased(KeyEvent e);
	
	/**
	 * Called when the mouse button is pressed
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean mousePressed(MouseEvent e);
	
	/**
	 * Called when the mouse is moved
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean mouseMoved(MouseEvent e);
	
	/**
	 * Called when the mouse button is released and the mouse is dragged.
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean mouseReleased(MouseEvent e);
	
	/**
	 * Called when the mouse wheel is moved
	 * @param e The event object. contains information about the event (source, key, mouse button, etc.).
	 * @return true if the application should continue running. false if it should proceed to OnClose
	 */
	public boolean mouseWheel(MouseWheelEvent e);
	
}
