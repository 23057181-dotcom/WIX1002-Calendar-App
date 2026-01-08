package main;

import auth.AuthService;
import Auth.SessionManager;
import model.User;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.print("Choose: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();

            if (AuthService.signup(u, p)) {
                System.out.println("Signup successful!");
            } else {
                System.out.println("Username already exists!");
            }

        } else if (choice == 2) {
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();

            User user = AuthService.login(u, p);
            if (user != null) {
                SessionManager.saveSession(user.getUserId());
                System.out.println("Login successful. Welcome " + user.getUsername());
            } 
            else {
                System.out.println("Invalid credentials.");
            }
        }
    }
}
