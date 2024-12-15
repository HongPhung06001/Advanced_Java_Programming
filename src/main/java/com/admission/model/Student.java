package com.admission.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.admission.model.base.DateAuditing;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nationalized
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Nationalized
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    @Column(name = "citizen_identity_number", nullable = false, unique = true)
    private String citizenIdentityNumber;

    @Column(name = "citizen_identity_issue_date", nullable = false)
    private LocalDate citizenIdentityIssueDate;

    @Column(name = "citizen_identity_issued_by", nullable = false)
    private String citizenIdentityIssuedBy;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "ethnic")
    private String ethnic;

    //Link to table User
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
    private User user;

    //link to table Admission
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "block")
    @JsonIgnore
    private Set<Admission> admissions = new HashSet<>();

}
