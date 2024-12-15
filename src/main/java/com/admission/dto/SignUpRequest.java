package com.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String citizenIdentityNumber;

    private LocalDate citizenIdentityIssueDate;

    private String citizenIdentityIssuedBy;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}
