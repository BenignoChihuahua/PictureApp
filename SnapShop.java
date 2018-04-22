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
	
		imageFilters = new ImageFilterPanel(this);
		this.getContentPane().add(imageFilters, BorderLayout.WEST);
		
		fl = new FileLoader(this);
		this.getContentPane().add(fl,BorderLayout.NORTH);
		
		SnapShopConfiguration.configure(this);

		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args)
	{

		SnapShop s = new SnapShop();
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
				ip.loadImage(fileName);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(s,
						"Could not open file",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		public void setDefaultFilename(String fileName) {
			filenameBox.setText(fileName);
		}

	}

	public void addFilter(Filter f, String name)
	{
		imageFilters.addImageFilter(f);
	}

	public void setDefaultFilename(String name)
	{
		fl.setDefaultFilename(name);
	}
	public ImagePanel getImagePanel() 
	{

		return ip;
	}

}
