package setGame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

public class DeckOfCards 
{
		public final static int NUMBEROFCARDS = 81;
		public Card[] cards;

		public Image[] cardPics = new Image[NUMBEROFCARDS];
		
		int [] myNum = {1,2,3};
		int [] myShape = {1,2,3};
		int [] myFill = {1,2,3};
		int [] myColor = {1,2,3};
		

		private static SecureRandom randomGenerator;

		
		public DeckOfCards() throws IOException
		{
			cards = new Card[NUMBEROFCARDS];

			randomGenerator = new SecureRandom();

			
			BufferedImage Picture = ImageIO.read(new File("setCards.png"));
			BufferedImage tempPic;
			
			final int MAXROWS = 9;
			final int MAXCOLUMNS = 9;
			final int CARDWIDTH = (Picture.getWidth())/MAXROWS;
			final int CARDHEIGHT = (Picture.getHeight())/MAXROWS;
			
			int currentCard = 0;
			for(int down = 0; down < 9; down++)
			{
				for(int across = 0; across < 9; across++)
				{
					tempPic = Picture.getSubimage(across*CARDWIDTH, down*CARDHEIGHT, CARDWIDTH, CARDHEIGHT);
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
							index++;
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

		public Image getCardImage(int index)

		{
			Image cardPic = cards[index].getPicture();
			return cardPic;
		}

		public Card getCard(int index)
		{
			Card card = cards[index];
			return card;
			
		}
		public static boolean isThisASet(Card cardA, Card cardB, Card cardC)
		{
			boolean match = false;
			
			if((cardA.getNumber()+ cardB.getNumber()+ cardC.getNumber()) % 3 == 0)
			{
				if((cardA.getShape()+ cardB.getShape()+ cardC.getShape()) % 3 == 0)
				{
					if((cardA.getColor()+ cardB.getColor()+ cardC.getColor()) % 3 == 0)
					{
						if((cardA.getFill()+ cardB.getFill()+ cardC.getFill()) % 3 == 0)
						{
							match = true;
						}
					}
				}
			}
			return match;
			
		}
		public static void Shuffle(Card[] cardArray)
		{
			for(int first = 0; first < NUMBEROFCARDS; first++)
			{
				int dest = randomGenerator.nextInt(NUMBEROFCARDS);
				Card temp = cardArray[first];
				cardArray[first] = cardArray[dest];
				cardArray[dest] = temp;
			}
		}

		
		public static void main(String[] args) throws IOException
		{
			DeckOfCards deck = new DeckOfCards();
//			deck.displayDeck();
			Card[] cards = new Card[81];
			for(int i = 0; i < 81; i++)
			{
				cards[i] = deck.getCard(i);
			}
			if(isThisASet(cards[1], cards[1], cards[1]))
			{
				System.out.println("It is a set");
			}
			else
			{
				System.out.println("It is not a set");
			}
			
		}
}
