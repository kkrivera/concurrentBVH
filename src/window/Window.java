package window;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import math.util.MathUtil;

public class Window
{
	private static int width;
	private static int height;
	public static BufferedImage raster;

	public static void initWindow(int windowWidth, int windowHeight)
	{
		width = windowWidth;
		height = windowHeight;
		raster = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		init();
	}

	/**
	 * Shows the raster in a window
	 */
	public static void showRaster()
	{
		ImageIcon icon = new ImageIcon(raster);
		JLabel label = new JLabel(icon, JLabel.CENTER);
		JOptionPane.showMessageDialog(null, label, "Ray Tracing", -1);
	}

	/**
	 * Sets the color of a pixel in the raster
	 * 
	 * @param i
	 *        X pixel coordinate
	 * @param j
	 *        Y pixel coordinate
	 * @param hexColor
	 *        The color of the pixel in hexadecimal
	 */
	public static void setPixelColor(int i, int j, String hexColor)
	{
		raster.setRGB(i, j, MathUtil.h2d(hexColor));
	}

	/**
	 * Sets the raster to a default color
	 */
	public static void clearRaster()
	{
		for (int i = 0; i < raster.getWidth(); i++)
		{
			for (int j = 0; j < raster.getHeight(); j++)
			{
				setPixelColor(i, j, "FF0000");
			}
		}
	}

	public static JFrame jFrame;

	private static void init()
	{
		jFrame = new JFrame("Java Ray Tracer");
		jFrame.setSize(width, height);
		Container content = jFrame.getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new FlowLayout());
		// content.add(new JButton("RayTrace"));
		jFrame.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{
				System.out.print(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				System.out.print(e.getKeyCode());
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				System.out.print(e.getKeyCode());
			}
		});
		jFrame.addWindowListener(new WindowListener()
		{

			@Override
			public void windowOpened(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				System.exit(0);
			}

			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}
		});

		// jFrame.setVisible(true);
	}
}
