package com.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionResultDTO {
    
    private String majorCode;
    
    private String majorName;

    private String lastName;

    private String firstName;

    private String orderNumber;

    private String citizenIdentityNumber;

    private String email;

    private String phoneNumber;
    
    private String gender;

    private String address;
    
    private Integer orders;
    
    private String block;
    
    private Float totalScore;
    
    private Integer status;
    
}
