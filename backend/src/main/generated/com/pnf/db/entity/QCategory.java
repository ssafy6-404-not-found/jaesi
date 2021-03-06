package com.pnf.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1263070740L;

    public static final QCategory category = new QCategory("category");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath bannerImg = createString("bannerImg");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SetPath<LiveRoom, QLiveRoom> liveRooms = this.<LiveRoom, QLiveRoom>createSet("liveRooms", LiveRoom.class, QLiveRoom.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final SetPath<Product, QProduct> products = this.<Product, QProduct>createSet("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

