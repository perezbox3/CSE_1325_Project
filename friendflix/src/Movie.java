
public class Movie {
    //Movie data
    private int id;
    private String title;
    private String description;
    private String genre;

    private double actingSum;
    private int actingCount;

    private double directionSum;
    private int directionCount;

    private double musicSum;
    private int musicCount;

    private double overallSum;
    private int overallCount;

    //Movie constructor
    public Movie(int id, String title, String genre, String description){
        //Movie data
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        //Acting category
        this.actingCount = 0;  
        this.actingSum = 0;
        //Direction category
        this.directionCount = 0; 
        this.directionSum = 0;
        //Music category
        this.musicCount = 0;  
        this.musicSum = 0;
        //Overall rating
        this.overallCount = 0;  
        this.overallSum = 0;
    }
    //Getters and Setters
    //TITLE
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        if(title != this.title){
            System.out.println("Title changed from '" + this.title + "' to '" + title + "'!");
        }
        this.title = title;
    }
    //ID
    public int getId(){
        return id;
    }
    public void setId(int id){
        if(id != this.id){
        System.out.println("Movie " + this.id + " changed to Movie " + id + "!");
        }
        this.id = id;
    }
    //GENRE
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        if(genre != this.genre){
            System.out.println("Genre changed from '" + this.genre + "' to '" + genre + "'!");
        }
        this.genre = genre;
    }
    //DESCRIPTION
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        if(description != this.description){
            System.out.println("Description changed from '" + this.description + "' to '" + description + "'!");
        }
        this.description = description;
    }
    //TO STRING
    public String toString(){
        return "Movie " + id + " (" + title + ", " + genre + ", " + description + ")" + ":";
    }
    //Movie methods
    public void addRating(String category, double score){
        //Makes sure score can't be higher than 5 stars
        if(score > 5.0){
            System.out.println("Rating cannot be higher than 5 stars.");
        }
        else{
            System.out.println("Adding " + score + " stars to " + category + " rating!");
            switch (category.toLowerCase()) {
            case "acting":
            actingSum += score;
            actingCount++;
            break;

            case "direction":
            directionSum += score;
            directionCount++;
            break;

            case "music":
            musicSum += score;
            musicCount++;
            break;

            default:
            System.out.println(category + " category does not exist. Cannot add rating.");
            break;
            }
        }
    }
    public double getAverage(String category){
        switch(category.toLowerCase()){
            case "acting": 
            //Prevents dividing by 0
            if(actingCount == 0){
                actingSum = 0.0;
            }
            else{
            actingSum = actingSum/actingCount;
            }
            return actingSum;
            case "direction":
            if(directionCount == 0){
                directionSum = 0.0;
            }
            else{
            directionSum = directionSum/directionCount;
            }
            return directionSum;
            case "music":
            if(musicCount == 0){
                musicSum = 0.0;
            }
            else{
            musicSum = musicSum/musicCount;
            }
            return musicSum;
            default:
            System.out.println(category + " category does not exist. Cannot get average.");
            return 0.0;
    }
}
    public double getCompositeAverage(){
        //Calculates sum and count for averaging
        overallSum = actingSum + directionSum + musicSum;
        overallCount = actingCount + directionCount + musicCount;
        //Prevents dividing by 0
        if(overallCount == 0){
            return 0.0;
        }
        else{
            return overallSum/overallCount;
        }
    }
}
class Main{
    public static void main(String[] args) {
        //Declarations
        Movie test = new Movie(1, "Title", "Genre", "Description");
        String category = "ACTING";

        //Testing object and methods
        
        //Testing to make sure getAverage does not divide by 0 (returns 0.0 if it works)
        System.out.println("Testing to make sure getAverage does not divide by 0 (returns 0.0 if it works)...");
        System.out.println(test.getAverage(category));
        System.out.println();
        //Testing addRating method
        System.out.println("Testing addRating method...");
        test.addRating("acting", 5);
        test.addRating("music", 2);
        System.out.println();
        //Testing toString, getAverage, getCompositeAverage, and getId methods
        System.out.println("Testing toString, getAverage, getCompositeAverage, and getId methods...");
        System.out.println(test.toString());
        System.out.println(category + " rating: " + test.getAverage(category) + " stars");
        System.out.println("Overall rating: " + test.getCompositeAverage() + " stars");
        System.out.println("Movie " + test.getId() + ":");
        System.out.println();
        category = "Action";
        //Testing default case for addRating
        System.out.println("Testing default case for addRating...");
        test.addRating(category, 5);
        System.out.println(category + " rating: " + test.getAverage(category) + " stars");
        System.out.println("Overall rating: " + test.getCompositeAverage() + " stars");
        System.out.println();
        //Testing setter outputs
        System.out.println("Testing setter outputs...");
        test.setId(2);
        test.setTitle("NewTitle");
        test.setGenre("NewGenre");
        test.setDescription("NewDescription");
        
    }
}