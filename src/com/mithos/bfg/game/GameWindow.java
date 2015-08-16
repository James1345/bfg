package com.mithos.bfg.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import com.mithos.bfg.core.BFGWindowProperties;
import com.mithos.bfg.loop.LoopWindow;
import com.mithos.bfg.loop.LoopWindowBehaviour;

/**
 * Game window designed to separate rendering from event behaviour,
 * and to allow different areas to be attached/detatched.
 * @author james
 *
 */
public class GameWindow extends LoopWindow {

	private static final long serialVersionUID = -7703200084932984567L;

	private static List<GameWindow> GAME_WINDOWS = new ArrayList<>();
	public static List<GameWindow> getGameWindows() { return GAME_WINDOWS; }
	public static GameWindow getWindowByName(String name) {
		for( GameWindow w : GAME_WINDOWS){
			if(w.getName().equals(name)){
				return w;
			}
		}
		return null;
	}
	
	Area area = null; //The area this window shows
	
	/*
	 *  The next area to be shown. Cannot change areas during a 
	 *  call to loop nor during events, so a special post loop event
	 *  is added for attaching it.
	 */
	private Area buffer = null; 
	
	private static LoopWindowBehaviour BLANK_SCREEN = new LoopWindowBehaviour() {
		
		@Override
		public boolean windowDeactivated(WindowEvent e) {
			throw new AssertionError("Unreachable Code called");
		}
		
		@Override
		public boolean windowClosed(WindowEvent e) {
			throw new AssertionError("Unreachable Code called");
		}
		
		@Override
		public boolean windowActivated(WindowEvent e) {
			throw new AssertionError("Unreachable Code called");
		}
		
		@Override
		public void render(Graphics2D g, BFGWindowProperties properties) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, properties.getResolution().width, properties.getResolution().height);
		}
	};
	
	@Override
	protected void render(Graphics2D g, BFGWindowProperties properties){
		if(area == null){
			BLANK_SCREEN.render(g, properties);
		}
		else{
			area.render(g, properties);
		}
	}
	
	public GameWindow(BFGWindowProperties properties,
			LoopWindowBehaviour behaviour) {
		super(properties, behaviour);
	}
	
	public GameWindow(BFGWindowProperties properties, LoopWindowBehaviour behaviour, Area area){
		super(properties, behaviour);
		this.area = area;
	}
	
	public void setArea(Area area){
		this.buffer = area;
	}
	
	/*
	 * called at the end of each loop
	 */
	void postLoop(){
		if(buffer != null){
			area = buffer;
			buffer = null;
		}
	}

}
