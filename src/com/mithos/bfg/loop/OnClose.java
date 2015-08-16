package com.mithos.bfg.loop;

/**
 * This interface defines code to run at the end of the program.
 * 
 * The method defined in this interface will be run once, as soon as
 * any {@link OnEvent}, {@link OnLoop} or {@link OnInit} method returns
 * false. Once it has run the application shuts down.
 * 
 * @author James McMahon
 *
 */
public interface OnClose {

	/**
	 * The code to run immediately proir to the application closing.
	 */
	public void close();
	
}
