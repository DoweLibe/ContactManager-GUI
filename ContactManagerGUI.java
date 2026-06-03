import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 Programming Assignment 7: Library Management System
 Compiler used: JDoodle
*/

interface Searchable  {
    boolean matches(String keyword);
}

// ---------------- ABSTRACT CLASS ----------------
abstract class LibraryItem implements Searchable {
    protected String title;
    protected String author;
    protected int yearPublished;
    protected String ISBN;

    @Override
    public boolean matches(String keyword) {
        keyword = keyword.toLowerCase();
        return title.toLowerCase().contains(keyword) ||
               author.toLowerCase().contains(keyword) ||
               ISBN.toLowerCase().contains(keyword) ||
               String.valueOf(yearPublished).contains(keyword);
    }

    public LibraryItem(String title, String author, String ISBN, int yearPublished) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public abstract void displayInfo();
}

// ---------------- BOOK CLASS ----------------
class Book extends LibraryItem {
    private String genre;
    private int pages;

    public Book(String title, String author, String ISBN, int yearPublished, String genre, int pages) {
        super(title, author, ISBN, yearPublished);
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public void displayInfo() {
        System.out.println("Book -> Title: " + title + ", Author: " + author
                + ", ISBN: " + ISBN + ", Year: " + yearPublished + ", Genre: " + genre + ", Pages: " + pages);
    }
}

// ---------------- MAGAZINE CLASS ----------------
class Magazine extends LibraryItem {
    private int issueNumber;
    private String frequency;

    public Magazine(String title, String author, String ISBN, int yearPublished, int issueNumber, String frequency) {
        super(title, author, ISBN, yearPublished);
        this.issueNumber = issueNumber;
        this.frequency = frequency;
    }

    @Override
    public void displayInfo() {
        System.out.println("Magazine -> Title: " + title + ", Author: " + author
                + ", ISBN: " + ISBN + ", Year: " + yearPublished + ", Issue Number: " + issueNumber
                + ", Frequency: " + frequency);
    }
}

// ---------------- LIBRARY CLASS ----------------
class Library {
    private List<LibraryItem> items = new ArrayList<>();
    private int[][] checkouts = new int[200][12];

    public void addItem(LibraryItem item) {
        items.add(item);
        System.out.println("Item added successfully.");
    }

    public void deleteItem(String title) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTitle().equalsIgnoreCase(title)) {
                items.remove(i);
                System.out.println("Item deleted successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    public LibraryItem searchByTitle(String title) {
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("Library is empty.");
        } else {
            for (LibraryItem item : items) {
                item.displayInfo();
            }
        }
    }

    public void sortItems(int option) {
        if (option == 1) {
            Collections.sort(items, Comparator.comparing(LibraryItem::getTitle));
        } else if (option == 2) {
            Collections.sort(items, Comparator.comparingInt(i -> i.yearPublished));
        }
        System.out.println("Items sorted.");
    }

    public void addCheckout(int index, int month) {
        if (index >= 0 && index < items.size() && month >= 0 && month < 12) {
            checkouts[index][month]++;
            System.out.println("Checkout recorded.");
        } else {
            System.out.println("Invalid item or month.");
        }
    }

    public void displayCheckoutTotals() {
        for (int i = 0; i < items.size(); i++) {
            int total = 0;
            for (int m = 0; m < 12; m++) {
                total += checkouts[i][m];
            }
            System.out.println(items.get(i).getTitle() + " -> Total Checkouts: " + total);
        }
    }
}

// ---------------- MAIN PROGRAM ----------------
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            System.out.println("\n Library Menu ");
            System.out.println("1. Add Book");
            System.out.println("2. Add Magazine");
            System.out.println("3. Search by Title");
            System.out.println("4. Delete by Title");
            System.out.println("5. Display All Items");
            System.out.println("6. Sort Items");
            System.out.println("7. Add Checkout");
            System.out.println("8. Display Checkout Totals");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter book title: ");
                    String bTitle = input.nextLine();
                    System.out.print("Enter book author: ");
                    String bAuthor = input.nextLine();
                    System.out.print("Enter ISBN: ");
                    String bISBN = input.nextLine();
                    System.out.print("Enter year published: ");
                    int bYear = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = input.nextLine();
                    System.out.print("Enter pages: ");
                    int pages = input.nextInt();
                    input.nextLine();

                    library.addItem(new Book(bTitle, bAuthor, bISBN, bYear, genre, pages));
                    break;

                case 2:
                    System.out.print("Enter magazine title: ");
                    String mTitle = input.nextLine();
                    System.out.print("Enter magazine author: ");
                    String mAuthor = input.nextLine();
                    System.out.print("Enter ISBN: ");
                    String mISBN = input.nextLine();
                    System.out.print("Enter year published: ");
                    int mYear = input.nextInt();
                    System.out.print("Enter issue number: ");
                    int issue = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter frequency: ");
                    String freq = input.nextLine();

                    library.addItem(new Magazine(mTitle, mAuthor, mISBN, mYear, issue, freq));
                    break;

                case 3:
                    System.out.print("Enter title to search: ");
                    String searchTitle = input.nextLine();
                    LibraryItem found = library.searchByTitle(searchTitle);

                    if (found != null) {
                        System.out.println("Item found:");
                        found.displayInfo();
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter title to delete: ");
                    String deleteTitle = input.nextLine();
                    library.deleteItem(deleteTitle);
                    break;

                case 5:
                    library.displayAllItems();
                    break;

                case 6:
                    System.out.println("Sort by: 1) Title 2) Year");
                    int sortOption = input.nextInt();
                    library.sortItems(sortOption);
                    break;

                case 7:
                    System.out.print("Enter item index: ");
                    int index = input.nextInt();
                    System.out.print("Enter month (0-11): ");
                    int month = input.nextInt();
                    library.addCheckout(index, month);
                    break;

                case 8:
                    library.displayCheckoutTotals();
                    break;

                case 9:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 9);

        input.close();
    }
}
