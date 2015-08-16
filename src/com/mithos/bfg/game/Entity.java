package com.mithos.bfg.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.mithos.bfg.loop.OnLoop;

/**
 * This is an Entity in the game.
 * 
 * An entity is anything that is represented on screen that is not
 * part of the background (i.e. it interacts in some way). It is
 * possible for these entities to be invisible in order to create
 * location dependant invisible interactions, however this class
 * should <em>not</em> be used for location independent interactions
 * 
 * This variety of entity uses cartesian coordinates (x, y)
 * for its positioning. 
 * 
 * @author James McMahon
 *
 */
public abstract class Entity implements OnLoop {

	// All entities
	private static final List<Entity> ENTITIES = new ArrayList<Entity>();

	public static List<Entity> getEntities(){
		return ENTITIES;
	}
	
	/**
	 * Entities use doubles for positioning, which are cast to ints for screen coords
	 */
	private double x, y, speedX, speedY;
	
	/** 
	 * How far this entity (and therefore, the image
	 * and collision mask) has rotated. rotations are
	 * measured in degrees (360 in a circle) anti-clockwise
	 */
	private double angle, rotationSpeed;
	
	private Animation anim = null;
	private Animation collisionMask = null;
	
	
	/**
	 * Creates a new entity and adds it to the list of all entities
	 */
	private Entity(){
		ENTITIES.add(this);
	}
	
	/**
	 * 
	 * @param anim animaton to use.
	 * @param solid is the entity solid? (uses anim as collision mask)
	 */
	public Entity(Animation anim, boolean solid){
		this();
		if(anim == null) throw new IllegalArgumentException("Entities cannot be invisible and non-solid.");
		this.anim = anim;
		if(solid) this.collisionMask = anim;
		else this.collisionMask = null;
	}
	
	/**
	 * 
	 * @param anim animaton to use.
	 * @param solid is the entity solid? (uses anim as collision mask)
	 */
	public Entity(Animation anim, Animation collisionMask){
		this();
		if(anim == null && collisionMask == null) throw new IllegalArgumentException("Entities cannot be invisible and non-solid.");
		this.anim = anim;
		this.collisionMask = collisionMask;
	}
	
	/**
	 * Get the image to draw on screen for this entity
	 * @return The image to draw, or null if no image is to be drawn
	 */
	public BufferedImage getDrawImage(){
		return getRotatedFrame(anim);
	}
	
	/**
	 * Get the image to draw on screen for this entity
	 * @return The image to draw, or null if no image is to be drawn
	 */
	public BufferedImage getCollisionMask(){
		return getRotatedFrame(collisionMask);
	}
	
	/**
	 * Get the bounding box of the collision mask
	 * @return the BBox, or null if not collidable
	 */
	public Rectangle getBBox(){
		if(getCollisionMask()==null) return null;
		return new Rectangle((int)x, (int)y, getCollisionMask().getWidth(), getCollisionMask().getHeight());
	}
	
	/*
	 * loop called by default adapter
	 */	
	boolean __loop(long milliseconds){
		if(milliseconds < 1) throw new IllegalArgumentException("Time must be positive");
		x+=milliseconds*speedX;
		y+=milliseconds*speedY;
		angle+=milliseconds*rotationSpeed;
		anim.timePassed(milliseconds);
		collisionMask.timePassed(milliseconds);
		return loop(milliseconds);
	}
	
	/*
	 * Rotates the current frame of an animation
	 */
	private BufferedImage getRotatedFrame(Animation anim){
		if(anim == null) return null;
		Image base = anim.getCurrentFrame();
		BufferedImage rot = new BufferedImage(base.getWidth(null), base.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = rot.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(base, AffineTransform.getRotateInstance(angle), null);
		return rot;
	}
}
