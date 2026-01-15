package com.atila.View;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.atila.Enums.Categories;
import com.atila.Enums.Role;
import com.atila.dto.LoanDetailsDTO;
import com.atila.model.User;
import com.atila.services.LibraryService;

public class Menus {
    Scanner sc = new Scanner(System.in);
    LibraryService lService = new LibraryService();

    private User user;
    private Integer memberId;
    private List<String> books;

    public Menus() {
    }

    public Menus(User user) {
        this.user = user;
    }

    public void startMenu() {
        while (true) {
            printHeader("LIBRARY SYSTEM");
            System.out.println("1. Login");
            System.out.println("2. Create an account");
            System.out.println("0. Exit");
            printSeparator();
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    loginMenu();
                    if (memberId != null) {
                        user = lService.userInfo(memberId);
                        redirectByRole();
                    }
                    break;
                case "2":
                    createMemberMenu();
                    break;
                case "0":
                    System.out.println("\n✓ Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("\n✗ Invalid option! Try again.\n");
            }
        }
    }

    private void redirectByRole() {
        if (user == null || user.getRole() == null) {
            System.out.println("\n✗ Error: Unable to identify user role.\n");
            return;
        }

        String role = user.getRole();

        if (role.equals("CUSTOMER")) {
            customerMenu();
        } else if (role.equals("ADMIN") || role.equals("EMPLOYEE")) {
            employeeMenu();
        } else {
            System.out.println("\n✗ Unknown role: " + role + "\n");
        }
    }

