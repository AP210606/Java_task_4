package Task_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter; // More convenient for writing lines
import java.util.Scanner;

public class NotesApp {

    private static final String NOTES_FILE = "notes.txt"; // Default file name
    private Scanner scanner;

    public NotesApp() {
        scanner = new Scanner(System.in);
    }

    // --- Core Operations ---

    // Method to add a new note
    public void addNote() {
        System.out.println("\n--- Add New Note ---");
        scanner.nextLine(); // Consume newline left-over

        System.out.print("Enter your note: ");
        String note = scanner.nextLine();

        // Use try-with-resources to ensure FileWriter is closed automatically
        try (FileWriter fw = new FileWriter(NOTES_FILE, true); // true for append mode
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(note); // Write the note to the file
            System.out.println("Note added successfully.");
        } catch (IOException e) {
            System.err.println("Error writing note to file: " + e.getMessage());
        }
    }

    // Method to view all notes
    public void viewNotes() {
        System.out.println("\n--- All Notes ---");
        // Use try-with-resources to ensure BufferedReader is closed automatically
        try (FileReader fr = new FileReader(NOTES_FILE);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            boolean hasNotes = false;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                hasNotes = true;
            }
            if (!hasNotes) {
                System.out.println("No notes found. Add some notes!");
            }
        } catch (IOException e) {
            // This will likely occur if the file doesn't exist yet, which is fine for reading
            System.out.println("No notes file found. Start by adding a note.");
            // You might want to log the full error for debugging in a real application
            // System.err.println("Error reading notes from file: " + e.getMessage());
        }
    }

    // --- Main Application Loop and Menu ---
    public void run() {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addNote();
                        break;
                    case 2:
                        viewNotes();
                        break;
                    case 3:
                        System.out.println("Exiting Notes App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                choice = 0; // Set choice to an invalid value to continue the loop
            }
        } while (choice != 3);

        scanner.close(); // Close scanner when exiting
    }

    private void displayMenu() {
        System.out.println("\n--- Notes App Menu ---");
        System.out.println("1. Add New Note");
        System.out.println("2. View All Notes");
        System.out.println("3. Exit");
        System.out.println("----------------------");
    }

    // Main method to start the application
    public static void main(String[] args) {
        NotesApp app = new NotesApp();
        app.run();
    }
}