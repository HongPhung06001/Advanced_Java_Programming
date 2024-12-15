package com.admission.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AdmissionStatus {
    
    PENDING(1),
    PASS(2),
    FAIL(3),
    SKIP(4);
    
    private Integer value;
    
}
