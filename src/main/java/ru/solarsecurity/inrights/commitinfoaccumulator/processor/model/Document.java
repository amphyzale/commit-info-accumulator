package ru.solarsecurity.inrights.commitinfoaccumulator.processor.model;

import java.util.List;

public record Document(String name, List<Record> records) {
}
