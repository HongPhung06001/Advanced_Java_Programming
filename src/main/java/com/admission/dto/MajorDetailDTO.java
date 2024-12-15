package com.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MajorDetailDTO {

    private Integer id;

    private String code;

    private String name;

    private Integer amountStudentReceived;

    private Float benchMark;

    private String blocks;
    
    private Integer majorId;

}
