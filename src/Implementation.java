import java.util.logging.Logger;

public class Implementation
{

    /*
    * Each game consists of N rounds
    * Each round is represented by a Round object
     */
    class ruleSet implements Round.ruleSet
    {
        Logger logger = Logger.getLogger(ruleSet.class.getName());
        Round round;
        public String dealCardsToPlayers(Round.gameState state)
        {
            if (state == Round.gameState.DEALING)
            {

                //


                for (int i = 0; i < round.numberOfPlayers; i++)
                {
                    // use get random card
                    Round.Card card = round.deck.getRandomCard();
                    round.players.get(i).hand.add(card);
                }
            }

            state = Round.gameState.HITTING;
            return "Dealt 2 cards to each player";
        }
        public void hit()
        {
        }

        public void stand()
        {
        }

        public void raise()
        {
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Hey");
    }
}
