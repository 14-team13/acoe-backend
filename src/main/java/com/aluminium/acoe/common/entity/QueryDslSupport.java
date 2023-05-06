package com.aluminium.acoe.common.entity;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

@Getter
@Repository
public abstract class QueryDslSupport {
    private final Class domainClass;
    private EntityManager entityManager;
    private Querydsl querydsl;
    private JPAQueryFactory queryFactory;

    public QueryDslSupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        JpaEntityInformation entityInformation = JpaEntityInformationSupport.getEntityInformation(this.domainClass, entityManager);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());
        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager, new PathBuilder(path.getType(), path.getMetadata()));
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(this.entityManager, "EntityManager must not be null!");
        Assert.notNull(this.querydsl, "Querydsl must not be null!");
        Assert.notNull(this.queryFactory, "QueryFactory must not be null!");
    }

    public <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery) {
        JPAQuery jpaQuery = (JPAQuery)contentQuery.apply(this.getQueryFactory());
        List<T> content = this.getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
        jpaQuery.getClass();
        return PageableExecutionUtils.getPage(content, pageable, jpaQuery::fetchCount);
    }
}
