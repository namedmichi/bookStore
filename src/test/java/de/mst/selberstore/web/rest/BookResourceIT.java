package de.mst.selberstore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.mst.selberstore.IntegrationTest;
import de.mst.selberstore.domain.Book;
import de.mst.selberstore.repository.BookRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLISHER = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_LIST_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_RETAIL_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_RETAIL_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_COUNT = 1;
    private static final Integer UPDATED_PAGE_COUNT = 2;
    private static final Integer SMALLER_PAGE_COUNT = 1 - 1;

    private static final Float DEFAULT_AVERAGE_RATING = 1F;
    private static final Float UPDATED_AVERAGE_RATING = 2F;
    private static final Float SMALLER_AVERAGE_RATING = 1F - 1F;

    private static final Integer DEFAULT_RATING_COUNT = 1;
    private static final Integer UPDATED_RATING_COUNT = 2;
    private static final Integer SMALLER_RATING_COUNT = 1 - 1;

    private static final String DEFAULT_INFO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_INFO_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_READER_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEB_READER_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_SNIPPET = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_SNIPPET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .name(DEFAULT_NAME)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .subTitle(DEFAULT_SUB_TITLE)
            .publisher(DEFAULT_PUBLISHER)
            .author(DEFAULT_AUTHOR)
            .listPrice(DEFAULT_LIST_PRICE)
            .language(DEFAULT_LANGUAGE)
            .retailPrice(DEFAULT_RETAIL_PRICE)
            .image(DEFAULT_IMAGE)
            .pageCount(DEFAULT_PAGE_COUNT)
            .averageRating(DEFAULT_AVERAGE_RATING)
            .ratingCount(DEFAULT_RATING_COUNT)
            .infoLink(DEFAULT_INFO_LINK)
            .webReaderLink(DEFAULT_WEB_READER_LINK)
            .isbn(DEFAULT_ISBN)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .textSnippet(DEFAULT_TEXT_SNIPPET);
        return book;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .subTitle(UPDATED_SUB_TITLE)
            .publisher(UPDATED_PUBLISHER)
            .author(UPDATED_AUTHOR)
            .listPrice(UPDATED_LIST_PRICE)
            .language(UPDATED_LANGUAGE)
            .retailPrice(UPDATED_RETAIL_PRICE)
            .image(UPDATED_IMAGE)
            .pageCount(UPDATED_PAGE_COUNT)
            .averageRating(UPDATED_AVERAGE_RATING)
            .ratingCount(UPDATED_RATING_COUNT)
            .infoLink(UPDATED_INFO_LINK)
            .webReaderLink(UPDATED_WEB_READER_LINK)
            .isbn(UPDATED_ISBN)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .textSnippet(UPDATED_TEXT_SNIPPET);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();
        // Create the Book
        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBook.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testBook.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getRetailPrice()).isEqualTo(DEFAULT_RETAIL_PRICE);
        assertThat(testBook.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBook.getPageCount()).isEqualTo(DEFAULT_PAGE_COUNT);
        assertThat(testBook.getAverageRating()).isEqualTo(DEFAULT_AVERAGE_RATING);
        assertThat(testBook.getRatingCount()).isEqualTo(DEFAULT_RATING_COUNT);
        assertThat(testBook.getInfoLink()).isEqualTo(DEFAULT_INFO_LINK);
        assertThat(testBook.getWebReaderLink()).isEqualTo(DEFAULT_WEB_READER_LINK);
        assertThat(testBook.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testBook.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testBook.getTextSnippet()).isEqualTo(DEFAULT_TEXT_SNIPPET);
    }

    @Test
    @Transactional
    void createBookWithExistingId() throws Exception {
        // Create the Book with an existing ID
        book.setId(1L);

        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setName(null);

        // Create the Book, which fails.

        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].retailPrice").value(hasItem(DEFAULT_RETAIL_PRICE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].pageCount").value(hasItem(DEFAULT_PAGE_COUNT)))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingCount").value(hasItem(DEFAULT_RATING_COUNT)))
            .andExpect(jsonPath("$.[*].infoLink").value(hasItem(DEFAULT_INFO_LINK)))
            .andExpect(jsonPath("$.[*].webReaderLink").value(hasItem(DEFAULT_WEB_READER_LINK)))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].textSnippet").value(hasItem(DEFAULT_TEXT_SNIPPET.toString())));
    }

    @Test
    @Transactional
    void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc
            .perform(get(ENTITY_API_URL_ID, book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.retailPrice").value(DEFAULT_RETAIL_PRICE))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.pageCount").value(DEFAULT_PAGE_COUNT))
            .andExpect(jsonPath("$.averageRating").value(DEFAULT_AVERAGE_RATING.doubleValue()))
            .andExpect(jsonPath("$.ratingCount").value(DEFAULT_RATING_COUNT))
            .andExpect(jsonPath("$.infoLink").value(DEFAULT_INFO_LINK))
            .andExpect(jsonPath("$.webReaderLink").value(DEFAULT_WEB_READER_LINK))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.textSnippet").value(DEFAULT_TEXT_SNIPPET.toString()));
    }

    @Test
    @Transactional
    void getBooksByIdFiltering() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        Long id = book.getId();

        defaultBookShouldBeFound("id.equals=" + id);
        defaultBookShouldNotBeFound("id.notEquals=" + id);

        defaultBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name equals to DEFAULT_NAME
        defaultBookShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bookList where name equals to UPDATED_NAME
        defaultBookShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBookShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bookList where name equals to UPDATED_NAME
        defaultBookShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name is not null
        defaultBookShouldBeFound("name.specified=true");

        // Get all the bookList where name is null
        defaultBookShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByNameContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name contains DEFAULT_NAME
        defaultBookShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bookList where name contains UPDATED_NAME
        defaultBookShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where name does not contain DEFAULT_NAME
        defaultBookShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bookList where name does not contain UPDATED_NAME
        defaultBookShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title equals to DEFAULT_TITLE
        defaultBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title is not null
        defaultBookShouldBeFound("title.specified=true");

        // Get all the bookList where title is null
        defaultBookShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByTitleContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title contains DEFAULT_TITLE
        defaultBookShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bookList where title contains UPDATED_TITLE
        defaultBookShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title does not contain DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bookList where title does not contain UPDATED_TITLE
        defaultBookShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksBySubTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where subTitle equals to DEFAULT_SUB_TITLE
        defaultBookShouldBeFound("subTitle.equals=" + DEFAULT_SUB_TITLE);

        // Get all the bookList where subTitle equals to UPDATED_SUB_TITLE
        defaultBookShouldNotBeFound("subTitle.equals=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksBySubTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where subTitle in DEFAULT_SUB_TITLE or UPDATED_SUB_TITLE
        defaultBookShouldBeFound("subTitle.in=" + DEFAULT_SUB_TITLE + "," + UPDATED_SUB_TITLE);

        // Get all the bookList where subTitle equals to UPDATED_SUB_TITLE
        defaultBookShouldNotBeFound("subTitle.in=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksBySubTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where subTitle is not null
        defaultBookShouldBeFound("subTitle.specified=true");

        // Get all the bookList where subTitle is null
        defaultBookShouldNotBeFound("subTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksBySubTitleContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where subTitle contains DEFAULT_SUB_TITLE
        defaultBookShouldBeFound("subTitle.contains=" + DEFAULT_SUB_TITLE);

        // Get all the bookList where subTitle contains UPDATED_SUB_TITLE
        defaultBookShouldNotBeFound("subTitle.contains=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksBySubTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where subTitle does not contain DEFAULT_SUB_TITLE
        defaultBookShouldNotBeFound("subTitle.doesNotContain=" + DEFAULT_SUB_TITLE);

        // Get all the bookList where subTitle does not contain UPDATED_SUB_TITLE
        defaultBookShouldBeFound("subTitle.doesNotContain=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBooksByPublisherIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher equals to DEFAULT_PUBLISHER
        defaultBookShouldBeFound("publisher.equals=" + DEFAULT_PUBLISHER);

        // Get all the bookList where publisher equals to UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.equals=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    void getAllBooksByPublisherIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher in DEFAULT_PUBLISHER or UPDATED_PUBLISHER
        defaultBookShouldBeFound("publisher.in=" + DEFAULT_PUBLISHER + "," + UPDATED_PUBLISHER);

        // Get all the bookList where publisher equals to UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.in=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    void getAllBooksByPublisherIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher is not null
        defaultBookShouldBeFound("publisher.specified=true");

        // Get all the bookList where publisher is null
        defaultBookShouldNotBeFound("publisher.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByPublisherContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher contains DEFAULT_PUBLISHER
        defaultBookShouldBeFound("publisher.contains=" + DEFAULT_PUBLISHER);

        // Get all the bookList where publisher contains UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.contains=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    void getAllBooksByPublisherNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher does not contain DEFAULT_PUBLISHER
        defaultBookShouldNotBeFound("publisher.doesNotContain=" + DEFAULT_PUBLISHER);

        // Get all the bookList where publisher does not contain UPDATED_PUBLISHER
        defaultBookShouldBeFound("publisher.doesNotContain=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author equals to DEFAULT_AUTHOR
        defaultBookShouldBeFound("author.equals=" + DEFAULT_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.equals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBooksByAuthorIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author in DEFAULT_AUTHOR or UPDATED_AUTHOR
        defaultBookShouldBeFound("author.in=" + DEFAULT_AUTHOR + "," + UPDATED_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.in=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBooksByAuthorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author is not null
        defaultBookShouldBeFound("author.specified=true");

        // Get all the bookList where author is null
        defaultBookShouldNotBeFound("author.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByAuthorContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author contains DEFAULT_AUTHOR
        defaultBookShouldBeFound("author.contains=" + DEFAULT_AUTHOR);

        // Get all the bookList where author contains UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.contains=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBooksByAuthorNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author does not contain DEFAULT_AUTHOR
        defaultBookShouldNotBeFound("author.doesNotContain=" + DEFAULT_AUTHOR);

        // Get all the bookList where author does not contain UPDATED_AUTHOR
        defaultBookShouldBeFound("author.doesNotContain=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    void getAllBooksByListPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where listPrice equals to DEFAULT_LIST_PRICE
        defaultBookShouldBeFound("listPrice.equals=" + DEFAULT_LIST_PRICE);

        // Get all the bookList where listPrice equals to UPDATED_LIST_PRICE
        defaultBookShouldNotBeFound("listPrice.equals=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByListPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where listPrice in DEFAULT_LIST_PRICE or UPDATED_LIST_PRICE
        defaultBookShouldBeFound("listPrice.in=" + DEFAULT_LIST_PRICE + "," + UPDATED_LIST_PRICE);

        // Get all the bookList where listPrice equals to UPDATED_LIST_PRICE
        defaultBookShouldNotBeFound("listPrice.in=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByListPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where listPrice is not null
        defaultBookShouldBeFound("listPrice.specified=true");

        // Get all the bookList where listPrice is null
        defaultBookShouldNotBeFound("listPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByListPriceContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where listPrice contains DEFAULT_LIST_PRICE
        defaultBookShouldBeFound("listPrice.contains=" + DEFAULT_LIST_PRICE);

        // Get all the bookList where listPrice contains UPDATED_LIST_PRICE
        defaultBookShouldNotBeFound("listPrice.contains=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByListPriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where listPrice does not contain DEFAULT_LIST_PRICE
        defaultBookShouldNotBeFound("listPrice.doesNotContain=" + DEFAULT_LIST_PRICE);

        // Get all the bookList where listPrice does not contain UPDATED_LIST_PRICE
        defaultBookShouldBeFound("listPrice.doesNotContain=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language equals to DEFAULT_LANGUAGE
        defaultBookShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllBooksByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultBookShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllBooksByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language is not null
        defaultBookShouldBeFound("language.specified=true");

        // Get all the bookList where language is null
        defaultBookShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByLanguageContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language contains DEFAULT_LANGUAGE
        defaultBookShouldBeFound("language.contains=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language contains UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllBooksByLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language does not contain DEFAULT_LANGUAGE
        defaultBookShouldNotBeFound("language.doesNotContain=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language does not contain UPDATED_LANGUAGE
        defaultBookShouldBeFound("language.doesNotContain=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllBooksByRetailPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where retailPrice equals to DEFAULT_RETAIL_PRICE
        defaultBookShouldBeFound("retailPrice.equals=" + DEFAULT_RETAIL_PRICE);

        // Get all the bookList where retailPrice equals to UPDATED_RETAIL_PRICE
        defaultBookShouldNotBeFound("retailPrice.equals=" + UPDATED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByRetailPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where retailPrice in DEFAULT_RETAIL_PRICE or UPDATED_RETAIL_PRICE
        defaultBookShouldBeFound("retailPrice.in=" + DEFAULT_RETAIL_PRICE + "," + UPDATED_RETAIL_PRICE);

        // Get all the bookList where retailPrice equals to UPDATED_RETAIL_PRICE
        defaultBookShouldNotBeFound("retailPrice.in=" + UPDATED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByRetailPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where retailPrice is not null
        defaultBookShouldBeFound("retailPrice.specified=true");

        // Get all the bookList where retailPrice is null
        defaultBookShouldNotBeFound("retailPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByRetailPriceContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where retailPrice contains DEFAULT_RETAIL_PRICE
        defaultBookShouldBeFound("retailPrice.contains=" + DEFAULT_RETAIL_PRICE);

        // Get all the bookList where retailPrice contains UPDATED_RETAIL_PRICE
        defaultBookShouldNotBeFound("retailPrice.contains=" + UPDATED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByRetailPriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where retailPrice does not contain DEFAULT_RETAIL_PRICE
        defaultBookShouldNotBeFound("retailPrice.doesNotContain=" + DEFAULT_RETAIL_PRICE);

        // Get all the bookList where retailPrice does not contain UPDATED_RETAIL_PRICE
        defaultBookShouldBeFound("retailPrice.doesNotContain=" + UPDATED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    void getAllBooksByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where image equals to DEFAULT_IMAGE
        defaultBookShouldBeFound("image.equals=" + DEFAULT_IMAGE);

        // Get all the bookList where image equals to UPDATED_IMAGE
        defaultBookShouldNotBeFound("image.equals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllBooksByImageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where image in DEFAULT_IMAGE or UPDATED_IMAGE
        defaultBookShouldBeFound("image.in=" + DEFAULT_IMAGE + "," + UPDATED_IMAGE);

        // Get all the bookList where image equals to UPDATED_IMAGE
        defaultBookShouldNotBeFound("image.in=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllBooksByImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where image is not null
        defaultBookShouldBeFound("image.specified=true");

        // Get all the bookList where image is null
        defaultBookShouldNotBeFound("image.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByImageContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where image contains DEFAULT_IMAGE
        defaultBookShouldBeFound("image.contains=" + DEFAULT_IMAGE);

        // Get all the bookList where image contains UPDATED_IMAGE
        defaultBookShouldNotBeFound("image.contains=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllBooksByImageNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where image does not contain DEFAULT_IMAGE
        defaultBookShouldNotBeFound("image.doesNotContain=" + DEFAULT_IMAGE);

        // Get all the bookList where image does not contain UPDATED_IMAGE
        defaultBookShouldBeFound("image.doesNotContain=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount equals to DEFAULT_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.equals=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount equals to UPDATED_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.equals=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount in DEFAULT_PAGE_COUNT or UPDATED_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.in=" + DEFAULT_PAGE_COUNT + "," + UPDATED_PAGE_COUNT);

        // Get all the bookList where pageCount equals to UPDATED_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.in=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is not null
        defaultBookShouldBeFound("pageCount.specified=true");

        // Get all the bookList where pageCount is null
        defaultBookShouldNotBeFound("pageCount.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is greater than or equal to DEFAULT_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.greaterThanOrEqual=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is greater than or equal to UPDATED_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.greaterThanOrEqual=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is less than or equal to DEFAULT_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.lessThanOrEqual=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is less than or equal to SMALLER_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.lessThanOrEqual=" + SMALLER_PAGE_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is less than DEFAULT_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.lessThan=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is less than UPDATED_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.lessThan=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByPageCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is greater than DEFAULT_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.greaterThan=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is greater than SMALLER_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.greaterThan=" + SMALLER_PAGE_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating equals to DEFAULT_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.equals=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating equals to UPDATED_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.equals=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating in DEFAULT_AVERAGE_RATING or UPDATED_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.in=" + DEFAULT_AVERAGE_RATING + "," + UPDATED_AVERAGE_RATING);

        // Get all the bookList where averageRating equals to UPDATED_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.in=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is not null
        defaultBookShouldBeFound("averageRating.specified=true");

        // Get all the bookList where averageRating is null
        defaultBookShouldNotBeFound("averageRating.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is greater than or equal to DEFAULT_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.greaterThanOrEqual=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is greater than or equal to UPDATED_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.greaterThanOrEqual=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is less than or equal to DEFAULT_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.lessThanOrEqual=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is less than or equal to SMALLER_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.lessThanOrEqual=" + SMALLER_AVERAGE_RATING);
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is less than DEFAULT_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.lessThan=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is less than UPDATED_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.lessThan=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    void getAllBooksByAverageRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is greater than DEFAULT_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.greaterThan=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is greater than SMALLER_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.greaterThan=" + SMALLER_AVERAGE_RATING);
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount equals to DEFAULT_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.equals=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount equals to UPDATED_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.equals=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount in DEFAULT_RATING_COUNT or UPDATED_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.in=" + DEFAULT_RATING_COUNT + "," + UPDATED_RATING_COUNT);

        // Get all the bookList where ratingCount equals to UPDATED_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.in=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is not null
        defaultBookShouldBeFound("ratingCount.specified=true");

        // Get all the bookList where ratingCount is null
        defaultBookShouldNotBeFound("ratingCount.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is greater than or equal to DEFAULT_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.greaterThanOrEqual=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is greater than or equal to UPDATED_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.greaterThanOrEqual=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is less than or equal to DEFAULT_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.lessThanOrEqual=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is less than or equal to SMALLER_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.lessThanOrEqual=" + SMALLER_RATING_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is less than DEFAULT_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.lessThan=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is less than UPDATED_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.lessThan=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByRatingCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is greater than DEFAULT_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.greaterThan=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is greater than SMALLER_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.greaterThan=" + SMALLER_RATING_COUNT);
    }

    @Test
    @Transactional
    void getAllBooksByInfoLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where infoLink equals to DEFAULT_INFO_LINK
        defaultBookShouldBeFound("infoLink.equals=" + DEFAULT_INFO_LINK);

        // Get all the bookList where infoLink equals to UPDATED_INFO_LINK
        defaultBookShouldNotBeFound("infoLink.equals=" + UPDATED_INFO_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByInfoLinkIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where infoLink in DEFAULT_INFO_LINK or UPDATED_INFO_LINK
        defaultBookShouldBeFound("infoLink.in=" + DEFAULT_INFO_LINK + "," + UPDATED_INFO_LINK);

        // Get all the bookList where infoLink equals to UPDATED_INFO_LINK
        defaultBookShouldNotBeFound("infoLink.in=" + UPDATED_INFO_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByInfoLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where infoLink is not null
        defaultBookShouldBeFound("infoLink.specified=true");

        // Get all the bookList where infoLink is null
        defaultBookShouldNotBeFound("infoLink.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByInfoLinkContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where infoLink contains DEFAULT_INFO_LINK
        defaultBookShouldBeFound("infoLink.contains=" + DEFAULT_INFO_LINK);

        // Get all the bookList where infoLink contains UPDATED_INFO_LINK
        defaultBookShouldNotBeFound("infoLink.contains=" + UPDATED_INFO_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByInfoLinkNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where infoLink does not contain DEFAULT_INFO_LINK
        defaultBookShouldNotBeFound("infoLink.doesNotContain=" + DEFAULT_INFO_LINK);

        // Get all the bookList where infoLink does not contain UPDATED_INFO_LINK
        defaultBookShouldBeFound("infoLink.doesNotContain=" + UPDATED_INFO_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByWebReaderLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where webReaderLink equals to DEFAULT_WEB_READER_LINK
        defaultBookShouldBeFound("webReaderLink.equals=" + DEFAULT_WEB_READER_LINK);

        // Get all the bookList where webReaderLink equals to UPDATED_WEB_READER_LINK
        defaultBookShouldNotBeFound("webReaderLink.equals=" + UPDATED_WEB_READER_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByWebReaderLinkIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where webReaderLink in DEFAULT_WEB_READER_LINK or UPDATED_WEB_READER_LINK
        defaultBookShouldBeFound("webReaderLink.in=" + DEFAULT_WEB_READER_LINK + "," + UPDATED_WEB_READER_LINK);

        // Get all the bookList where webReaderLink equals to UPDATED_WEB_READER_LINK
        defaultBookShouldNotBeFound("webReaderLink.in=" + UPDATED_WEB_READER_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByWebReaderLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where webReaderLink is not null
        defaultBookShouldBeFound("webReaderLink.specified=true");

        // Get all the bookList where webReaderLink is null
        defaultBookShouldNotBeFound("webReaderLink.specified=false");
    }

    @Test
    @Transactional
    void getAllBooksByWebReaderLinkContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where webReaderLink contains DEFAULT_WEB_READER_LINK
        defaultBookShouldBeFound("webReaderLink.contains=" + DEFAULT_WEB_READER_LINK);

        // Get all the bookList where webReaderLink contains UPDATED_WEB_READER_LINK
        defaultBookShouldNotBeFound("webReaderLink.contains=" + UPDATED_WEB_READER_LINK);
    }

    @Test
    @Transactional
    void getAllBooksByWebReaderLinkNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where webReaderLink does not contain DEFAULT_WEB_READER_LINK
        defaultBookShouldNotBeFound("webReaderLink.doesNotContain=" + DEFAULT_WEB_READER_LINK);

        // Get all the bookList where webReaderLink does not contain UPDATED_WEB_READER_LINK
        defaultBookShouldBeFound("webReaderLink.doesNotContain=" + UPDATED_WEB_READER_LINK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].retailPrice").value(hasItem(DEFAULT_RETAIL_PRICE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].pageCount").value(hasItem(DEFAULT_PAGE_COUNT)))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingCount").value(hasItem(DEFAULT_RATING_COUNT)))
            .andExpect(jsonPath("$.[*].infoLink").value(hasItem(DEFAULT_INFO_LINK)))
            .andExpect(jsonPath("$.[*].webReaderLink").value(hasItem(DEFAULT_WEB_READER_LINK)))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].textSnippet").value(hasItem(DEFAULT_TEXT_SNIPPET.toString())));

        // Check, that the count call also returns 1
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .subTitle(UPDATED_SUB_TITLE)
            .publisher(UPDATED_PUBLISHER)
            .author(UPDATED_AUTHOR)
            .listPrice(UPDATED_LIST_PRICE)
            .language(UPDATED_LANGUAGE)
            .retailPrice(UPDATED_RETAIL_PRICE)
            .image(UPDATED_IMAGE)
            .pageCount(UPDATED_PAGE_COUNT)
            .averageRating(UPDATED_AVERAGE_RATING)
            .ratingCount(UPDATED_RATING_COUNT)
            .infoLink(UPDATED_INFO_LINK)
            .webReaderLink(UPDATED_WEB_READER_LINK)
            .isbn(UPDATED_ISBN)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .textSnippet(UPDATED_TEXT_SNIPPET);

        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBook.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getRetailPrice()).isEqualTo(UPDATED_RETAIL_PRICE);
        assertThat(testBook.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBook.getPageCount()).isEqualTo(UPDATED_PAGE_COUNT);
        assertThat(testBook.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
        assertThat(testBook.getRatingCount()).isEqualTo(UPDATED_RATING_COUNT);
        assertThat(testBook.getInfoLink()).isEqualTo(UPDATED_INFO_LINK);
        assertThat(testBook.getWebReaderLink()).isEqualTo(UPDATED_WEB_READER_LINK);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testBook.getTextSnippet()).isEqualTo(UPDATED_TEXT_SNIPPET);
    }

    @Test
    @Transactional
    void putNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, book.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(book))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(book))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookWithPatch() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book using partial update
        Book partialUpdatedBook = new Book();
        partialUpdatedBook.setId(book.getId());

        partialUpdatedBook
            .title(UPDATED_TITLE)
            .publisher(UPDATED_PUBLISHER)
            .retailPrice(UPDATED_RETAIL_PRICE)
            .averageRating(UPDATED_AVERAGE_RATING)
            .infoLink(UPDATED_INFO_LINK)
            .isbn(UPDATED_ISBN)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .textSnippet(UPDATED_TEXT_SNIPPET);

        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBook.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getRetailPrice()).isEqualTo(UPDATED_RETAIL_PRICE);
        assertThat(testBook.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBook.getPageCount()).isEqualTo(DEFAULT_PAGE_COUNT);
        assertThat(testBook.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
        assertThat(testBook.getRatingCount()).isEqualTo(DEFAULT_RATING_COUNT);
        assertThat(testBook.getInfoLink()).isEqualTo(UPDATED_INFO_LINK);
        assertThat(testBook.getWebReaderLink()).isEqualTo(DEFAULT_WEB_READER_LINK);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testBook.getTextSnippet()).isEqualTo(UPDATED_TEXT_SNIPPET);
    }

    @Test
    @Transactional
    void fullUpdateBookWithPatch() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book using partial update
        Book partialUpdatedBook = new Book();
        partialUpdatedBook.setId(book.getId());

        partialUpdatedBook
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .subTitle(UPDATED_SUB_TITLE)
            .publisher(UPDATED_PUBLISHER)
            .author(UPDATED_AUTHOR)
            .listPrice(UPDATED_LIST_PRICE)
            .language(UPDATED_LANGUAGE)
            .retailPrice(UPDATED_RETAIL_PRICE)
            .image(UPDATED_IMAGE)
            .pageCount(UPDATED_PAGE_COUNT)
            .averageRating(UPDATED_AVERAGE_RATING)
            .ratingCount(UPDATED_RATING_COUNT)
            .infoLink(UPDATED_INFO_LINK)
            .webReaderLink(UPDATED_WEB_READER_LINK)
            .isbn(UPDATED_ISBN)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .textSnippet(UPDATED_TEXT_SNIPPET);

        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBook.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getRetailPrice()).isEqualTo(UPDATED_RETAIL_PRICE);
        assertThat(testBook.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBook.getPageCount()).isEqualTo(UPDATED_PAGE_COUNT);
        assertThat(testBook.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
        assertThat(testBook.getRatingCount()).isEqualTo(UPDATED_RATING_COUNT);
        assertThat(testBook.getInfoLink()).isEqualTo(UPDATED_INFO_LINK);
        assertThat(testBook.getWebReaderLink()).isEqualTo(UPDATED_WEB_READER_LINK);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testBook.getTextSnippet()).isEqualTo(UPDATED_TEXT_SNIPPET);
    }

    @Test
    @Transactional
    void patchNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, book.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(book))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(book))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc
            .perform(delete(ENTITY_API_URL_ID, book.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
