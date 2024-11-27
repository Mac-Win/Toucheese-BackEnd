package com.toucheese.studio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudio is a Querydsl query type for Studio
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudio extends EntityPathBase<Studio> {

    private static final long serialVersionUID = 2063863155L;

    public static final QStudio studio = new QStudio("studio");

    public final StringPath address = createString("address");

    public final ListPath<com.toucheese.conceptstudio.entity.ConceptStudio, com.toucheese.conceptstudio.entity.QConceptStudio> conceptStudios = this.<com.toucheese.conceptstudio.entity.ConceptStudio, com.toucheese.conceptstudio.entity.QConceptStudio>createList("conceptStudios", com.toucheese.conceptstudio.entity.ConceptStudio.class, com.toucheese.conceptstudio.entity.QConceptStudio.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Location> location = createEnum("location", Location.class);

    public final StringPath name = createString("name");

    public final StringPath notice = createString("notice");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<com.toucheese.product.entity.Product, com.toucheese.product.entity.QProduct> products = this.<com.toucheese.product.entity.Product, com.toucheese.product.entity.QProduct>createList("products", com.toucheese.product.entity.Product.class, com.toucheese.product.entity.QProduct.class, PathInits.DIRECT2);

    public final StringPath profileImage = createString("profileImage");

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    public final ListPath<com.toucheese.review.entity.Review, com.toucheese.review.entity.QReview> reviews = this.<com.toucheese.review.entity.Review, com.toucheese.review.entity.QReview>createList("reviews", com.toucheese.review.entity.Review.class, com.toucheese.review.entity.QReview.class, PathInits.DIRECT2);

    public final ListPath<com.toucheese.image.entity.StudioImage, com.toucheese.image.entity.QStudioImage> studioImages = this.<com.toucheese.image.entity.StudioImage, com.toucheese.image.entity.QStudioImage>createList("studioImages", com.toucheese.image.entity.StudioImage.class, com.toucheese.image.entity.QStudioImage.class, PathInits.DIRECT2);

    public QStudio(String variable) {
        super(Studio.class, forVariable(variable));
    }

    public QStudio(Path<? extends Studio> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudio(PathMetadata metadata) {
        super(Studio.class, metadata);
    }

}

