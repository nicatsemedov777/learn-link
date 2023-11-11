package az.iktlab.learnlink.entity;

import az.iktlab.learnlink.entity.generator.IdGenerator;
import az.iktlab.learnlink.util.DateHelper;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    @Column(name = "create_date")
    private Date createDate;

    private String username;
    private String email;

    @Column(name = "born_date")
    private Date bornDate;

    private BigDecimal balance;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns ={@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns ={@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    Set<Role> roles;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


    @PrePersist
    private void setCreateDateIfNull() {
        if (createDate == null) {
            this.createDate = DateHelper.now();
        }
    }
}
