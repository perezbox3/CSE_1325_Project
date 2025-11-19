import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class User 
{

    private String username;
    private String password;
    private String email;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 16;

    private String hashPassword(String password)
    {
        byte[] salt = new byte[SALT_LENGTH];        //Allocate Storage
        RANDOM.nextBytes(salt);                     //Fill salt with Randomness        
        byte[] hash = digest(password, salt);       //Produce final hash value for the values of password and salt (which is random)
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash); //Returns string value of hashed password for txt file storage
    }

    public boolean verifyPassword (String password, String storedSaltColonHash)
    {
        String [] parts = storedSaltColonHash.split(":");       //Will be salt:password (hashed) so if not matching pattern it is wrong
        if (parts.length != 2)
        {
            return false;
        }
        //Converts back from the Base64 encoded hash to a byte array
        byte[] salt = Base64.getDecoder().decode(parts[0]);     
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);
        byte[] candidateHash = digest(password, salt);
        return MessageDigest.isEqual(storedHash, candidateHash);

    }

    private byte [] digest(String password, byte[] salt)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");    //Creates object for hashing and specifies what type of hash to use
            digest.update(salt);                                            //Sends salt into hashing engine
            digest.update(password.getBytes(StandardCharsets.UTF_8));       //Converts the password to bytes and sends into hashing engine
            return digest.digest();                                         //It then hashes these together to create the full hash
        } catch (NoSuchAlgorithmException e) 
        {
            throw new IllegalArgumentException("SHA-256 Unavailable.");
        }
    }

    public String getPasswordHash()
    {
        return password;
    }

    public String getUsername() 
    {
        return username;
    }

    public String getEmail() 
    {
        return email;
    }

    private boolean isValidUsername(String username)
    {
        return username != null && username.length() >= 1 && username.length() <= 50 && username.matches("^[A-Za-z0-9]+$");
    }

    public void setUsername(String username) 
    {
        if(!isValidUsername(username))
        {
            throw new IllegalArgumentException("Username must be 1-50 characters and contain only letters and numbers.");
        }
        this.username = username;
    }

    private boolean isValidPassword(String password)
    {
        return password != null && password.length() >= 3 && password.length() <= 50;
    }

    public void setPassword(String password) 
    {
        if(!isValidPassword(password))
        {
            throw new IllegalArgumentException("Password must be 3-50 characters.");
        }
        this.password = hashPassword(password);
    }

    public boolean changePassword(String attemptedPassword, String newPassword)
    {
        if(!verifyPassword(attemptedPassword, this.password))
        {
            return false;
        }
        if(!isValidPassword(newPassword))
        {
            throw new IllegalArgumentException("Password must be 3-50 characters.");
        }
        password = hashPassword(newPassword);
        return true;

    }

    public boolean changeEmail(String attemptedPassword, String email)
    {
        if (!verifyPassword(attemptedPassword, this.password))
        {
            return false;
        }
        try 
        {
            setEmail(email);
            return true;
        } 
        catch (IllegalArgumentException e) 
        {
            return false;
        }
    }

    private boolean  isValidEmail(String email)
    {
  
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        
    }

    public void setEmail(String email) 
    {
        if(!isValidEmail(email))
        {
            throw new IllegalArgumentException("Invalid Email.");
        }    
        this.email = email;
    }

    public User(String username, String password, String email)
    {
        setUsername(username);
        setPassword(password);
        setEmail(email);
    }

    public User(String username, String password, String email, boolean isHashed)
    {
        setUsername(username);
        setEmail(email);
        if(isHashed)
        {
            this.password = password;
        } 
        else 
        {
            setPassword(password);
        }
    }
}
