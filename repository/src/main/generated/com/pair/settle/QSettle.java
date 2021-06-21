package com.pair.settle;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSettle is a Querydsl query type for Settle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSettle extends EntityPathBase<Settle> {

    private static final long serialVersionUID = -1283873561L;

    public static final QSettle settle = new QSettle("settle");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ownerId = createNumber("ownerId", Long.class);

    public QSettle(String variable) {
        super(Settle.class, forVariable(variable));
    }

    public QSettle(Path<? extends Settle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSettle(PathMetadata metadata) {
        super(Settle.class, metadata);
    }

}

