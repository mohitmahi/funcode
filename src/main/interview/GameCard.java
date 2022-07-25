import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameCard {
    public static void main(String[] args) {

        final GameTransaction.Card first = new GameTransaction.Card(ImmutableMap.of("B", 3, "G", 5));
        final GameTransaction.Card second = new GameTransaction.Card(ImmutableMap.of("B", 7, "U", 2));
        final GameTransaction.Card third = new GameTransaction.Card(ImmutableMap.of("U", 7, "G", 2));

        final GameTransaction.Player playerFirst = new GameTransaction.Player(ImmutableMap.of("B", 10, "U", 3, "G", 6, "X", 10));

        System.out.println(playerFirst.getTotalCard());
        System.out.println(playerFirst.getTokensMap());

        System.out.println(GameTransaction.canPurchase(playerFirst, first));
        System.out.println(GameTransaction.canPurchase(playerFirst, second));
        System.out.println(GameTransaction.canPurchase(playerFirst, third));

        System.out.println(playerFirst.getTotalCard());
        System.out.println(playerFirst.getTokensMap());

    }

    static class GameTransaction {
        public static boolean canPurchase(Player player, Card card) {
            final Map<String, Integer> tokenMap = player.getTokensMap();
            boolean allColorCanBePurchased = true;
            final Map<String, Integer> tempTokenMap = new HashMap<>(tokenMap);
            for(Map.Entry<String, Integer> entry: card.getCostMap().entrySet()) {
                if(!tokenMap.containsKey(entry.getKey()) || tokenMap.get(entry.getKey()) < entry.getValue()) {
                    allColorCanBePurchased = false;
                    break;
                } else {
                    tempTokenMap.put(entry.getKey(), tokenMap.get(entry.getKey()) - entry.getValue());
                }
            }
            if(!allColorCanBePurchased) {
                return allColorCanBePurchased;
            }
            player.setTokensMap(tempTokenMap);
            player.addCard(card);
            return true;
        }

        static class Card {
            private Map<String, Integer> costMap = new HashMap<>();

            public Card(Map<String, Integer> map) {
                this.costMap = map;
            }

            public Map<String, Integer> getCostMap() {
                return this.costMap;
            }

        }

        static class Player {
            private List<Card> cardsInHand = new ArrayList<>();
            private Map<String, Integer> tokenMap;

            public Player(Map<String, Integer> map) {
                this.tokenMap = map;
            }

            public Map<String, Integer> getTokensMap() {
                return this.tokenMap;
            }
            public void setTokensMap(Map<String, Integer> newMap) {
                this.tokenMap = newMap;
            }
            public void addCard(Card purchardCard) {
                this.cardsInHand.add(purchardCard);
            }

            public int getTotalCard() {
                return this.cardsInHand.size();
            }
        }

    }
}
