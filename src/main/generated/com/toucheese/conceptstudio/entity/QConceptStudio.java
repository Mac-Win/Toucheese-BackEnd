package com.toucheese.conceptstudio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConceptStudio is a Querydsl query type for ConceptStudio
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConceptStudio extends EntityPathBase<ConceptStudio> {

    private static final long serialVersionUID = 1736983757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConceptStudio conceptStudio = new QConceptStudio("conceptStudio");

    public final com.toucheese.concept.entity.QConcept concept;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.toucheese.studio.entity.QStudio studio;

    public QConceptStudio(String variable) {
        this(ConceptStudio.class, forVariable(variable), INITS);
    }

    public QConceptStudio(Path<? extends ConceptStudio> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConceptStudio(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConceptStudio(PathMetadata metadata, PathInits inits) {
        this(ConceptStudio.class, metadata, inits);
    }

    public QConceptStudio(Class<? extends ConceptStudio> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.concept = inits.isInitialized("concept") ? new com.toucheese.concept.entity.QConcept(forProperty("concept")) : null;
        this.studio = inits.isInitialized("studio") ? new com.toucheese.studio.entity.QStudio(forProperty("studio")) : null;
    }

}

