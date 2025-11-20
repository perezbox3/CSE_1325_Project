public class Review 
{

    private int movieId;
    private String username;
    private String text;
    private long timestamp;

	    //Constructor
	    public Review(int movieId, String username, String text, long timestamp) 
	    {
	        this.movieId = movieId;
	        this.username = username;
	        this.text = text;
	        this.timestamp = timestamp;
	    }
	
	    	//Setters
		    public int getMovieId() 
		    {
		        return movieId;
		    }
		
		    public String getUsername() 
		    {
		        return username;
		    }
		
		    public String getText() 
		    {
		        return text;
		    }
		
		    public long getTimestamp() 
		    {
		        return timestamp;
		    }
	
		    	//Default
			    @Override
			    public String toString() 
			    {
			        return movieId + " | " + username + " | " + timestamp + " | " + text;
			    }
}
