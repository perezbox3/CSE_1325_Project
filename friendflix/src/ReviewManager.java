import java.io.*;
import java.util.*;

public class ReviewManager 
{

    private String filePath;
    private Map<Integer, List<Review>> reviewsByMovie;

	    public ReviewManager(String filePath) 
	    {
	        this.filePath = filePath;
	        this.reviewsByMovie = new HashMap<>();
	    }

    
		    public void load() 
		    {
		        reviewsByMovie.clear();
		
		        File file = new File(filePath);
		        if (!file.exists()) 
		        {
		            System.out.println("Review file not found. Starting empty.");
		            return;
		        }
		
			        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
			        {
			
			            String line;
			
			            while ((line = br.readLine()) != null) 
			            {
			                if (line.trim().isEmpty()) 
			                {
			                	continue;
			                }
			
			                	String[] parts = line.split("\\|");
			
			                	if (parts.length < 4)
			                	{
			                		System.out.println("Skipping bad review line: " + line);
			                		continue;
			                	}
			
					                int movieId = Integer.parseInt(parts[0]);
					                String username = parts[1];
					                long timestamp = Long.parseLong(parts[2]);
					                String text = parts[3];
					
					                Review review = new Review(movieId, username, text, timestamp);
			
					                	reviewsByMovie.computeIfAbsent(movieId, k -> new ArrayList<>()).add(review);
			            }
			
			            	System.out.println("Reviews loaded successfully.");
			
			        } 
		        
			        catch (IOException e) 
			        {
			            System.out.println("Error loading reviews: " + e.getMessage());
			        }
		    }
		
		
			    public void save() 
			    {
			        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) 
			        {
			
			            for (List<Review> list : reviewsByMovie.values()) 
			            {
			                for (Review r : list) 
			                {
			                    bw.write(r.getMovieId() + "|" + r.getUsername() + "|" + r.getTimestamp() + "|" + r.getText());
			                    bw.newLine();
			                }
			            }
			
			            	System.out.println("Reviews saved.");
			
			        } 
			        
			        	catch (IOException e) 
			        	{
			        		System.out.println("Error saving reviews: " + e.getMessage());
			        	}
			    }
			
			
				    public void addReview(int movieId, String username, String text) 
				    {
				
				        long time = System.currentTimeMillis();
				        Review r = new Review(movieId, username, text, time);
				
				        	reviewsByMovie.computeIfAbsent(movieId, k -> new ArrayList<>()).add(r);
				        	save(); 
				    }
				
				
					    public List<Review> getReviewsForMovie(int movieId) 
					    {
					        return reviewsByMovie.getOrDefault(movieId, new ArrayList<>());
					    }
					
					  
						    public int getReviewCountForMovie(int movieId) 
						    {
						        return reviewsByMovie.getOrDefault(movieId, Collections.emptyList()).size();
						    }
}
