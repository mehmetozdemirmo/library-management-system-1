import java.util.ArrayList;

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private int nextId = 1;

    public void addBook(String title, String author) {
        books.add(new Book(nextId++, title, author));
        System.out.println("Book added.");
    }

    public void removeBook(int id) {
        boolean removed = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                if (books.get(i).isBorrowed()) {
                    System.out.println("Cannot remove a borrowed book.");
                    return;
                }
                books.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            // Reassign IDs
            for (int i = 0; i < books.size(); i++) {
                books.get(i).setId(i + 1);
            }
            // Update nextId
            nextId = books.size() + 1;
            System.out.println("Book removed and IDs rearranged.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public String getBookList() {
        if (books.isEmpty()) {
            return "No books in the library.";
        }
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void borrowBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                if (book.isBorrowed()) {
                    System.out.println("Book is already borrowed.");
                    return;
                }
                book.borrow();
                System.out.println("Book borrowed.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void returnBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                if (!book.isBorrowed()) {
                    System.out.println("This book was not borrowed.");
                    return;
                }
                book.giveBack();
                System.out.println("Book returned.");
                return;
            }
        }
        System.out.println("Book not found.");
    }
}
