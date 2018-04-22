import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;

public class ImagePanel extends JPanel
{
	
	private SnapShop s;
	private BufferedImage filteredImage;
	private BufferedImage unfilteredImage;
	private int imageWidth;
	private int imageHeight;

	public ImagePanel(SnapShop s)
	{

		unfilteredImage = null;
		filteredImage = null;
		imageWidth = 0;
		imageHeight = 0;
		this.s = s;
	}

	public void loadImage(String filename) 
	{
		Image img = Toolkit.getDefaultToolkit().getImage(filename);
		try {
			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(img,0);
			tracker.waitForID(0);
		}catch(Exception e) {}
		imageWidth = img.getWidth(this);
		imageHeight = img.getHeight(this);
		
		unfilteredImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);			
		filteredImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D imageContext = unfilteredImage.createGraphics();
		imageContext.drawImage(img, 0, 0, null);
		setPreferredSize(new Dimension(unfilteredImage.getWidth(), unfilteredImage.getHeight()));
		
		Graphics2D fimageContext = filteredImage.createGraphics();
		fimageContext.drawImage(img,0,0,null);
		setPreferredSize(new Dimension(filteredImage.getWidth(), filteredImage.getHeight()));
		
		revalidate();
		s.pack();
		s.repaint();
	}


	public void paint(Graphics g) {
		super.paint(g);
		if(filteredImage != null)
			g.drawImage(filteredImage, 0, 0, this);
	}

	public void applyFilter(Filter f)
	{
		if(unfilteredImage == null)
			return;
		BufferedImage unfiltered = copyImage(unfilteredImage);
      		PixelImage newImage = new PixelImage(unfiltered);
		f.filter(newImage);
		filteredImage = newImage.getImage();
		repaint();

	
	}

	public BufferedImage copyImage(BufferedImage source) 
	{
		if(source == null)
			return source;
		BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight(),source.getType());
		Graphics g = bi.getGraphics();
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return bi;
	}

	public BufferedImage deepCopy(BufferedImage bi)
	{
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public BufferedImage getImageCopy()
	{
		return copyImage(unfilteredImage);
	}

	public int getHeight()
	{
		return imageHeight;
	}

	public int getWidth()
	{
		return imageWidth;
	}

	public BufferedImage getUnfilteredImage()
	{
		return unfilteredImage;
	}

	public BufferedImage getFilteredImage()
	{
		return filteredImage;
	}

}
