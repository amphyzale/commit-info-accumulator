package ru.solarsecurity.inrights.commitinfoaccumulator.processor;

import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Document;

import java.util.List;

public interface CommitInfoProcessor {
    List<Document> getFilesAsStrings();
}
