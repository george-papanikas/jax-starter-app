package gr.aueb.cf.jaxstarter.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeacherInsertDTO {

//    @NotNull(message = "Please fill the id")
//    private Long id;

    @Size(min = 6, max = 6, message = "Ssn must be 6-digit long")
    private String ssn;

    @NotBlank(message = "Please fill the firstname")
    private String firstname;

    @NotBlank(message = "Please fill the lastname")
    private String lastname;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
