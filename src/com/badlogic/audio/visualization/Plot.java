package com.badlogic.audio.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * A simple class that allows to plot float[] arrays
 * to a swing window. The first function to plot that
 * is given to this class will set the minimum and
 * maximum height values. I'm not that good with Swing
 * so i might have done a couple of stupid things in here :)
 *
 * @author mzechner
 *
 */
public class Plot
{
	/** the frame **/
	private JFrame frame;

	/** the scroll pane **/
	private JScrollPane scrollPane;

	/** the image gui component **/
	private JPanel panel;

	/** the image **/
	private BufferedImage image;

	/** the last scaling factor to normalize samples **/
	private float scalingFactor = 1;

	/** wheter the plot was cleared, if true we have to recalculate the scaling factor **/
	private boolean cleared = true;

	/** current marker position and color **/
	private int markerPosition = 0;
	private Color markerColor = Color.white;

	/**
	 * Creates a new Plot with the given title and dimensions.
	 *
	 * @param title The title.
	 * @param width The width of the plot in pixels.
	 * @param height The height of the plot in pixels.
	 */
	public Plot( final String title, final int width, final int height )
	{
		this.image = new BufferedImage( 1, 1, BufferedImage.TYPE_4BYTE_ABGR );

		try
		{
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run()
				{
					Plot.this.frame = new JFrame( title );
					Plot.this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
					Plot.this.frame.setPreferredSize( new Dimension( width + Plot.this.frame.getInsets().left + Plot.this.frame.getInsets().right, Plot.this.frame.getInsets().top + Plot.this.frame.getInsets().bottom + height ) );
					BufferedImage img = new BufferedImage( width, height, BufferedImage.TYPE_4BYTE_ABGR );
					Graphics2D g = (Graphics2D)img.getGraphics();
					g.setColor( Color.black );
					g.fillRect( 0, 0, width, height );
					g.dispose();
					Plot.this.image = img;
					Plot.this.panel = new JPanel( ) {

							@Override
							public void paintComponent( Graphics g )
							{
								super.paintComponent(g);
								synchronized( Plot.this.image )
								{
									g.drawImage( Plot.this.image, 0, 0, null );
									g.setColor( Plot.this.markerColor );
									g.drawLine( Plot.this.markerPosition, 0, Plot.this.markerPosition, Plot.this.image.getHeight() );
								}

								Thread.yield();


								Plot.this.frame.repaint();
							}

							@Override
							public void update(Graphics g){
								this.paint(g);
							}

							@Override
							public Dimension getPreferredSize()
							{
								return new Dimension( Plot.this.image.getWidth(), Plot.this.image.getHeight( ) );
							}
						};
//					panel.setPreferredSize( new Dimension( width, height ) );
					Plot.this.scrollPane = new JScrollPane( Plot.this.panel );
					Plot.this.frame.getContentPane().add(Plot.this.scrollPane);
					Plot.this.frame.pack();
					Plot.this.frame.setVisible( true );

				}
			});
		}
		catch( Exception ex )
		{
			// doh...
		}
	}

	public void clear( )
	{
		SwingUtilities.invokeLater( new Runnable( ) {

			@Override
			public void run() {
				Graphics2D g = Plot.this.image.createGraphics();
				g.setColor( Color.black );
				g.fillRect( 0, 0, Plot.this.image.getWidth(), Plot.this.image.getHeight() );
				g.dispose();
				Plot.this.cleared = true;
			}
		});
	}

	public void plot( float[] samples, final float samplesPerPixel, final Color color )
	{
		synchronized( this.image )
		{
			if( this.image.getWidth() <  samples.length / samplesPerPixel )
			{
				this.image = new BufferedImage( (int)(samples.length / samplesPerPixel), this.frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
				Graphics2D g = this.image.createGraphics();
				g.setColor( Color.black );
				g.fillRect( 0, 0, this.image.getWidth(), this.image.getHeight() );
				g.dispose();
				this.panel.setSize( this.image.getWidth(), this.image.getHeight( ));
			}

			if( this.cleared )
			{
				float min = 0;
				float max = 0;
				for( int i = 0; i < samples.length; i++ )
				{
					min = Math.min( samples[i], min );
					max = Math.max( samples[i], max );
				}
				this.scalingFactor = max - min;
				this.cleared = false;
			}

			Graphics2D g = this.image.createGraphics();
			g.setColor( color );
			float lastValue = (samples[0] / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2;
			for( int i = 1; i < samples.length; i++ )
			{
				float value = (samples[i] / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2;
				g.drawLine( (int)((i-1) / samplesPerPixel), this.image.getHeight() - (int)lastValue, (int)(i / samplesPerPixel), this.image.getHeight() - (int)value );
				lastValue = value;
			}
			g.dispose();
		}
	}

	public void plot( List<Float> samples, final float samplesPerPixel, final Color color )
	{
		synchronized( this.image )
		{
			if( this.image.getWidth() <  samples.size() / samplesPerPixel )
			{
				this.image = new BufferedImage( (int)(samples.size() / samplesPerPixel), this.frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
				Graphics2D g = this.image.createGraphics();
				g.setColor( Color.black );
				g.fillRect( 0, 0, this.image.getWidth(), this.image.getHeight() );
				g.dispose();
				this.panel.setSize( this.image.getWidth(), this.image.getHeight( ));
			}

			if( this.cleared )
			{
				float min = 0;
				float max = 0;
				for( int i = 0; i < samples.size(); i++ )
				{
					min = Math.min( samples.get(i), min );
					max = Math.max( samples.get(i), max );
				}
				this.scalingFactor = max - min;
				this.cleared = false;
			}

			Graphics2D g = this.image.createGraphics();
			g.setColor( color );
			float lastValue = (samples.get(0) / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2;
			for( int i = 1; i < samples.size(); i++ )
			{
				float value = (samples.get(i) / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2;
				g.drawLine( (int)((i-1) / samplesPerPixel), this.image.getHeight() - (int)lastValue, (int)(i / samplesPerPixel), this.image.getHeight() - (int)value );
				lastValue = value;
			}
			g.dispose();
		}
	}

	public void plot( float[] samples, final float samplesPerPixel, final float offset, final boolean useLastScale, final Color color )
	{
		synchronized( this.image )
		{
			if( this.image.getWidth() <  samples.length / samplesPerPixel )
			{
				this.image = new BufferedImage( (int)(samples.length / samplesPerPixel), this.frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
				Graphics2D g = this.image.createGraphics();
				g.setColor( Color.black );
				g.fillRect( 0, 0, this.image.getWidth(), this.image.getHeight() );
				g.dispose();
				this.panel.setSize( this.image.getWidth(), this.image.getHeight( ));
			}

			if( !useLastScale )
			{
				float min = 0;
				float max = 0;
				for( int i = 0; i < samples.length; i++ )
				{
					min = Math.min( samples[i], min );
					max = Math.max( samples[i], max );
				}
				this.scalingFactor = max - min;
			}

			Graphics2D g = this.image.createGraphics();
			g.setColor( color );
			float lastValue = (samples[0] / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2 - offset * this.image.getHeight() / 3;
			for( int i = 1; i < samples.length; i++ )
			{
				float value = (samples[i] / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2 - offset * this.image.getHeight() / 3;
				g.drawLine( (int)((i-1) / samplesPerPixel), this.image.getHeight() - (int)lastValue, (int)(i / samplesPerPixel), this.image.getHeight() - (int)value );
				lastValue = value;
			}
			g.dispose();
		}
	}

	public void plot( List<Float> samples, final float samplesPerPixel, final float offset, final boolean useLastScale, final Color color )
	{
		synchronized( this.image )
		{
			if( this.image.getWidth() <  samples.size() / samplesPerPixel )
			{
				this.image = new BufferedImage( (int)(samples.size() / samplesPerPixel), this.frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
				Graphics2D g = this.image.createGraphics();
				g.setColor( Color.black );
				g.fillRect( 0, 0, this.image.getWidth(), this.image.getHeight() );
				g.dispose();
				this.panel.setSize( this.image.getWidth(), this.image.getHeight( ));
			}

			if( !useLastScale )
			{
				float min = 0;
				float max = 0;
				for( int i = 0; i < samples.size(); i++ )
				{
					min = Math.min( samples.get(i), min );
					max = Math.max( samples.get(i), max );
				}
				this.scalingFactor = max - min;
			}

			Graphics2D g = this.image.createGraphics();
			g.setColor( color );
			float lastValue = (samples.get(0) / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2 - offset * this.image.getHeight() / 3;
			for( int i = 1; i < samples.size(); i++ )
			{
				float value = (samples.get(i) / this.scalingFactor) * this.image.getHeight() / 3 + this.image.getHeight() / 2 - offset * this.image.getHeight() / 3;
				g.drawLine( (int)((i-1) / samplesPerPixel), this.image.getHeight() - (int)lastValue, (int)(i / samplesPerPixel), this.image.getHeight() - (int)value );
				lastValue = value;
			}
			g.dispose();
		}
	}

	public void setMarker( int x, Color color )
	{
			this.markerPosition = x;
			this.markerColor = color;
	}
}
