package kg.attractor.movie_review.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cast_member")
public class CastMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cast_member")
//    private List<MovieCastMember> movieCastMembers;
}
