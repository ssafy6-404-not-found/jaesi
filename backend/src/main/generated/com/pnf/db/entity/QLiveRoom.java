package com.pnf.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLiveRoom is a Querydsl query type for LiveRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLiveRoom extends EntityPathBase<LiveRoom> {

    private static final long serialVersionUID = 104047829L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLiveRoom liveRoom = new QLiveRoom("liveRoom");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCategory category;

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> hit = createNumber("hit", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath password = createString("password");

    public final NumberPath<Long> realTimeViewers = createNumber("realTimeViewers", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public final QUser user;

    public QLiveRoom(String variable) {
        this(LiveRoom.class, forVariable(variable), INITS);
    }

    public QLiveRoom(Path<? extends LiveRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLiveRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLiveRoom(PathMetadata metadata, PathInits inits) {
        this(LiveRoom.class, metadata, inits);
    }

    public QLiveRoom(Class<? extends LiveRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

