package com.pnf.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QExpert is a Querydsl query type for Expert
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QExpert extends EntityPathBase<Expert> {

    private static final long serialVersionUID = 573154968L;

    public static final QExpert expert = new QExpert("expert");

    public final QUser _super = new QUser(this);

    //inherited
    public final StringPath baseAddress = _super.baseAddress;

    //inherited
    public final SetPath<Bookmark, QBookmark> bookmarks = _super.bookmarks;

    public final StringPath businessName = createString("businessName");

    public final StringPath businessNumber = createString("businessNumber");

    public final StringPath businessType = createString("businessType");

    //inherited
    public final SetPath<Orders, QOrders> buys = _super.buys;

    //inherited
    public final SetPath<Review, QReview> buysreview = _super.buysreview;

    //inherited
    public final StringPath certifiedToken = _super.certifiedToken;

    //inherited
    public final StringPath detailAddress = _super.detailAddress;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final BooleanPath emailCertified = _super.emailCertified;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final SetPath<LiveRoom, QLiveRoom> liveRooms = _super.liveRooms;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath nickname = _super.nickname;

    //inherited
    public final StringPath password = _super.password;

    //inherited
    public final StringPath phone = _super.phone;

    //inherited
    public final SetPath<Product, QProduct> products = _super.products;

    //inherited
    public final StringPath profilePhoto = _super.profilePhoto;

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    //inherited
    public final SetPath<ChatRoom, QChatRoom> receives = _super.receives;

    //inherited
    public final SetPath<Orders, QOrders> sells = _super.sells;

    //inherited
    public final SetPath<Review, QReview> sellsreview = _super.sellsreview;

    //inherited
    public final SetPath<ChatRoom, QChatRoom> sends = _super.sends;

    //inherited
    public final NumberPath<Integer> socialType = _super.socialType;

    //inherited
    public final StringPath zipcode = _super.zipcode;

    public QExpert(String variable) {
        super(Expert.class, forVariable(variable));
    }

    public QExpert(Path<? extends Expert> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExpert(PathMetadata metadata) {
        super(Expert.class, metadata);
    }

}

