package com.teamfresh.voc.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamfresh.voc.model.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.teamfresh.voc.model.QCompany.company;

@RequiredArgsConstructor
@Repository
public class CompanyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Company> findByCompanyName(String companyName){
        return Optional.ofNullable(queryFactory.selectFrom(company)
        .where(company.companyName.eq(companyName))
        .fetchOne());
    }

    public Optional<Company> findById(Long id){
        return Optional.ofNullable(queryFactory.selectFrom(company)
        .where(company.id.eq(id))
        .fetchOne());
    }
}
