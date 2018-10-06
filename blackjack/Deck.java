package com.example.marianaarmelin.blackjack;

/**
 * Deck class that deals 52 cards
 *  @author Mariana Armelin
 *  @version 1.0 (10/05/18)
 *  @since version 0.0
 */
public class Deck
{
    private Card[] deck;
    private int cardsUsed;

    public Deck()
    {
        deck = new Card[52];
        fill();
        shuffle();
    }

    /**
     * Creates an unshuffled deck of 52 cards
     */
    public void fill()
    {
        int numberOfCards= 0;
        for(int suit = 0; suit <= 3; suit++)
        {
            for(int value = 1; value <= 13; value++)
            {
                deck[numberOfCards] = new Card(value, suit);
                numberOfCards++;
            }
        }
    }

    /**
     * Shuffle the deck of the deck
     */
    public void shuffle()
    {
        for ( int i = 51; i > 0; i-- )
        {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0;
    }

    /**
     * Deal new card from top of the deck
     * @return new card from current currentCard
     */
    public Card deal()
    {
        if (cardsUsed == 52)
            shuffle();
        cardsUsed++;
        return deck[cardsUsed - 1];
    }

    /**
     * Number of cards still left in the deck
     * @return integer of cards still left after dealing
     */
    public int cardsLeft()
    {
        return 52 - cardsUsed;
    }


}