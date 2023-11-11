package az.iktlab.learnlink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @MapsId(value = "roleId")
    private Role role;

    @ManyToOne
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id")
    private User user;



}
