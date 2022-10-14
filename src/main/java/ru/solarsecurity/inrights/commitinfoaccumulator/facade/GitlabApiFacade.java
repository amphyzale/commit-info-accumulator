package ru.solarsecurity.inrights.commitinfoaccumulator.facade;

import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.CommitInfo;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.FileInfo;

import java.util.List;

public interface GitlabApiFacade {
    List<CommitInfo> getCommitInfoList(String projectId, String filePath);
    List<FileInfo> getFiles(String projectId, Boolean isRecursive, int page);
}
