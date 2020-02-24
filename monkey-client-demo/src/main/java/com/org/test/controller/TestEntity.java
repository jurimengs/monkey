package com.org.test.controller;

import javax.persistence.Entity;

import org.monkey.db.face.annotation.PrimaryKey;

import lombok.Data;

@Data
@Entity(name = "t_test")
@PrimaryKey("id")
public class TestEntity {
    private String id;
    private String name;
}
