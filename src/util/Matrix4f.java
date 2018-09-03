package util;

import java.nio.FloatBuffer;

public class Matrix4f
{
	public float m[][] = new float[4][4];
	public static Matrix4f createOrthographicMatrix() {
        Matrix4f m = new Matrix4f();
        m.m[0][0] = 2f/ Window.getWidth();
        m.m[1][1] = -2f/Window.getHeight();
        m.m[2][2] = 1;
        m.m[3][0] = -1;
        m.m[3][1]= 1;
        m.m[3][2] = 0;
        m.m[3][3] = 1;
        return m;
    }
	public void store(FloatBuffer buf)
	{
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				buf.put(this.m[i][j]);
	}
}
