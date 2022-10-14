package ru.solarsecurity.inrights.commitinfoaccumulator.processor.model;

import java.util.List;

public record Record(String packageName, List<Commit> commits) {
}
