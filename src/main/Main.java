package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import audio.AudioBuffer;
import audio.AudioSystem;
import audio.CodecMP3;
import audio.SoundSource;
import drawer.Drawer;
import drawer.FFTModel;
import drawer.Matrix2DShader;
import drawer.VAOLoader;
import util.FileInputStreamSource;
import util.Matrix4f;
import util.Window;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		boolean log = true, power = false;

		String music = "Imprinting.mp3";
		int size = 1024;
		Window.createWindow(size+2, 300, "Test");
		Window.setup();

		AudioSystem.setDefaultBuffers(3, 65536, 200000000);
		InputStream stream = new BufferedInputStream(new FileInputStream(new File(music)));
		CodecMP3 mp3 = new CodecMP3(stream);
		AudioBuffer buf = mp3.readAll();
		mp3.quit();
		System.out.println(buf.getAudioDatas().length);
		System.out.println(buf.getCodec().getChannelsNumber());
		int bytes = 2;
		stream.close();
		float[] test = new float[size];
		float[] fft = new float[size / 2 + 1];
		//FFT f = new FFT(size, buf.getCodec().getSamplerate());
		FFT f = new FFT(size);

		FFTModel model = new FFTModel(fft.length);
		int j = 0;

		AudioSystem.init();
		AudioSystem.setDefaultCodec(CodecMP3.class);
		SoundSource source = AudioSystem.newSoundSource(new FileInputStreamSource(new File(music)));
		source.play();

		while (!Window.shouldClose())
		{
			for (int i=0;i<size;i++)
			{
				test[i] = readShort(buf.getAudioDatas(), (i+j)*bytes);
				if (power)
					test[i] = (float)Math.log(test[i] * 100);
			}

			j = source.getOffset();
			//f.forward(test);
		    System.arraycopy(f.fft(test), 0, fft, 0, fft.length);

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
		source.stop();
		source.delete();
		AudioSystem.quit();
		VAOLoader.quit();
		Window.quit();
	}
	public static float readShort(byte[] b, int indice)
	{
		byte b1 = b[indice];
		byte b2 = b[indice + 1];
		return ((b1) + (b2 << 8)) / 32786f;
	}
	public static void updateWindow(int w, int h)
	{
		Matrix2DShader.setMatrix(Matrix4f.createOrthographicMatrix());
		Matrix2DShader.setScreenData(w, h);
	}
}
