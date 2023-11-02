package pl.dk.usermanager.domain.confirmationToken;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;
import pl.dk.usermanager.domain.user.User;

@Entity
@Table(name = "confirmation_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @UUID
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
