package com.example.mandelbrot;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;

import javax.imageio.ImageIO;

public class ImageInvert extends RecursiveAction {
	private static String IN_FILENAME = "Mandelbrot.jpg";
	private static String OUT_FILENAME_NO_EXT = "Mandelbrot0";
	private static int computeThreshold = 10;
    private int[] sourceArray;
    private int start;
    private int length;
	private int width;
	private int height;
	private double blurSize;
    private int[] destinationArray;	
    
    public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Hit enter when ready");
			br.readLine();
			for(int i = 0; i < 25; i++) { // <-- this sets the number of iterations
				ImageInvert.blur();
			}
			System.out.println("Hit enter when finished");
			br.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

    public ImageInvert(int[] source, int start, int length, int width, int height, int blur, int[] destination) {
        this.sourceArray = source;
        this.start = start;
        this.length = length;
        this.width = width;
        this.height=height;
        this.blurSize = blur;
        this.destinationArray = destination;
    }

    private static LongAdder decompCount = new LongAdder();
    private static int[] dest = null;

    private static void blur() {
		try  {
	        for (int i = 0; i < 4; i++) {
	        	decompCount.reset();
				BufferedImage image = ImageIO.read(new File(IN_FILENAME));
				int width = image.getWidth();
				int height = image.getHeight();
		        System.out.println("Width="+width+" Height="+height+" length="+(width*height));
		        long startTime = System.currentTimeMillis();
		        int[] source = image.getRGB(0, 0, width, height, null, 0, width);
		        dest = new int[source.length];
		        ForkJoinPool pool = new ForkJoinPool();
	        	ImageInvert imageInverter = new ImageInvert(source, 0, source.length, width, height, 50*(i+1), dest);
	        	pool.invoke(imageInverter);		        	
	        	if ((i & 1) == 1) imageInverter.invert();
	        	if ((i & 2) == 2) imageInverter.swap();
		        long endTime = System.currentTimeMillis();

		        System.out.println("Image inversion took " + (endTime - startTime) + 
		                " milliseconds with "+decompCount.longValue()+" decompositions.");
				BufferedImage invertedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
				invertedImage.setRGB(0, 0, width, height, dest, 0, width);
		        ImageIO.write(invertedImage, "jpeg", new File(OUT_FILENAME_NO_EXT+i+".jpg"));
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			
		}
	}

    public void invert() {
    	for (int y = 0; y < height; y++) {
    		int[] temp = new int[destinationArray.length];
//    		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
    		for (int x = 0; x < width; x++) {
        	    System.arraycopy(destinationArray, y*width, temp, (height-1)*width, width);
//    			pixels.add(getPixel(destinationArray, x,y));
    		}
        	destinationArray = temp;
//    		for (int x = 0; x < width; x++) {
//    			setPixel(destinationArray, x, (height - 1) - y, pixels.get(x));
//    		}
    	}
    }

    public void swap() {
    	for (int y = 0; y < height; y++) {
        	int[] line = new int[width];
//    		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
    		for (int x = 0; x < width; x++) {
            	System.arraycopy(destinationArray, y*width, line, 0, width);
//     			pixels.add(getPixel(destinationArray, x,y));
    		}
    		for (int x = 0; x < width; x++) {
       			destinationArray[(width-1)-x] = line[x];
//    			setPixel(destinationArray, (width - 1) - x, y, pixels.get(x));
    		}
    	}    	
    }
    
    public void setPixel(int[] source, int x, int y, Pixel pixel) {
        int dpixel = (0xff000000)
                | (((int) pixel.getR()) << 16)
                | (((int) pixel.getG()) << 8)
                | (((int) pixel.getB()) << 0);
        source[(y*width)+x] = dpixel;
    }
    
    public Pixel getPixel(int[]destination, int x, int y) {
    	int pixel = destination[(y*width)+x];
        int r = ((pixel & 0x00ff0000) >> 16) ;
        int g = ((pixel & 0x0000ff00) >> 8);
        int b = (pixel & 0x000000ff);
        return new Pixel(r, g, b);
    }
    
    @Override
	public void compute() {
    	decompCount.add(1);
		if (length < computeThreshold) {
		    int sidePixels = (int)(blurSize - 1) / 2;
		    for (int i = start; i < start + length; i++) {
	            double rt = 0, gt = 0, bt = 0;
	            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
	                int mindex = Math.min(Math.max(mi + i, 0), sourceArray.length - 1);
	                int pixel = sourceArray[mindex];
	                rt += (double) ((pixel & 0x00ff0000) >> 16) / blurSize;
	                gt += (double) ((pixel & 0x0000ff00) >> 8) / blurSize;
	                bt += (double) ((pixel & 0x000000ff) >> 0) / blurSize;
	            }

	            // Re-assemble destination pixel.
	            int dpixel = (0xff000000)
	                    | (((int) rt) << 16)
	                    | (((int) gt) << 8)
	                    | (((int) bt) << 0);
	            destinationArray[i] = dpixel;
		    }
			return;
		} else {
	       int split = length / 2;

	        invokeAll(new ImageInvert(sourceArray, start, split, width, height, (int)blurSize,destinationArray),
	                new ImageInvert(sourceArray, start + split, length - split, width, height, (int)blurSize,
	                destinationArray));
		}
	}
	
}
