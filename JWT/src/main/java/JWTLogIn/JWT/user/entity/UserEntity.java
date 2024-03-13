package JWTLogIn.JWT.user.entity;

import JWTLogIn.JWT.user.dto.UserDTO;
import JWTLogIn.JWT.user.entity.Enum.Level;
import JWTLogIn.JWT.user.entity.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor @AllArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 점직적 증가
    private Long id; // 기본 키.

    @Column(nullable = false, unique = true)
    private String studentId; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false, unique = true, length = 25)
    private String email;

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private Status status; // 재학/휴학 상태

    @Column(nullable = false, length = 9)
    private String semester; // 학년, 학기(3글자로 설정)

    @Column(nullable = false, unique = true, length = 13)
    private String phoneNumber; // 전화번호(13글자로 설정)

    @Column(length = 7)
    private Level level;

    // 연관매핑: 일대다
    // 참조 당하는 엔티티에서 사용
    // mappedBy - 양방향 매핑 시 어떤 변수로 참조되었는지 알려주는
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JoinColumn(name="student_id")
//    private List<PostEntity> posts = new ArrayList<>(); // 연관매핑 아직 공부해야함.

    public static UserDTO toUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .studentId(userEntity.getStudentId())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .status(userEntity.getStatus())
                .semester(userEntity.getSemester())
                .phoneNumber(userEntity.getPhoneNumber())
                .level(userEntity.getLevel())
                .build();
    }

    /*
    * param : 암호화할 인코더 클레스
    * return : 변경된 유저의 entity(비밀번호)
    */
    public UserEntity hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    } // 비밀번호 암호화 과정. entity의 기존 비밀번호를 암호화시킴.


    /*
    * param1 : 암호화전 비밀번호
    * param2 : 암호화에 사용된 class
    * return : 같은지 다른지. true false
    * */
    public Boolean checkPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    } // 비밀번호 확인
}
