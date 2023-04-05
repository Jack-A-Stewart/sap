package nl.codegorilla.sap.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, name = "id")
    private long id;

    private long sessionId;

    @OneToMany
    private List<MailCourseStatus> statusList;

    public FileData(long id, long sessionId, List<MailCourseStatus> statusList) {
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

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public List<MailCourseStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<MailCourseStatus> statusList) {
        this.statusList = statusList;
    }
}
