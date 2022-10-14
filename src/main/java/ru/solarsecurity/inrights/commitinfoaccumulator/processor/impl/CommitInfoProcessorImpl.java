package ru.solarsecurity.inrights.commitinfoaccumulator.processor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.solarsecurity.inrights.commitinfoaccumulator.configuration.ApplicationConfig;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.GitlabApiFacade;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.CommitInfo;
import ru.solarsecurity.inrights.commitinfoaccumulator.facade.model.FileInfo;
import ru.solarsecurity.inrights.commitinfoaccumulator.manager.RepositoryFilesManager;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.CommitInfoProcessor;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Commit;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Document;
import ru.solarsecurity.inrights.commitinfoaccumulator.processor.model.Record;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommitInfoProcessorImpl implements CommitInfoProcessor {

    private final GitlabApiFacade facade;
    private final RepositoryFilesManager manager;

    @Override
    public List<Document> getFilesAsStrings() {
        final Map<ApplicationConfig.Gitlab.ProjectInfo, List<FileInfo>> repositoryFiles = manager.getRepositoryFiles();
        return repositoryFiles.entrySet().stream()
                .map(e -> toFileAsString(e.getKey(), e.getValue()))
                .toList();
    }

    private Document toFileAsString(ApplicationConfig.Gitlab.ProjectInfo projectInfo, List<FileInfo> fileInfoList) {
        final String id = projectInfo.id();
        final List<Record> records = getRecords(fileInfoList, id);
        return new Document(projectInfo.name(), records);
    }

    private List<Record> getRecords(List<FileInfo> fileInfoList, String id) {
        final Map<String, List<Commit>> commits = getCommits(fileInfoList, id);
        return commits.entrySet().stream()
                .map(e -> new Record(e.getKey(), e.getValue()))
                .toList();
    }

    private Map<String, List<Commit>> getCommits(List<FileInfo> fileInfoList, String id) {
        return fileInfoList.stream()
                .map(f -> toCommit(id, f))
                .filter(c -> !c.commitMessages().isEmpty())
                .collect(Collectors.groupingBy(Commit::packageName));
    }

    private Commit toCommit(String id, FileInfo fileInfo) {
        final String packageName = getPackageName(fileInfo);
        final List<String> commitMessages = getCommitMessages(id, fileInfo.path());
        return new Commit(fileInfo.name(), packageName, commitMessages);
    }

    private String getPackageName(FileInfo fileInfo) {
        return String.format("package %s\n", getPath(fileInfo));
    }

    private String getPath(FileInfo fileInfo) {
        return fileInfo.path().contains("/") ?
                fileInfo.path().substring(0, fileInfo.path().lastIndexOf("/")).replaceAll("/", ".") :
                "root";
    }

    private List<String> getCommitMessages(String id, String path) {
        final List<CommitInfo> commits = facade.getCommitInfoList(id, path);
        return commits.stream()
                .map(CommitInfo::message)
                .toList();
    }

}
