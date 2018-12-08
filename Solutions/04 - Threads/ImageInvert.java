package com.example.mandelbrot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

import javax.imageio.ImageIO;

public class ImageInvert extends RecursiveAction {
	private static String IN_FILENAME = "Mandelbrot.jpg";
	private static String OUT_FILENAME = "Mandelbrot01.jpg";
	private static int computeThreshold = 10000;
    private int[] sourceArray;
    private int start;
    private int length;
    private int[] destinationArray;	
    
    public static void main(String[] args) {
    	ImageInvert.invert();
	}
    public ImageInvert(int[] source, int start, int length, int[] destination) {
        this.sourceArray = source;
        this.start = start;
        this.length = length;
        this.destinationArray = destination;
    }
    private static LongAdder decompCount = new LongAdder();
	private static void invert() {
		try  {
			BufferedImage image = ImageIO.read(new File(IN_FILENAME));
			int width = image.getWidth();
			int height = image.getHeight();
			int[] source = image.getRGB(0, 0, width, height, null, 0, width);
	        int[] destination = null;
	        long startTime = System.currentTimeMillis();
	        for (int i = 0; i < 1000; i++) {
		        int[] dest = new int[source.length];
		        if (i == 0) {
		        	destination = dest;
		        }
				ForkJoinPool pool = new ForkJoinPool();
	        	ImageInvert imageInverter = new ImageInvert(source, 0, source.length, dest);
	        	pool.invoke(imageInverter);	        	
	        }
	        long endTime = System.currentTimeMillis();

	        System.out.println("Image inversion took " + (endTime - startTime) + 
	                " milliseconds with "+decompCount.longValue()+" decompositions.");
			BufferedImage invertedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			invertedImage.setRGB(0, 0, width, height, destination, 0, width);
	        ImageIO.write(invertedImage, "jpeg", new File(OUT_FILENAME));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			
		}
	}
	@Override
	public void compute() {
        decompCount.add(1);
		if (length < computeThreshold) {
		       for (int i = start; i < start + length; i++) {
		            int pixel = sourceArray[i];
		            int red = 255 - ((pixel & 0x00ff0000) >> 16);
		            int green = 255 - ((pixel & 0x0000ff00) >> 8);
		            int blue = 255 - ((pixel & 0x000000ff) >> 0);
		            destinationArray[i] = (0xff000000)
		                    | ((red) << 16)
		                    | ((green) << 8)
		                    | ((blue) << 0);
		       }
			return;
		} else {
	       int split = length / 2;

	        invokeAll(new ImageInvert(sourceArray, start, split, destinationArray),
	                new ImageInvert(sourceArray, start + split, length - split, 
	                destinationArray));
		}
	}
	
}
