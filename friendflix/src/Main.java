import java.util.*;

public class Main 
{
    public static void main (String [] args)
    {
        MovieManager movieManager = new MovieManager("../data/movies.txt");
        ReviewManager reviewManager = new ReviewManager("../data/reviews.txt");
        UserManager userManager = new UserManager();

        Scanner input = new Scanner(System.in);
        boolean running = true;

        movieManager.load();
        reviewManager.load();

        while (running)
        {
            if(!userManager.isLoggedIn())
            {
            System.out.println("1. Login");
            System.out.println("2. Signup");                
            }
            System.out.println("3. List Movies");
            System.out.println("4. Search Movies");
            System.out.println("5. Rate Movie");
            System.out.println("6. Write Review");
            System.out.println("7. Read Reviews");
            if(userManager.isLoggedIn())
            {
            System.out.println("8. Change Password");
            System.out.println("9. Change Email");                
            }
            System.out.println("10. Show Top Movies by Composite Score");
            System.out.println("11. Show Average for Category");
            if(userManager.isLoggedIn())
            {
                System.out.println("12. Logout");
            }
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine();

            String username, password, newPassword, newEmail, email, search, review, category;
            int id, count;
            double rating, average;

            switch(choice)
            {
                case 0:
                    running = false;
                    break;

                case 1: 
                    if(userManager.isLoggedIn())
                    {
                        System.out.println("You are already loggged in.");
                        break;
                    }

                    System.out.println("======= Login =======");
                    System.out.printf("Enter your username: ");
                    username = input.nextLine();
                    System.out.printf("Enter your password: ");
                    password = input.nextLine();
                    if (userManager.login(username, password))
                    {
                        System.out.println("Login successful. Welcome back, " + username + "!");
                    }
                    else 
                    {
                        System.out.println("Login failed. Check your username/password or sign up first.");
                    }
                    break;

                case 2:
                    if(userManager.isLoggedIn())
                    {
                        System.out.println("You are already loggged in.");
                        break;
                    }
                    System.out.println("======= Signup =======");
                    System.out.printf("Enter your email: ");
                    email = input.nextLine();                   
                    System.out.printf("Enter your username: ");
                    username = input.nextLine();
                    System.out.printf("Enter your password: ");
                    password = input.nextLine();
                    if (userManager.signup(username, password, email))
                    {
                        System.out.println("Signup successful. You can now log in.");
                    }
                    else 
                    {
                        System.out.println("Signup failed. Username may already exist or inputs were invalid.");
                    }
                    break;

                case 3:
                    movieManager.listAll();
                    break;

                case 4:
                    System.out.println("======= Movie Search =======");
                    System.out.printf("Enter the movie name by title: ");
                    search = input.nextLine();
                    List<Movie> found = movieManager.searchByTitle(search);
                    if (found.isEmpty())
                    {
                        System.out.println("No matches found.");
                    }
                    else
                    {
                        for (Movie m : found)
                        {
                            System.out.println(m.getId() +" | "+ m);
                        }
                    }
                    break;

                case 5:
                    if(!userManager.isLoggedIn())
                    {
                        System.out.println("You need to be logged in to do this.");
                        break;
                    }
                    System.out.println("======= Rate Movie =======");
                    System.out.printf("Enter the movie ID to rate: ");
                    id = input.nextInt();
                    input.nextLine();
                    System.out.printf("Enter category to rate (acting/direction/music): ");
                    category = input.nextLine();
                    System.out.printf("Enter the rating (1-5): ");
                    rating = input.nextInt();
                    input.nextLine();
                    movieManager.addRating(id, category, rating);
                    break;

                case 6:
                    if(!userManager.isLoggedIn())
                    {
                        System.out.println("You need to be logged in to do this.");
                        break;
                    }
                    System.out.println("======= Write Review =======");
                    System.out.printf("Enter the movie ID to review: ");
                    choice = input.nextInt();
                    input.nextLine();
                    System.out.printf("Enter the review: ");
                    review = input.nextLine();
                    reviewManager.addReview(choice, userManager.getCurrentUser().getUsername(), review);
                    break;

                case 7: 
                    System.out.println("======= Read Reviews =======");
                    System.out.printf("Enter the movie ID to view the reviews: ");
                    id = input.nextInt();
                    input.nextLine();
                    List<Review> reviews = reviewManager.getReviewsForMovie(id);
                    if(reviews.isEmpty())
                    {
                        System.out.println("There is no reviews for this movie.");
                    }
                    else 
                    {
                        for (Review r : reviews)
                        {
                            System.out.println(r);
                        }
                    }
                    break;
                
                case 8:
                    if (!userManager.isLoggedIn()) {
                        System.out.println("You need to be logged in to do this.");
                        break;
                    }
                    System.out.println("======= Change Password =======");
                    System.out.printf("Enter your password: ");
                    password = input.nextLine();                   
                    System.out.printf("Enter your new password: ");
                    newPassword = input.nextLine();
                    if (userManager.changeUserPassword(userManager.getCurrentUser().getUsername(), password, newPassword))
                    {
                        System.out.println("Password updated.");
                    }   
                    else
                    {
                        System.out.println("Password not updated, try again.");
                    }                   
                    break;
                
                case 9:
                    if (!userManager.isLoggedIn()) {
                        System.out.println("You need to be logged in to do this.");
                        break;
                    }
                    System.out.println("======= Change Email =======");
                    System.out.printf("Enter your password: ");
                    password = input.nextLine();                   
                    System.out.printf("Enter your new email: ");
                    newEmail = input.nextLine();
                    if (userManager.changeUserEmail(userManager.getCurrentUser().getUsername(), password, newEmail))
                    {
                        System.out.println("Email updated.");
                    }
                    else
                    {
                        System.out.println("Email not updated, try again.");
                    }
                    break;

                case 10:
                    System.out.println("======= Show Top Movies =======");
                    System.out.print("How many top movies to show: ");
                    count = input.nextInt();
                    input.nextLine();
                    List <Movie> topMovies = movieManager.topByComposite(count);
                    if (topMovies.isEmpty())
                    {
                        System.out.println("No movies have been rated yet.");
                    }
                    else
                    {
                        for (Movie m : topMovies)
                        {
                            System.out.println(m + "| Composite Average: "+ m.getCompositeAverage());
                        }
                    }
                    break;

                case 11:
                    System.out.println("======= Average Per Category =======");
                    System.out.println("Enter the category (acting/direction/music): ");
                    category = input.nextLine();
                    average = movieManager.globalAverageForCategory(category);
                    System.out.println("Average for "+ category +" across all movies : "+ average);
                    break;

                case 12:
                    userManager.logout();
                    break;

                
                default:
                    System.out.println("Invalid Choice.");
                    break;
            }
        }
        movieManager.save();
        reviewManager.save();

        System.out.println("Goodbye!");
    }
}