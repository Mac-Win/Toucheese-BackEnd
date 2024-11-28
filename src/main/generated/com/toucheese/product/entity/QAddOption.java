package com.toucheese.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddOption is a Querydsl query type for AddOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddOption extends EntityPathBase<AddOption> {

    private static final long serialVersionUID = 64662356L;

    public static final QAddOption addOption = new QAddOption("addOption");

    public final StringPath addOptionName = createString("addOptionName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAddOption(String variable) {
        super(AddOption.class, forVariable(variable));
    }

    public QAddOption(Path<? extends AddOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddOption(PathMetadata metadata) {
        super(AddOption.class, metadata);
    }

}

