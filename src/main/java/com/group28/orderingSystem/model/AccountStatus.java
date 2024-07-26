package com.group28.orderingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AccountStatus {
    @Id
    private int id;
    private String status;

    public AccountStatus() {
    }

    public AccountStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


