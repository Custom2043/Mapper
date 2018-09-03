package util;

public class QuadColor
{
	public Color u, d, t, q;
	public QuadColor(Color hg, Color hd, Color bg, Color bd)
	{
		this.u = hg;
		this.d = hd;
		this.t = bg;
		this.q = bd;
	}
	public QuadColor(Color color)
	{
		this.u = this.d = this.t = this.q = color;
	}
	public QuadColor()
	{
		this.u = this.d = this.t = this.q = Color.WHITE;
	}
	public Color[] getAsColorArray()
	{
		return new Color[]{this.u, this.d, this.t, this.q};
	}
	public QuadColor multiply(Color c)
	{
		return new QuadColor(this.u.multiply(c),
		this.d.multiply(c),
		this.t.multiply(c),
		this.q.multiply(c));
	}
}
