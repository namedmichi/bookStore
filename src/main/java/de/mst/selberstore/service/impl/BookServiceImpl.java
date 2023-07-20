package de.mst.selberstore.service.impl;

import de.mst.selberstore.domain.Book;
import de.mst.selberstore.repository.BookRepository;
import de.mst.selberstore.service.BookService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        log.debug("Request to update Book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> partialUpdate(Book book) {
        log.debug("Request to partially update Book : {}", book);

        return bookRepository
            .findById(book.getId())
            .map(existingBook -> {
                if (book.getName() != null) {
                    existingBook.setName(book.getName());
                }
                if (book.getTitle() != null) {
                    existingBook.setTitle(book.getTitle());
                }
                if (book.getDescription() != null) {
                    existingBook.setDescription(book.getDescription());
                }
                if (book.getSubTitle() != null) {
                    existingBook.setSubTitle(book.getSubTitle());
                }
                if (book.getPublisher() != null) {
                    existingBook.setPublisher(book.getPublisher());
                }
                if (book.getAuthor() != null) {
                    existingBook.setAuthor(book.getAuthor());
                }
                if (book.getListPrice() != null) {
                    existingBook.setListPrice(book.getListPrice());
                }
                if (book.getLanguage() != null) {
                    existingBook.setLanguage(book.getLanguage());
                }
                if (book.getRetailPrice() != null) {
                    existingBook.setRetailPrice(book.getRetailPrice());
                }
                if (book.getImage() != null) {
                    existingBook.setImage(book.getImage());
                }
                if (book.getPageCount() != null) {
                    existingBook.setPageCount(book.getPageCount());
                }
                if (book.getAverageRating() != null) {
                    existingBook.setAverageRating(book.getAverageRating());
                }
                if (book.getRatingCount() != null) {
                    existingBook.setRatingCount(book.getRatingCount());
                }
                if (book.getInfoLink() != null) {
                    existingBook.setInfoLink(book.getInfoLink());
                }
                if (book.getWebReaderLink() != null) {
                    existingBook.setWebReaderLink(book.getWebReaderLink());
                }
                if (book.getIsbn() != null) {
                    existingBook.setIsbn(book.getIsbn());
                }
                if (book.getLongDescription() != null) {
                    existingBook.setLongDescription(book.getLongDescription());
                }
                if (book.getTextSnippet() != null) {
                    existingBook.setTextSnippet(book.getTextSnippet());
                }

                return existingBook;
            })
            .map(bookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }
}
