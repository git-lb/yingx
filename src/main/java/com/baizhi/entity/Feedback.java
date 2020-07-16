package com.baizhi.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yingx_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    @Id
    private String id;

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "feedback_date")
    private Date feedbackDate;

    @Column(name = "user_id")
    private String userId;
}