package vu.lt.usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import vu.lt.CDI.ProcessEnteredData;
import vu.lt.entities.Category;
import vu.lt.entities.Product;
import vu.lt.entities.Shop;
import vu.lt.persistence.CategoriesDAO;
import vu.lt.persistence.ProductsDAO;
import vu.lt.persistence.ShopsDAO;

@Model
public class ProductsForShop implements Serializable {

    @Inject
    private ProcessEnteredData processData;

    @Inject
    private ShopsDAO shopsDAO;
    @Inject
    private ProductsDAO productsDAO;
    @Inject
    private CategoriesDAO categoriesDAO;

    @Getter @Setter
    private Shop shop;
    @Getter @Setter
    private Product productToCreate = new Product();
    @Getter @Setter
    private Category categoryToCreate = new Category();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer shopId = Integer.parseInt(requestParameters.get("shopId"));
        this.shop = shopsDAO.findOne(shopId);
    }

    @Transactional
    public String createProduct() {
        productToCreate.setShop(this.shop);
        productsDAO.persist(productToCreate);
        return "products?faces-redirect=true&shopId=" + this.shop.getId();
    }

    @Transactional
    public String createCategory() {
        categoryToCreate.setShop(this.shop);
        categoriesDAO.persist(categoryToCreate);
        return "products?faces-redirect=true&shopId=" + this.shop.getId();
    }

    @Transactional
    public String connectProductWithCat() {

        categoryToCreate.setShop(this.shop);
        productToCreate.setShop(this.shop);
        boolean legitCategory = false;
        boolean legitProduct = false;
        int catID = 0, prodID = 0;

        for(Category cat : this.shop.getCategories()){
            if(cat.getName().equals(categoryToCreate.getName())){
                System.out.println("Such category already exists in DB");
                legitCategory = true;
                catID = cat.getId();
                categoryToCreate.setId(catID);
                break;
            }
        }

        for(Product prod : this.shop.getProducts()){
            if(prod.getName().equals(productToCreate.getName())){
                System.out.println("Such product already exists in DB");
                legitProduct = true;
                prodID = prod.getId();
                productToCreate.setId(prodID);
                break;
            }
        }

        if(legitCategory && legitProduct)
        {
            productToCreate  = processData.setProductConnection(prodID, categoryToCreate, productToCreate);
            categoryToCreate = processData.setCategoryConnection(catID, categoryToCreate, productToCreate);

            productToCreate = productsDAO.update(productToCreate, prodID);
            categoryToCreate = categoriesDAO.update(categoryToCreate, catID);
        }

        return "products?faces-redirect=true&shopId=" + this.shop.getId();
    }
}