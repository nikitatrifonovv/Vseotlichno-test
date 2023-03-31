package com.example.vseotlichno.screens.main.model;

public class RefundGood {
    private int article;
    private String name;
    private int quantity;
    private int quantity_refund;
    private double price;
    private String reason;
    private String imgUri;

    public RefundGood(Good g) {
        article = g.getArticle();
        name = g.getName();
        quantity = g.getQuantity();
        price = g.getPrice();
    }

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

    public int getQuantity_refund() {
        return quantity_refund;
    }

    public void setQuantity_refund(int quantity_refund) {
        this.quantity_refund = quantity_refund;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RefundGood{" +
                "article=" + article +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", quantity_refund=" + quantity_refund +
                ", price=" + price +
                ", reason='" + reason + '\'' +
                ", imgUri='" + imgUri + '\'' +
                '}';
    }
}
