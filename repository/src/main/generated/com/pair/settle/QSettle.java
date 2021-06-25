package com.pair.settle;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSettle is a Querydsl query type for Settle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSettle extends EntityPathBase<Settle> {

    private static final long serialVersionUID = -1283873561L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSettle settle = new QSettle("settle");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.pair.owner.QOwner owner;

    public QSettle(String variable) {
        this(Settle.class, forVariable(variable), INITS);
    }

    public QSettle(Path<? extends Settle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSettle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSettle(PathMetadata metadata, PathInits inits) {
        this(Settle.class, metadata, inits);
    }

    public QSettle(Class<? extends Settle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new com.pair.owner.QOwner(forProperty("owner")) : null;
    }

}

