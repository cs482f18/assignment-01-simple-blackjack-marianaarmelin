package com.example.marianaarmelin.blackjack;

/**
 *  Card class represents a real world card containing rank and suit information
 *  @author Mariana Armelin
 *  @version 2.0 10/05/18  -- Don't need the parenthesis around the 
 *  @since version 0.0
 */

public class Card
{
    // Use javadoc comments for your instance variables
    private int rank;
    private int suit;
    String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    int[] ranks = {1,2,3,4,5,6,7,8,9,10,11,12,13};

    /**
     * Constructor method for Card class
     * @param rank of card
     * @param suit of card
     */
    /**
     * Parameterized constructor 
     * @param rank the rank value of the card
     * @param suit the suit of the card 
     */
    public Card(int rank, int suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Getter for rank
     * @return int value of rank
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Getter for suit
     * @return int value of the suit
     */
    public int getSuit()
    {
        return suit;
    }

    /**
     * Converts card object into a string
     * @return string for value and suit of card
     */
    public String toString() {
        // in these types of cases, use switch-case construct
        String value;

        if(rank == 1)
            value = "ace";
        else if(rank == 2)
            value = "two";
        else if(rank == 3)
            value = "three";
        else if(rank == 4)
            value = "four";
        else if(rank == 5)
            value = "five";
        else if(rank == 6)
            value = "six";
        else if(rank == 7)
            value = "seven";
        else if(rank == 8)
            value = "eight";
        else if(rank ==9)
            value = "nine";
        else if(rank == 10)
            value = "jack";
        else if(rank == 11)
            value = "queen";
        else if(rank == 12)
            value = "king";
        else
            value = String.valueOf(rank);

        return value + " of " + suits[suit];
    }

}
