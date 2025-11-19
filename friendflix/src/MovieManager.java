import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MovieManager{
    private String filePath;
    private HashMap<Integer, Movie> byId;
    private List<Movie> allMovies;

    public MovieManager(String filePath){
        this.filePath = filePath;
        this.byId = new HashMap<>();
        this.allMovies = new ArrayList<Movie>();
    }

    public void load(){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            byId.clear();
            allMovies.clear();
            while((line = reader.readLine()) != null){
                String parts[] = line.split("|");
                if(parts.length >= 4){
                    
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1].trim();
                    String genre = parts[2].trim();
                    String description = parts[3].trim();

                    Movie temp = new Movie(id, title, genre, description);
                    allMovies.add(temp);
                    byId.put(id, temp);
                }
            }
            System.out.println("Loaded movies successfully! " + allMovies.size() + " movies loaded!");
        } catch (Exception e) {
            System.out.println("Error loading movies: " + e.getMessage());
        }
    }

    public void save(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Movie movie : allMovies) {
                String line = movie.getId() + "|" + movie.getTitle() + "|" + movie.getGenre() + "|" + movie.getDescription();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Movies saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving movies: " + e.getMessage());
        }
    }

    public void listAll(){
        if(allMovies.isEmpty()){
            System.out.println("No Movies found!");
        }
        else{
            System.out.println("-----------ALL MOVIES-----------");
            for (Movie movie : allMovies) {
                System.out.println(movie.toString());
                
            }
        System.out.println("--------------------------------");
        }
    }

    public Movie findById(int id){
        return byId.get(id);
    }

    public List<Movie> searchByTitle(String query){
        List<Movie> foundMovies = new ArrayList<>();
        String lcQuery = query.toLowerCase();
        for (Movie movie : foundMovies) {
            if(movie.getTitle().toLowerCase().contains(lcQuery)){
                foundMovies.add(movie);
            }
        }
        return foundMovies;
    }

    public void addRating(int movieId, String category, double score){
        Movie movie = findById(movieId);
        if(movie != null){
            movie.addRating(category, score);
        }
        else{
            System.out.println("No movie with ID:" + movieId + " found.");
        }
    }

    public List<Movie> topByComposite(int count){
        List<Movie> sortedMovies = new ArrayList<>(allMovies);
        sortedMovies.sort((m1, m2) -> Double.compare(m2.getCompositeAverage(), m1.getCompositeAverage()));
        return sortedMovies.subList(0, Math.min(count, sortedMovies.size()));
    }

    public double globalAverageForCategory(String category){
        if (allMovies.isEmpty()) {
            return 0.0;
        }
        double totalAverage = 0.0;
        int ratedMovies = 0;

        for (Movie movie : allMovies) {
            double movieAverage = movie.getAverage(category);
            totalAverage += movieAverage;
            ratedMovies++;
        }
        if(ratedMovies == 0){
            return 0.0;
        }
        return totalAverage/ratedMovies;
    }
    }