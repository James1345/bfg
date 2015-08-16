package com.mithos.bfg.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A window in the application.
 * 
 * An app window is provided with an instance of
 * {@link BFGWindowProperties} on creation. This specifies what the window should display
 * and the initial properties of the window.
 * 
 * NOTE: this is not the same {@link BFGWindowProperties} that is supplied to {@link #render(Graphics2D, BFGWindowProperties)}.
 * That instance represents the state of the window <strong>at the time {@link #render(Graphics2D, AppWindowProperties)()} is called.</strong>
 * @author James McMahon
 * @see BFGWindowProperties
 */
public abstract class BFGWindow extends JFrame {

	private static final long serialVersionUID = -3620966985464524576L;
	
	// The list of all windows
	private static final List<BFGWindow> WINDOWS = new ArrayList<BFGWindow>();
	
	/**
	 * Returns a list of all created BFGWindows
	 * @return
	 */
	public static BFGWindow[] getBFGWindows() {
		BFGWindow[] w = new BFGWindow[0];
		return WINDOWS.toArray(w); 
	}
	
	// This window's canvas and back buffer
	JPanel canvas = null;
	private VolatileImage backBuffer = null;

	
	/**
	 * Create a new window with the specified renderer and properties.
	 * @param properties The instance that specifies initial properties of the window.
	 */
	protected BFGWindow(BFGWindowProperties properties){
		
		if(properties == null) throw new NullPointerException("Properties object may not be null!");
		
		WINDOWS.add(this);
		
		setIgnoreRepaint(true);
		setBounds(properties.getOrigin().x, properties.getOrigin().y, getWidth(),getHeight());
		setTitle(properties.getName());
		setName(properties.getName());
		
		canvas = new JPanel();
		canvas.setIgnoreRepaint(true);
		add(canvas, BorderLayout.CENTER);
		canvas.setPreferredSize(properties.getResolution());
		canvas.setCursor(properties.getMouseCursor());
		backBuffer = canvas.createVolatileImage(canvas.getWidth(), canvas.getHeight());
		pack();
		
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public void render(){
		
		// Create properties object
		BFGWindowProperties props = new BFGWindowProperties();
		props.setResolution(canvas.getSize());
		props.setOrigin(this.getLocation());
		props.setName(this.getName());
		props.setMouseCursor (canvas.getCursor());
		props.setMousePosition(canvas.getMousePosition());
		props.makeReadOnly();
		
		// Create graphics object
		backBuffer = canvas.createVolatileImage(canvas.getWidth(), canvas.getHeight());
		
		Graphics2D g = (Graphics2D)backBuffer.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g.setColor(Color.BLACK);
		
		// Call render
		render(g, props);
		
		// BLT to the screen
		canvas.getGraphics().drawImage(backBuffer, 0, 0, null);
		
		// Release image resources
		backBuffer.flush();
	}
	
	protected abstract void render(Graphics2D g, BFGWindowProperties properties);
	
}
