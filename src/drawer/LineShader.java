package drawer;

import org.lwjgl.opengl.GL20;

public class LineShader extends ScreenCoorShader
{
	private int location_text, location_lineStart, location_bold, location_italic, location_bold_italic;
	public LineShader(String vertexFile, String fragmentFile)
	{
		super(vertexFile, fragmentFile);
	}
	@Override
	protected void bindAttributes()
	{
		super.bindAttributes();
		super.bindAttribute(4, "textureUsed");
	}
	@Override
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		this.location_text = super.getUniformLocation("text");
		this.location_lineStart = super.getUniformLocation("lineStart");
		this.location_bold = super.getUniformLocation("boldText");
		this.location_italic = super.getUniformLocation("italicText");
		this.location_bold_italic = super.getUniformLocation("boldItalic");
		this.start();
		GL20.glUniform1i(this.location_text, 0);
		GL20.glUniform1i(this.location_bold, 1);
		GL20.glUniform1i(this.location_italic, 2);
		GL20.glUniform1i(this.location_bold_italic, 3);
	}
	public void loadLineStart(float x, float y)
	{
		this.loadVector2f(this.location_lineStart, x, y);
	}
}
