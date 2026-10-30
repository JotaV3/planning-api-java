package io.github.jotav3.planning_api.entities.services;

import io.github.jotav3.planning_api.entities.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    CardRepository repository;
}
