package com.mithos.bfg.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class Sprites {
	
	private Sprites(){}
	
	public static final int MODE_SLICE_HORIZONTAL = 0;
	public static final int MODE_SLICE_VERTICAL = 1;
	
	/**
	 * Slice an image into a list of images.
	 * 
	 * This method slices an image into a list of images (e.g. for a tileset or animation).
	 * The base image is split into equal rectangles with height and width as specified in the parameters.
	 * The first sprite is always taken from the top left. The entire image is then sliced into equal rectangles
	 * either by progressing in rows (left to right, top to bottom) or in columns (top to bottom, left to right).
	 * 
	 * E.g. Consider we have a square image from which we can make four sprites.
	 * Using SLICE_HORIZONTAL they will be in order:
	 * 
	 * 1 2
	 * 
	 * 3 4
	 * 
	 * However, using SLICE_VERTICAL they will be in order
	 * 
	 * 1 3
	 * 
	 * 2 4
	 * 
	 * @param baseImage The image to slice
	 * @param x The x coordinate of the first subimage
	 * @param y The y coordinate of the first subimage
	 * @param width The width of each subimage
	 * @param heigth The height of each subimage
	 * @param xSeparation The gap between each subimage in the x direction
	 * @param ySeparation The gap between each subimage in the y direction
	 * @param mode The slice mode.
	 * @return The list of sliced images
	 */
	public static List<Image> sliceImage(Image baseImage, int x, int y, int width, int height, int xSeparation, int ySeparation, int mode) {

		// TODO check preconditions
		
		// Convert Image to bufferedImage
		// The conversion ensures that the image to work with will be a buffered image and that
		// no exceptions will be raised (empty space will be filled with transparency)
		BufferedImage image = new BufferedImage(baseImage.getWidth(null) + width, baseImage.getHeight(null) + height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.drawImage(image, new AffineTransform(), null);
		
		List<Image> list = new ArrayList<>();
		
		int baseWidth = baseImage.getWidth(null);
		int baseHeight = baseImage.getHeight(null);
		
		if(mode == MODE_SLICE_HORIZONTAL){ // increment x in inner loop
			for(int y0 = y; y0 < baseHeight; y0+=height+ySeparation){
				for(int x0 = x; x0 < baseWidth; x0+=width+xSeparation){
					list.add(image.getSubimage(x0, y0, width, height));
				}
			}
		}
		else if(mode == MODE_SLICE_VERTICAL){ // increment y in inner loop
			for(int x0 = x; x0 < baseWidth; x0+=width+xSeparation){
				for(int y0 = y; y0 < baseHeight; y0+=height+ySeparation){
					list.add(image.getSubimage(x0, y0, width, height));
				}
			}
		}
		else {
			throw new IllegalArgumentException("Mode must be one of the predefined values.");
		}
		return list;
	}
}
