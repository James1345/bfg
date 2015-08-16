package com.mithos.bfg.loop;

/**
 * This interface defines code that should be run once when the application
 * starts, before main processing begins.
 * @author James McMahon
 *
 */
public interface OnInit {
	
	/**
	 * Code to run when the application starts. Returns true to continue processing and false to stop
	 * @return true if setup is successful, false if not
	 */
	public boolean init();
	
}
