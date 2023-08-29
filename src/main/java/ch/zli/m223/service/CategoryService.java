package ch.zli.m223.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import ch.zli.m223.model.Category;
import java.util.List;

@ApplicationScoped
public class CategoryService {
    
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Category createCategory(Category category) {
        entityManager.persist(category);
        return category;
    }

    public List<Category> findAll() {
        var query = entityManager.createQuery("FROM Category", Category.class);
        return query.getResultList();
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            entityManager.remove(category);
        } else {
            System.out.println("didn't work :( category edition");
        }
    }

    @Transactional
    public Category editCategory(Long id, Category category) {
        category.setId(id);
        return entityManager.merge(category);
    }
}
