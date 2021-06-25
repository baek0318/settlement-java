package com.pair.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderTable is a Querydsl query type for OrderTable
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderTable extends EntityPathBase<OrderTable> {

    private static final long serialVersionUID = -941789241L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderTable orderTable = new QOrderTable("orderTable");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<OrderDetail, QOrderDetail> orderDetails = this.<OrderDetail, QOrderDetail>createList("orderDetails", OrderDetail.class, QOrderDetail.class, PathInits.DIRECT2);

    public final com.pair.owner.QOwner owner;

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QOrderTable(String variable) {
        this(OrderTable.class, forVariable(variable), INITS);
    }

    public QOrderTable(Path<? extends OrderTable> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderTable(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderTable(PathMetadata metadata, PathInits inits) {
        this(OrderTable.class, metadata, inits);
    }

    public QOrderTable(Class<? extends OrderTable> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new com.pair.owner.QOwner(forProperty("owner")) : null;
    }

}

