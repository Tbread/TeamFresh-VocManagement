package com.teamfresh.voc.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamfresh.voc.model.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.teamfresh.voc.model.QDriver.driver;

@Repository
@RequiredArgsConstructor
public class DriverQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Driver> findById(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(driver)
                .where(driver.id.eq(id))
                .join(driver.company)
                .fetchJoin()
                .fetchOne());
    }
}
