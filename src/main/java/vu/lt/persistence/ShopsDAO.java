package vu.lt.persistence;

import vu.lt.entities.Shop;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ShopsDAO {

    @Inject
    private EntityManager em;

    public List<Shop> loadAll() {
        return em.createNamedQuery("Shop.findAll", Shop.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Shop shop){
        this.em.persist(shop);
    }

    public Shop findOne(Integer id) {
        return em.find(Shop.class, id);
    }
}