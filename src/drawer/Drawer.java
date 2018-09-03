package drawer;

import static org.lwjgl.opengl.GL11.glColor4f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import util.Matrix4f;
import util.Window;

public class Drawer
{
	public static FFTShader fftShader;

	static
	{
		fftShader = new FFTShader("fftVertex.txt", "fftFragment.txt");

		updateWindowSize();
	}

	public static void drawModel(Model model)
	{
		drawModelPart(model, 0, model.vertexNumber);
	}
	public static void drawModelPart(Model model, int base, int length)
	{
		glColor4f(1,1,1,1);

		GL30.glBindVertexArray(model.vaoId);

		for (int i=0;i<16;i++)
			if (model.isVertexArrayEnabled(i))
				GL20.glEnableVertexAttribArray(i);
			else
				GL20.glDisableVertexAttribArray(i);

		GL11.glDrawArrays(GL11.GL_QUADS, base, length);
	}

	public static void updateWindowSize()
	{
		Matrix2DShader.setScreenData(Window.getWidth(), Window.getHeight());
		Matrix2DShader.setMatrix(Matrix4f.createOrthographicMatrix());
	}
}
