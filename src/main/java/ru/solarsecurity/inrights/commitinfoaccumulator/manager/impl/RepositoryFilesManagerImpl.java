package ru.solarsecurity.inrights.commitinfoaccumulator.manager.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.solarsecurity.inrights.commitinfoaccumulator.configuration.ApplicationConfig;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.GitlabApiFacade;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.FileInfo;
import ru.solarsecurity.inrights.commitinfoaccumulator.manager.RepositoryFilesManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositoryFilesManagerImpl implements RepositoryFilesManager {

    private static final String FILE_TYPE = "blob";

    private final GitlabApiFacade facade;
    private final ApplicationConfig config;

    @Override
    public Map<ApplicationConfig.Gitlab.ProjectInfo, List<FileInfo>> getRepositoryFiles() {
        return config.gitlab().projects().stream()
                .collect(Collectors.toMap(Function.identity(), v -> getRepositoryFiles(v.id())));
    }

    private List<FileInfo> getRepositoryFiles(String projectId) {
        List<FileInfo> response = facade.getFiles(projectId, true,1);
        if (response.isEmpty()) {
            log.warn("NO FILES WAS RETURNED FROM GITLAB");
            return response;
        }
        final List<FileInfo> result = new LinkedList<>(response);
        for (int i = 2; !response.isEmpty(); i++) {
            response = facade.getFiles(projectId, true,i);
            result.addAll(response);
        }
        return result.stream()
                .filter(t -> FILE_TYPE.equals(t.type()))
                .toList();
    }
}
