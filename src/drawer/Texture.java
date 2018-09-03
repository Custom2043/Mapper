package drawer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL13;

public class Texture
{
	public static Texture[] bounds = new Texture[16];
	private static int currentlyBound = -1;

	public final int id;
	public final int width, height;

	public Texture(int id, int width, int height)
	{
		this.id = id;
		this.width = width;
		this.height = height;
	}
	public void bind(int slot)
	{
		if (bounds[slot] != this)
		{
			if (slot != currentlyBound)
				GL13.glActiveTexture(GL13.GL_TEXTURE0 + (currentlyBound = slot));
			glBindTexture(GL_TEXTURE_2D, (bounds[currentlyBound] = this).id);
		}
	}
	public void release()
	{
		glDeleteTextures(this.id);
	}
}
