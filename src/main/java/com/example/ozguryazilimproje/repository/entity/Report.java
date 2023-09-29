package com.example.ozguryazilimproje.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId")
    private Long reportId;
    @Column(name = "fileNo",nullable = false,unique = true)
    private Long fileNo;

    @ManyToOne
    @JoinColumn(name="TC", nullable=false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="hospitalIdNo", nullable=false)
    private  Laborant laborant;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image", referencedColumnName = "imageId")
    private Image image;

    private  String diagnosis;

    private String diagnosisDetail;

    @Builder.Default
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate = new Date();



}
