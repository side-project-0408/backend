package com.example.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTokenBlackList is a Querydsl query type for TokenBlackList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTokenBlackList extends EntityPathBase<TokenBlackList> {

    private static final long serialVersionUID = -1151479357L;

    public static final QTokenBlackList tokenBlackList = new QTokenBlackList("tokenBlackList");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    public QTokenBlackList(String variable) {
        super(TokenBlackList.class, forVariable(variable));
    }

    public QTokenBlackList(Path<? extends TokenBlackList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTokenBlackList(PathMetadata metadata) {
        super(TokenBlackList.class, metadata);
    }

}

