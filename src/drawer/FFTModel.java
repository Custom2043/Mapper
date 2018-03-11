package drawer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import util.Window;

public class FFTModel extends Model
{
	public FFTModel(int number)
	{
		super(number * 4);
		this.enableVertexArray(0);
		VAOLoader.storeBufferInAttributeList(0, 3, storeDatas(), GL11.GL_FLOAT); 
	}
	
	public ByteBuffer storeDatas()
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(this.vertexNumber * 12);
		for (int i=0;i<vertexNumber/4;i++)
		{
			buf.putFloat(i * (Window.getWidth() / (vertexNumber / 4f)));
			buf.putFloat(0);
			buf.putFloat(i);
			
			buf.putFloat(i * (Window.getWidth() / (vertexNumber / 4f)));
			buf.putFloat(1);
			buf.putFloat(i);
			
			buf.putFloat((i+1f) * (Window.getWidth() / (vertexNumber / 4f)));
			buf.putFloat(1);
			buf.putFloat(i);
			
			buf.putFloat((i+1f) * (Window.getWidth() / (vertexNumber / 4f)));
			buf.putFloat(0);
			buf.putFloat(i);
		}
		buf.flip();
		return buf;
	}
}