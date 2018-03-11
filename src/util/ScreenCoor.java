package util;

public class ScreenCoor
{
	public static ScreenCoor allScreen = new ScreenCoor(0,0,1,1,0,0,0,0);
	public float xScreen,yScreen,wScreen,hScreen, xFlat, yFlat, wFlat, hFlat;

	public static ScreenCoor screen(float x, float y, float w, float h){return new ScreenCoor(x,y,w,h,0,0,0,0);}
	public static ScreenCoor screenFlat(float x, float y, float w, float h, float xF, float yF, float wF, float hF){return new ScreenCoor(x,y,w,h,xF,yF,wF,hF);}

	public static ScreenCoor flat(float xF, float yF, float wF, float hF){return new ScreenCoor(0,0,0,0,xF,yF,wF,hF);}

	public static ScreenCoor nul(){return new ScreenCoor(0,0,0,0,0,0,0,0);}

	private ScreenCoor(float x,float y,float w,float h, float xF, float yF, float wF, float hF)
	{
		this.xScreen = x; this.xFlat = xF;
		this.yScreen = y; this.yFlat = yF;
		this.wScreen = w; this.wFlat = wF;
		this.hScreen = h; this.hFlat = hF;
	}
	public float[] inFloatArray()
	{
		return new float[]{this.getStartX(), this.getStartY(), this.getStartX(), this.getEndY(), this.getEndX(), this.getEndY(), this.getEndX(), this.getStartY()};
	}
	@Override
	public String toString()
	{
		return "Screen ; X : "+this.xScreen+", Y : "+this.yScreen+",W : "+this.wScreen+",H : "+this.hScreen+
			   "\nFlat ; X : "+this.xFlat+", Y : "+this.yFlat+",W : "+this.wFlat+",H : "+this.hFlat;
	}
	public boolean isIn(float X, float Y)
	{
		return (X >= this.getStartX() && X <= this.getEndX() && Y >= this.getStartY() && Y <= this.getEndY());
	}
	public int getStartX()
	{
		return (int)(this.xScreen*this.getScreenWidth() + this.xFlat);
	}
	public int getStartY()
	{
		return (int)(this.yScreen*this.getScreenHeight() + this.yFlat);
	}
	public int getWidth()
	{
		return (int)(this.wScreen*this.getScreenWidth() + this.wFlat);
	}
	public int getHeight()
	{
		return (int)(this.hScreen*this.getScreenHeight() + this.hFlat);
	}
	public int getEndX()
	{
		return this.getStartX() + this.getWidth();
	}
	public int getEndY()
	{
		return this.getStartY() + this.getHeight();
	}
	public int getMiddleX()
	{
		return this.getStartX() + this.getWidth() / 2;
	}
	public int getMiddleY()
	{
		return this.getStartY() + this.getHeight() / 2;
	}
	private int getScreenHeight()
	{
		return Window.getHeight();
	}
	private int getScreenWidth()
	{
		return Window.getWidth();
	}
	public ScreenCoor addScreen(float xAdd, float yAdd, float wAdd, float hAdd){return new ScreenCoor(this.xScreen + xAdd,this.yScreen + yAdd,this.wScreen + wAdd,this.hScreen + hAdd, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addFlat(float xAdd, float yAdd, float wAdd, float hAdd){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat + xAdd, this.yFlat + yAdd, this.wFlat + wAdd, this.hFlat + hAdd);}

	public ScreenCoor setScreen(float xSet, float ySet, float wSet, float hSet){return new ScreenCoor(xSet,ySet,wSet,hSet, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setFlat(float xSet, float ySet, float wSet, float hSet){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen,xSet,ySet,wSet,hSet);}

	public ScreenCoor addX(float screen, float flat){return new ScreenCoor(this.xScreen + screen,this.yScreen,this.wScreen,this.hScreen, this.xFlat + flat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addY(float screen, float flat){return new ScreenCoor(this.xScreen,this.yScreen + screen,this.wScreen,this.hScreen, this.xFlat, this.yFlat + flat, this.wFlat, this.hFlat);}
	public ScreenCoor addW(float screen, float flat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen + screen,this.hScreen, this.xFlat, this.yFlat, this.wFlat + flat, this.hFlat);}
	public ScreenCoor addH(float screen, float flat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen + screen,this.xFlat, this.yFlat, this.wFlat, this.hFlat + flat);}

	public ScreenCoor setX(float screen, float flat){return new ScreenCoor(screen,this.yScreen,this.wScreen,this.hScreen, flat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setY(float screen, float flat){return new ScreenCoor(this.xScreen,screen,this.wScreen,this.hScreen, this.xFlat, flat, this.wFlat, this.hFlat);}
	public ScreenCoor setW(float screen, float flat){return new ScreenCoor(this.xScreen,this.yScreen,screen,this.hScreen, this.xFlat, this.yFlat, flat, this.hFlat);}
	public ScreenCoor setH(float screen, float flat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,screen, this.xFlat, this.yFlat, this.wFlat, flat);}

	public ScreenCoor setXScreen(float xScreen){return new ScreenCoor(xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setYScreen(float yScreen){return new ScreenCoor(this.xScreen,yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setWScreen(float wScreen){return new ScreenCoor(this.xScreen,this.yScreen,wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setHScreen(float hScreen){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setXFlat(float xFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setYFlat(float yFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor setWFlat(float wFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, wFlat, this.hFlat);}
	public ScreenCoor setHFlat(float hFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, hFlat);}

	public ScreenCoor addXScreen(float xScreen){return new ScreenCoor(this.xScreen+xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addYScreen(float yScreen){return new ScreenCoor(this.xScreen,this.yScreen+yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addWScreen(float wScreen){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen+wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addHScreen(float hScreen){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen+hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addXFlat(float xFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat+xFlat, this.yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addYFlat(float yFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat+yFlat, this.wFlat, this.hFlat);}
	public ScreenCoor addWFlat(float wFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat+wFlat, this.hFlat);}
	public ScreenCoor addHFlat(float hFlat){return new ScreenCoor(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat+hFlat);}

	public ScreenCoor clone()
	{
		return screenFlat(this.xScreen,this.yScreen,this.wScreen,this.hScreen, this.xFlat, this.yFlat, this.wFlat, this.hFlat);
	}
}
