package com.example.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProposal is a Querydsl query type for Proposal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProposal extends EntityPathBase<Proposal> {

    private static final long serialVersionUID = 199400625L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProposal proposal = new QProposal("proposal");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath message = createString("message");

    public final NumberPath<Long> proposalId = createNumber("proposalId", Long.class);

    public final QUser proposerUser;

    public final QUser recievedUser;

    public QProposal(String variable) {
        this(Proposal.class, forVariable(variable), INITS);
    }

    public QProposal(Path<? extends Proposal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProposal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProposal(PathMetadata metadata, PathInits inits) {
        this(Proposal.class, metadata, inits);
    }

    public QProposal(Class<? extends Proposal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.proposerUser = inits.isInitialized("proposerUser") ? new QUser(forProperty("proposerUser")) : null;
        this.recievedUser = inits.isInitialized("recievedUser") ? new QUser(forProperty("recievedUser")) : null;
    }

}

