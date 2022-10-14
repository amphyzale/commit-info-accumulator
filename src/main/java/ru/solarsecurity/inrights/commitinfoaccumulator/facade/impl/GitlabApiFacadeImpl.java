package ru.solarsecurity.inrights.commitinfoaccumulator.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.solarsecurity.inrights.commitinfoaccumulator.configuration.ApplicationConfig;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.GitlabApiFacade;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.CommitInfo;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.FileInfo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GitlabApiFacadeImpl implements GitlabApiFacade {

    public static final int MAX_PAGE_SIZE = 100;
    private final ApplicationConfig config;
    private final RestTemplate gitlabRestTemplate;

    @Override
    public List<FileInfo> getFiles(String projectId, Boolean isRecursive, int page) {
        final Map<String, ? extends Serializable> parameters = Map.of(
                "id", projectId,
                "isRecursive", isRecursive,
                "count", MAX_PAGE_SIZE,
                "page", page
        );
        return doRequest(config.gitlab().fileTreePath(), parameters, FileInfo[].class);
    }

    @Override
    public List<CommitInfo> getCommitInfoList(String projectId, String filePath) {
        final Map<String, ? extends Serializable> parameters = Map.of(
                "id", projectId,
                "filePath", filePath,
                "since", config.since()
        );
        return doRequest(config.gitlab().commitInfoPath(), parameters, CommitInfo[].class);
    }

    private <T> List<T> doRequest(String path, Map<String, ? extends Serializable> parameters, Class<T[]> type) {
        final ResponseEntity<T[]> response = gitlabRestTemplate.getForEntity(
                path,
                type,
                parameters
        );
        return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
    }

}
