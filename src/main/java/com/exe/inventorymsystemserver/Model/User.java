package com.exe.inventorymsystemserver.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "user_type_id", nullable = true)
    private Long userTypeId;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference  // Break the bidirectional relationship
    private UserLogs userLogsId;

    public User(Long userId, String userName, String password, boolean isActive, Long userTypeId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.userTypeId = userTypeId;
    }

    public User() {

    }
}
