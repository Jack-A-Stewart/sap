package nl.codegorilla.sap.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, name = "id")
    private long id;


    // change sessionID to something we can identify users with
//    private String sessionId;
    private String userEmail;


    @ElementCollection
    private List<MailCourseStatus> statusList;

    public FileData() {
    }

    public FileData(long id, String userEmail, List<MailCourseStatus> statusList) {
        this.id = id;
        this.userEmail = userEmail;
        this.statusList = statusList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<MailCourseStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<MailCourseStatus> statusList) {
        this.statusList = statusList;
    }


}
