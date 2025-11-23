# FriendFlix

## What is FriendFlix?
FriendFlix is a simple program you run in the terminal. It lets you make an account, look at a list of movies, rate them, and write reviews. You can also see what other people thought about the movies.

## What can you do?
- Make an account and log in
- Look at movies and search for them by name
- Rate movies (acting, direction, music)
- Write and read reviews
- See which movies are rated the best

## How do you use it?
1. Open a terminal and go to the `src` folder.
2. Type: javac *.java
3. Type: java Main
3. Follow the menu instructions on the screen.

## How does it save stuff?
The program saves your account, movie ratings, and reviews in text files. When you run it again, your data is still there.

### User.java
Represents a single user account in the system.

**Fields**
- String username
- String password (stores the hashed password)
- String email
- SecureRandom RANDOM (for salt generation)
- int SALT_LENGTH (for password hashing)

**Main Methods**
- getUsername()
- getPasswordHash()
- getEmail()
- setUsername(String username) - includes validation
- setPassword(String password) - hashes password and includes validation
- setEmail(String email) - includes validation
- changePassword(String attemptedPassword, String newPassword) - verifies old and sets new hash
- changeEmail(String attemptedPassword, String email) - verifies password and sets new email
- verifyPassword(String password, String storedSaltColonHash) - checks if a plain password matches stored hash
- (private) hashPassword(String password) - generates salt and SHA-256 hash
- (private) digest(String password, byte[] salt) - performs SHA-256 hashing
- (private) isValidUsername(String username) - validates username format/length
- (private) isValidPassword(String password) - validates password strength/length
- (private) isValidEmail(String email) - validates email format



### UserManager.java
Manages the collection of all users, handles authentication, and persists user data to file.

**Fields**
- Map<String, User> users (stores all User objects, keyed by username)
- User currentUser (tracks the currently logged-in user)
- String USER_FILE (path to users.txt)

**Main Methods**
- UserManager() - constructor, loads users from file on startup
- signup(String username, String password, String email) - registers a new user, saves to file
- login(String username, String password) - authenticates a user and sets currentUser
- logout() - clears currentUser
- findUser(String username) - retrieves a User object by username
- userExists(String username) - checks if a username is already registered
- deleteUser(String username) - removes a user and updates file
- changeUserPassword(String username, String oldPassword, String newPassword) - updates user's password and saves to file
- changeUserEmail(String username, String attemptedPassword, String email) - updates user's email and saves to file
- getCurrentUser() - returns the currently logged-in user
- isLoggedIn() - checks if any user is currently logged in
- (private) loadUsersFromFile() - reads all users from data/users.txt
- (private) saveUserToFile() - writes all current user data to data/users.txt



### Movie.java
Represents a single movie and its aggregated ratings across different categories.

**Fields**
- int id
- String title
- String description
- String genre
- double actingSum, int actingCount
- double directionSum, int directionCount
- double musicSum, int musicCount
- double overallSum, int overallCount (for composite average)

**Main Methods**
- Movie(int id, String title, String genre, String description) - constructor
- getTitle(), getId(), getGenre(), getDescription() - getters
- setTitle(), setId(), setGenre(), setDescription() - setters with basic feedback
- addRating(String category, double score) - adds a rating to a specific category (e.g., "acting", "direction", "music")
- getAverage(String category) - calculates average score for a given category
- getCompositeAverage() - calculates overall average across all categories
- toString() - provides string representation of the movie



### MovieManager.java
Manages the collection of all movies, handles loading from file, searching, and adding ratings.

**Fields**
- String filePath (path to movies.txt)
- HashMap<Integer, Movie> byId (maps movie ID to Movie object)
- List<Movie> allMovies (list of all Movie objects)

**Main Methods**
- MovieManager(String filePath) - constructor, initializes collections
- load() - reads movie data from data/movies.txt
- save() - writes all current movie data to data/movies.txt
- listAll() - displays all movies with their IDs
- findById(int id) - retrieves a Movie object by ID
- searchByTitle(String query) - finds movies whose titles contain the query string
- addRating(int movieId, String category, double score) - adds a rating to a specific movie
- topByComposite(int count) - returns a list of top movies based on composite average
- globalAverageForCategory(String category) - calculates the average score for a category across all movies



### Review.java
Represents a single user-submitted review for a movie.

**Fields**
- int movieId
- String username
- String text
- long timestamp (creation time in milliseconds)

**Main Methods**
- Review(int movieId, String username, String text, long timestamp) - constructor, includes text length validation
- getMovieId(), getUsername(), getText(), getTimestamp() - getters
- toString() - provides string representation of the review



### ReviewManager.java
Manages the collection of all user reviews, handles persistence, and retrieval of reviews for specific movies.

**Fields**
- String filePath (path to reviews.txt)
- Map<Integer, List<Review>> reviewsByMovie (stores lists of reviews, keyed by movie ID)

**Main Methods**
- ReviewManager(String filePath) - constructor, initializes collection
- load() - reads review data from data/reviews.txt
- save() - writes all current review data to data/reviews.txt
- addReview(int movieId, String username, String text) - creates a new review and adds it to the collection, saves to file
- getReviewsForMovie(int movieId) - retrieves all reviews for a given movie ID
- getReviewCountForMovie(int movieId) - returns the number of reviews for a given movie



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

## Data Storage Format

**users.txt**
username|passwordHash|email
alice|salt:5f4dcc3b5aa765d61d8327deb882cf99|alice@email.com

**movies.txt**
id|title|genre|description|aSum|aCnt|dSum|dCnt|mSum|mCnt|oSum|oCnt
1|The Matrix|Sci-fi|Sci-fi classic|40|5|39|5|41|5|42|5

**reviews.txt**
movieId|username|timestamp|reviewText
1|alice|1730060000|Loved the visuals!
