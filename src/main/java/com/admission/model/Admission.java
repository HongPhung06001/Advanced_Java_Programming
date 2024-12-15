package com.admission.model;

import com.admission.model.base.DateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admissions")
public class Admission extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "orders", nullable = false)
    private Integer orders;

    @Column(name = "total_score")
    private Float totalScore;

    /**
     * 1. Pending
     * 2. Pass
     * 3. Fail
     * 4. Skip
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    //link to table Student
    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_ADMISSION_STUDENT"))
    private Student student;

    //link to table Major
    @ManyToOne
    @JoinColumn(name = "major_id", foreignKey = @ForeignKey(name = "FK_ADMISSION_MAJOR"))
    private Major major;

    //link to table Block
    @ManyToOne
    @JoinColumn(name = "block_id", foreignKey = @ForeignKey(name = "FK_ADMISSION_BLOCK"))
    private Block block;

}
