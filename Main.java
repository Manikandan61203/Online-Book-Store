import java.util.*;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class UserManager {
    private Map<String, User> users = new HashMap<>();

    public void registerUser(String username, String password) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }
        users.put(username, new User(username, password));
        System.out.println("User registered successfully.");
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

class Book {
    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, Price: $%.2f", title, author, price);
    }
}

class BookStore {
    private List<Book> books;

    public BookStore() {
        books = new ArrayList<>();
        books.add(new Book("Harry Potter and the Sorcerer’s Stone (1997)", "J.K. Rowling", 49.99));
        books.add(new Book("Harry Potter and the Chamber of Secrets (1998)", "J.K. Rowling", 52.99));
        books.add(new Book("Harry Potter and the Prisoner of Azkaban (1999)", "J.K. Rowling", 63.99));
        books.add(new Book("Harry Potter and the Goblet of Fire (2000)", "J.K. Rowling", 69.99));
        books.add(new Book("Harry Potter and the Order of the Phoenix (2003)", "J.K. Rowling", 68.99));
        books.add(new Book("Harry Potter and the Half-Blood Prince (2005)", "J.K. Rowling", 72.99));
        books.add(new Book("Harry Potter and the Deathly Hallows (2007)", "J.K. Rowling", 78.99));
        books.add(new Book("Harry Potter and the Cursed Child (2016)", "J.K. Rowling", 89.99));
        books.add(new Book("The Hunger Games(2008)", "Suzanne Collins", 56.99));
        books.add(new Book("Don Quixote(1605)", "Miguel de Cervantes", 63.99));
        books.add(new Book("The Lord of the Rings(1937)", "John Ronald Reuel Tolkien,", 66.99));
        books.add(new Book("The Cruel Prince(2018)", "Holly Black", 52.99));
        books.add(new Book("Good Omens(1990)", "Neil Gaiman", 58.99));
        books.add(new Book("This Is How You Lose the Time War(2019)", "Amal El-Mohtar", 63.50));
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("Available Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public List<Book> searchBooks(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
}

class ShoppingCart {
    private List<Book> cartItems = new ArrayList<>();

    public void addBook(Book book) {
        cartItems.add(book);
    }

    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("Your Cart:");
        for (Book book : cartItems) {
            System.out.println(book);
        }
    }

    public double calculateTotal() {
        return cartItems.stream().mapToDouble(Book::getPrice).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}

public class Main {
    private static Map<String, ShoppingCart> userCarts = new HashMap<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        BookStore bookStore = new BookStore();
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Online Book Store!");

        while (true) {
            // Show commands for register and login if no user is logged in
            if (currentUser == null) {
                System.out.println("\nCommands: [register, login, exit]");
                System.out.print("Enter command: ");
                command = scanner.nextLine();

                switch (command.toLowerCase()) {
                    case "register":
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        userManager.registerUser(username, password);
                        break;

                    case "login":
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        User user = userManager.login(loginUsername, loginPassword);
                        if (user != null) {
                            currentUser = user;
                            userCarts.putIfAbsent(currentUser.getUsername(), new ShoppingCart());
                            System.out.println("Login successful! Welcome, " + currentUser.getUsername());
                        } else {
                            System.out.println("Invalid credentials. Try again.");
                        }
                        break;

                    case "exit":
                        System.out.println("Thank you for visiting!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Unknown command. Please try again.");
                        continue;
                }
            }

            // If a user is logged in, show book-related commands
            if (currentUser != null) {
                boolean keepGoing = true;
                while (keepGoing) {
                    System.out.println("\nCommands: [view, search, cart, logout]");
                    System.out.print("Enter command: ");
                    command = scanner.nextLine();

                    switch (command.toLowerCase()) {
                        case "view":
                            bookStore.displayBooks();
                            break;

                        case "search":
                            System.out.print("Enter book title to search: ");
                            String title = scanner.nextLine();
                            List<Book> foundBooks = bookStore.searchBooks(title);
                            if (foundBooks.isEmpty()) {
                                System.out.println("No books found.");
                            } else {
                                System.out.println("Found Books:");
                                for (Book book : foundBooks) {
                                    System.out.println(book);
                                }
                            }
                            break;

                        case "cart":
                            ShoppingCart cart = userCarts.get(currentUser.getUsername());
                            while (true) {
                                System.out.println("Commands: [add, view, checkout, back]");
                                System.out.print("Enter command: ");
                                String cartCommand = scanner.nextLine();
                                switch (cartCommand.toLowerCase()) {
                                    case "add":
                                        System.out.print("Enter book title to add to cart: ");
                                        String cartTitle = scanner.nextLine();
                                        List<Book> booksToAdd = bookStore.searchBooks(cartTitle);
                                        if (!booksToAdd.isEmpty()) {
                                            cart.addBook(booksToAdd.get(0)); // Add the first matched book
                                            System.out.println("Added to cart: " + booksToAdd.get(0).getTitle());
                                        } else {
                                            System.out.println("No book found with that title.");
                                        }
                                        break;

                                    case "view":
                                        cart.viewCart();
                                        break;

                                    case "checkout":
                                        double total = cart.calculateTotal();
                                        System.out.println("Total Amount: $" + total);
                                        cart.clearCart();
                                        System.out.println("Checkout complete. Your cart is now empty.");
                                        break;

                                    case "back":
                                        break;

                                    default:
                                        System.out.println("Unknown command.");
                                }
                                if (cartCommand.equalsIgnoreCase("back")) break;
                            }
                            break;

                        case "logout":
                            System.out.println("Logging out...");
                            currentUser = null; // Set current user to null to log out
                            System.out.println("Logged out successfully.");
                            keepGoing = false; // End the inner loop after logout
                            break;

                        default:
                            System.out.println("Unknown command. Please try again.");
                    }
                }
            }
        }
    }
}

