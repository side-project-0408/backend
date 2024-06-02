package com.example.backend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVerificationCode is a Querydsl query type for VerificationCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVerificationCode extends EntityPathBase<VerificationCode> {

    private static final long serialVersionUID = 363165031L;

    public static final QVerificationCode verificationCode1 = new QVerificationCode("verificationCode1");

    public final StringPath email = createString("email");

    public final StringPath verificationCode = createString("verificationCode");

    public QVerificationCode(String variable) {
        super(VerificationCode.class, forVariable(variable));
    }

    public QVerificationCode(Path<? extends VerificationCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVerificationCode(PathMetadata metadata) {
        super(VerificationCode.class, metadata);
    }

}

