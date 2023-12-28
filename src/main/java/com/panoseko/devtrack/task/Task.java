package com.panoseko.devtrack.task;

import com.panoseko.devtrack.image.Image;
import com.panoseko.devtrack.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task")
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "task")
    private Image image;


    public Task(String title, String description, Status status, LocalDate createdAt, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
    }

}

