package com.tsti.controller;


import com.tsti.entity.Client;
import com.tsti.entity.Passage;
import com.tsti.service.IPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/passage")
@RestController
public class PassageController {

    private IPassageService passageService;
    @Autowired
    public PassageController(IPassageService passageService) {
        this.passageService = passageService;
    }
    @PostMapping
    public ResponseEntity<?> createPassage(@RequestBody Passage passage) throws Exception {
        return passageService.create(passage);
    }
    @GetMapping("/{document}/{flightNumber}")
    public Optional<Passage> searchPassage(@PathVariable Long document,@PathVariable Long flightNumber) throws Exception {
        return passageService.search(document,flightNumber);
    }
}
