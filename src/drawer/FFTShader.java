package drawer;

import org.lwjgl.opengl.GL20;

public class FFTShader extends Matrix2DShader
{
	private int location_fft;
	public FFTShader(String vertexFile, String fragmentFile)
	{
		super(vertexFile, fragmentFile);
	}
	
	protected void bindAttributes()
	{
		super.bindAttribute(0, "datas");
	}
	
	@Override
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		this.location_fft = super.getUniformLocation("fft");
	}
	
	public void loadFFT(float[] fft)
	{
		GL20.glUniform1fv(location_fft, fft);
	}
}
