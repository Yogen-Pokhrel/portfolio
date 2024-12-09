package com.portfolio.auth.user.entity;

import com.portfolio.auth.address.entity.Address;
import com.portfolio.core.helpers.validators.ValidEnum;
import com.portfolio.auth.role.entity.Role;
import com.portfolio.core.common.BaseEntity;
import com.portfolio.core.common.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="\"user\"")
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First Name should not be blank")
    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email should not be blank")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String phone;
    private String image;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    @Column(nullable = true, length = 20)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = UserStatus.class, message = "Please provide a valid status")
    @NotNull(message = "User status cannot be blank")
    public UserStatus status;
}
