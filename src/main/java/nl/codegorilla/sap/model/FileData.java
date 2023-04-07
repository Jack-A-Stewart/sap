package nl.codegorilla.sap.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, name = "id")
    private long id;

    private String sessionId;

    @ElementCollection
    private List<MailCourseStatus> statusList;

    public FileData(long id, String sessionId, List<MailCourseStatus> statusList) {
        this.id = id;
        this.sessionId = sessionId;
        this.statusList = statusList;
    }

    public FileData() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<MailCourseStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<MailCourseStatus> statusList) {
        this.statusList = statusList;
    }
}
