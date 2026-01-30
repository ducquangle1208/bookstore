package app.services;

import app.dto.BookRequest;
import app.entities.Book;
import app.repositories.BookRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> search(String title) {
        return bookRepo.findByTitleContaining(title);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Optional<Book> getBookById(long id) {
        return  bookRepo.findById(id);
    }

    public Book saveBook(BookRequest request) {
        if (bookRepo.existsByTitle((request.getTitle()))) {
            throw new RuntimeException("Book already exists!");
        }

        if (request.getPrice() != null && request.getPrice().doubleValue() < 0) {
            throw new RuntimeException("Price cannot be negative!");
        }
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        return bookRepo.save(book);
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        return bookRepo.save(book);
    }

}
