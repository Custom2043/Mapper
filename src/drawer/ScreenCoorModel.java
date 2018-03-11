package drawer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import util.QuadColor;
import util.ScreenCoor;
import util.TextureCoor;

public class ScreenCoorModel extends ColoredScreenCoorModel
{
	public Texture tt;
	public TextureCoor textCoors[];
	public ScreenCoorModel(ScreenCoor sc, QuadColor c, TextureCoor tc, Texture text)
	{
		this(new ScreenCoor[]{sc}, new QuadColor[]{c}, new TextureCoor[]{tc}, text);
	}
	public ScreenCoorModel(ScreenCoor[] sc, QuadColor[] c, TextureCoor[] tc, Texture text)
	{
		super(sc, c);
		this.textCoors = tc;
		tt = text;
		this.enableVertexArray(3);
		VAOLoader.storeBufferInAttributeList(3, 2, this.storeTextureCoors(), GL11.GL_FLOAT);
	}
	protected ByteBuffer storeTextureCoors()
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(this.vertexNumber * 8); // Textures
		if (tt != null)
			for (TextureCoor t : textCoors)
				for (float i : t.inFloatArray(tt))
					buf.putFloat(i);
		else
			for (int i=0;i<textCoors.length * 8;i++)
				buf.putFloat(-1);
		buf.flip();
		return buf;
	}
}
