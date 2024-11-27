package com.toucheese.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 609606903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.toucheese.product.entity.QProduct product;

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    public final ListPath<com.toucheese.image.entity.ReviewImage, com.toucheese.image.entity.QReviewImage> reviewImages = this.<com.toucheese.image.entity.ReviewImage, com.toucheese.image.entity.QReviewImage>createList("reviewImages", com.toucheese.image.entity.ReviewImage.class, com.toucheese.image.entity.QReviewImage.class, PathInits.DIRECT2);

    public final com.toucheese.studio.entity.QStudio studio;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.toucheese.product.entity.QProduct(forProperty("product")) : null;
        this.studio = inits.isInitialized("studio") ? new com.toucheese.studio.entity.QStudio(forProperty("studio")) : null;
    }

}

