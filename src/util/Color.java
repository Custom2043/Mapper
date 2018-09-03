package util;

public class Color
{
	public static Color WHITE = new Color(1f, 1, 1, 1);
	private int r, g, b, a;
	public Color(int rr, int gg, int bb, int aa)
	{
		this.r = rr;
		this.g = gg;
		this.b = bb;
		this.a = aa;
	}
	public Color(float rr, float gg, float bb, float aa)
	{
		this ((byte)(rr * 255), (byte)(gg * 255), (byte)(bb * 255), (byte)(aa * 255));
	}
	public Color multiply(Color c)
	{
		return new Color((byte)(this.r/255f * c.r),
		(byte)(this.g/255f * c.g),
		(byte)(this.b/255f * c.b),
		(byte)(this.a/255f * c.a));
	}
	public byte getRed()
	{
		return (byte)this.r;
	}
	public byte getGreen()
	{
		return (byte)this.g;
	}
	public byte getBlue()
	{
		return (byte)this.b;
	}
	public byte getAlpha()
	{
		return (byte)this.a;
	}
}
