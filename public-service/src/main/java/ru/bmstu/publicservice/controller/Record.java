package ru.bmstu.publicservice.controller;

import java.util.UUID;

public class Record {
    private UUID uuid;
    private String name;

    public Record(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
