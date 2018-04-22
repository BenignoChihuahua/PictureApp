import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;

/*
 *SnapShop is the main user-image point of interaction, displays the filtered or unfiltered
 *image or camera feed to the user.  
 *
 *@author Benigno Vargas
 *@version 0.1.0
 */

public class SnapShop extends JFrame
{

	private ImageFilterPanel imageFilters;
	private ImagePanel ip;
	private FileLoader fl;

	/**
	 *Constructor for subset panels of SnapShop frame
	 */
	public SnapShop()
	{
		super("CameraFun");

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { System.exit(0); }	
		});

		ip = new ImagePanel(this);
		this.getContentPane().add(ip, BorderLayout.CENTER);
	
		imageFilters = new ImageFilterPanel(ip);
		this.getContentPane().add(imageFilters, BorderLayout.WEST);
		
		fl = new FileLoader(this);
		this.getContentPane().add(fl,BorderLayout.NORTH);
		
		SnapShopConfiguration.configure(this);

		this.pack();
		this.setVisible();
	}

	private class ImagePanel extends JPanel
	{
		private BufferedImage image;
		private SnapShop parentFrame;

		public ImagePanel(SnapShop s)
		{
			image = null;
			parentFrame = s;
		}

		public void loadImage(String filename) {
			Image img = Toolkit.getDefaultToolkit().getImage(filename);
			try{

			}catch(Exception e) {}
			int width = img.getWidth(this);
			int height = img.getHeight(this);
			image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D imageContext = image.createGraphics();
			imageContext.drawImage(img,0,0,null);
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
			revalidate();
			parentFrame.pack();
			parentFrame.repaint();
		}

		public void paint(Graphics g) {
			super.paint(g);
			if(image != null)
				g.drawImage(image, 0, 0, this);
		}	

		public void applyFilter(Filter f) {
			if(image == null)
				return;
			PixelImage newImage = new PixelImage(image);
			f.filter(newImage);
			image = newImage.getImage();
			repaint();
		}

	}
	
	public class FileLoader extends JPanel implements ActionListener {
		
		private JTextArea filenameBox;
		private ImagePanel ip;
		private SnapShop s;

		public FileLoader(SnapShop s)
		{
			super(new FlowLayout());

			this.s = s;
			this.ip = s.getImagePanel();
			
			add(new JLabel("Enter file name: "));

			filenameBox = new JTextArea(1,50);
			add(filenameBox);

			JButton loadButton = new JButton("Load");
			loadButton.addActionListener(this);
			add(loadButton);
		}

		public void actionPerformed(ActionEvent e)
		{
			String fileName = filenameBox.getText();
			try {

			} catch(Exception ex) {
				JOptionPane.showMessageDialog(s,
						"Could not open file",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		public void setDefaultFilename(String fileName) {
			filenameBox.setText(filename);
		}

	}

}
