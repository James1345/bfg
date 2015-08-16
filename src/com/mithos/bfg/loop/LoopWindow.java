package com.mithos.bfg.loop;

import java.awt.Graphics2D;

import com.mithos.bfg.core.BFGWindow;
import com.mithos.bfg.core.BFGWindowProperties;

/**
 * This is a class designed to separate the behaviour from the window,
 * by use of an {@link LoopWindowBehaviour} object.
 * 
 * The properties are specified by {@link BFGWindowProperties} as in the 
 * parent class.
 * 
 * @author James McMahon
 *
 */
public class LoopWindow extends BFGWindow {

	private static final long serialVersionUID = -3593477120033309453L;
	
	// package-private
	LoopWindowBehaviour behaviour;
	
	public LoopWindow(BFGWindowProperties properties, LoopWindowBehaviour behaviour){
		super(properties);
		if(behaviour == null) throw new NullPointerException("Behaviour may not be null");
		this.behaviour = behaviour;
	}
	
	@Override
	protected void render(Graphics2D g, BFGWindowProperties properties) {
			behaviour.render(g, properties);
	}
	
	/**
	 * Sets a new behaviour for the window.
	 * @param behaviour The new behaviour to use. Often used for changing area.
	 */
	public void setBehaviour(LoopWindowBehaviour behaviour){
		if(behaviour == null) throw new NullPointerException("Behaviour may not be null");
		this.behaviour = behaviour;
	}

}
