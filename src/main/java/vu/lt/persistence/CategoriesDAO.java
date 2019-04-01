package vu.lt.persistence;

import vu.lt.entities.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CategoriesDAO{
    @Inject
    private EntityManager em;

    public void persist(Category cat){
        this.em.persist(cat);
    }

    public Category update(Category cat, int id){
        Category categoryToUpdate = this.em.find(Category.class, id);
        if(categoryToUpdate == null)
            return null; // fail
        else {
            cat.setId(id);
            return this.em.merge(cat); // https://docs.oracle.com/javaee/5/api/javax/persistence/EntityManager.html#merge%28T%29
        }
    }

    public Category findCategory(int id){
        return this.em.find(Category.class, id);
    }
}