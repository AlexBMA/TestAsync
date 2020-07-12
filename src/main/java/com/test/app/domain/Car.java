package com.test.app.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String word;
//
//    private String manufacturer;
//    private String model;
//    private String type;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
