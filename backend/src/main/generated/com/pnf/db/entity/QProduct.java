package com.pnf.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1587814977L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final SetPath<Bookmark, QBookmark> bookmarks = this.<Bookmark, QBookmark>createSet("bookmarks", Bookmark.class, QBookmark.class, PathInits.DIRECT2);

    public final QCategory category;

    public final StringPath content = createString("content");

    public final BooleanPath custom = createBoolean("custom");

    public final NumberPath<Long> hit = createNumber("hit", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath img = createString("img");

    public final SetPath<Orders, QOrders> orders = this.<Orders, QOrders>createSet("orders", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> productionPeriod = createNumber("productionPeriod", Integer.class);

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    public final DateTimePath<java.time.LocalDateTime> regTime = createDateTime("regTime", java.time.LocalDateTime.class);

    public final SetPath<Review, QReview> reviews = this.<Review, QReview>createSet("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final NumberPath<Long> sellCount = createNumber("sellCount", Long.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public final QUser user;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

