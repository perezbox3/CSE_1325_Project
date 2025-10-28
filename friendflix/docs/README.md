# FriendFlix

## Project Overview
FriendFlix is a terminal-based Java application that simulates a small video rental store with built-in ratings and reviews.  
Users can create accounts, browse from a curated list of movies, rate them by category, and leave reviews.  
The program calculates averages for each movie and lets users see whether the “top 100” truly deserve their spot.

The system includes:
- User authentication (signup/login/logout)
- Movie list browsing and multi-category rating
- Review system with stored user feedback
- Simple statistics and averages

---

## Features
- **User Authentication** – Create accounts, log in, and log out.
- **Movie Database** – Display and search through 100 curated movies.
- **Rating System** – Rate movies by acting, direction, music, and overall enjoyment.
- **Reviews** – Write and view reviews left by other users.
- **Statistics** – Show per-category averages and top-rated movies.
- **Data Persistence** – All user, movie, and review data saved to `.txt` files.

---

## Recommended Class and Method Structure

### User.java
Represents one user in the system.

**Fields**
- String username
- String passwordHash
- Set<Integer> likedMovieIds
- Set<Integer> dislikedMovieIds

**Main Methods**
- getUsername()
- getPasswordHash()
- likeMovie(int movieId)
- dislikeMovie(int movieId)
- hasLiked(int movieId)
- hasDisliked(int movieId)

**Purpose**
Stores personal data and like/dislike interactions.  
No file I/O in this class.

---

### UserManager.java
Handles user authentication and persistence.

**Fields**
- String filePath
- Map<String, User> usersByName

**Main Methods**
- load() – Reads user data from users.txt
- save() – Writes user data to file
- signup(String username, String password) – Registers a new user
- login(String username, String password) – Authenticates user
- recordLike(User user, int movieId)
- recordDislike(User user, int movieId)

**Purpose**
Manages account creation, authentication, and persistence to data/users.txt.

---

### Movie.java
Represents one movie and its accumulated ratings.

**Fields**
- int id
- String title
- String description
- int actingSum, actingCount
- int directionSum, directionCount
- int musicSum, musicCount
- int overallSum, overallCount

**Main Methods**
- addRating(String category, int score)
- getAverage(String category)
- getCompositeAverage()
- getTitle(), getId(), getDescription()

**Purpose**
Stores all movie rating data and provides access to calculated averages.

---

### MovieManager.java
Maintains the list of all movies, handles searching and rating.

**Fields**
- String filePath
- Map<Integer, Movie> byId
- List<Movie> allMovies

**Main Methods**
- load() – Reads movies from movies.txt
- save() – Saves updated movie data
- listAll()
- findById(int id)
- searchByTitle(String query)
- addRating(int movieId, String category, int score)
- topByComposite(int count)
- globalAverageForCategory(String category)

**Purpose**
Controls all movie-related logic such as listing, rating, and statistics.

---

### Review.java
Represents a user review for a movie.

**Fields**
- int movieId
- String username
- String text
- long timestamp

**Main Methods**
- getMovieId()
- getUsername()
- getText()
- getTimestamp()

**Purpose**
Simple data holder for reviews. Each review is tied to one movie and one user.

---

### ReviewManager.java
Handles all review storage, retrieval, and persistence.

**Fields**
- String filePath
- Map<Integer, List<Review>> reviewsByMovie

**Main Methods**
- load() – Reads reviews from reviews.txt
- save() – Saves reviews to file
- addReview(int movieId, String username, String text)
- getReviewsForMovie(int movieId)
- getReviewCountForMovie(int movieId)

**Purpose**
Stores and retrieves user reviews for each movie.

---

### Main.java
The entry point that ties everything together with a menu-driven interface.

**Fields**
- Scanner in
- UserManager userManager
- MovieManager movieManager
- ReviewManager reviewManager
- User currentUser

**Main Methods**
- run() – Initializes managers, loads data, and starts the main loop
- mainMenu() – Displays options based on login state
- handleChoice(int choice) – Routes user input to correct module
- loadAllData(), saveAllData()
- promptLogin(), promptSignup()
- promptRateMovie(), promptReview()
- showTopMovies(), showStats()

**Purpose**
Provides the console menu interface and directs flow between managers.  
Example:
Main -> UserManager.signup()
Main -> MovieManager.addRating()
Main -> ReviewManager.addReview()

yaml
Copy code

---

## Data Storage Format

**users.txt**
username|passwordHash|likedIds|dislikedIds
alice|5f4dcc3b5aa765d61d8327deb882cf99|1,5|2

markdown
Copy code

**movies.txt**
id|title|description|aSum|aCnt|dSum|dCnt|mSum|mCnt|oSum|oCnt
1|The Matrix|Sci-fi classic|40|5|39|5|41|5|42|5

markdown
Copy code

**reviews.txt**
movieId|username|timestamp|reviewText
1|alice|1730060000|Loved the visuals!

yaml
Copy code

---

## Integration Flow
1. Main loads all managers and data files.  
2. User logs in or signs up via UserManager.  
3. User selects a movie from MovieManager to:
   - Rate it (addRating)
   - Write a review (ReviewManager.addReview)
   - View averages and reviews (MovieManager + ReviewManager)
4. Likes/dislikes are updated through UserManager.
5. On exit, Main saves data for all managers.

---

## Compilation & Execution
From within the `src/` directory:
javac *.java
java Main

yaml
Copy code

---

## Summary of Responsibilities

| File | Purpose | Key Methods |
|------|----------|-------------|
| Main.java | Menu, navigation, integration | run(), mainMenu(), handleChoice() |
| User.java | Stores user info | likeMovie(), dislikeMovie() |
| UserManager.java | Handles signup/login | signup(), login(), save() |
| Movie.java | Movie model | addRating(), getCompositeAverage() |
| MovieManager.java | Manages movie list | listAll(), searchByTitle(), addRating() |
| Review.java | Review model | getMovieId(), getText() |
| ReviewManager.java | Handles reviews | addReview(), getReviewsForMovie() |

---

## Development Workflow
1. Person 1 builds User management (signup/login/logout).
2. Person 2 builds Movie system (list/search/rate/averages).
3. Person 3 builds Review system (add/view) and statistics.
4. Integrate all modules into Main.java.

---

## Notes
- One instance of each manager shared by Main.
- No subclasses needed yet; one class per concept.
- Simple `|`-separated `.txt` files for storage.
- Add advanced patterns (like inheritance or interfaces) only if required later.
