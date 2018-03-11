package drawer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import util.Color;
import util.QuadColor;
import util.ScreenCoor;

public class ColoredScreenCoorModel extends Model
{
	public ScreenCoor[] coor;
	public QuadColor[] colors;
	public ColoredScreenCoorModel(ScreenCoor sc, QuadColor c)
	{
		this(new ScreenCoor[]{sc}, new QuadColor[]{c});
	}
	protected ColoredScreenCoorModel(ScreenCoor[] sc, QuadColor[] c)
	{
		super(sc.length*4);
		coor = sc;
		colors = c;
		this.enableVertexArray(0);
		this.enableVertexArray(1);
		this.enableVertexArray(2);
		
		VAOLoader.storeBufferInAttributeList(0, 2, storeX(), GL11.GL_FLOAT); 
		VAOLoader.storeBufferInAttributeList(1, 2, storeY(), GL11.GL_FLOAT); 
		VAOLoader.storeBufferInAttributeList(2, 4, storeColors(), GL11.GL_UNSIGNED_BYTE);
	}
	public ByteBuffer storeX()
	{
		ByteBuffer bufX = BufferUtils.createByteBuffer(this.vertexNumber * 12);
		for (ScreenCoor screenCoor : coor)
		{
			bufX.putFloat(screenCoor.xScreen);
			bufX.putFloat(screenCoor.xFlat);

			bufX.putFloat(screenCoor.xScreen + screenCoor.wScreen);
			bufX.putFloat(screenCoor.xFlat + screenCoor.wFlat);

			bufX.putFloat(screenCoor.xScreen + screenCoor.wScreen);
			bufX.putFloat(screenCoor.xFlat + screenCoor.wFlat);

			bufX.putFloat(screenCoor.xScreen);
			bufX.putFloat(screenCoor.xFlat);
		}
		bufX.flip();
		return bufX;
	}
	public ByteBuffer storeY()
	{
		ByteBuffer bufY = BufferUtils.createByteBuffer(this.vertexNumber * 12);
		for (ScreenCoor screenCoor : coor)
		{
			bufY.putFloat(screenCoor.yScreen);
			bufY.putFloat(screenCoor.yFlat);

			bufY.putFloat(screenCoor.yScreen);
			bufY.putFloat(screenCoor.yFlat);

			bufY.putFloat(screenCoor.yScreen + screenCoor.hScreen);
			bufY.putFloat(screenCoor.yFlat + screenCoor.hFlat);

			bufY.putFloat(screenCoor.yScreen + screenCoor.hScreen);
			bufY.putFloat(screenCoor.yFlat + screenCoor.hFlat);
		}
		bufY.flip();
		return bufY;
	}
	public ByteBuffer storeColors()
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(this.vertexNumber * 4); // Couleurs
		for (QuadColor qc : colors)
			for (Color co : qc.getAsColorArray())
			{
				buf.put(co.getRed());
				buf.put(co.getGreen());
				buf.put(co.getBlue());
				buf.put(co.getAlpha());
			}
		buf.flip();
		return buf;
	}
}

