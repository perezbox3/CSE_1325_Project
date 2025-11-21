import java.io.*;
import java.util.*;

public class UserManager
{

    private Map<String, User> users;
    private static final String USER_FILE = "../data/users.txt";
    private User currentUser;

    public UserManager()
    {
        users = new HashMap<>();
        loadUsersFromFile();
    }

    public boolean signup(String username, String password, String email)
    {
        if(userExists(username))
        {
            return false;
        }
        try
        {
        User newUser = new User(username, password, email);
        users.put(username, newUser);
        saveUserToFile();
        return true;
        } 
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }

    public boolean login(String username, String password)
    {
        User user = findUser(username);
        if (user == null)
        {
            return false;
        }
        if(user.verifyPassword(password, user.getPasswordHash()))
        {
            currentUser = user;
            return true;
        }
        return false;
    }

    public boolean logout()
    {
        if(currentUser == null)
        {
            return false;
        }
        currentUser = null;
        return true;
    }

    public User findUser(String username)
    {
        return users.get(username);
    }

    public boolean userExists(String username)
    {
        return users.containsKey(username);
    }

    public boolean deleteUser(String username)
    {
        if(users.containsKey(username))
        {
            users.remove(username);
            saveUserToFile();
            return true;
        }
        return false;
    }

    private void loadUsersFromFile()
    {
        File file = new File(USER_FILE);
        if(!file.exists())
        {
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String [] sections = line.split("\\|");
                if (sections.length == 3)
                {
                    User user = new User(sections[0], sections[1], sections[2], true);
                    users.put(user.getUsername(), user);
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error loading users: "+ e.getMessage());
        }
    }

    private void saveUserToFile()
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE)))
        {
            for(User user : users.values())
            {
                String line = user.getUsername()+"|"+user.getPasswordHash()+"|"+user.getEmail();
                writer.write(line);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            System.err.println("Error saving users: "+ e.getMessage());
        }
    }

    public boolean changeUserPassword(String username, String oldPassword, String newPassword)
    {
        if(userExists(username))
        {
            User user = users.get(username);
            boolean success = user.changePassword(oldPassword, newPassword);
            if(success)
            {
                saveUserToFile();
            }
            return success;
        }
        return false;
    }

    public boolean changeUserEmail(String username, String attemptedPassword, String email)
    {
        if(userExists(username))
        {
            User user = users.get(username);
            boolean success = user.changeEmail(attemptedPassword, email);
            if (success)
            {
                saveUserToFile();
            }
            return success;
        }
        return false;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public boolean isLoggedIn()
    {
        return currentUser != null;
    }

}