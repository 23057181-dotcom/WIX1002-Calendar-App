import java.io.*;
import java.util.Scanner;

public class BackupManager {

    // Relative paths (exam requirement)
    private static final String DATA_DIR = "data/";
    private static final String BACKUP_DIR = "backup/";
    private static final String BACKUP_FILE = BACKUP_DIR + "backup.txt";

    private static final String EVENT_FILE = DATA_DIR + "event.csv";
    private static final String RECURRENT_FILE = DATA_DIR + "recurrent.csv";
    private static final String ADDITIONAL_FILE = DATA_DIR + "additional.csv";

    // ================= BACKUP =================
    public static void createBackup() {
        try {
            new File(BACKUP_DIR).mkdirs();

            BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE));

            writer.write("===EVENT===\n");
            copyFile(EVENT_FILE, writer);

            writer.write("\n===RECURRENT===\n");
            copyFile(RECURRENT_FILE, writer);

            writer.write("\n===ADDITIONAL===\n");
            copyFile(ADDITIONAL_FILE, writer);

            writer.close();
            System.out.println("Backup completed successfully.");

        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    private static void copyFile(String path, BufferedWriter writer) throws IOException {
        File file = new File(path);
        if (!file.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line + "\n");
        }
        reader.close();
    }

    // ================= RESTORE =================
    public static void restoreBackup() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("Backup file not found.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("1 = Append, 2 = Replace: ");
        int choice = sc.nextInt();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(backupFile));

            BufferedWriter eventW = openWriter(EVENT_FILE, choice);
            BufferedWriter recurrentW = openWriter(RECURRENT_FILE, choice);
            BufferedWriter additionalW = openWriter(ADDITIONAL_FILE, choice);

            BufferedWriter current = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("===EVENT===")) current = eventW;
                else if (line.equals("===RECURRENT===")) current = recurrentW;
                else if (line.equals("===ADDITIONAL===")) current = additionalW;
                else if (current != null && !line.isEmpty()) {
                    current.write(line + "\n");
                }
            }

            reader.close();
            eventW.close();
            recurrentW.close();
            additionalW.close();

            System.out.println("Restore completed.");

        } catch (IOException e) {
            System.out.println("Restore failed: " + e.getMessage());
        }
    }

    private static BufferedWriter openWriter(String path, int choice) throws IOException {
        new File(DATA_DIR).mkdirs();
        return new BufferedWriter(new FileWriter(path, choice == 1));
    }
}
