package drawer;

import util.Matrix4f;
import util.ScreenData;

public abstract class Matrix2DShader extends ShaderProgram
{
	private ScreenData screenData;
	private Matrix4f currentMatrix;
	private static Matrix4f trueMatrix;
	private static ScreenData trueScreenData;
	private int location_projectionMatrix, location_ScreenData;

	public Matrix2DShader(String vertexFile, String fragmentFile)
	{
		super(vertexFile, fragmentFile);
	}
	private void loadProjectionMatrix()
	{
		this.loadMatrix(this.location_projectionMatrix, this.currentMatrix);
	}
	private void loadScreenData()
	{
		this.loadVector2f(this.location_ScreenData, this.screenData.width, this.screenData.height);
	}
    @Override
	public void start()
	{
		super.start();
		if (this.currentMatrix != trueMatrix)
		{
			this.currentMatrix = trueMatrix;
			this.screenData = trueScreenData;
			this.loadProjectionMatrix();
			this.loadScreenData();
		}
	}
	@Override
	protected void getAllUniformLocations()
	{
		this.location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		this.location_ScreenData = super.getUniformLocation("screen");
	}
	public static void setMatrix(Matrix4f m){trueMatrix = m;}
	public static void setScreenData(int w, int h){trueScreenData = new ScreenData(w, h);}
}
