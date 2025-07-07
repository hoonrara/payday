package com.example.payday.user.repository;

import com.example.payday.user.domain.QUser;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.domain.QUserProfile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProfileCustomRepositoryImpl implements UserProfileCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserProfile> findAllSorted(String sortKey, Pageable pageable) {
        QUserProfile profile = QUserProfile.userProfile;
        QUser user = QUser.user;

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortKey, profile);

        List<UserProfile> content = queryFactory
                .selectFrom(profile)
                .leftJoin(profile.user, user).fetchJoin()
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(profile.count())
                .from(profile)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private OrderSpecifier<?> getOrderSpecifier(String sortKey, QUserProfile profile) {
        PathBuilder<UserProfile> path = new PathBuilder<>(UserProfile.class, "userProfile");

        return switch (sortKey) {
            case "name" -> path.getString("nickname").asc(); // 이름 가나다순
            case "view" -> path.getNumber("viewCount", Integer.class).desc(); // 조회수 내림차순
            case "date" -> path.getDateTime("createdAt", java.time.LocalDateTime.class).desc(); // 최신순
            default -> path.getDateTime("createdAt", java.time.LocalDateTime.class).desc(); // 기본값: 최신순
        };
    }

    @Override
    public Optional<UserProfile> findWithUserByUserId(Long userId) {
        QUserProfile profile = QUserProfile.userProfile;
        QUser user = QUser.user;

        UserProfile result = queryFactory
                .selectFrom(profile)
                .leftJoin(profile.user, user).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}