package com.example.jwtauth.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.jwtauth.domain.Token;
import com.example.jwtauth.service.PrimarySequenceService;

@Component
public class TokenListener extends AbstractMongoEventListener<Token> {

    @Autowired
    PrimarySequenceService primarySequenceService;

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Token> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue());
        }
    }
}
