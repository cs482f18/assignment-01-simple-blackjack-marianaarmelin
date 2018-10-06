package com.example.marianaarmelin.blackjack;

import java.util.ArrayList;

/**
 * Player class creates player object
 *  @author Mariana Armelin
 *  @version 1.0 (10/05/18)
 *  @since version 1.0
 */
public class Player
{
    private ArrayList<Card> hand;
    private int score;
    private String name;

    public Player(String name)
    {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    /**
     * Adds card to player's hand
     * @param card
     */
    public void giveCard(Card card)
    {
        hand.add(card);
    }

    /**
     * Getter method for player score
     * @return player score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Getter method for player name
     * @return player name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for player's new score
     * @param score
     */
    public void setScore(int score)
    {
        this.score += score;
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }


}
