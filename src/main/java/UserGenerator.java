import java.util.Random;

public class UserGenerator {

    UserCollection generateUsers(int userLimit, int maxRating){
        UserCollection map = new UserCollection();
        Random random = new Random();
        int skips = 0;
        for(int i = 1; map.size() <= userLimit || i < 5000000; i++){
            int rating = random.nextInt(maxRating);
            int prevSize = map.size();
            map.put(rating,i);
            if(map.size() == prevSize){
                skips++;
            }
            if(map.size() % 100000 == 0){
                System.out.println("Added "+map.size()+" users. Skips for 100k: "+skips);
                skips = 0;
            }
        }
        return map;
    }
}
