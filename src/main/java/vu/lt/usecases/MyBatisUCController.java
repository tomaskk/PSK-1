package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.mybatis.dao.ShopMapper;
import vu.lt.mybatis.model.Shop;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class MyBatisUCController {
    @Inject
    private ShopMapper shopMapper;

    @Getter
    private List<Shop> allShops;

    @Getter @Setter
    private Shop shopToCreate = new Shop();

    @PostConstruct
    public void init() {
        this.loadAllTeams();
    }

    private void loadAllTeams() {
        this.allShops = shopMapper.selectAll();
    }

    @Transactional
    public String createShop() {
        shopMapper.insert(shopToCreate);
        return "/myBatis/shops?faces-redirect=true";
    }
}
