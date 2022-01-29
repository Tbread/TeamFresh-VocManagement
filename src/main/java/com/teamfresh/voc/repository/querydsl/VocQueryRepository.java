package com.teamfresh.voc.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamfresh.voc.model.Voc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.teamfresh.voc.model.QVoc.voc;

@RequiredArgsConstructor
@Repository
public class VocQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Voc> findAll() {
        return queryFactory.selectFrom(voc)
                .join(voc.compensation)
                .fetchJoin()
                .fetch();
    }
}
