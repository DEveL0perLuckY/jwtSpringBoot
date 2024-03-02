package com.example.jwtauth.repos;

import com.example.jwtauth.domain.Tree;
import com.example.jwtauth.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@SuppressWarnings("null")

@Component
public class TreeListener extends AbstractMongoEventListener<Tree> {

    private final PrimarySequenceService primarySequenceService;

    public TreeListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Tree> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue());
        }
    }

}
