import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Provides camera input to the main filter application. The camera-feed runs on 
 * its own thread to improve the speed of the overall application.
 *
 * @author Benigno Vargas Chihuahua
 * @version 0.1.0
 */
public class WebCam implements Runnable
{

	private VideoCapture cameraFeed;
	private BufferedImage videoFrame;
	private ImagePanel imagePanel;
	private ImageFilterPanel imageFilters;
	private BufferedImage takenFrame;
	private boolean imageHasBeenTaken = false;
	private boolean hasLoadedImage = false;

	public WebCam(SnapShop s)
	{
		imagePanel = s.getImagePanel();
		imageFilters = s.getImageFilters();
		imageFilters.setCameraMode(true);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		cameraFeed = new VideoCapture(0);
	}

	@Override 
	public void run()
	{
		while(true)
		{
			if(imageHasBeenTaken)
			{
				if(!hasLoadedImage)
				{
					imagePanel.loadBufferedImage(takenFrame);
					hasLoadedImage = true;
				}
			}	
			else
				imagePanel.loadBufferedImage(getLatestFrame());
		}
	}

	public BufferedImage matToBufferedImage(Mat original)
	{
		// init

		BufferedImage image = null;
		if(original == null || original.width() <= 0 || original.height() <= 0)
			return image;
		int width = original.width(), height = original.height(), channels = original.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		original.get(0, 0, sourcePixels);
		
		if (original.channels() > 1)
			image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		else
			image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
		
		return image;
	}

	public BufferedImage getLatestFrame()
	{
		Mat frame = new Mat();
		if(this.cameraFeed.isOpened())
		{	
			cameraFeed.read(frame);
			//Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
		}
		return matToBufferedImage(frame);
	}

	public void takeImage()
	{
		imageHasBeenTaken = true;
		takenFrame = getLatestFrame();
	}


	

}