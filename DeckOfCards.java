package setGame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

public class DeckOfCards 
{
		public final int NUMBEROFCARDS = 81;
		public Card[] cards;
		public Image[] cardPics = new Image[NUMBEROFCARDS];
		
		public final int  ONE = 0;
		public final int TWO = 1;
		public final int  THREE = 2;
		
		public final int RED = 0;
		public final int GREEN = 27;
		public final int PURPLE = 54;
		
		public final int  DIAMOND = 0;
		public final int OVAL = 3;
		public final int  SQUIGGLE = 6;
		
		public final int  EMPTY = 0;
		public final int STRIPE = 9;
		public final int  SOLID = 18;
		
		private int currentCard;
		private SecureRandom randomGenerator;
		
		public DeckOfCards() throws IOException
		{
			cards = new Card[NUMBEROFCARDS];
			
			String [] myNum = {"One", "Two", "Three"};
			String [] myShape = {"Diamond", "Oval", "Sqiggle"};
			String [] myFill = {"Empty", "Stripe", "Solid"};
			String [] myColor = {"Red", "Green", "Purple"};
			
			
			randomGenerator = new SecureRandom();
			
			final int CARDWIDTH = 97;
			final int CARDHEIGHT = 54;
			final int MAXROWS = 9;
			final int MAXCOLUMNS = 9;
			
			BufferedImage Picture = ImageIO.read(new File("setCards.png"));
			BufferedImage tempPic;
			
			currentCard = 0;
			for(int down = 0; down < 9; down++)
			{
				for(int across = 0; across < 9; across++)
				{
//					System.out.println(Picture.getWidth() + "x" + Picture.getHeight());
					tempPic = Picture.getSubimage(across*CARDWIDTH + (across*5), down*CARDHEIGHT + (down*3), CARDWIDTH, CARDHEIGHT);
//					System.out.println(tempPic.getWidth() + "x" + tempPic.getHeight());
					cardPics[currentCard] = tempPic;
					currentCard++;
					
				}
				
			}
			
			int index = 0;
			for(int color = 0; color < 3; color++)
			{
				for(int fill = 0; fill < 3; fill++)
				{
					for(int shape = 0; shape < 3; shape++)
					{
						for(int num = 0; num < 3; num++)
						{
							
							cards[index] = new Card(myNum[num], myShape[shape], myFill[fill], myColor[color], cardPics[index]);
							System.out.println(index + ": " + cards[index].getNumber()+ " "+ cards[index].getShape()+ " "+ cards[index].getFill()+ " " + cards[index].getColor()+ " "+ cards[index].getPicture());
							index++;
						}
					}
				}
			}
//			System.out.println(cards[GREEN+EMPTY+DIAMOND+ONE].getColor() + cards[RED+EMPTY+DIAMOND+ONE].getShape());
			
			
		}
		
		public void displayDeck()
		{
			int i = 0;
			for(Card card : cards)
			{
				i++;
				System.out.println(i + ": " +card.getNumber()+ " "+ card.getShape()+ " "+ card.getFill()+ " " + card.getColor());
			}
		}
		public Image getCardImage()
		{
			int i = 0;
			Image cardPic = cards[45].getPicture();
//			for(Card card : cards)
//			{
//				i++;
//				cardPic = card.getPicture();
//			}
			return cardPic;
		}
		
//		public static void main(String[] args) throws IOException
//		{
//			DeckOfCards deck = new DeckOfCards();
////			deck.displayDeck();
//			
//		}
}