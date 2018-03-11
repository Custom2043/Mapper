package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.lwjgl.openal.AL10;

import com.badlogic.audio.analysis.FFT;

import audio.AudioBuffer;
import audio.AudioSystem;
import audio.CodecMP3;
import audio.CodecWav;
import drawer.Drawer;
import drawer.FFTModel;
import drawer.Matrix2DShader;
import drawer.VAOLoader;
import util.Matrix4f;
import util.Window;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		boolean log = true, power = false;
		
		String music = "GNE.mp3";
		int size = 65536;
		Window.createWindow(size+2, 300, "Test");
		Window.setup();
		
		AudioSystem.setDefaultBuffers(65536, 200000000, 3);
		InputStream stream = new BufferedInputStream(new FileInputStream(new File(music)));
		CodecMP3 mp3 = new CodecMP3(stream);
		AudioBuffer buf = mp3.readAll();
		System.out.println(buf.audioData.length);
		System.out.println(buf.audioFormat);
		int bytes = buf.audioFormat == AL10.AL_FORMAT_STEREO16 ? 4 : 2;
		stream.close();
		float[] test = new float[size];
		float[] fft = new float[size / 2 + 1];
		FFT f = new FFT(size, buf.samplerate);
		
		FFTModel model = new FFTModel(fft.length);
		int j = 0;
		
		AudioSystem.init();
		AudioSystem.setDefaultCodec(CodecMP3.class);
		int sound = AudioSystem.loadSound(new FileInputStream(new File(music)));
		int source = AudioSystem.newSoundSource(sound, false, true);
		AudioSystem.play(source);
		
		while (!Window.shouldClose())
		{
			for (int i=0;i<size;i++)
			{
				test[i] = readShort(buf.audioData, i*bytes + j);
				if (power)
					test[i] = (float)Math.log(test[i] * 100);
			}
			
			j = AudioSystem.getOffset(source, AudioSystem.BYTE_OFFSET);
			f.forward(test);
		    System.arraycopy(f.getSpectrum(), 0, fft, 0, fft.length);
		    
		    if (log)
			    for (int i=0;i<fft.length;i++)
			    	fft[i] = (float)Math.log(1 + fft[i]/20) * 100;
		    float max = 0;
		    for (int i=0;i<fft.length;i++)
		    	if (fft[i] > max)
		    		max = fft[i];
		    System.out.println(max);
		    
		    for (int i=0;i<fft.length;i++)
		    	fft[i] /= max / 200f;
		    
		    Drawer.fftShader.start();
			Drawer.fftShader.loadFFT(fft);
			Drawer.drawModel(model);
			Window.update();
		}
		AudioSystem.quit();
		VAOLoader.quit();
		Window.quit();
	}
	public static float readShort(byte[] b, int indice)
	{
		byte b1 = b[indice];
		byte b2 = b[indice + 1];
		return ((b1) + (b2) * 256f) / 32876f;
	}
	public static void updateWindow(int w, int h)
	{
		Matrix2DShader.setMatrix(Matrix4f.createOrthographicMatrix());
		Matrix2DShader.setScreenData(w, h);
	}
}
