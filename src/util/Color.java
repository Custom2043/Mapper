package util;

public class Color
{
	public static Color WHITE = new Color(1f, 1, 1, 1);
	private int r, g, b, a;
	public Color(int rr, int gg, int bb, int aa) 
	{
		r = rr;
		g = gg;
		b = bb;
		a = aa;
	}
	public Color(float rr, float gg, float bb, float aa) 
	{
		this ((byte)(rr * 255), (byte)(gg * 255), (byte)(bb * 255), (byte)(aa * 255));
	}
	public Color multiply(Color c)
	{
		return new Color((byte)(r/255f * c.r),
		(byte)(g/255f * c.g),
		(byte)(b/255f * c.b),
		(byte)(a/255f * c.a));
	}
	public byte getRed()
	{
		return (byte)r;
	}
	public byte getGreen()
	{
		return (byte)g;
	}
	public byte getBlue()
	{
		return (byte)b;
	}
	public byte getAlpha()
	{
		return (byte)a;
	}
}
