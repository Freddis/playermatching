public class Team {

    private final User player1;
    private final User player2;

    public Team(User player1, User player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public int getRating() {
        return player1.getRating() + player2.getRating();
    }
}
