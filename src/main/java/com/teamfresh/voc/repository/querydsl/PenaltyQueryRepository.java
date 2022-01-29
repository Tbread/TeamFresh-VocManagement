package com.teamfresh.voc.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamfresh.voc.model.Penalty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.teamfresh.voc.model.QPenalty.penalty;

@Repository
@RequiredArgsConstructor
public class PenaltyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Penalty> findAllByDriverId(Long id) {
        return queryFactory.selectFrom(penalty)
                .where(penalty.driver.id.eq(id))
                .join(penalty.driver)
                .join(penalty.compensation)
                .fetchJoin()
                .fetch();
    }

    public Optional<Penalty> findById(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(penalty)
                .where(penalty.id.eq(id))
                .join(penalty.driver)
                .join(penalty.compensation)
                .fetchJoin()
                .fetchOne());
    }
    
}
