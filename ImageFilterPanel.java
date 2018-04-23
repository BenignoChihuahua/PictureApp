import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.awt.geom.AffineTransform;


/**
 * @author Benigno Vargas
 * @version 0.1.0 
 */
public class ImageFilterPanel extends JScrollPane
{

	
	private ImagePanel imageDisplay;
	private Filter videoFilter;
	private static JPanel enclosingPanel = new JPanel();
	private boolean inCameraMode = false;


	private static boolean newImage = false;
	private static final int imageWidth = 250;
	private static final int imageHeight = 125;

	public ImageFilterPanel(SnapShop s)
	{
		super(enclosingPanel);
		enclosingPanel.setLayout(new BoxLayout(enclosingPanel, BoxLayout.Y_AXIS));	
		super.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		imageDisplay = s.getImagePanel();
		videoFilter = new NormalFilter();
		
	}

	public void addImageFilter(Filter f)
	{
		enclosingPanel.add(new ImageFilterButton(f, this));
		
	}

	private class ImageFilterButton extends JPanel
	{
		private Filter imageFilter;
		private BufferedImage filteredImage;
		private ImageFilterPanel imagePanel;
		private boolean hasBeenFiltered;


		public ImageFilterButton(Filter f, ImageFilterPanel imagePanel)
		{

			super();
			this.imagePanel = imagePanel;
			imageFilter = f;
			hasBeenFiltered = false;
			filterImage(imagePanel.getUnfilteredImage());
			setPreferredSize(new Dimension(250, 125));
			
			addMouseListener(new MouseAdapter(){	
				

				@Override
				public void mouseClicked(MouseEvent e)
				{	
					imagePanel.applyFilter(imageFilter);
				}

			});
		}
		
		public void paint(Graphics g) {
			super.paint(g); 
		
			if(ImageFilterPanel.gotNewImage())
				hasBeenFiltered = false;			
		        filterImage(imagePanel.getUnfilteredImage());	
			g.drawImage(filteredImage, 0, 0, this);
			
		}


		public void filterImage(BufferedImage image)
		{

			if(image == null || hasBeenFiltered)
				return;
						
			PixelImage sampleImage  = new PixelImage(image);
			imageFilter.filter(sampleImage);
			filteredImage = sampleImage.getImage();
			
			hasBeenFiltered = true;
						
		}

	}

	public void applyFilter(Filter f)
	{
		if(inCameraMode)
			videoFilter = f;
		try
		{
			imageDisplay.applyFilter(f);
		}catch(Exception e) {System.out.println("Failure in Filtering");}

	}

	public Filter getCameraFilter()
	{
		return videoFilter;
	}

	public void setCameraMode(boolean state)
	{
		inCameraMode = state;
	}

	public static void receivedNewImage(boolean b)
	{
		newImage = b;
	}

	public static boolean gotNewImage()
	{
		return newImage;
	}

	public BufferedImage getUnfilteredImage()
	{
		BufferedImage sourceImg = imageDisplay.getImageCopy();
		BufferedImage scaledImg = null;
		
		if(sourceImg != null)
		{
			double scaleW = ((double)(imageWidth)/imageDisplay.getWidth());
			double scaleH = ((double)(imageHeight)/imageDisplay.getHeight());
			scaledImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D imageContext = scaledImg.createGraphics();

			AffineTransform at = AffineTransform.getScaleInstance(scaleW,scaleH);
			imageContext.drawRenderedImage(sourceImg, at);
		}

		return scaledImg;
	}

	public BufferedImage getDisplayedImage()
	{
		return imageDisplay.getFilteredImage();
	}



}
