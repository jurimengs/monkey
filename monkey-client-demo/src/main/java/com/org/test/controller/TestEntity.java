package com.org.test.controller;

import org.monkey.db.face.annotation.Id;

import lombok.Data;

@Data
public class TestEntity {
    @Id
    private String id;
    private String name;
}
