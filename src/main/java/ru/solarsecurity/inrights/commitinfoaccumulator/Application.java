package ru.solarsecurity.inrights.commitinfoaccumulator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.CommitInfoProcessor;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Document;
import ru.solarsecurity.inrights.commitinfoaccumulator.writer.DocumentWriter;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class Application implements CommandLineRunner {

    private final CommitInfoProcessor producer;
    private final DocumentWriter documentWriter;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        final List<Document> files = producer.getFilesAsStrings();
        //System.out.println(files);
        documentWriter.write(files);
    }
}
