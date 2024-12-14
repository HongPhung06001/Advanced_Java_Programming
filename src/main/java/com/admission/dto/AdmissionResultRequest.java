package com.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionResultRequest {

    private Integer year;
    
    private String keyword;
    
    private String code;
    
    private Integer statusAdmission;

}
