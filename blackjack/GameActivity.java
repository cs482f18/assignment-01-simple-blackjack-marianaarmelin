package com.example.marianaarmelin.blackjack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageSwitcher;
import android.graphics.*;
import android.content.res.AssetManager;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.io.IOException;
import android.content.*;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity
{
    private Button hit, stand, newGame;
    BlackJack game;
    TextView dealerScoreLabel, userScoreLabel, dealerScoreText, userScoreText;
    int dealerScore, userScore;
    private Player player;
    private ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5;
    private ImageView yourCard1, yourCard2, yourCard3, yourCard4, yourCard5;

    boolean gameOver = false;
    Bitmap[] cardImages;
    Toast loseText,winText,winName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        game = new BlackJack();
        game.dealHands();
        gameCardsSetup();

        hit.setOnClickListener(new View.OnClickListener()
        {
            int clicks = 0;
            int maxClicks = 3;

            public void onClick(View v)
            {

                if (clicks == maxClicks)
                    hit.setEnabled(false);
                else
                    clicks++;

                hitClick();
                setDealerImages();
                setUserImages();
            }
        });

        stand.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                standClick();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(GameActivity.this,MainActivity.class));
            }
        });
    }

    /**
     * Initializes and declares variables for the images of the
     * player and dealer's card and their score "text"
     */
    private void gameCardsSetup() {
        hit = (Button) findViewById(R.id.hit);
        stand = (Button) findViewById(R.id.stand);
        newGame = (Button) findViewById(R.id.newGame);

        dealerCard1 = (ImageView) findViewById(R.id.dealerCard1);
        dealerCard2 = (ImageView) findViewById(R.id.dealerCard2);
        dealerCard3 = (ImageView) findViewById(R.id.dealerCard3);
        dealerCard4 = (ImageView) findViewById(R.id.dealerCard4);
        dealerCard5 = (ImageView) findViewById(R.id.dealerCard5);

        yourCard1 = (ImageView) findViewById(R.id.userCard1);
        yourCard2 = (ImageView) findViewById(R.id.userCard2);
        yourCard3 = (ImageView) findViewById(R.id.userCard3);
        yourCard4 = (ImageView) findViewById(R.id.userCard4);
        yourCard5 = (ImageView) findViewById(R.id.userCard5);

        dealerScoreText = (TextView) findViewById(R.id.dealerScore);
        userScoreText = (TextView) findViewById(R.id.userScore);

        dealerScoreLabel = (TextView) findViewById(R.id.dealerScoreLabel);
        userScoreLabel = (TextView) findViewById(R.id.userScoreLabel);
    }

    /**
     * If current player chooses to hit then points
     * will be added given the cards that are currently
     * in his hand. Aces will count either as 1 or 11.
     */
    private void hitClick()
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence lose = "You Lose!";
        loseText = Toast.makeText(context, lose, duration);

        ArrayList<Card> hand = game.getCurrentPlayer().getHand();
        int uScore = game.score(game.getCurrentPlayer());

        game.hit();
        if (uScore > 21)
        {
            for (int i = 0; i < hand.size(); i++)
            {
                //Aces will count as 1
                Card c = hand.get(i);
                if (c.getRank() == 1 && (game.score(game.getCurrentPlayer())) == 11)
                    (game.getCurrentPlayer()).setScore(1);
                    break;
            }
            loseText.show();
        }

        if (game.score(game.getCurrentPlayer()) >= 5 && (game.score(game.getCurrentPlayer())) < 22)
            standClick();
    }

    /**
     * Current player hits stand button and next player's turn.
     * Check score and new player's hand to determine score
     * and to determine winner/loser
     */
    private void standClick()
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence win = "You Win!";
        winText = Toast.makeText(context, win, duration);
        game.nextPlayer();

        ArrayList<Card> hand = game.getCurrentPlayer().getHand();
        int dScore = game.score(game.getCurrentPlayer());

        game.stand();
        while (dScore < 17 && (hand.size() < 5))
        {
            game.hit();
            if (dScore > 21)
            {
                for (int i = 0; i < hand.size(); i++)
                {
                    //Aces will count as 1
                    Card c = hand.get(i);
                    if (c.getRank() == 1 && (game.score(game.getCurrentPlayer())) == 11)
                        (game.getCurrentPlayer()).setScore(1);
                    break;
                }
            }
        }
        if (dScore > 21) { winText.show(); }
        else checkWin();
    }

    /**
     * Checks to see which player had the most points
     * and will use Toast to show the name of the winner
     */
    public void checkWin()
    {
        int dScore = game.score(game.getCurrentPlayer());
        game.nextPlayer();
        int uScore = game.score(game.getCurrentPlayer());

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if (dScore > uScore)
        {
            CharSequence winnerName = game.getCurrentPlayer().getName();
            winName = Toast.makeText(context,winnerName,duration);
            winName.show();
        }
        else if (dScore < uScore)
        {
            CharSequence winnerName = game.getCurrentPlayer().getName();
            winName = Toast.makeText(context,winnerName,duration);
            winName.show();
        }
    }

    /**
     * Displays the .png images to the cards in the
     * the dealer's hands. Turns the bitmap into the imageView
     * declared in "gameCardsSetup()"
     */
    private void setDealerImages() {
        AssetManager assetManager = this.getAssets();
        ArrayList<Card> hand = game.getPlayers().get(0).getHand();
        int score = game.score(game.getPlayers().get(0));
        String strScore = Integer.toString(score);

        dealerScoreText.setText(strScore);

        for (int i = 0; i < hand.size(); i++)
        {
            Card c = hand.get(i);

            if (i == 0)
            {
                Bitmap back, back_bitmap = null;
                try
                {
                    back = BitmapFactory.decodeStream(assetManager.open("back_red.png"));
                    back_bitmap = Bitmap.createBitmap(back);
                    dealerCard1.setImageBitmap(back_bitmap);
                } catch (IOException e) {}

            }

            else if (i == 1)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                        String fileName = words[0] + "_of_" + words[2] + ".png";
                        bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                        bitmap = bitmap.createBitmap(bitmap);
                        dealerCard2.setImageBitmap(bitmap);
                    } catch (IOException e) { }
            }

            else if (i == 2)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    dealerCard3.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }

            else if (i == 3)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    dealerCard4.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }

            else if (i == 4)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    dealerCard5.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }
        }
    }

    /**
     * Displays the .png images to the cards in the
     * the user's hands. Turns the bitmap into the imageView
     * declared in "gameCardsSetup()"
     */
    private void setUserImages() {
        AssetManager assetManager = this.getAssets();
        ArrayList<Card> hand = game.getPlayers().get(1).getHand();
        int score = game.score(game.getPlayers().get(1));
        String strScore = Integer.toString(score);

        userScoreText.setText(strScore);

        for (int i = 0; i < hand.size(); i++)
        {
            Card c = hand.get(i);

            if (i == 0)
            {
                Bitmap back, back_bitmap = null;
                try
                {
                    back = BitmapFactory.decodeStream(assetManager.open("back_blue.png"));
                    back_bitmap = Bitmap.createBitmap(back);
                    yourCard1.setImageBitmap(back_bitmap);
                } catch (IOException e) {}

            }

            else if (i == 1)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    yourCard2.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }

            else if (i == 2)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    yourCard3.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }

            else if (i == 3)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    yourCard4.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }

            else if (i == 4)
            {
                String value = c.toString();
                String[] words = value.split(" ");
                Bitmap bitmap = null;
                try {
                    String fileName = words[0] + "_of_" + words[2] + ".png";
                    bitmap = BitmapFactory.decodeStream(assetManager.open(fileName));
                    bitmap = bitmap.createBitmap(bitmap);
                    yourCard5.setImageBitmap(bitmap);
                } catch (IOException e) { }
            }
        }
    }
}

