package vu.lt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "select a from Category as a")
})
@Table(name = "CATEGORY")
@Getter
@Setter
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="SHOP_ID")
    private Shop shop;

    @JoinTable(name = "CATEGORY_PRODUCT", joinColumns = {
            @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Product> productList = new ArrayList<>();

    public Category() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}