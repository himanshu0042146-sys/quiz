package com.ajackus.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "correct")
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
