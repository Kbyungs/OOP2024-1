// Enjoy blackjack
// dr.seungchul@gmail.com
// To do list
// 1. make displayStats( ) method
// 2. 'A' should be 1 or 11
// 3. BlackJack = AJ, AK, AQ
// 4. insurance
// 5. split

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackJack {
    static int stage = 1;
    static boolean isExit = false;
    static Random random = new Random();
    static int totalGame = 0;
    static int wins = 0;
    static int ties;
    static boolean insurance;

    public static void main(String[] args) {
        List<Integer> cards = new ArrayList<>();

        System.out.println("	Welcome to dr.han's Casino! \n");

        cards = startGame(cards);
        while (!isExit) {
            cards = round(cards);
        }
    }

    private static int getSum(List<Integer> cards) {
        int sum = 0;
        boolean hasAce = false;

        for (int i = 0; i < cards.size(); i++) {
            sum += cards.get(i);

            // 1이 하나라도 있으면 hasAce를 true로 설정
            if (cards.get(i) == 1) {
                hasAce = true;
            }
        }

        // 1을 11로 간주해도 총합이 21을 넘지 않으면 10을 추가로 더함
        if (hasAce && sum + 10 <= 21) {
            sum += 10;
        }

        return sum;
    }

    private static void printCards(List<Integer> cards) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) == 1) System.out.print("A");
            else System.out.print(cards.get(i));

            if (i != cards.size()-1) System.out.print(", ");
            else System.out.print("\ntotal: " + getSum(cards));
        }
    }

    private static List<Integer> startGame(List<Integer> cards) {
        int newCard1 = random.nextInt(13) + 1;
        int newCard2 = random.nextInt(13) + 1;
        int dealer = random.nextInt(13) + 1;
//        int dealer = 1;
        if (dealer == 1) {
            System.out.println("Dealer's hand: A");
            System.out.println("Do you want to buy insurance? (yes/no)");
            Scanner sc = new Scanner(System.in);
            String temp = sc.nextLine();
            if (temp.equals("yes")){
                insurance = true;
            } else if (temp.equals("no")){
                insurance = false;
            } else {
                System.out.println("Do you want to buy insurance? (yes/no)");
                temp = sc.nextLine();
            }
        } else if ((dealer > 1) && (dealer < 11)) System.out.println("Dealer's hand: " + dealer);
        else if (dealer == 11) System.out.println("Dealer's hand: JACK");
        else if (dealer == 12) System.out.println("Dealer's hand: JACK");
        else System.out.println("Dealer's hand: KING");


        if (newCard1 == 1) {
            cards.add(newCard1);
            System.out.print("New card is  A, ");
        }
        else if ((newCard1 > 1) && (newCard1 < 11)) {
            cards.add(newCard1);
            System.out.print("New card is " + newCard1 + ", ");
        }
        else if (newCard1 == 11) {
            cards.add(10);
            System.out.print("New card is JACK, ");
        }
        else if (newCard1 == 12) {
            cards.add(10);
            System.out.print("New card is QUEEN, ");
        }
        else {
            cards.add(10);
            System.out.print("New card is KING, ");
        }
        if (newCard2 == 1) {
            cards.add(newCard2);
            System.out.println("A");
        }
        else if ((newCard2 > 1) && (newCard2 < 11)) {
            cards.add(newCard2);
            System.out.println(newCard2);
        }
        else if (newCard2 == 11) {
            cards.add(10);
            System.out.println("JACK");
        }
        else if (newCard2 == 12) {
            cards.add(10);
            System.out.println("QUEEN");
        }
        else {
            cards.add(10);
            System.out.println("KING");
        }

        if (getSum(cards) == 21) {
            System.out.println(" BLACKJACK! You win!!\n\n");
            System.out.println("----------------------");
            cards.clear();
            cards = getCard(cards);
            wins++; totalGame++;
        }
        return cards;
     }
    private static List<Integer> hit(List<Integer> cards) {
        cards = getCard(cards);

        if (getSum(cards) > 21) {
            System.out.print("You have ");
            printCards(cards);
            System.out.println(" Over 21! You lose.\n\n");
            System.out.println("----------------------");
            cards.clear();
            cards = getCard(cards);
            totalGame++;
        }
        if (getSum(cards) == 21) {
            System.out.print("You have ");
            printCards(cards);
            System.out.println(" BLACKJACK! You win!!\n\n");
            System.out.println("----------------------");
            cards.clear();
            cards = getCard(cards);
            wins++; totalGame++;
        }

        return cards;
    }

    private static List<Integer> getCard(List<Integer> cards) {
        if (cards.size() == 0) cards = startGame(cards);
        int newCard = random.nextInt(13) + 1;

        if (newCard == 1) {
            cards.add(newCard);
            System.out.println("New card is  A");
        }
        else if ((newCard > 1) && (newCard < 11)) {
            cards.add(newCard);
            System.out.println("New card is " + newCard);
        }
        else if (newCard == 11) {
            cards.add(10);
            System.out.println("New card is JACK");
        }
        else if (newCard == 12) {
            cards.add(10);
            System.out.println("New card is QUEEN");
        }
        else {
            cards.add(10);
            System.out.println("New card is KING");
        }
        return cards;
    }


    private static List<Integer> round(List<Integer> cards) {
        int choice;

        System.out.print("You have ");
        printCards(cards);
        System.out.println();
        choice = options(cards);

        switch (choice) {
            case 1:
                cards = hit(cards);
                break;
            case 2:
                cards = hold(cards);
                break;
            case 3:
                isExit = true;
                displayStats();
                break;
        }

        return cards;
    }

    private static int options(List<Integer> cards) {
        System.out.println("	1. Hit");
        System.out.println("	2. Hold");
        System.out.println("	3. Exit");
        System.out.print("Choose an option: ");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        System.out.println();

        while ((choice < 1) || (choice > 3)) {
            System.out.println("Invalid input!");
            System.out.println("Please enter an integer value between 1 and 4.");
            System.out.println("	1. Hit");
            System.out.println("	2. Hold");
            System.out.println("	3. Exit");
            System.out.print("Choose an option: ");
            choice = input.nextInt();
        }

        return choice;
    }


    private static List<Integer> hold(List<Integer> cards) {
        int dealer = random.nextInt(11) + 16;
        System.out.println("Dealer's hand: " + dealer);
        System.out.print("Your hand is: ");
        printCards(cards); System.out.println();

        if ((getSum(cards) > dealer) || (dealer > 21)) {
            System.out.println("You win!\n\n");
            totalGame++; wins++;
        }
        else if (getSum(cards) < dealer) {
            System.out.println("Dealer win!\n\n");
            totalGame++;
        }
        else {
            System.out.println("It's a tie! No one wins!\n\n");
            totalGame++; ties++;
        }

        cards.clear();
        cards = hit(cards);
        return cards;
    }

    private static void displayStats() {
        // wins, ties, game statistics
        System.out.println("Number of Player wins: " + wins);
        System.out.println("Number of Dealer wins: " + (totalGame-wins-ties));
        System.out.println("Number of tie games: " + ties);
        System.out.println("Total " + totalGame +" of games played is: ");
        System.out.println("Percentage of Player wins: " + (100*wins/totalGame) + "%");
        System.out.println();
    }
}
