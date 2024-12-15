package com.admission.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.admission.model.base.DateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blocks")
public class Block extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    //link to table Admission
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "block")
    @JsonIgnore
    private Set<Admission> admissions = new HashSet<>();

    //link to table Major
    @ManyToMany(mappedBy = "blocks", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Major> majors = new HashSet<>();

    //link to table Subject
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "block_subject",
            joinColumns = @JoinColumn(name = "block_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_BLOCK_SUBJECT"))
    )
    @JsonIgnore
    private Set<Subject> subjects = new HashSet<>();

}

