package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.csmis.enums.ContentType;
import com.spring.csmis.enums.IsSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement")
public class AnnouncementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title" , nullable = false, length = 40, unique = true)
    private String title;

    @Column(name = "content", nullable = false, length = 40, unique = true)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "pdf_link", nullable = true,columnDefinition = "VARCHAR(150)")
    private String pdfLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "contentType", nullable = false)
    private ContentType contentType;

    @Column(name ="isSchedule", nullable = false, columnDefinition = "ENUM('TRUE', 'FALSE')")
    private IsSchedule isSchedule;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id" ,nullable = false)
    private UserEntity user;
}