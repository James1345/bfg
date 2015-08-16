package com.mithos.bfg.loop;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * This class is an adapter for {@link OnEvent}. All methods do
 * nothing and return true, so you need only implement the methods
 * that you need to.
 * @author James McMahon
 *
 */
public class OnEventAdapter implements OnEvent {

	@Override
	public boolean keyPressed(KeyEvent e) {
		return true;
	}

	@Override
	public boolean keyReleased(KeyEvent e) {
		return true;
	}

	@Override
	public boolean mousePressed(MouseEvent e) {
		return true;
	}

	@Override
	public boolean mouseMoved(MouseEvent e) {
		return true;
	}

	@Override
	public boolean mouseReleased(MouseEvent e) {
		return true;
	}

	@Override
	public boolean mouseWheel(MouseWheelEvent e) {
		return true;
	}

}
