package com.toucheese.image.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudioImage is a Querydsl query type for StudioImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudioImage extends EntityPathBase<StudioImage> {

    private static final long serialVersionUID = 1605662487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudioImage studioImage = new QStudioImage("studioImage");

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.toucheese.studio.entity.QStudio studio;

    public final StringPath url = createString("url");

    public QStudioImage(String variable) {
        this(StudioImage.class, forVariable(variable), INITS);
    }

    public QStudioImage(Path<? extends StudioImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudioImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudioImage(PathMetadata metadata, PathInits inits) {
        this(StudioImage.class, metadata, inits);
    }

    public QStudioImage(Class<? extends StudioImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studio = inits.isInitialized("studio") ? new com.toucheese.studio.entity.QStudio(forProperty("studio")) : null;
    }

}

