package setGame;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Card
{

	private int number;
	private int shape;
	private int fill;
	private int color;
	private BufferedImage picture;
	

	public Card(int myNum, int myShape, int myFill, int myColor, BufferedImage myPic)

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

	public BufferedImage getPicture()
	{
		return picture;
	}
	
}
