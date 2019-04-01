package vu.lt.CDI;

import vu.lt.entities.Category;
import vu.lt.entities.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import vu.lt.persistence.CategoriesDAO;
import vu.lt.persistence.ProductsDAO;

@Named
@RequestScoped
public class ProcessEnteredData {

    @Inject
    private ProductsDAO productsDAO;
    @Inject
    private CategoriesDAO categoriesDAO;

    public Category setCategoryConnection(int catID, Category categoryToCreate, Product productToCreate){
        List<Product> products = categoriesDAO.findCategory(catID).getProductList();
        products.add(productToCreate);
        categoryToCreate.setProductList(products);

        return categoryToCreate;
    }

    public Product setProductConnection(int prodID, Category categoryToCreate, Product productToCreate){
        List<Category> cats = productsDAO.findProduct(prodID).getCatList();
        cats.add(categoryToCreate);
        productToCreate.setCatList(cats);

        return productToCreate;
    }
}
