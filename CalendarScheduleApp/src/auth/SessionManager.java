package Auth;

import java.io.*;

public class SessionManager {

    private static final String SESSION_FILE = "data/session.txt";

    public static void saveSession(int userId) throws IOException {
        FileWriter fw = new FileWriter(SESSION_FILE);
        fw.write(Integer.toString(userId));
        fw.close();
    }

    public static int getCurrentUserId() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(SESSION_FILE));
        String line = br.readLine();
        br.close();

        if (line == null || line.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(line);
    }

    public static void clearSession() throws IOException {
        new FileWriter(SESSION_FILE).close();
    }
}
