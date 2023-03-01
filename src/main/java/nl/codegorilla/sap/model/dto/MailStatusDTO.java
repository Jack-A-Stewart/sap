package nl.codegorilla.sap.model.dto;

public class MailStatusDTO {

    private String email;

    private String status;

    public MailStatusDTO(String email) {
        this.email = email;
        this.status = "false";
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
