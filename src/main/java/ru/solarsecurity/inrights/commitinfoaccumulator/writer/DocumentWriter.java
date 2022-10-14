package ru.solarsecurity.inrights.commitinfoaccumulator.writer;

import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Document;

import java.util.List;

public interface DocumentWriter {
    void write(List<Document> documents);
}
