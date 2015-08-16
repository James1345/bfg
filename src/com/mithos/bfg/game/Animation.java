package com.mithos.bfg.game;

import java.awt.Image;
import java.util.List;

import com.mithos.bfg.loop.OnLoop;

/**
 * An animation is a series of sprites (images) and a frame rate. 
 * 
 * The animation implementation presented here is an example and is not
 * the only way to create one (nor is it necessarily the 'correct' one for your game).
 * This animation class needs informing of time's passage, which should be handled in the
 * "Loop" section of the main loop (or using the {@link OnLoop} class if it is used). The 
 * image of the current frame can then be retrieved from the object.
 * 
 * @author James McMahon
 *
 */
public class Animation {

	private List<Image> frames = null;
	private int currentFrame = 0;
	private int mspf = 0; // milliseconds between each frame
	private long partialFrameTime = 0; //counter for partially passed frames
	
	
	public Animation(List<Image> frames, int fps){
		if(frames == null) throw new NullPointerException("Frame list cannot be null!");
		if(frames.size() < 0) throw new IllegalArgumentException("Frame list cannot be empty!");
		if(frames.contains(null)) throw new NullPointerException("Frame list cannot contain null!");
		this.frames = frames;
		setFps(fps);
	}

	public int getCurrentFrameIndex() {
		return currentFrame;
	}

	public void setCurrentFrameIndex(int currentFrame) {
		if(currentFrame < 0 || currentFrame >= frames.size()) 
			throw new IndexOutOfBoundsException("currentFrame outside number of frames available to animation.");
		this.currentFrame = currentFrame;
		partialFrameTime = 0; // Reset timer!
	}

	/**
	 * Returns the actual value of the frame rate.
	 * 
	 * Note that this may be slightly different to the set value due to conversions and storage.
	 * @return
	 */
	public int getFps() {
		return 1000/mspf;
	}

	public void setFps(int fps) {
		if(fps < 1) throw new IllegalArgumentException("fps must be positive." + 
				(fps==0 ? "Use a list of size 1 and a positive fps to get a stationary image":""));
		this.mspf = 1000/fps;
	}
	
	public void timePassed(long milliseconds){
		if(milliseconds < 1) throw new IllegalArgumentException("Time passed must be positive");
		currentFrame += (int) ((partialFrameTime+milliseconds) / mspf);
		currentFrame = currentFrame % frames.size();
		partialFrameTime = (partialFrameTime+milliseconds) % mspf;
	}
	
	public Image getCurrentFrame(){
		return frames.get(currentFrame);
	}
}
