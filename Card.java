package setGame;

import java.awt.Graphics;
import java.awt.Image;

public class Card
{
	private String number;
	private String shape;
	private String fill;
	private String color;
	private Image picture;
	
	public Card(String myNum, String myShape, String myFill, String myColor, Image myPic)
	{
		number = myNum;
		shape = myShape;
		fill = myFill;
		color = myColor;
		picture = myPic;
	}
	
	public String getNumber()
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
	public String getPicture()
	{
		return picture.toString();
	}
	
}
