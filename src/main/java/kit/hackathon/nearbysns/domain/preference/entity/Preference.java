package kit.hackathon.nearbysns.domain.preference.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Table(name = "preference")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Preference {

    @EmbeddedId
    private PreferenceKey id;

    @Column(name = "preference_type")
    @Enumerated(EnumType.ORDINAL)
    private PreferenceType preferenceType;

    @LastModifiedDate
    @Column(name = "preference_update_at")
    private Instant updateAt;

    public Preference(PreferenceKey id, PreferenceType preferenceType) {
        this.id = id;
        this.preferenceType = preferenceType;
    }


}
