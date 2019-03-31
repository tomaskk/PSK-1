package vu.lt.persistence;

import vu.lt.entities.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ProductsDAO {
    @Inject
    private EntityManager em;

    public void persist(Product product){
        this.em.persist(product);
    }

    public Product update(Product prod, int id){
        Product productToUpdate = this.em.find(Product.class, id);
        if(productToUpdate == null)
            return null; // fail
        else {
            prod.setId(id);
            return this.em.merge(prod);
        }
    }

    public Product findProduct(int id){
        return this.em.find(Product.class, id);
    }
}