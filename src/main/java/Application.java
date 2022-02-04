
public class Application {

    public static void main(String[] args) throws Exception {

        //Seems like Java stumbles into memory limit, so I had to scale this down to a factor of 10
        float scale = 0.1f;
        //Another thing to note is that users have unique ratings for 3 reasons:
        //1)TreeMap (RB Tree) doesn't allow for duplicates in keys
        //2)Even if TreeMap allowed it, the memory consumption would've been too big
        //3)This doesn't affect the algorithm significantly
        final int userLimit = (int) (500000000 * scale);
        final int maxRating = (int) (1000000000 * scale);
        final int sleepLimitMs = 2000;
        UserGenerator generator = new UserGenerator();
        UserCollection users = generator.generateUsers(userLimit, maxRating);
        UserMatcher matcher = new UserMatcher(users, maxRating);

        double minDuration = Integer.MAX_VALUE;
        double maxDuration = 0;
        double averageDuration;
        double expected = 0;
        double[] durations = new double[100];
        for (int i = 1; i <= 100; i++) {
            System.out.println("Iteration: " + i);
            System.out.println("Number of players: " + users.size() + ", Max rating: " + maxRating);
            System.out.println("Generating first team:");
            Team firstTeam = matcher.getRandomTeam();
            int rating1 = firstTeam.getPlayer1().getRating();
            int rating2 = firstTeam.getPlayer2().getRating();
            System.out.println("Team 1: " + rating1 + " + " + rating2 + " = " + firstTeam.getRating());
            System.out.println("Searching for matches:");
            long startTime = System.nanoTime();
            Team secondTeam = matcher.findMatch(firstTeam);
            long endTime = System.nanoTime();
            double duration = (double) (endTime - startTime) / 1000000;
            if (secondTeam != null) {
                rating1 = secondTeam.getPlayer1().getRating();
                rating2 = secondTeam.getPlayer2().getRating();
                System.out.println("Matched Team 2: " + rating1 + " + " + rating2 + " = " + secondTeam.getRating());
            } else {
                System.out.println("Match not found.");
            }
            durations[i - 1] = duration;
            if (duration < minDuration) {
                minDuration = duration;
            }
            if (duration > maxDuration) {
                maxDuration = duration;
            }
            averageDuration = (minDuration + maxDuration) / 2;
            expected = getExpectation(durations);
            System.out.printf("Stats: min: %.6f ms, max: %.6f, avg: %.6f, expected: %.6f \n", minDuration, maxDuration, averageDuration, expected);
            System.out.printf("Took %.6f ms\n", duration);


            System.out.println("Sleep(" + sleepLimitMs + "ms) to let you check results.");
            System.out.println("");
            Thread.sleep(sleepLimitMs);
        }
    }

    static double getExpectation(double[] values) {
        double prb = 1 / (double) values.length;
        double sum = 0;
        for (double value : values) {
            sum += value * prb;
        }
        return sum;
    }
}
