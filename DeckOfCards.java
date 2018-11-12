package setGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

public class DeckOfCards 
{
		public final int NUMBEROFCARDS = 81;
		public Card[] cards;
		public Card[] cards1 = new Card[27];
		public Card[] cards2 = new Card[27];
		public Card[] cards3 = new Card[27];
		private int currentCard;
		private SecureRandom randomGenerator;
		
		public DeckOfCards() throws IOException
		{
			cards = new Card[NUMBEROFCARDS];
			
			int [] myNum = {1, 2, 3};
			String [] myShape = {"Diamond", "Oval", "Sqiggle"};
			String [] myFill = {"Empty", "Stripe", "Solid"};
			String [] myColor = {"Red", "Green", "Purple"};
			
			
			randomGenerator = new SecureRandom();
			currentCard = 0;
			
			final int width = 92;
			final int height = 51;
			final int rows = 9;
			final int columns = 9;
			
			BufferedImage Picture = ImageIO.read(new File("setCards.png"));
			BufferedImage tempPic;
			int i = 0;
			for(int color = 0; color < 3; color++)
			{
				for(int fill = 0; fill < 3; fill++)
				{
					for(int shape = 0; shape < 3; shape++)
					{
						for(int num = 0; num < 3; num++)
						{
							
							tempPic = Picture.getSubimage(rows*width + (rows*5), columns*height + (columns*3), width, height);
							cards[i] = new Card(myNum[num], myShape[shape], myFill[fill], myColor[color], tempPic);
//							System.out.println(i + ": " + cards[i].getNumber()+ " "+ cards[i].getShape()+ " "+ cards[i].getFill()+ " " + cards[i].getColor());
							i++;
						}
					}
				}
			}
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
		public static void main(String[] args) throws IOException
		{
			DeckOfCards deck = new DeckOfCards();
			deck.displayDeck();
		}
}
