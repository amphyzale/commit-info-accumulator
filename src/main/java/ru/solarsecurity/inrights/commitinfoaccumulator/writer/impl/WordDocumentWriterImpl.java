package ru.solarsecurity.inrights.commitinfoaccumulator.writer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;
import ru.solarsecurity.inrights.commitinfoaccumulator.configuration.ApplicationConfig;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Commit;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Document;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Record;
import ru.solarsecurity.inrights.commitinfoaccumulator.writer.DocumentWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WordDocumentWriterImpl implements DocumentWriter {

    private final ApplicationConfig config;

    @Override
    public void write(List<Document> documents) {
        documents.forEach(this::save);
    }


    private void save(Document document) {
        if (!Paths.get(config.pathToSave()).toFile().exists()) {
            try {
                Files.createDirectories(Paths.get(config.pathToSave()));
            } catch (IOException e) {
                log.error("Cant create directory to save!" + e);
            }
        }
        try (
                final XWPFDocument wordDocument = new XWPFDocument();
                final FileOutputStream out = new FileOutputStream(config.pathToSave() + document.name() + ".docx")
        ) {
            final XWPFParagraph paragraph = wordDocument.createParagraph();
            final XWPFRun run = paragraph.createRun();
            run.setFontSize(14);
            run.setFontFamily("Time New Roman");
            for (Record record : document.records()) {
                run.setText(record.packageName());
                run.addBreak();
                run.addBreak();
                for (Commit commit : record.commits()) {
                    run.setText(commit.fileName());
                    run.addBreak();
                    for (String commitMessage : commit.commitMessages()) {
                        run.setText(commitMessage);
                        run.addBreak();
                    }
                    run.addBreak();
                }
                run.addBreak();
            }
            wordDocument.write(out);
        } catch (IOException e) {
            log.error("Can`t save file with name: " + document.name(), e);
        }
    }
}
