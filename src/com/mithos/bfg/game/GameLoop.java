package com.mithos.bfg.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.mithos.bfg.loop.MainLoop;
import com.mithos.bfg.loop.OnClose;
import com.mithos.bfg.loop.OnEvent;
import com.mithos.bfg.loop.OnInit;
import com.mithos.bfg.loop.OnLoop;

/**
 * Extends MainLoop to cause loops to happen in 2 steps
 * 
 * Main step is carried out as normal using the provided object
 * Then each window has its postLoop method called to update areas that cannot
 * update during the loop call
 * 
 * @author james
 *
 */
public class GameLoop extends MainLoop {
	
	private static final OnLoop ONLOOP_EXTENDER = new OnLoop() {
		
		@Override
		public boolean loop(long milliseconds) {
			boolean _return = true;
			for(GameWindow win : GameWindow.getGameWindows()){
				_return = _return && win.area.__loop(milliseconds);
				win.postLoop();
			}
			return _return;
		}

		@Override
		public long getMaxDelay() {
			long maxDelay = Long.MAX_VALUE;
			for(GameWindow win : GameWindow.getGameWindows()){
				maxDelay = Math.min(maxDelay, win.area.getMaxDelay());
			}
			return maxDelay;
		}
		
	};
	
	private static final OnEvent ONEVENT_EXTENDER = new OnEvent(){

		@Override
		public boolean keyPressed(KeyEvent e) {
			boolean _return = true;
			for(GameWindow win : GameWindow.getGameWindows()){
				_return = _return && win.area._keyPressed(e);
			}
			return _return;
		}

		@Override
		public boolean keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseWheel(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	};
	
	public GameLoop(OnInit onInit, OnClose onClose) {
		super(onInit, ONEVENT_EXTENDER, ONLOOP_EXTENDER, onClose);
	}

}
