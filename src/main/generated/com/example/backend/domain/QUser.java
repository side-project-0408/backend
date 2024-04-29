package com.example.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1435773686L;

    public static final QUser user = new QUser("user");

    public final StringPath accessToken = createString("accessToken");

    public final BooleanPath alarmStatus = createBoolean("alarmStatus");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath employmentStatus = createBoolean("employmentStatus");

    public final NumberPath<Integer> favoriteCount = createNumber("favoriteCount", Integer.class);

    public final StringPath importantQuestion = createString("importantQuestion");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final StringPath links = createString("links");

    public final StringPath nickname = createString("nickname");

    public final StringPath position = createString("position");

    public final ListPath<Project, QProject> projects = this.<Project, QProject>createList("projects", Project.class, QProject.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath softSkill = createString("softSkill");

    public final StringPath techStack = createString("techStack");

    public final StringPath userFileUrl = createString("userFileUrl");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final SetPath<Integer, NumberPath<Integer>> userLike = this.<Integer, NumberPath<Integer>>createSet("userLike", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final StringPath year = createString("year");

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

