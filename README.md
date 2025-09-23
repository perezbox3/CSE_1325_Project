# FriendFlix

## Project Overview
FriendFlix is a terminal-based Java application that simulates a small video rental store with built-in ratings and reviews. Users can create accounts, browse from a curated list of the top 100 movies, and rate them across multiple categories such as acting, direction, music, and overall enjoyment. Each rating is out of 10, and the program calculates averages to reveal whether the “top 100” movies truly deserve their spots.

The system includes user authentication, personalized reviews, and the ability to track liked or disliked movies. In the future, FriendFlix may expand into features like friend connections and recommendation sharing, making it a fun, console-based alternative to IMDb.

---

## Features
- **User Authentication**: Account creation, login, and logout system.
- **Movie Database**: A curated list of 100 movies with descriptions.
- **Multi-Category Ratings**: Rate movies by acting, direction, music, and overall enjoyment.
- **Review System**: Users can write reviews and see others’ feedback.
- **Statistics**: Display average scores and compare movies.

### Future Enhancements
- Friend system to share ratings.
- Recommendation engine.
- Export/Import ratings.

---

## Team Task Breakdown
**Person 1: User Management & Authentication**
- Create and manage user accounts.
- Implement login/logout functionality.
- Store user details securely in files.

**Person 2: Movie Database & Ratings**
- Build and maintain the movie list (top 100).
- Implement functions for rating movies across multiple categories.
- Calculate and display averages.

**Person 3: Reviews & Statistics**
- Enable users to write and read reviews.
- Manage likes/dislikes for movies.
- Generate statistics (average scores, comparisons).

---

## File Structure
friendflix/
│── src/
│ ├── Main.java # Main entry point (menu-driven system)
│ ├── User.java # User class (account management)
│ ├── UserManager.java # Handles signup/login/logout
│ ├── Movie.java # Movie class (movie details & ratings)
│ ├── MovieManager.java # Manages list of movies and ratings
│ ├── Review.java # Review class
│ ├── ReviewManager.java # Handles storing and displaying reviews
│── data/
│ ├── users.txt # Stores user info (username, password)
│ ├── movies.txt # Stores movie list & ratings
│ ├── reviews.txt # Stores user reviews
│── docs/
│ ├── README.md # Project documentation
│ ├── flowchart.png # Optional: program flowchart
│── Makefile (or build.gradle) # Compilation instructions

---

## File Content & Functionality
**User Management (User.java, UserManager.java)**
- `signup()`: Register new users.  
- `login()`: Authenticate users.  
- `logout()`: End user session.  

**Movie Management (Movie.java, MovieManager.java)**
- `listMovies()`: Display all movies.  
- `rateMovie()`: Add multi-category ratings.  
- `calculateAverage()`: Compute average scores.  

**Review Management (Review.java, ReviewManager.java)**
- `addReview()`: User writes a review.  
- `displayReviews()`: Show reviews for a movie.  
- `likeMovie()` / `dislikeMovie()`: Track likes/dislikes.  

---

## Task Assignments
| Person   | Tasks | Files |
|----------|-------|-------|
| Person 1 | User management (signup/login/logout) | User.java, UserManager.java |
| Person 2 | Movie listing & rating system | Movie.java, MovieManager.java |
| Person 3 | Reviews & statistics | Review.java, ReviewManager.java |

---

## Development Workflow
1. Person 1 builds user management system.  
2. Person 2 implements movie database & ratings.  
3. Person 3 sets up reviews & statistics.  
4. Integrate all modules into `Main.java` with a menu-driven interface.  

---

## How to Compile & Run
From the `src/` directory:
```bash
javac Main.java
java Main
