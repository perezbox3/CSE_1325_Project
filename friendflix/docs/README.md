FriendFlix
Project Overview

FriendFlix is a terminal-based Java application that simulates a small video rental store with built-in ratings and reviews.
Users can create accounts, browse from a curated list of movies, rate them by category, and leave reviews. The program calculates averages for each movie and lets users see whether the “top 100” truly deserve their spot.

The system includes:

User authentication (signup/login/logout)

Movie list browsing and multi-category rating

Review system with stored user feedback

Simple statistics and averages

Features

User Authentication – Create accounts, log in, and log out.

Movie Database – Display and search through 100 curated movies.

Rating System – Rate movies by acting, direction, music, and overall enjoyment.

Reviews – Write and view reviews left by other users.

Statistics – Show per-category averages and top-rated movies.

Data Persistence – All user, movie, and review data saved to .txt files.

Recommended Class and Method Structure
🧍 User.java

Represents one user in the system.

Fields

String username

String passwordHash

Set<Integer> likedMovieIds

Set<Integer> dislikedMovieIds

Main Methods

getUsername()

getPasswordHash()

likeMovie(int movieId)

dislikeMovie(int movieId)

hasLiked(int movieId)

hasDisliked(int movieId)

Purpose
Stores personal data and like/dislike interactions. Simple object model — no file I/O here.

🔐 UserManager.java

Handles user authentication and persistence.

Fields

String filePath

Map<String, User> usersByName

Main Methods

load() – Reads all user data from users.txt.

save() – Writes current user data to file.

signup(String username, String password) – Registers a new user if available.

login(String username, String password) – Authenticates and returns a User object.

recordLike(User user, int movieId) – Adds movie to user’s liked list.

recordDislike(User user, int movieId) – Adds movie to disliked list.

Purpose
Manages account creation, authentication, and persistence to data/users.txt.

🎬 Movie.java

Represents one movie and its accumulated ratings.

Fields

int id

String title

String description

int actingSum, actingCount

int directionSum, directionCount

int musicSum, musicCount

int overallSum, overallCount

Main Methods

addRating(String category, int score) – Adds a new score for a category.

getAverage(String category) – Returns the average score for a category.

getCompositeAverage() – Returns overall movie average (mean of categories).

getTitle(), getId(), getDescription()

Purpose
Stores all movie rating data and provides access to calculated averages.

🎞️ MovieManager.java

Maintains the list of all movies, handles searching and rating.

Fields

String filePath

Map<Integer, Movie> byId

List<Movie> allMovies

Main Methods

load() – Reads all movie data from movies.txt.

save() – Saves updated rating data back to file.

listAll() – Returns a list of all movies.

findById(int id) – Returns a movie object by ID.

searchByTitle(String query) – Finds movies matching part of a title.

addRating(int movieId, String category, int score) – Updates movie ratings.

topByComposite(int count) – Returns top-rated movies.

globalAverageForCategory(String category) – Returns average for all movies in a category.

Purpose
Central controller for all movie-related operations.
Connects directly with Main for listing, rating, and statistics.

📝 Review.java

Represents a user review for a movie.

Fields

int movieId

String username

String text

long timestamp

Main Methods

getMovieId()

getUsername()

getText()

getTimestamp()

Purpose
Simple data holder for reviews. Each review is tied to one movie and one user.

💬 ReviewManager.java

Handles all review storage, retrieval, and persistence.

Fields

String filePath

Map<Integer, List<Review>> reviewsByMovie

Main Methods

load() – Reads reviews from reviews.txt.

save() – Saves all reviews to file.

addReview(int movieId, String username, String text) – Adds a new review.

getReviewsForMovie(int movieId) – Returns all reviews for a specific movie.

getReviewCountForMovie(int movieId) – Returns number of reviews for a movie.

Purpose
Handles reading/writing user feedback and making it displayable by movie.

🧠 Main.java

The entry point that ties everything together through a menu-driven interface.

Fields

Scanner in

UserManager userManager

MovieManager movieManager

ReviewManager reviewManager

User currentUser

Main Methods

run() – Initializes managers, loads data, starts main loop.

mainMenu() – Shows options depending on login status.

handleChoice(int choice) – Routes user input to correct module.

loadAllData() / saveAllData() – Coordinates data persistence.

promptLogin(), promptSignup() – User authentication options.

promptRateMovie(), promptReview(), showTopMovies(), showStats() – User actions.

Purpose
Provides the UI loop (text menu). Main calls into each manager to perform actions.
Example flow:

Main → UserManager.signup()
Main → MovieManager.addRating()
Main → ReviewManager.addReview()

Data Storage Format

users.txt

username|passwordHash|likedIds|dislikedIds
alice|5f4dcc3b5aa765d61d8327deb882cf99|1,5|2


movies.txt

id|title|description|aSum|aCnt|dSum|dCnt|mSum|mCnt|oSum|oCnt
1|The Matrix|Sci-fi classic|40|5|39|5|41|5|42|5


reviews.txt

movieId|username|timestamp|reviewText
1|alice|1730060000|Loved the visuals!

Integration Flow

Main.java loads all managers and data files.

User logs in or signs up through UserManager.

User selects a movie (via MovieManager) to:

Rate it (addRating())

Write a review (ReviewManager.addReview())

View averages and reviews (MovieManager + ReviewManager)

Likes/dislikes stored via UserManager.

On program exit, Main calls save() on all managers to update files.

Compilation & Execution

From within the src/ directory:

javac *.java
java Main

Recommended Design Notes

Keep one instance of each manager shared by Main.

No subclasses needed yet — each file handles one responsibility.

Use |-separated text files for easy reading and writing.

Add subclasses or extra layers (e.g., AdminUser) later if complexity grows.

Summary of Responsibilities
File	Purpose	Key Methods
Main.java	Menu, navigation, integration	run(), mainMenu(), handleChoice()
User.java	Stores user info	likeMovie(), dislikeMovie()
UserManager.java	Handles signup/login and persistence	signup(), login(), save()
Movie.java	Movie model	addRating(), getCompositeAverage()
MovieManager.java	Manages movie list and ratings	listAll(), searchByTitle(), addRating()
Review.java	Review model	getMovieId(), getText()
ReviewManager.java	Handles storing and displaying reviews	addReview(), getReviewsForMovie()
