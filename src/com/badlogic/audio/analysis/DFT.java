/*
 *  Copyright (c) 2007 - 2008 by Damien Di Fede <ddf@compartmental.net>
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package com.badlogic.audio.analysis;


/**
 * DFT stands for Discrete Fourier Transform and is the most widely used Fourier
 * Transform. You will never want to use this class due to the fact that it is a
 * brute force implementation of the DFT and as such is quite slow. Use an FFT
 * instead. This exists primarily as a way to ensure that other implementations
 * of the DFT are working properly. This implementation expects an even
 * <code>timeSize</code> and will throw and IllegalArgumentException if this
 * is not the case.
 *
 * @author Damien Di Fede
 *
 * @see FourierTransform
 * @see FFT
 * @see <a href="http://www.dspguide.com/ch8.htm">The Discrete Fourier Transform</a>
 *
 */
public class DFT extends FourierTransform
{
  /**
   * Constructs a DFT that expects audio buffers of length <code>timeSize</code> that
   * have been recorded with a sample rate of <code>sampleRate</code>. Will throw an
   * IllegalArgumentException if <code>timeSize</code> is not even.
   *
   * @param timeSize the length of the audio buffers you plan to analyze
   * @param sampleRate the sample rate of the audio samples you plan to analyze
   */
  public DFT(int timeSize, float sampleRate)
  {
    super(timeSize, sampleRate);
    if (timeSize % 2 != 0)
      throw new IllegalArgumentException("DFT: timeSize must be even.");
    this.buildTrigTables();
  }

  @Override
protected void allocateArrays()
  {
    this.spectrum = new float[this.timeSize / 2 + 1];
    this.real = new float[this.timeSize / 2 + 1];
    this.imag = new float[this.timeSize / 2 + 1];
  }

  /**
   * Not currently implemented.
   */
  @Override
public void scaleBand(int i, float s)
  {
  }

  /**
   * Not currently implemented.
   */
  @Override
public void setBand(int i, float a)
  {
  }

  @Override
public void forward(float[] samples)
  {
    if (samples.length != this.timeSize) throw new IllegalArgumentException("DFT.forward: The length of the passed sample buffer must be equal to DFT.timeSize().");
    this.doWindow(samples);
    int N = samples.length;
    for (int f = 0; f <= N / 2; f++)
    {
      this.real[f] = 0.0f;
      this.imag[f] = 0.0f;
      for (int t = 0; t < N; t++)
      {
        this.real[f] += samples[t] * this.cos(t * f);
        this.imag[f] += samples[t] * -this.sin(t * f);
      }
    }
    this.fillSpectrum();
  }

  @Override
public void inverse(float[] buffer)
  {
    int N = buffer.length;
    this.real[0] /= N;
    this.imag[0] = -this.imag[0] / (N / 2);
    this.real[N / 2] /= N;
    this.imag[N / 2] = -this.imag[0] / (N / 2);
    for (int i = 0; i < N / 2; i++)
    {
      this.real[i] /= (N / 2);
      this.imag[i] = -this.imag[i] / (N / 2);
    }
    for (int t = 0; t < N; t++)
    {
      buffer[t] = 0.0f;
      for (int f = 0; f < N / 2; f++)
		buffer[t] += this.real[f] * this.cos(t * f) + this.imag[f] * this.sin(t * f);
    }
  }

  // lookup table data and functions

  private float[] sinlookup;
  private float[] coslookup;

  private void buildTrigTables()
  {
    int N = this.spectrum.length * this.timeSize;
    this.sinlookup = new float[N];
    this.coslookup = new float[N];
    for (int i = 0; i < N; i++)
    {
      this.sinlookup[i] = (float) Math.sin(i * TWO_PI / this.timeSize);
      this.coslookup[i] = (float) Math.cos(i * TWO_PI / this.timeSize);
    }
  }

  private float sin(int i)
  {
    return this.sinlookup[i];
  }

  private float cos(int i)
  {
    return this.coslookup[i];
  }
}
