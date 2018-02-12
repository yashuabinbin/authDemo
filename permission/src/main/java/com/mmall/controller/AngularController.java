/*
 * AngularController.java 1.0.0 2018/2/8  上午 11:07 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/8  上午 11:07 created by lbb
 */
package com.mmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/angular/")
public class AngularController {

    List<Product> products = new ArrayList<Product>() {{
        add(new Product(1, "第一个商品", 1, 3, "第一个商品", Arrays.asList("电子产品")));
        add(new Product(2, "第二个商品", 2, 0, "第二个商品", Arrays.asList("工业物品")));
        add(new Product(3, "第三个商品", 3, 1, "第三个商品", Arrays.asList("日常用品")));
        add(new Product(4, "第四个商品", 4, 4, "第四个商品", Arrays.asList("工业物品")));
        add(new Product(5, "第五个商品", 5, 5, "第五个商品", Arrays.asList("日常用品")));
        add(new Product(5, "第六个商品", 6, 2, "第六个商品", Arrays.asList("电子产品")));
    }};

    @RequestMapping(value = "/hello.json")
    @ResponseBody
    public String hello() {
        return "hello node";
    }

    @RequestMapping(value = "/products.json")
    @ResponseBody
    public List<Product> products() {
        return products;
    }

    @RequestMapping(value = "/products/{id}.json")
    @ResponseBody
    public Product getProduct(@PathVariable(value = "id") Integer id) {
        return products.stream().filter(product -> product.getId() == id.intValue()).findFirst().orElseGet(null);
    }

}

class Product {

    private Integer id;

    private String title;

    private double price;

    private double rating;

    private String desc;

    private List<String> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Product(Integer id, String title, double price, double rating, String desc, List<String> categories) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.desc = desc;
        this.categories = categories;
    }
}