package com.exe.inventorymsystemserver.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_logs")
@Data
public class UserLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_logs_id")
    private Long userLogsId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference  // Break the bidirectional relationship
    private User user;

    @Column(name = "login_datetime")
    private LocalDateTime loginDateTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "jwtoken")
    private String jwToken;

    public UserLogs(Long userLogsId, User user, LocalDateTime loginDateTime, boolean isActive, String jwToken) {
        this.userLogsId = userLogsId;
        this.user = user;
        this.loginDateTime = loginDateTime;
        this.isActive = isActive;
        this.jwToken = jwToken;
    }

    public UserLogs() {
    }
}
