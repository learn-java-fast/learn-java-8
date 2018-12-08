package com.example.mandelbrot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageInvert  {
	private static String IN_FILENAME = "Mandelbrot.jpg";
	private static String OUT_FILENAME = "Mandelbrot01.jpg";
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

	private static void invert() {
		try  {
			BufferedImage image = ImageIO.read(new File(IN_FILENAME));
			int width = image.getWidth();
			int height = image.getHeight();
			int[] source = image.getRGB(0, 0, width, height, null, 0, width);
	        int[] destination = new int[source.length];
	        long startTime = System.currentTimeMillis();
	        for (int i = 0; i < 1000; i++) {
	        	ImageInvert imageInverter = new ImageInvert(source, 0, source.length, destination);
	        	imageInverter.compute();
	        }
	        long endTime = System.currentTimeMillis();

	        System.out.println("Image inversion took " + (endTime - startTime) + 
	                " milliseconds.");
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

	public void compute() {
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
	}
}
// Snippets for later are here
//   	int size = source.length / numThreads;
//	    Thread[] threads = new Thread[numThreads];
//	    for (int t = 0; t < numThreads; t++) {
//	    	ImageInvert imager = new ImageInvert(source, t*size, size, destination);
//	    	threads[t] = new Thread(imager);
//	        threads[t].start();
//	    }
//	    for (int j = 0; j < numThreads; j++) {
//	    	threads[j].join();
//	    }
//	     
//
//		        ExecutorService e = Executors.newFixedThreadPool(numThreads);
//	        	int size = source.length / numThreads;
//	        	for (int t = 0; t < numThreads; t++) {
//	        		ImageInvert imager = new ImageInvert(source, t*size, size, destination);
//	        		e.execute(imager);
//	        	}
//	        	e.shutdown();

// change for loop to use the ForkJoinPool
//				ForkJoinPool pool = new ForkJoinPool();
//	        	ImageInvert imageInverter = new ImageInvert(source, 0, source.length, destination);
//	        	pool.invoke(imageInverter);	        	


// compute method with threshold test and decomposing
//	@Override
//	public void compute() {
//		if (length < computeThreshold) {
//		       for (int i = start; i < start + length; i++) {
//		            int pixel = sourceArray[i];
//		            int red = 255 - ((pixel & 0x00ff0000) >> 16);
//		            int green = 255 - ((pixel & 0x0000ff00) >> 8);
//		            int blue = 255 - ((pixel & 0x000000ff) >> 0);
//		            destinationArray[i] = (0xff000000)
//		                    | ((red) << 16)
//		                    | ((green) << 8)
//		                    | ((blue) << 0);
//		       }
//			return;
//		} else {
//	       int split = length / 2;
//
//	        invokeAll(new ImageInvert(sourceArray, start, split, destinationArray),
//	                new ImageInvert(sourceArray, start + split, length - split, 
//	                destinationArray));
//		}
//	}


//	put synchronized around the update of the destination array
//		            synchronized (destinationArray) {
//		            destinationArray[i] = (0xff000000)
//		                    | ((red) << 16)
//		                    | ((green) << 8)
//		                    | ((blue) << 0);
//		            }

//
