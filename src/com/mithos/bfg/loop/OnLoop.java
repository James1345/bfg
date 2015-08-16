package com.mithos.bfg.loop;

/**
 * This interface describes what the application is to do
 * as time progresses.
 * 
 * It declares two methods:
 * <ul>
 * 
 * <li>{@link #loop(long)} is called at regular intervals, and tells the application
 * what to do when it is. The argument is the amount of time that has passed since loop
 * was last called</li>
 * 
 * <li>{@link #getMaxDelay()} returns a long that is the Maximum delay that will occur between
 * invocations of {@link #loop(long)} (in milliseconds). How long the delay between invocations is will vary,
 * and may sometimes (rarely) exceed this maximum, so the argument of {@link #loop(long)} should always be used
 * to determine the actual time that has passed.</li>
 * </ul>
 * 
 * @author James McMahon
 *
 */
public interface OnLoop {

	/**
	 * This method is called at regular intervals, and tells the application what to do when it is.
	 * @param milliseconds The amount of time since loop was last called.
	 * @return true if the application should continue, false if it should proceed to OnClose.
	 * @see #getMaxDelay()
	 */
	public boolean loop(long milliseconds);

	/**
	 * returns a long that is the Maximum delay that will occur between
	 * invocations of {@link #loop(long)} (in milliseconds). How long the delay between invocations is will vary,
	 * and may sometimes (rarely) exceed this maximum, so the argument of {@link #loop(long)} should always be used
	 * to determine the actual time that has passed.
	 * @return The requested maximum delay between invocations of {@link #loop(long)}.
	 * 
	 * @see #loop(long)
	 */
	public long getMaxDelay();
	
}
