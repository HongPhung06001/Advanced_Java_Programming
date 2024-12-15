package com.admission.mapper;

import com.admission.dto.SubjectDTO;
import com.admission.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface SubjectMapper {

    SubjectDTO toSubjectDTO(Subject subject);

    Subject toSubject(SubjectDTO subjectDTO);

    void updateSubjectFromDTO(SubjectDTO subjectDTO, @MappingTarget Subject subject);

}
