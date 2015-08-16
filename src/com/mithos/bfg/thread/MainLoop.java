package com.mithos.bfg.thread;

import com.mithos.bfg.loop.OnClose;
import com.mithos.bfg.loop.OnEvent;
import com.mithos.bfg.loop.OnInit;
import com.mithos.bfg.loop.OnLoop;

/**
 * This is a more advanced main loop, that will thread the different aspects
 * of the game (Rendering, Loop and Event). Due to this, the specialized
 * entity class should <em>always</em> be used for game entities.
 * @author James McMahon
 *
 */
public class MainLoop extends com.mithos.bfg.loop.MainLoop {

	public MainLoop(OnInit onInit, OnEvent onEvent, OnLoop onLoop,
			OnClose onClose) {
		super(onInit, onEvent, onLoop, onClose);
		// TODO Auto-generated constructor stub
	}

}
