package com.example.marianaarmelin.blackjack;

import java.util.ArrayList;

/**
 * Class for the simplified version of black jack.
 *  @author Mariana Armelin
 *  @version 1.5 (10/05/18)
 *  @since version 0.0
 * */
public class BlackJack
{
    private Deck gameDeck;
    private ArrayList<Player> players;
    int cardCount;
    private int currPlayer;

    public BlackJack()
    {
        gameDeck = new Deck();
        players = new ArrayList<Player>();
        players.add(new Player("Dealer"));
        players.add(new Player("User"));

        //user
        currPlayer = 1;

        dealHands();
    }

    /**
     * Deals 2 cards to current player
     */
    public void dealHands()
    {
        for(Player p : players)
            for(int i = 0; i < 2; i++) {
                Card c = gameDeck.deal();
                p.giveCard(c);
            }
    }

    /**
     * Player chooses to draw another card.
     * Adds top card from deck to player's hand
     */
    public void hit()
    {
        Card c = gameDeck.deal();
        getCurrentPlayer().giveCard(c);

    }

    /**
     * Player chooses to end his turn, goes to next player
     * and next player draws another card from top of deck
     */
    public void stand()
    {
        nextPlayer();
        Card c = gameDeck.deal();
        getCurrentPlayer().giveCard(c);
    }

    /**
     * Getter for current player of the game (dealer or user)
     * @return current player
     */
    public Player getCurrentPlayer()
    {
        return players.get(currPlayer);
    }

    /**
     * Gets score of current player's hand
     * @return winner
     */
    public Player handScore()
    {
        Player winner = null;
        int maxScore = Integer.MIN_VALUE;
        for (Player p: players)
        {
            p.setScore(score(p));
            if (p.getScore() > maxScore && p.getScore() <=21)
            {
                winner = p;
                maxScore = p.getScore();
            }
        }

        return winner;
    }

    /**
     * Checks value of each card and adds them up for each card in
     * player P's hand. Value of aces will be determined during this method
     * @param Player p
     * @return total score of the player's cards
     */
    public int score(Player p)
    {
        int score = 0;
        int numAces = 0;

        for(Card c : p.getHand())
        {
            int rank = c.getRank();
            if(rank <= 10)
                score += rank;
            else if(rank <= 13)
                score += 10;
            else {
                score += 11;
                numAces++; }
            while(score > 21 && numAces > 0)
            {
                score -= 10;
                numAces--;
            }
        }
        return score;
    }

    /**
     * Gets players of the game
     * @return array list of players
     */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    /**
     * Moves to next player
     */
    public void nextPlayer()
    {
        if (currPlayer == 1)
            currPlayer = currPlayer--;
        else if (currPlayer == 0)
            currPlayer = currPlayer++;
        else
            ;
    }

}
