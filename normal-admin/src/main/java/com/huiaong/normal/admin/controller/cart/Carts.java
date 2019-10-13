package com.huiaong.normal.admin.controller.cart;


import com.alibaba.dubbo.config.annotation.Reference;
import com.huiaong.normal.item.log.service.CartLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/api/admin/carts")
public class Carts {

    @Reference
    private CartLogService cartLogService;

    @RequestMapping(value = "/add-to-cart", method = RequestMethod.POST)
    public Boolean addToCart(){

//        ...业务逻辑

//        添加购物车日志
        return cartLogService.create();
    }

}
