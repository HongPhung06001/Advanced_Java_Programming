package com.admission.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String orderNumber;

    private String citizenIdentityNumber;

    private LocalDate citizenIdentityIssueDate;

    private String citizenIdentityIssuedBy;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String address;

    private String gender;

    private LocalDate birthDay;

    private String ethnic;

}
