package de.mst.selberstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "author")
    private String author;

    @Column(name = "list_price")
    private String listPrice;

    @Column(name = "language")
    private String language;

    @Column(name = "retail_price")
    private String retailPrice;

    @Column(name = "image")
    private String image;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "info_link")
    private String infoLink;

    @Column(name = "web_reader_link")
    private String webReaderLink;

    @Lob
    @Column(name = "isbn")
    private String isbn;

    @Lob
    @Column(name = "long_description")
    private String longDescription;

    @Lob
    @Column(name = "text_snippet")
    private String textSnippet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Book name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public Book title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Book description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public Book subTitle(String subTitle) {
        this.setSubTitle(subTitle);
        return this;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book publisher(String publisher) {
        this.setPublisher(publisher);
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getListPrice() {
        return this.listPrice;
    }

    public Book listPrice(String listPrice) {
        this.setListPrice(listPrice);
        return this;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getLanguage() {
        return this.language;
    }

    public Book language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRetailPrice() {
        return this.retailPrice;
    }

    public Book retailPrice(String retailPrice) {
        this.setRetailPrice(retailPrice);
        return this;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getImage() {
        return this.image;
    }

    public Book image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public Book pageCount(Integer pageCount) {
        this.setPageCount(pageCount);
        return this;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Float getAverageRating() {
        return this.averageRating;
    }

    public Book averageRating(Float averageRating) {
        this.setAverageRating(averageRating);
        return this;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingCount() {
        return this.ratingCount;
    }

    public Book ratingCount(Integer ratingCount) {
        this.setRatingCount(ratingCount);
        return this;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getInfoLink() {
        return this.infoLink;
    }

    public Book infoLink(String infoLink) {
        this.setInfoLink(infoLink);
        return this;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getWebReaderLink() {
        return this.webReaderLink;
    }

    public Book webReaderLink(String webReaderLink) {
        this.setWebReaderLink(webReaderLink);
        return this;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public Book isbn(String isbn) {
        this.setIsbn(isbn);
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public Book longDescription(String longDescription) {
        this.setLongDescription(longDescription);
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getTextSnippet() {
        return this.textSnippet;
    }

    public Book textSnippet(String textSnippet) {
        this.setTextSnippet(textSnippet);
        return this;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", author='" + getAuthor() + "'" +
            ", listPrice='" + getListPrice() + "'" +
            ", language='" + getLanguage() + "'" +
            ", retailPrice='" + getRetailPrice() + "'" +
            ", image='" + getImage() + "'" +
            ", pageCount=" + getPageCount() +
            ", averageRating=" + getAverageRating() +
            ", ratingCount=" + getRatingCount() +
            ", infoLink='" + getInfoLink() + "'" +
            ", webReaderLink='" + getWebReaderLink() + "'" +
            ", isbn='" + getIsbn() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", textSnippet='" + getTextSnippet() + "'" +
            "}";
    }
}
