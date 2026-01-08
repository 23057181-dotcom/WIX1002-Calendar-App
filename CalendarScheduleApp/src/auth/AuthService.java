
    
package auth;

import model.User;
import java.io.*;
import java.util.*;

public class AuthService {

    private static final String USER_FILE = "data/users.csv";

    private static String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    public static boolean signup(String username, String password) throws IOException {
        List<User> users = loadUsers();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; // username already exists
            }
        }

        int newId = users.size() + 1;
        String hashedPassword = hashPassword(password);

        FileWriter fw = new FileWriter(USER_FILE, true);
        fw.write(newId + "," + username + "," + hashedPassword + "\n");
        fw.close();

        return true;
    }

    public static User login(String username, String password) throws IOException {
        String hashedPassword = hashPassword(password);
        List<User> users = loadUsers();

        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.getPasswordHash().equals(hashedPassword)) {
                return u;
            }
        }
        return null;
    }

    private static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
        String line;

        br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            users.add(new User(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2]
            ));
        }
        br.close();
        return users;
    }
}
    
