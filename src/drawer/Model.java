package drawer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public abstract class Model
{
	private short usedVertexArray = 0;
	public final int vaoId;
	public int vertexNumber;

	public Model(int vN)
	{
		this.vertexNumber = vN;

		this.vaoId = VAOLoader.createVAO();
	}
	public void enableVertexArray(int index)
	{
		this.usedVertexArray |= (1 << index);
	}

	public void disableVertexArray(int index)
	{
		this.usedVertexArray &= (0b1111111111111111 - (1 << index));
	}
	public boolean isVertexArrayEnabled(int index)
	{
		return ((this.usedVertexArray >> index) & 1) == 1 ? true : false;
	}
	/**
	 * Assumes that the VAO is already bound
	 */
	public int getVBO(int bufferNumber)
	{
		return GL30.glGetVertexAttribIi(bufferNumber, GL15.GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING);
	}
}
