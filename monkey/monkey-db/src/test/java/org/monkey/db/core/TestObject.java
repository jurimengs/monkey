package org.monkey.db.core;

import org.monkey.db.face.annotation.Id;

import lombok.Data;

@Data
public class TestObject {
    @Id
    private String id;
    private String name;
}
