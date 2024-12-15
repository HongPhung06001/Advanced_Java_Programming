package com.admission.model;

import com.admission.constants.MajorDetailsStatus;
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
@Table(name = "major_details")
public class MajorDetail extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount_student_received")
    private Integer amountStudentReceived;

    @Column(name = "bench_mark")
    private Float benchMark;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MajorDetailsStatus status;

    //link to table Major
    @ManyToOne
    @JoinColumn(name = "major_id", foreignKey = @ForeignKey(name = "FK_MAJOR_DETAIL_MAJOR"))
    private Major major;

}
