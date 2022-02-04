public class User {
    private int id;
    private int rating;

    public User(int id, int rating){
        this.id = id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }
}
