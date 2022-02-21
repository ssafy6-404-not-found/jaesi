package com.pnf.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -213456967L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath baseAddress = createString("baseAddress");

    public final SetPath<Bookmark, QBookmark> bookmarks = this.<Bookmark, QBookmark>createSet("bookmarks", Bookmark.class, QBookmark.class, PathInits.DIRECT2);

    public final SetPath<Orders, QOrders> buys = this.<Orders, QOrders>createSet("buys", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final SetPath<Review, QReview> buysreview = this.<Review, QReview>createSet("buysreview", Review.class, QReview.class, PathInits.DIRECT2);

    public final StringPath certifiedToken = createString("certifiedToken");

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath email = createString("email");

    public final BooleanPath emailCertified = createBoolean("emailCertified");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SetPath<LiveRoom, QLiveRoom> liveRooms = this.<LiveRoom, QLiveRoom>createSet("liveRooms", LiveRoom.class, QLiveRoom.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final SetPath<Product, QProduct> products = this.<Product, QProduct>createSet("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final StringPath profilePhoto = createString("profilePhoto");

    public final SetPath<ChatRoom, QChatRoom> receives = this.<ChatRoom, QChatRoom>createSet("receives", ChatRoom.class, QChatRoom.class, PathInits.DIRECT2);

    public final SetPath<Orders, QOrders> sells = this.<Orders, QOrders>createSet("sells", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final SetPath<Review, QReview> sellsreview = this.<Review, QReview>createSet("sellsreview", Review.class, QReview.class, PathInits.DIRECT2);

    public final SetPath<ChatRoom, QChatRoom> sends = this.<ChatRoom, QChatRoom>createSet("sends", ChatRoom.class, QChatRoom.class, PathInits.DIRECT2);

    public final NumberPath<Integer> socialType = createNumber("socialType", Integer.class);

    public final StringPath zipcode = createString("zipcode");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

