package com.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadFileExcelResponse<T> {

    private Boolean status;

    private String message;
    
    private T response;
    
}