    private void customerMenu() {
        while (true) {
            printHeader("CUSTOMER MENU - Welcome " + user.getName());
            System.out.println("1. View all books");
            System.out.println("2. My information");
            System.out.println("3. Search books");
            System.out.println("4. My information");
            System.out.println("5. Books loaned");
            System.out.println("0. Logout");
            printSeparator();
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    booksMenu();
                    break;
                case "2":
                    userInfo();
                    break;
                case "3":
                    searchBooksMenu();
                    break;
                case "4":
                    userInfo();
                    break;
                case "5":
                    seeUserLoans();
                    break;
                case "0":
                    System.out.println("\n✓ Logging out...\n");
                    memberId = null;
                    user = null;
                    return;
                default:
                    System.out.println("\n✗ Invalid option! Try again.\n");
            }
        }
    }

    private void employeeMenu() {
        while (true) {
            printHeader("EMPLOYEE MENU - Welcome " + user.getName());
            System.out.println("1. Create new book");
            System.out.println("2. View all books");
            System.out.println("3. Create new member");
            System.out.println("4. My information");
            System.out.println("5. Show all loans");
            System.out.println("0. Logout");
            printSeparator();
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    createBookMenu();
                    break;
                case "2":
                    booksMenu();
                    break;

                case "3":
                    if (user.getRole().equals("ADMIN")) {
                        createEmployeeMenu();
                    } else {
                        System.out.println("\n✗ Employee can't create a new employee account\n");
                    }
                    break;

                case "4":
                    userInfo();
                    break;
                case "5":
                    seeAllLoans();
                    break;
                case "0":
                    System.out.println("\n✓ Logging out...\n");
                    memberId = null;
                    user = null;
                    return;
                default:
                    System.out.println("\n✗ Invalid option! Try again.\n");
            }
        }
    }

    public void createMemberMenu() {
        printHeader("CREATE NEW ACCOUNT");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

        lService.createMember(name, email, phone, username, pw_hash);
        System.out.println("\n✓ Account created successfully!\n");
    }

    public void createEmployeeMenu() {
        printHeader("CREATE NEW ACCOUNT");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

        lService.createEmployee(name, email, phone, username, pw_hash);
        System.out.println("\n✓ Account created successfully!\n");
    }

    public void createBookMenu() {
        printHeader("CREATE NEW BOOK");

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Publication Year (YYYY): ");
        Integer publicationYear = sc.nextInt();
        sc.nextLine(); // Limpa o buffer

        System.out.print("Author: ");
        String author = sc.nextLine();

        Categories category = categoriesMenu();

        lService.createBook(title, publicationYear, author, category);
        System.out.println("\n✓ Book created successfully!\n");
    }

    private Categories categoriesMenu() {
        Categories[] categories = Categories.values();

        System.out.println("\n--- Choose a category ---");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        printSeparator();
        System.out.print("Option: ");
        int option = sc.nextInt();
        sc.nextLine();

        if (option >= 1 && option <= categories.length) {
            return categories[option - 1];
        }

        System.out.println("\n✗ Invalid option, try again.\n");
        return categoriesMenu();
    }

    public void loginMenu() {
        printHeader("LOGIN");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            memberId = lService.login(username, password);

            if (memberId == null) {
                System.out.println("\n✗ Login failed: Invalid username or password.\n");
            } else {
                System.out.println("\n✓ Login successful!\n");
            }
        } catch (SQLException e) {
            System.out.println("\n✗ Database error: " + e.getMessage() + "\n");
        }
    }

    public void userInfo() {
        printHeader("USER INFORMATION");

        if (user == null) {
            user = lService.userInfo(memberId);
        }

        if (user == null) {
            System.out.println("✗ Error: User not found.\n");
            return;
        }

        System.out.println("Name:     " + user.getName());
        System.out.println("Username: " + user.getUser());
        System.out.println("Email:    " + user.getEmail());
        System.out.println("Phone:    " + user.getPhone());
        System.out.println("Role:     " + user.getRole());
        printSeparator();
        System.out.println();
    }

    public void booksMenu() {
        if (books == null) {
            books = lService.allBooks();
        }

        printHeader("AVAILABLE BOOKS");

        if (books == null || books.isEmpty()) {
            System.out.println("No books available at the moment.\n");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
        printSeparator();
        System.out.println();
    }

    // Métodos auxiliares para formatação
    private void printHeader(String title) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.printf("║  %-36s  ║%n", title);
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void printSeparator() {
        System.out.println("──────────────────────────────────────────");
    }

    public void searchBooksMenu() {
        printHeader("SEARCH BOOKS");
        System.out.print("Enter book title: ");
        String keyword = sc.nextLine();
        List<String> results = lService.searchBooks(keyword);

        if (results == null || results.isEmpty()) {
            System.out.println("\n✗ No books found with '" + keyword + "'.\n");
            return;
        }

        System.out.println("\n✓ Found " + results.size() + " book(s):\n");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        printSeparator();
        System.out.print("Do you want to loan a book (y/n): ");
        String op = sc.nextLine();

        if (op.equalsIgnoreCase("y")) {
            System.out.print("Enter the book number: ");
            Integer choice = sc.nextInt();
            sc.nextLine(); // Limpar buffer

            if (choice < 1 || choice > results.size()) {
                System.out.println("\n✗ Invalid book number.\n");
                return;
            }

            LocalDateTime date = LocalDateTime.now();
            String bookName = results.get(choice - 1);

            try {
                lService.loanBook(date, bookName, memberId);
                System.out.println("\n✓ Book loaned successfully!\n");
            } catch (Exception e) {
                System.out.println("\n✗ Error loaning book: " + e.getMessage() + "\n");
            }
        }

        System.out.println();
    }

    public void seeAllLoans() {
        printHeader("ALL LOANS");

        try {
            List<LoanDetailsDTO> results = lService.seeAllLoans();

            if (results == null || results.isEmpty()) {
                System.out.println("\n✗ No loans found with '.\n");
                return;
            }

            System.out.println("\n✓ Found " + results.size() + " loan(s):\n");
            for (LoanDetailsDTO loanDetailsDTO : results) {
                System.out.println(loanDetailsDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void seeUserLoans() {
        printHeader("ALL LOANS");

        try {
            List<LoanDetailsDTO> results = lService.seeUserLoans(memberId);

            if (results == null || results.isEmpty()) {
                System.out.println("\n✗ No loans found with '.\n");
                return;
            }

            System.out.println("\n✓ Found " + results.size() + " loan(s):\n");
            for (LoanDetailsDTO loanDetailsDTO : results) {
                System.out.println(loanDetailsDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}