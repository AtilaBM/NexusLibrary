package com.atila.View;

import java.sql.SQLException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.atila.Enums.Categories;
import com.atila.model.User;
import com.atila.services.LibraryService;

public class Menus {
    Scanner sc = new Scanner(System.in);
    LibraryService lService = new LibraryService();
    private Integer memberId;

    public void startMenu() {
        while (true) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Create Book");
            System.out.println("2. Create Member");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    createBookMenu();
                    break;
                case "2":
                    createMemberMenu();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    public void createMemberMenu() {
        System.out.println("\n==============================");
        System.out.println("       CREATE NEW MEMBER      ");
        System.out.println("==============================");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.println("\n==============================");
        System.out.println("       CREATE NEW USER      ");
        System.out.println("==============================");

        System.out.print("User: ");
        String user = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

        lService.createMember(name, email, phone, user, pw_hash);

    }

    public void createBookMenu() {
        System.out.println("\n==============================");
        System.out.println("       CREATE NEW BOOK        ");
        System.out.println("==============================");

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Publication Year(XXXX): ");
        Integer publicationYear = sc.nextInt();
        sc.nextLine();

        System.out.print("Author: ");
        String author = sc.nextLine();

        Categories category = categoriesMenu();

        lService.createBook(title, publicationYear, author, category);

    }

    private Categories categoriesMenu() {
        Categories[] categories = Categories.values();

        System.out.println("\nChoose a category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + " - " + categories[i]);
        }

        System.out.print("Option: ");
        int option = sc.nextInt();

        if (option >= 1 && option <= categories.length) {
            return categories[option - 1];
        }

        System.out.println("Invalid option, try again.");
        return categoriesMenu();
    }

    public void loginMenu() {
        System.out.println("\n==============================");
        System.out.println("            LOGIN        ");
        System.out.println("==============================");

        System.out.print("User: ");
        String user = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            memberId = lService.login(user, password);

        } catch (SQLException e) {
            System.out.println(e);
        }
        if (memberId == null) {
            System.out.println("Login failed: invalid user or password.");
            return;
        }
    }

    public void userInfo() {
        System.out.println("\n==============================");
        System.out.println("            INFORMATION      ");
        System.out.println("==============================");

        User user = lService.userInfo(memberId);

        if (user == null) {
            System.out.println("User null");
        }

        System.out.println(user.getName());
        System.out.println(user.getUser());
        System.out.println(user.getEmail());
        System.out.println(user.getPhone());

    }
}
