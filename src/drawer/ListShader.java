package drawer;

public class ListShader extends ScreenCoorShader
{
	private int location_start, location_fading;
	public ListShader(String vertexFile, String fragmentFile)
	{
		super(vertexFile, fragmentFile);
	}
	@Override
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		this.location_start = super.getUniformLocation("start");
		this.location_fading = super.getUniformLocation("fading");
	}
	public void loadStart(float x, float y)
	{
		this.loadVector2f(location_start, x, y);
	}
	public void loadFading(float f, float yStart, float height)
	{
		this.loadVector(location_fading, f, yStart, height);
	}
}
