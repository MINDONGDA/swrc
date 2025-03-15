package SWRC.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder  // ✅ 상속 관계에서 SuperBuilder 사용!
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @Column(nullable = false)
    private String sportType;
}
