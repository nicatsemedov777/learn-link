package az.iktlab.learnlink.entity;

import az.iktlab.learnlink.entity.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE courses SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Course {

    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    private String name;

    @Column(name = "create_date")
    private Date createDate;

    private String subject;


    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "teacher_id" )
    private User teacher;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;
}
