package com.admission.mapper;

import com.admission.dto.MajorDetailDTO;
import com.admission.dto.StatisticMajorDetailDTO;
import com.admission.model.MajorDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface MajorDetailMapper {

    MajorDetail toMajorDetail(MajorDetailDTO majorDetailDTO);

    @Mapping(target = "id", ignore = true)
    void updateMajorDetailFromDTO(MajorDetailDTO majorDetailDTO, @MappingTarget MajorDetail majorDetail);

    default MajorDetailDTO objectToMajorDetailDto(Object[] object) {
        Integer id = (Integer) object[0];
        String code = (String) object[1];
        String name = (String) object[2];
        String blocks = (String) object[3];
        BigDecimal benchMarkValue = (BigDecimal) object[4];
        Float benchMark;
        if (benchMarkValue != null) {
            benchMark = benchMarkValue.floatValue();
        } else {
            benchMark = null;
        }
        Integer amountStudentReceived = (Integer) object[5];
        Integer majorId = (Integer) object[6];
        return new MajorDetailDTO(id, code, name, amountStudentReceived, benchMark, blocks, majorId);
    }

    default List<MajorDetailDTO> objectsToMajorDetailDtos(List<Object[]> objects) {
        List<MajorDetailDTO> results = new LinkedList<>();
        objects.forEach(object -> {
            results.add(objectToMajorDetailDto(object));
        });
        return results;
    }

    default StatisticMajorDetailDTO objectToStatisticMajorDetailDto(Object[] object) {
        String code = (String) object[0];
        String name = (String) object[1];
        Integer amountStudentReceived = (Integer) object[2];
        Float benchMark = (Float) object[3];
        BigInteger numberOfStudentsRegistered = (BigInteger) object[4];
        BigInteger numberOfStudentsPassed = (BigInteger) object[5];
        BigInteger numberOfStudentsFailed = (BigInteger) object[6];
        return new StatisticMajorDetailDTO(code, name, amountStudentReceived, benchMark,
                numberOfStudentsRegistered.intValue(), numberOfStudentsPassed.intValue(), numberOfStudentsFailed.intValue());
    }

    default List<StatisticMajorDetailDTO> objectsToStatisticMajorDetailDtos(List<Object[]> objects) {
        List<StatisticMajorDetailDTO> results = new LinkedList<>();
        objects.forEach(object -> {
            results.add(objectToStatisticMajorDetailDto(object));
        });
        return results;
    }

}
