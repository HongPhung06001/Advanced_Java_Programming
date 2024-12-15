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
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "majors")
public class Major extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Nationalized
    @Column(name = "name", nullable = false)
    private String name;

    //link to table MajorDetail
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "major")
    @JsonIgnore
    private Set<MajorDetail> majorDetails = new HashSet<>();

    //link to table Admission
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "major")
    @JsonIgnore
    private Set<Admission> admissions = new HashSet<>();

    //link to table Block
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "major_block",
            joinColumns = @JoinColumn(name = "major_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "block_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_MAJOR_BLOCK"))
    )
    @JsonIgnore
    private Set<Block> blocks = new HashSet<>();

}
