package com.teamfresh.voc.repository.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamfresh.voc.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.teamfresh.voc.model.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<User> findByUsername(String username){
        return Optional.ofNullable(queryFactory.selectFrom(user)
                .where(user.username.eq(username))
                .join(user.driver)
                .join(user.company)
                .fetchJoin()
                .fetchOne());
    }
}
