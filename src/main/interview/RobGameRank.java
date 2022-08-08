import java.util.HashMap;

public class RobGameRank {

/*
One of the ways to rank games on a platform is through user engagement.

Assume we have a file containing data about user activity on a single server.

We want to know what the Top Game is, defined by:
The Top Game is the game users spent the most time in.

Each line of the file has the following information (comma separated):
- timestamp in seconds (long)
- user id (string)
- game id (int)
- action (string, either "join" or "quit")

e.g.
[
"1000000000,user1,1001,join", // user 1 joined game 1001
"1000000005,user2,1002,join", // user 2 joined game 1002
"1000000010,user1,1001,quit", // user 1 quit game 1001 after 10 seconds
"1000000020,user2,1002,quit", // user 2 quit game 1002 after 15 seconds
]

In this log,
The total time spent in game 1001 is 10 seconds.
The total time spent in game 1002 is 15 seconds.

Hence game 1002 is the Top Game.

This file could be missing some lines of data (e.g. the user joined, but then the app crashed).

To recover some data, we attempt to estimate session length with this rule:

    If a user joined a game but did not leave, assume they spent the minimum of
    - time spent before they joined a different game; and
    - average time spent across the same user's gaming sessions (from join to leave)

    e.g.
    "1500000000,user1,1001,join"
    "1500000005,user1,1001,quit"
    "1500000010,user1,1002,join"
    "1500000014,user1,1003,join" // (1500000014,user1,1002,quit) - Implicit
    "1500000015,user1,1003,quit"

    the user spent 5 seconds in game 1, so we assume they spent 4 seconds in game 2 (with missing quit event), since that's less than the average time they spend in game (5 seconds)

Write a function that returns the top game ID, given an array of strings representing
each line in the log file.


 // Q1: Tie
 // Q2: Just one game
 // Q3: What if a game has active user and its Top based on so far usage from live user.
    // Q3.1 => Apply the avg
    // Live vs LastSeen

 // Focus on:: LastSeen first (good to have 'Live')

 MaxHeap => The root will be game with max recorded usage time

<GameId, Duration(long)> add each map entry to a heap(max) with a comparator on Duration

PQ -> Node -> Object(GameId, Duration)
==> remove LogN
==> adding back with just updated duration LogN

==> TreeMap (Decreasing)

// N * 2LogN
// O(1) ==> Peek the root

int topGameID(string[] data) {

}

*/

    // Main class should be named 'Solution'
        public static void main(String[] args) {
            System.out.println("Hello, World");

        /*
         "1500000000,user1,1001,join"
        "1500000005,user1,1001,quit"
        "1500000010,user1,1002,join"
        "1500000014,user1,1003,join" // (1500000014,user1,1002,quit) - Implicit
        "1500000015,user1,1003,quit"
        */
            System.out.println(topGameID(new String[]{"1500000000,user1,1001,join", "1500000005,user1,1001,quit", "1500000010,user1,1002,join", "1500000014,user1,1003,join", "1500000015,user1,1003,quit"}));
        }

        // "1500000000,user1,1001,join"
        static int topGameID(String[] data) {
            final HashMap<String, Long> gameUsageMap = new HashMap<>();
            final HashMap<String, Long> userJoinMap = new HashMap<>();
            final HashMap<String, UserEngagement> userAvgEng = new HashMap<>();

            long maxUsage = 0l;
            int maxGameId = -1;
            for (String eachItem : data) {
                final String[] eachRecord = eachItem.split(",");
                if (eachRecord[3].equals("quit")) {
                    final String gameId = eachRecord[2];
                    long duration = Long.parseLong(eachRecord[0]) - userJoinMap.get(eachRecord[1]);

                    if (userAvgEng.containsKey(eachRecord[1])) {
                        final UserEngagement currentEng = userAvgEng.get(eachRecord[1]);
                        final long newTotalDuration = (currentEng.avgDuration * currentEng.totalGames) + duration;
                        final long newAvg = newTotalDuration / (currentEng.totalGames + 1);
                        userAvgEng.put(eachRecord[1], new UserEngagement(currentEng.totalGames + 1, newAvg));
                    } else {
                        userAvgEng.put(eachRecord[1], new UserEngagement(1, duration));
                    }

                    userJoinMap.remove(eachRecord[1]); //offline

                    gameUsageMap.put(gameId, gameUsageMap.getOrDefault(gameId, 0L) + duration);
                    if (maxUsage < gameUsageMap.get(gameId)) {
                        maxGameId = Integer.parseInt(gameId);
                        maxUsage = gameUsageMap.get(gameId);
                    }
                } else {  // join
                    if (userJoinMap.containsKey(eachRecord[1])) {
                        // Already Active
                        long duration = Long.parseLong(eachRecord[0]) - userJoinMap.get(eachRecord[1]);
                        long newDuration = Math.min(duration, userAvgEng.get(eachRecord[1]).avgDuration);

                        final String gameId = eachRecord[2];
                        gameUsageMap.put(gameId, gameUsageMap.getOrDefault(gameId, 0L) + newDuration);

                        if (maxUsage < gameUsageMap.get(gameId)) {
                            maxGameId = Integer.parseInt(gameId);
                            maxUsage = gameUsageMap.get(gameId);
                        }
                    }
                    userJoinMap.put(eachRecord[1], Long.valueOf(eachRecord[0])); // Override last live (quited last one)
                }
            }
            return maxGameId;
        }


        static class UserEngagement {
            int totalGames;
            long avgDuration; // trade-off If we really need to know what total so far (totalGames * avg)

            UserEngagement(int totalGames, long avgDuration) {
                this.totalGames = totalGames;
                this.avgDuration = avgDuration;
            }
        }
    }