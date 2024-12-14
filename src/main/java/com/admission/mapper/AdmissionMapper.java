package com.admission.mapper;

import com.admission.dto.AdmissionResultDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface AdmissionMapper {

    default AdmissionResultDTO objectToAdmissionResultDto(Object[] object) {
        String majorCode = (String) object[0];
        String majorName = (String) object[1];
        String lastName = (String) object[2];
        String firstName = (String) object[3];
        String orderNumber = (String) object[4];
        String citizenIdentityNumber = (String) object[5];
        String email = (String) object[6];
        String phoneNumber = (String) object[7];
        String gender = (String) object[8];
        String address = (String) object[9];
        Integer orders = (Integer) object[10];
        String block = (String) object[11];
        BigDecimal totalScoreValue = (BigDecimal) object[12];
        Float totalScore;
        if (totalScoreValue != null) {
            totalScore = totalScoreValue.floatValue();
        } else {
            totalScore = null;
        }
        Integer admissionStatus = (Integer) object[13];
        return new AdmissionResultDTO(majorCode, majorName, lastName, firstName, orderNumber, 
                citizenIdentityNumber, email, phoneNumber, gender, address, orders, 
                block, totalScore, admissionStatus);
    }

    default List<AdmissionResultDTO> objectsToAdmissionResultDtos(List<Object[]> objects) {
        List<AdmissionResultDTO> results = new LinkedList<>();
        objects.forEach(object -> {
            results.add(objectToAdmissionResultDto(object));
        });
        return results;
    }

}
