/*
Microsoft Java Project Four: Battle Ships
 */
import java.util.*;
public class BattleShips {
    public static final int GRID_SIZE = 10;

    public static void main(String[] args){
        //Welcome the user
        System.out.println("\n**** Welcome to Battle Ship Game ****");
        System.out.println("\nRight now, the sea is empty.\n");

        Scanner input = new Scanner(System.in);

        //Initialize an array of ints size 10 with all values set to 0
        int[][] intArr = new int[GRID_SIZE][GRID_SIZE];

        drawGrid(intArr);

        //Ask user if he or she wants to start the game
        System.out.print("Start Game?, Y for Yes or N for No ");
        String startGame = input.next();
        //Keep asking until user answers either y or n
        while (!startGame.equalsIgnoreCase("y") && !startGame.equalsIgnoreCase("n")){
            System.out.print("Incorrect Answer.\nStart Game?, Y for Yes or N for No ");
            startGame = input.next();
        }
        //If the user says no, terminate the game
        if (startGame.equalsIgnoreCase("n")){
            System.out.println("Game has ended.");
            System.exit(0);
        }
        else{
            game(intArr);
        }

    }

    public static void game(int[][] intArr){
        Scanner input = new Scanner(System.in);

        //Initialize the number of ships for each player to 0
        int playerShipsCount = 0, computerShipsCount = 0;
        playerShipsCount = shipCoordinatesPlayer(intArr);


        //Draw the current ships on board
        System.out.println("\nThis is how you decided to arrange your ships\n");
        drawGrid(intArr);

        System.out.println("Computer is deploying ships.");
        computerShipsCount = shipCoordinatesComputer(intArr);
        //drawGrid(intArr);

        while (computerShipsCount > 0 && playerShipsCount > 0){
            int shipSubPlayer = 0, shipSubComp = 0;
            shipSubPlayer = playerSetTarget(shipSubPlayer, intArr);
            if(shipSubPlayer == 1){
                playerShipsCount--;
            }
            else if(shipSubPlayer == 2){
                computerShipsCount--;
            }
            shipSubComp = computerSetTarget(shipSubComp, intArr);
            drawGrid(intArr);
            if(shipSubComp == 1){
                playerShipsCount--;
            }
            else if(shipSubComp == 2){
                computerShipsCount--;
            }
            System.out.println("Your ships: " + playerShipsCount + " | Computer Ships: " + computerShipsCount);
            System.out.println("----------------------------------");
        }
        if (playerShipsCount == 0 && computerShipsCount > 0){
            System.out.println("\nComputer wins, player loses");
        }
        else if (computerShipsCount == 0 && playerShipsCount > 0){
            System.out.println("\nPlayer wins, computer loses");
        }
        else {
            System.out.println("Tie");
        }

        System.out.print("Want to play again?, Y for Yes or N for No ");
        String restartGame = input.next();
        while (!restartGame.equalsIgnoreCase("y") && !restartGame.equalsIgnoreCase("n")) {
            System.out.print("Incorrect Answer.\nWant to play again?, Y for Yes or N for No ");
            restartGame = input.next();
        }
        if (restartGame.equalsIgnoreCase("n")){
            System.out.println("Thanks for playing.");
            System.exit(0);
        }
        else{
            restartGame(intArr);
        }
    }

    public static void drawGrid(int[][] mainGrid){
        System.out.println("\n   0 1 2 3 4 5 6 7 8 9 ");
        for (int xCor = 0; xCor < GRID_SIZE; xCor++){
            System.out.print(xCor + "|");
            for (int yCor = 0; yCor < GRID_SIZE; yCor++){
                if (mainGrid[xCor][yCor] == 1) {
                    System.out.print(" @");
                }
                //else if (mainGrid[xCor][yCor] == 2) {
                //    System.out.print(" $");
                //}
                else if (mainGrid[xCor][yCor] == 3) {
                    System.out.print(" X");
                }
                else if (mainGrid[xCor][yCor] == 4) {
                    System.out.print(" -");
                }
                else {
                    System.out.print("  ");
                }
            }
            System.out.print(" |" + xCor + "\n");
        }
        System.out.println("   0 1 2 3 4 5 6 7 8 9 \n");
    }

    public static int shipCoordinatesPlayer(int[][] intArr){
        Scanner input = new Scanner(System.in);
        int playerShipsCount = 0;
        for (;playerShipsCount < 5; playerShipsCount++){
            System.out.println();
            int xCor = -1;
            while (xCor > 9 || xCor < 0){
                System.out.print("Enter X coordinate for your ship " + (playerShipsCount + 1) + ": ");
                xCor = input.nextInt();
            }
            int yCor = -1;
            while (yCor > 9 || yCor < 0){
                System.out.print("Enter Y coordinate for your ship " + (playerShipsCount + 1) + ": ");
                yCor = input.nextInt();
            }
            if(intArr[yCor][xCor] == 1){
                System.out.println("You have deployed a ship there already, try new set of coordinates.");
                playerShipsCount--;
            }
            else {
                intArr[yCor][xCor] = 1;
            }
        }
        return playerShipsCount;
    }

    public static int shipCoordinatesComputer(int[][] intArr){
        int computerShipsCount = 0;
        Random rand = new Random();
        for (;computerShipsCount < 5; computerShipsCount++){
            int computerX = rand.nextInt(10);
            int computerY = rand.nextInt(10);
            if (intArr[computerY][computerX] == 1 || intArr[computerY][computerX] == 2){
                computerShipsCount--;
            }
            else{
                intArr[computerY][computerX] = 2;
                System.out.println("Ship " + (computerShipsCount + 1) + " DEPLOYED");
            }
        }
        return computerShipsCount;
    }

    public static int playerSetTarget(int shipSub, int[][] intArr){
        Scanner input = new Scanner(System.in);
        System.out.println("\nYOUR TURN");
        int xCor = -1;
        while (xCor > 9 || xCor < 0){
            System.out.print("Enter X coordinate: ");
            xCor = input.nextInt();
        }
        int yCor = -1;
        while (yCor > 9 || yCor < 0){
            System.out.print("Enter Y coordinate: ");
            yCor = input.nextInt();
        }
        if (intArr[yCor][xCor] == 1){
            System.out.println("Better watch out, you hit one of your own ships");
            intArr[yCor][xCor] = 3;
            shipSub = 1;
        }
        else if (intArr[yCor][xCor] == 2){
            System.out.println("Great you hit it, one less enemy ship");
            intArr[yCor][xCor] = 4;
            shipSub = 2;
        }
        else {
            System.out.println("You hit water");
            intArr[yCor][xCor] = 3;
        }
        return shipSub;
    }

    public static int computerSetTarget(int shipSub, int[][] intArr){
        System.out.println("\nCOMPUTER'S TURN");
        Random rand = new Random();
        int computerX = rand.nextInt(10);
        int computerY = rand.nextInt(10);
        while(intArr[computerY][computerX] == 2 || intArr[computerY][computerX] == 3 || intArr[computerY][computerX] == 4){
            computerX = rand.nextInt(10);
            computerY = rand.nextInt(10);
        }
        if (intArr[computerY][computerX] == 1){
            System.out.println("The computer has destroy one of your ships");
            intArr[computerY][computerX] = 3;
            shipSub = 1;
        }
        else{
            System.out.println("Computer missed");
            intArr[computerY][computerX] = 3;
        }
        return shipSub;
    }

    public static void restartGame(int[][] intArr){
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                intArr[i][j] = 0;
            }
        }
        game(intArr);
    }

}
