package com.example.vseotlichno.screens.main.model;

public class Good {
    private int article;
    private String name;
    private int quantity;
    private double price;
    private double summ;

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSumm() {
        return summ;
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }

    @Override
    public String toString() {
        return "Good{" +
                "article=" + article +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", summ=" + summ +
                '}';
    }
}
