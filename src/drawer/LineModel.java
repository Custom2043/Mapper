package drawer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import util.QuadColor;
import util.ScreenCoor;
import util.TextureCoor;

public class LineModel extends ScreenCoorModel
{
	public byte[] types;
	public LineModel(ScreenCoor[] sc, QuadColor[] c, TextureCoor[] tc, byte[] t, Texture tt)
	{
		super(sc, c, tc, tt);
		types = t;
		this.enableVertexArray(4);
		VAOLoader.storeBufferInAttributeList(4, 1, storeTypes(), GL11.GL_FLOAT);
	}
	public void updateModel(ScreenCoor[] sc, QuadColor[] c, TextureCoor[] tc, byte[] t)
	{
		this.vertexNumber = sc.length * 4;
		coor = sc; colors = c; textCoors = tc; types = t;
		
		VAOLoader.bind(vaoId);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVBO(0));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeX(), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVBO(1));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeY(), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVBO(2));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeColors(), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVBO(3));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeTextureCoors(), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVBO(4));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeTypes(), GL15.GL_STATIC_DRAW);
	}
	protected ByteBuffer storeTypes()
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(this.vertexNumber*4); // Textures
		for (byte b : types)
			for (int i=0;i<4;i++)
				buf.putFloat(b);
		buf.flip();
		return buf;
	}
}
