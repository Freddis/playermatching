import java.util.Map;
import java.util.Random;

public class UserMatcher {

    private final UserCollection users;
    private final int maxRating;

    public UserMatcher(UserCollection users, int maxRating) throws Exception {
        if(users.size() == 0){
            throw new Exception("UserCollection can't be empty");
        }
        this.users = users;
        this.maxRating = maxRating;
    }

    public Team findMatch(Team team) {
        int teamRating = team.getRating();
        int ceiling = Math.min(team.getRating(), maxRating);
        Random random = new Random();
        int startRating = random.nextInt(ceiling);
        boolean direction = random.nextInt(1) == 1;
        if(direction) {
            Team match = goUpTheTree(startRating, teamRating);
            if (match != null) {
                return match;
            }
            return goDownTheTree(startRating,teamRating);
        }
        else {
            Team match = goDownTheTree(startRating, teamRating);
            if (match != null) {
                return match;
            }
            return goUpTheTree(startRating,teamRating);
        }
    }

    private Team goDownTheTree(int rating, int teamRating) {
        while(rating <= maxRating) {
            Map.Entry<Integer, Integer> firstPlayer = users.ceilingEntry(rating++);
            int secondPlayerRating = teamRating - firstPlayer.getKey();
            if(users.containsKey(secondPlayerRating)){
                User player1 = new User(firstPlayer.getValue(), firstPlayer.getKey());
                User player2 = new User(users.get(secondPlayerRating),secondPlayerRating);
                return new Team(player1,player2);
            }
        }
        return null;
    }

    private Team goUpTheTree(int rating, int teamRating) {
        while(rating >= 0) {
            Map.Entry<Integer, Integer> firstPlayer = users.ceilingEntry(rating--);
            int secondPlayerRating = teamRating - firstPlayer.getKey();
            if(users.containsKey(secondPlayerRating)){
                User player1 = new User(firstPlayer.getValue(), firstPlayer.getKey());
                User player2 = new User(users.get(secondPlayerRating),secondPlayerRating);
                return new Team(player1,player2);
            }
        }
        return null;
    }

    public Team getRandomTeam() {
        User user1 = getRandomUser();
        User user2 = getRandomUser();
        return new Team(user1, user2);
    }

    private User getRandomUser() {
        Random random = new Random();
        Map.Entry<Integer, Integer> entry = null;
        while(entry == null) {
            int targetRating = random.nextInt(maxRating);
            entry = this.getClosestEntry(targetRating);
        }
        return new User(entry.getValue(),entry.getKey());
    }

    private Map.Entry<Integer, Integer> getClosestEntry(int key) {
        Map.Entry<Integer, Integer> low = users.floorEntry(key);
        Map.Entry<Integer, Integer> high = users.ceilingEntry(key);
        Map.Entry<Integer, Integer> res = null;
        if (low != null && high != null) {
            res = Math.abs(key - low.getKey()) < Math.abs(key - high.getKey()) ? low : high;
        } else if (low != null || high != null) {
            res = low != null ? low : high;
        }
        return res;
    }
}
