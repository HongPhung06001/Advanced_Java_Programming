package com.admission.mapper;


import com.admission.dto.SignUpRequest;
import com.admission.dto.StudentDTO;
import com.admission.model.Student;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public interface StudentMapper {

    @Mappings({
            @Mapping(target = "citizenIdentityNumber", source = "citizenIdentityNumber"),
            @Mapping(target = "citizenIdentityIssueDate", source = "citizenIdentityIssueDate"),
            @Mapping(target = "citizenIdentityIssuedBy", source = "citizenIdentityIssuedBy"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "phoneNumber", source = "phoneNumber")
    })
    Student signUpRequestToStudent(SignUpRequest signUpRequest);

    StudentDTO toStudentDTO(Student student);

    List<StudentDTO> toStudentDTOs(List<Student> students);

    Student toStudent(StudentDTO studentDTO);

    void updateStudentFromDTO(StudentDTO studentDTO, @MappingTarget Student student);

}
