package com.sarrou.taskmanagementapi.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
 public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(name = "email")
    private String email;

   @Column(name = "username")
    private String username;

    @CreationTimestamp
    private LocalDateTime createdDate;

    User(String email){
        this.email = email;
    }

}
