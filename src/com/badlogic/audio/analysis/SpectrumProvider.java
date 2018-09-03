package com.badlogic.audio.analysis;

import com.badlogic.audio.io.Decoder;

/**
 * Provides float[] arrays of successive spectrum frames retrieved via
 * FFT from a Decoder. The frames might overlapp by n samples also called
 * the hop size. Using a hop size smaller than the spectrum size is beneficial
 * in most cases as it smears out the spectra of successive frames somewhat.
 * @author mzechner
 *
 */
public class SpectrumProvider
{
	/** the decoder to use **/
	private final Decoder decoder;

	/** the current sample array **/
	private float[] samples;

	/** the look ahead sample array **/
	private float[] nextSamples;

	/** temporary samples array **/
	private float[] tempSamples;

	/** the current sample, always modulo sample window size **/
	private int currentSample = 0;

	/** the hop size **/
	private final int hopSize;

	/** the fft **/
	private final FFT fft;

	/**
	 * Constructor, sets the {@link Decoder}, the sample window size and the
	 * hop size for the spectra returned. Say the sample window size is 1024
	 * samples. To get an overlapp of 50% you specify a hop size of 512 samples,
	 * for 25% overlap you specify a hopsize of 256 and so on. Hop sizes are of
	 * course not limited to powers of 2.
	 *
	 * @param decoder The decoder to get the samples from.
	 * @param sampleWindowSize The sample window size.
	 * @param hopSize The hop size.
	 * @param useHamming Wheter to use hamming smoothing or not.
	 */
	public SpectrumProvider( Decoder decoder, int sampleWindowSize, int hopSize, boolean useHamming )
	{
		if( decoder == null )
			throw new IllegalArgumentException( "Decoder must be != null" );

		if( sampleWindowSize <= 0 )
			throw new IllegalArgumentException( "Sample window size must be > 0" );
		if( hopSize <= 0 )
			throw new IllegalArgumentException( "Hop size must be > 0" );

		if( sampleWindowSize < hopSize )
			throw new IllegalArgumentException( "Hop size must be <= sampleSize" );


		this.decoder = decoder;
		this.samples = new float[sampleWindowSize];
		this.nextSamples = new float[sampleWindowSize];
		this.tempSamples = new float[sampleWindowSize];
		this.hopSize = hopSize;
		this.fft = new FFT( sampleWindowSize, 44100 );
		if( useHamming )
			this.fft.window(FourierTransform.HAMMING);

		decoder.readSamples( this.samples );
		decoder.readSamples( this.nextSamples );
	}

	/**
	 * Returns the next spectrum or null if there's no more data.
	 * @return The next spectrum or null.
	 */
	public float[] nextSpectrum( )
	{
		if( this.currentSample >= this.samples.length )
		{
			float[] tmp = this.nextSamples;
			this.nextSamples = this.samples;
			this.samples = tmp;
			if( this.decoder.readSamples( this.nextSamples ) == 0 )
				return null;
			this.currentSample -= this.samples.length;
		}

		System.arraycopy( this.samples, this.currentSample, this.tempSamples, 0, this.samples.length - this.currentSample );
		System.arraycopy( this.nextSamples, 0, this.tempSamples, this.samples.length - this.currentSample, this.currentSample );
		this.fft.forward( this.tempSamples );
		this.currentSample += this.hopSize;
		return this.fft.getSpectrum();
	}

	/**
	 * @return the FFT instance used
	 */
	public FFT getFFT( )
	{
		return this.fft;
	}
}
