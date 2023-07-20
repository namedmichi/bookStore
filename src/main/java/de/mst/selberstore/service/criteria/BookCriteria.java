package de.mst.selberstore.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link de.mst.selberstore.domain.Book} entity. This class is used
 * in {@link de.mst.selberstore.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter title;

    private StringFilter subTitle;

    private StringFilter publisher;

    private StringFilter author;

    private StringFilter listPrice;

    private StringFilter language;

    private StringFilter retailPrice;

    private StringFilter image;

    private IntegerFilter pageCount;

    private FloatFilter averageRating;

    private IntegerFilter ratingCount;

    private StringFilter infoLink;

    private StringFilter webReaderLink;

    private Boolean distinct;

    public BookCriteria() {}

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.subTitle = other.subTitle == null ? null : other.subTitle.copy();
        this.publisher = other.publisher == null ? null : other.publisher.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.listPrice = other.listPrice == null ? null : other.listPrice.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.retailPrice = other.retailPrice == null ? null : other.retailPrice.copy();
        this.image = other.image == null ? null : other.image.copy();
        this.pageCount = other.pageCount == null ? null : other.pageCount.copy();
        this.averageRating = other.averageRating == null ? null : other.averageRating.copy();
        this.ratingCount = other.ratingCount == null ? null : other.ratingCount.copy();
        this.infoLink = other.infoLink == null ? null : other.infoLink.copy();
        this.webReaderLink = other.webReaderLink == null ? null : other.webReaderLink.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getSubTitle() {
        return subTitle;
    }

    public StringFilter subTitle() {
        if (subTitle == null) {
            subTitle = new StringFilter();
        }
        return subTitle;
    }

    public void setSubTitle(StringFilter subTitle) {
        this.subTitle = subTitle;
    }

    public StringFilter getPublisher() {
        return publisher;
    }

    public StringFilter publisher() {
        if (publisher == null) {
            publisher = new StringFilter();
        }
        return publisher;
    }

    public void setPublisher(StringFilter publisher) {
        this.publisher = publisher;
    }

    public StringFilter getAuthor() {
        return author;
    }

    public StringFilter author() {
        if (author == null) {
            author = new StringFilter();
        }
        return author;
    }

    public void setAuthor(StringFilter author) {
        this.author = author;
    }

    public StringFilter getListPrice() {
        return listPrice;
    }

    public StringFilter listPrice() {
        if (listPrice == null) {
            listPrice = new StringFilter();
        }
        return listPrice;
    }

    public void setListPrice(StringFilter listPrice) {
        this.listPrice = listPrice;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public StringFilter language() {
        if (language == null) {
            language = new StringFilter();
        }
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public StringFilter getRetailPrice() {
        return retailPrice;
    }

    public StringFilter retailPrice() {
        if (retailPrice == null) {
            retailPrice = new StringFilter();
        }
        return retailPrice;
    }

    public void setRetailPrice(StringFilter retailPrice) {
        this.retailPrice = retailPrice;
    }

    public StringFilter getImage() {
        return image;
    }

    public StringFilter image() {
        if (image == null) {
            image = new StringFilter();
        }
        return image;
    }

    public void setImage(StringFilter image) {
        this.image = image;
    }

    public IntegerFilter getPageCount() {
        return pageCount;
    }

    public IntegerFilter pageCount() {
        if (pageCount == null) {
            pageCount = new IntegerFilter();
        }
        return pageCount;
    }

    public void setPageCount(IntegerFilter pageCount) {
        this.pageCount = pageCount;
    }

    public FloatFilter getAverageRating() {
        return averageRating;
    }

    public FloatFilter averageRating() {
        if (averageRating == null) {
            averageRating = new FloatFilter();
        }
        return averageRating;
    }

    public void setAverageRating(FloatFilter averageRating) {
        this.averageRating = averageRating;
    }

    public IntegerFilter getRatingCount() {
        return ratingCount;
    }

    public IntegerFilter ratingCount() {
        if (ratingCount == null) {
            ratingCount = new IntegerFilter();
        }
        return ratingCount;
    }

    public void setRatingCount(IntegerFilter ratingCount) {
        this.ratingCount = ratingCount;
    }

    public StringFilter getInfoLink() {
        return infoLink;
    }

    public StringFilter infoLink() {
        if (infoLink == null) {
            infoLink = new StringFilter();
        }
        return infoLink;
    }

    public void setInfoLink(StringFilter infoLink) {
        this.infoLink = infoLink;
    }

    public StringFilter getWebReaderLink() {
        return webReaderLink;
    }

    public StringFilter webReaderLink() {
        if (webReaderLink == null) {
            webReaderLink = new StringFilter();
        }
        return webReaderLink;
    }

    public void setWebReaderLink(StringFilter webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(title, that.title) &&
            Objects.equals(subTitle, that.subTitle) &&
            Objects.equals(publisher, that.publisher) &&
            Objects.equals(author, that.author) &&
            Objects.equals(listPrice, that.listPrice) &&
            Objects.equals(language, that.language) &&
            Objects.equals(retailPrice, that.retailPrice) &&
            Objects.equals(image, that.image) &&
            Objects.equals(pageCount, that.pageCount) &&
            Objects.equals(averageRating, that.averageRating) &&
            Objects.equals(ratingCount, that.ratingCount) &&
            Objects.equals(infoLink, that.infoLink) &&
            Objects.equals(webReaderLink, that.webReaderLink) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            title,
            subTitle,
            publisher,
            author,
            listPrice,
            language,
            retailPrice,
            image,
            pageCount,
            averageRating,
            ratingCount,
            infoLink,
            webReaderLink,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (subTitle != null ? "subTitle=" + subTitle + ", " : "") +
            (publisher != null ? "publisher=" + publisher + ", " : "") +
            (author != null ? "author=" + author + ", " : "") +
            (listPrice != null ? "listPrice=" + listPrice + ", " : "") +
            (language != null ? "language=" + language + ", " : "") +
            (retailPrice != null ? "retailPrice=" + retailPrice + ", " : "") +
            (image != null ? "image=" + image + ", " : "") +
            (pageCount != null ? "pageCount=" + pageCount + ", " : "") +
            (averageRating != null ? "averageRating=" + averageRating + ", " : "") +
            (ratingCount != null ? "ratingCount=" + ratingCount + ", " : "") +
            (infoLink != null ? "infoLink=" + infoLink + ", " : "") +
            (webReaderLink != null ? "webReaderLink=" + webReaderLink + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
