package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //seed
        Random r = new Random(System.currentTimeMillis());

        //number of shoots and ladders
        int numShoots = 6;
        int numLadders = 6;

        //games
        int numTestGames = 100000;

        //players
        int numPlayers = 2;

        Map<Integer,Integer> shootAndLadderMap = new HashMap<>();

        generateShootsAndLadders(r, numShoots, numLadders, shootAndLadderMap);

        //System.out.println("shoots and ladders:" + shootAndLadderMap);


        StringBuilder csvOut = new StringBuilder();
        for( int n = 0; n < numTestGames; n++) {
            int[] playerPositions = new int[numPlayers];
            int turn = 0;
            boolean isWinner = false;

            while(!isWinner) {
                //System.out.println("turn " + turn);

                for(int p = 0; p < numPlayers && !isWinner; p++) {
                    //System.out.println("player " + p + " starting turn at " + playerPositions[p]);
                    int roll = (int) (r.nextDouble() * 6) + 1;
                    //System.out.println("roll:" + roll);

                    int newPlayerPosition = playerPositions[p] += roll;
                    //System.out.println(("initially moved to: " + newPlayerPosition));
                    if (shootAndLadderMap.containsKey(newPlayerPosition)) {
                        Integer shootAndLadderMoveTo = shootAndLadderMap.get(newPlayerPosition);
                        newPlayerPosition = playerPositions[p] = shootAndLadderMoveTo;
                        //System.out.println("sliding or climbing to:" + newPlayerPosition);
                    }

                    if (newPlayerPosition >= 100) {
                        isWinner = true;
                        break;
                    }
                }
                turn++;
            }

            //System.out.println("victory reached after " + turn + " turns");

            csvOut.append(turn).append("\n");
        }

        System.out.println(csvOut.toString());
    }

    private static void generateShootsAndLadders(Random r, int numShoots, int numLadders, Map<Integer, Integer> shootAndLadderMap) {
        for(int n = 0; n < numLadders; n++ ) {
            int startLadder = (int) (r.nextDouble() * 99) + 1;
            int endLadder = (int)( r.nextDouble() * (100 - startLadder + 1)) +  startLadder;

            shootAndLadderMap.put(startLadder,endLadder);
        }
        for(int n = 0; n < numShoots; n++ ) {
            int startShoot = (int) (r.nextDouble() * 99) + 1;
            int endShot = (int)( r.nextDouble() * (startShoot -1)) + 1;

            shootAndLadderMap.put(startShoot,endShot);
        }
    }
}
