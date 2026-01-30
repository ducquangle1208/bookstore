package app.controllers;

import app.dto.BookRequest;
import app.entities.Book;
import app.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    public Book addBook(@RequestBody BookRequest request) {
        return bookService.saveBook(request);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Book deleted";
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String title) {
        return bookService.search(title);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id,request));
    }

}
