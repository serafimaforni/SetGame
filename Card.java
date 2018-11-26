package setGame;


import java.awt.Graphics;
import java.awt.Image;

public class Card
{

	private int number;
	private int shape;
	private int fill;
	private int color;
	private Image picture;
	

	public Card(int myNum, int myShape, int myFill, int myColor, Image myPic)

	{
		number = myNum;
		shape = myShape;
		fill = myFill;
		color = myColor;
		picture = myPic;
	}
	
	public int getNumber()

	{
		return number;
	}
	
	public int getShape()
	{
		return shape;
	}
	
	public int getFill()
	{
		return fill;
	}
	
	public int getColor()
	{
		return color;
	}

	public Image getPicture()
	{
		return picture;
	}
	
}
