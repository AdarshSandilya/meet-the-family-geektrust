package com.adarsh.geektrust.strategies;

import com.adarsh.geektrust.models.RelationshipType;

import java.util.HashMap;
import java.util.Map;

public class FetchRelationStrategyFactory {
    private final Map<RelationshipType, RelationStrategy> strategies = new HashMap<>();


    public FetchRelationStrategyFactory() {
        strategies.put(RelationshipType.PATERNAL_UNCLE, new PaternalUncleStrategy());
        strategies.put(RelationshipType.MATERNAL_UNCLE, new MaternalUncleStrategy());
        strategies.put(RelationshipType.PATERNAL_AUNT, new PaternalAuntStrategy());
        strategies.put(RelationshipType.MATERNAL_AUNT, new MaternalAuntStrategy());
        strategies.put(RelationshipType.SISTER_IN_LAW, new SisterInLawStrategy());
        strategies.put(RelationshipType.BROTHER_IN_LAW, new BrotherInLawStrategy());
        strategies.put(RelationshipType.SON, new SonStrategy());
        strategies.put(RelationshipType.DAUGHTER, new DaughterStrategy());
        strategies.put(RelationshipType.SIBLINGS, new SiblingsStrategy());
    }

    public RelationStrategy getStrategy(RelationshipType type) {
        return strategies.get(type);
    }
}
