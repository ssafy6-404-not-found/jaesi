package com.pnf.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 853547859L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath baseAddress = createString("baseAddress");

    public final QUser buyer;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath detailAddress = createString("detailAddress");

    public final DatePath<java.time.LocalDate> dueDate = createDate("dueDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isPayComplete = createBoolean("isPayComplete");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProduct product;

    public final StringPath receiverName = createString("receiverName");

    public final StringPath receiverPhone = createString("receiverPhone");

    public final DateTimePath<java.time.LocalDateTime> regTime = createDateTime("regTime", java.time.LocalDateTime.class);

    public final QReview review;

    public final QUser seller;

    public final BooleanPath state = createBoolean("state");

    public final NumberPath<Integer> zipcode = createNumber("zipcode", Integer.class);

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new QUser(forProperty("buyer")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
        this.seller = inits.isInitialized("seller") ? new QUser(forProperty("seller")) : null;
    }

}

