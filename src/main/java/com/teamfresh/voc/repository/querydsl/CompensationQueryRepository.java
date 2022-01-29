package com.teamfresh.voc.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamfresh.voc.model.Compensation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.teamfresh.voc.model.QCompensation.compensation;

@Repository
@RequiredArgsConstructor
public class CompensationQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Compensation> findAll(){
        return queryFactory.selectFrom(compensation)
                .join(compensation.voc)
                .fetchJoin()
                .fetch();
    }
}
