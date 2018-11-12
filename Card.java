package setGame;

import java.awt.Image;

public class Card
{
	private int number;
	private String shape;
	private String fill;
	private String color;
	private Image picture;
	
	public Card(int myNum, String myShape, String myFill, String myColor, Image myPic)
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
	
	public String getShape()
	{
		return shape;
	}
	
	public String getFill()
	{
		return fill;
	}
	
	public String getColor()
	{
		return color;
	}
	
	
}
