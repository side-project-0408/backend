package com.example.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = 283338106L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> deadline = createDate("deadline", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> favoriteCount = createNumber("favoriteCount", Integer.class);

    public final StringPath importantQuestion = createString("importantQuestion");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final StringPath projectFileUrl = createString("projectFileUrl");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final SetPath<Long, NumberPath<Long>> projectLike = this.<Long, NumberPath<Long>>createSet("projectLike", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final StringPath recruitment = createString("recruitment");

    public final ListPath<Recruit, QRecruit> recruits = this.<Recruit, QRecruit>createList("recruits", Recruit.class, QRecruit.class, PathInits.DIRECT2);

    public final StringPath softSkill = createString("softSkill");

    public final StringPath techStack = createString("techStack");

    public final StringPath title = createString("title");

    public final QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

