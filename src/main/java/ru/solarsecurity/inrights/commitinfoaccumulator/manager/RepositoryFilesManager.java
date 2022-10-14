package ru.solarsecurity.inrights.commitinfoaccumulator.manager;

import ru.solarsecurity.inrights.commitinfoaccumulator.configuration.ApplicationConfig;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.FileInfo;

import java.util.List;
import java.util.Map;

public interface RepositoryFilesManager {
    Map<ApplicationConfig.Gitlab.ProjectInfo, List<FileInfo>> getRepositoryFiles();
}
