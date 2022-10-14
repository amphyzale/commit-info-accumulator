package ru.solarsecurity.inrights.commitinfoaccumulator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@ConstructorBinding
@ConfigurationProperties(prefix = "commit-info-accumulator")
public record ApplicationConfig(
        String pathToSave,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime since,
        Gitlab gitlab) {

    public record Gitlab(
            String url,
            String token,
            List<ProjectInfo> projects,
            String fileTreePath,
            String commitInfoPath
    ) {

        public record ProjectInfo(String id, String name) {

        }

    }

}
