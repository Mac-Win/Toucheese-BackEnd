package com.toucheese.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 392390477L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath product_description = createString("product_description");

    public final StringPath product_image = createString("product_image");

    public final StringPath product_name = createString("product_name");

    public final NumberPath<Integer> product_price = createNumber("product_price", Integer.class);

    public final ListPath<ProductAddOption, QProductAddOption> products_add_options = this.<ProductAddOption, QProductAddOption>createList("products_add_options", ProductAddOption.class, QProductAddOption.class, PathInits.DIRECT2);

    public final ListPath<com.toucheese.review.entity.Review, com.toucheese.review.entity.QReview> reviews = this.<com.toucheese.review.entity.Review, com.toucheese.review.entity.QReview>createList("reviews", com.toucheese.review.entity.Review.class, com.toucheese.review.entity.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Integer> standard = createNumber("standard", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

