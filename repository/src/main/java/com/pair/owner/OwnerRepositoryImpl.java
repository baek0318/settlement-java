package com.pair.owner;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.pair.owner.QOwner.owner;

public class OwnerRepositoryImpl implements OwnerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OwnerRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Owner> findOwner(String id, String name, String email) {
        return queryFactory.selectFrom(owner)
                .where(getId(id),
                getName(name),
                getEmail(email))
                .fetch();
    }

    private BooleanExpression getId(String id) {
        if(id == null || id.isEmpty()) {
            return null;
        }
        return owner.id.eq(Long.parseLong(id));
    }

    private BooleanExpression getName(String name) {
        if(name == null || name.isEmpty()) {
            return null;
        }
        return owner.name.eq(name);
    }

    private BooleanExpression getEmail(String email) {
        if(email == null || email.isEmpty()) {
            return null;
        }
        return owner.email.like("%"+email+"%");
    }
}
