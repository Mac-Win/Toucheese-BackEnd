package com.toucheese.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductAddOption is a Querydsl query type for ProductAddOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductAddOption extends EntityPathBase<ProductAddOption> {

    private static final long serialVersionUID = -16003159L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductAddOption productAddOption = new QProductAddOption("productAddOption");

    public final StringPath add_option_price = createString("add_option_price");

    public final QAddOption addOption;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QProductAddOption(String variable) {
        this(ProductAddOption.class, forVariable(variable), INITS);
    }

    public QProductAddOption(Path<? extends ProductAddOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductAddOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductAddOption(PathMetadata metadata, PathInits inits) {
        this(ProductAddOption.class, metadata, inits);
    }

    public QProductAddOption(Class<? extends ProductAddOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addOption = inits.isInitialized("addOption") ? new QAddOption(forProperty("addOption")) : null;
    }

}

