package ru.igojig.taskmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.igojig.taskmanagementsystem.enums.TaskPriority;
import ru.igojig.taskmanagementsystem.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "header")
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "taskStatus")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "taskPriority")
    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;



//    private List<String> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "implementer_id")
    private User implementer;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

}
