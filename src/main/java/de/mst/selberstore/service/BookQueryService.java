package de.mst.selberstore.service;

import de.mst.selberstore.domain.*; // for static metamodels
import de.mst.selberstore.domain.Book;
import de.mst.selberstore.repository.BookRepository;
import de.mst.selberstore.service.criteria.BookCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Book} entities in the database.
 * The main input is a {@link BookCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Book} or a {@link Page} of {@link Book} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookQueryService extends QueryService<Book> {

    private final Logger log = LoggerFactory.getLogger(BookQueryService.class);

    private final BookRepository bookRepository;

    public BookQueryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Return a {@link List} of {@link Book} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Book> findByCriteria(BookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Book} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Book> findByCriteria(BookCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BookCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.count(specification);
    }

    /**
     * Function to convert {@link BookCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Book> createSpecification(BookCriteria criteria) {
        Specification<Book> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Book_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Book_.name));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Book_.title));
            }
            if (criteria.getSubTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubTitle(), Book_.subTitle));
            }
            if (criteria.getPublisher() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPublisher(), Book_.publisher));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), Book_.author));
            }
            if (criteria.getListPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getListPrice(), Book_.listPrice));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), Book_.language));
            }
            if (criteria.getRetailPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRetailPrice(), Book_.retailPrice));
            }
            if (criteria.getImage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImage(), Book_.image));
            }
            if (criteria.getPageCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPageCount(), Book_.pageCount));
            }
            if (criteria.getAverageRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAverageRating(), Book_.averageRating));
            }
            if (criteria.getRatingCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRatingCount(), Book_.ratingCount));
            }
            if (criteria.getInfoLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoLink(), Book_.infoLink));
            }
            if (criteria.getWebReaderLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebReaderLink(), Book_.webReaderLink));
            }
        }
        return specification;
    }
}
