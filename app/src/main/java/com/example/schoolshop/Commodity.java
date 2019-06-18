package com.example.schoolshop;

public class Stuff {
    private String id;
    private String name;
    private String owner;
    private String description;
    private String img_url;
    private String price;
    private String status;
    private String created_at;
    private String updated_at;

    public Stuff(String id,
                     String name,
                     String owner,
                     String description,
                     String img_url,
                     String price,
                     String status,
                     String created_at,
                     String updated_at){

        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getOwner() { return this.owner; }
    public String getDescription() {
        return this.description;
    }
    public String getImgUrl() {
        return this.img_url;
    }
    public String getPrice() { return this.price; }
    public String getStatus() { return this.status; }
    public String getCreatedAt() { return this.created_at; }
    public String getUpdatedAt() { return this.updated_at; }
}
