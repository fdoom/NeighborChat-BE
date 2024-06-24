package kit.hackathon.nearbysns.domain.preference.repository;

import kit.hackathon.nearbysns.domain.preference.dto.PreferenceDetailResponse;
import kit.hackathon.nearbysns.domain.preference.dto.PreferenceSummaryResponse;
import kit.hackathon.nearbysns.domain.preference.entity.Preference;
import kit.hackathon.nearbysns.domain.preference.entity.PreferenceKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface PreferenceRepository extends Repository<Preference, PreferenceKey> {

    void deleteById(PreferenceKey id);

    @Query(nativeQuery = true, value = """
            insert into preference (account_id, article_id, preference_type, preference_update_at)
            values (:#{#preference.id.accountId}, :#{#preference.id.articleId}, :#{#preference.preferenceType.ordinal()}, now())
            on conflict (account_id, article_id) do update set preference_type = excluded.preference_type, preference_update_at = now()
            """)
    @Modifying
    Preference upsert(Preference preference);

    @Query("""
            select new kit.hackathon.nearbysns.domain.preference.dto.PreferenceSummaryResponse(
                p.id.articleId,
                sum(case p.preferenceType when kit.hackathon.nearbysns.domain.preference.entity.PreferenceType.LIKE then 1 else 0 end),
                sum(case p.preferenceType when kit.hackathon.nearbysns.domain.preference.entity.PreferenceType.DISLIKE then 1 else 0 end)
            )
            from Preference p join Article a
            where p.id.articleId = :articleId
            """)
    PreferenceSummaryResponse getPreferenceSummary(Long articleId);

    @Query("""
            select new kit.hackathon.nearbysns.domain.preference.dto.PreferenceDetailResponse(
                    p.id.accountId,
                    p.preferenceType
            )
            from Preference p
            where p.id.articleId = :articleId
            order by p.updateAt desc
            """
    )
    Page<PreferenceDetailResponse> getPreferenceDetail(Long articleId, Pageable pageable);

    Optional<Preference> findById(PreferenceKey id);

}
