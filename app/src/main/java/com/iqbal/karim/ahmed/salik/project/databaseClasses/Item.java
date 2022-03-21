package com.iqbal.karim.ahmed.salik.project.databaseClasses;

public class Item {
    private int id;
    private String name;
    private int requesterId;
    private String requesterName;
    private String category;
    private String givenBy;
    private int shared;

    public Item(int id, String name, int requesterId, String requesterName, String category) {
        this.id = id;
        this.name = name;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.category = category;
    }

    public Item(String name, int requesterId, String requesterName, String category) {
        this.name = name;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.category = category;
    }

    public Item(int id, String name, int requesterId, String requesterName, String category, int shared , String givenBy) {
        this.id = id;
        this.name = name;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.category = category;
        this.givenBy = givenBy;
        this.shared = shared;
    }


    public String getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public int getShared() {
        return shared;
    }

    public void setShared(int shared) {
        this.shared = shared;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
