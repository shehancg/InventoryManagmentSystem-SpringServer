package com.exe.inventorymsystemserver.Model;

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
    private User user;

    @Column(name = "login_datetime")
    private LocalDateTime loginDateTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "jwtoken")
    private String jwToken;
}
