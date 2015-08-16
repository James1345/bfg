package com.mithos.bfg.core;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This class is used to hold information about the current or initial state of
 * an AppWindow. it is instantiated with sensible defaults (detailed below).
 * 
 * There are two variants of this class. One is created by the user, and can have
 * its values edited before being given to a Window to set the values of the
 * window. The other is one obtained from the window. The values in such an
 * instance are read only and cannot be changed. Some of the values are only
 * ever readable (such as mouse position) as they do not make sense to be set.
 * 
 * @author James McMahon
 *
 */
public class BFGWindowProperties {

	/**
	 * By default this class is not read only.
	 */
	private boolean readOnly = false;
	
	/**
	 * Creates a new AppWindowProperties that can be edited with default values.
	 */
	public BFGWindowProperties(){}
	
	/**
	 * The resolution (in pixels) of the canvas that will be shown.
	 */
	private Dimension resolution = new Dimension(640, 480);
	
	/**
	 * The desired Origin (top left corner) of the Window.
	 */
	private Point origin = new Point(0,0);
	
	/**
	 * The name of the window containing the canvas.
	 * 
	 * The name placed in this method will be used in the window title bar, and
	 * also can be used to identify the window that caused a particular event to occur.
	 */
	private String name = "BFG Window";
	
	/**
	 * The mouseCursor to use when rendering.
	 */
	private Cursor mouseCursor = Cursor.getDefaultCursor();
	
	/**
	 * The current position of the mouse withing the window's canvas
	 */
	private Point mousePosition = new Point(0,0);

	/**
	 * @return the resolution
	 */
	public Dimension getResolution() {
		return resolution;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public boolean setResolution(Dimension resolution) {
		if(!readOnly)
			this.resolution = resolution;
		return !readOnly;
	}	

	/**
	 * @return the origin
	 */
	public Point getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public boolean setOrigin(Point origin) {
		if(!readOnly)
			this.origin = origin;
		return !readOnly;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public boolean setName(String name) {
		if(!readOnly)
			this.name = name;
		return !readOnly;
	}

	/**
	 * @return the mouseCursor
	 */
	public Cursor getMouseCursor() {
		return mouseCursor;
	}

	/**
	 * Attempts to set the cursor on this AppWindowProperties.
	 * 
	 * Returns a boolean that is true if the mouse position was set, and false if not.
	 * Failure to set a position is normally due to the object being read only.
	 * 
	 * @param mouseCursor the Cursor to set
	 * @return True if the mouse cursor was set, false otherwise.
	 * 
	 * @see #isReadOnly()
	 * 
	 */
	public boolean setMouseCursor(Cursor mouseCursor) {
		if(!readOnly)
			this.mouseCursor = mouseCursor;
		return !readOnly;
	}

	/**
	 * @return the mousePosition
	 */
	public Point getMousePosition() {
		return mousePosition;
	}
	
	/**
	 * Attempts to set the position on this AppWindowProperties.
	 * 
	 * Returns a boolean that is true if the mouse position was set, and false if not.
	 * Failure to set a position is normally due to the object being read only.
	 * 
	 * Setting the mouse position does not change the location of the mouse, it
	 * merely sets the value contained by this object. This method is used internally
	 * by the {@link AppWindow} class when returning AppWindowProperties to other
	 * methods.
	 * 
	 * @param mousePosition the current position of the mouse to set
	 * @return True if the mouse cursor was set, false otherwise.
	 * 
	 * @see #isReadOnly()
	 * 
	 */
	boolean setMousePosition(Point mousePoistion) {
		if(!readOnly)
			this.mousePosition = mousePoistion;
		return !readOnly;
	}

	/**
	 * @return is the AppWindowProperties object read only?
	 */
	public boolean isReadOnly() {
		return readOnly;
	}
	
	/**
	 * Makes this AppWindowProperties read only. After this method has been called, it
	 * can never be made writable and the values cannot be changed.
	 */
	public void makeReadOnly(){
		readOnly = true;
	}
}
