package ru.solarsecurity.inrights.commitinfoaccumulator.processor.model;

import java.util.List;

public record Commit(String fileName, String packageName, List<String> commitMessages) {
}
