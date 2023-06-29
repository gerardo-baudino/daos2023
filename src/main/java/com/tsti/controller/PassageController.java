package com.tsti.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.controller.error.MessageError;
import com.tsti.entity.Passage;
import com.tsti.exception.ExceptionCustom;
import com.tsti.service.IPassageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/passage")
@RestController
@Validated
public class PassageController {

    private IPassageService passageService;

    @Autowired
    public PassageController(IPassageService passageService) {
        this.passageService = passageService;
    }

    @PostMapping
    public ResponseEntity<?> createPassage(@Valid @RequestBody Passage passage, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatterError(result));
        }
        try {
        ResponseEntity<?> responseEntity = passageService.create(passage);
        Link selfLink = Link.of("/passage/" + passage.getDocument() + "/" + passage.getFlightNumber());
        EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(resourceWithLink);
      } catch (
        ExceptionCustom exceptionCustom){
        throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
      }
    }

    @GetMapping("/{document}/{flightNumber}")
    public ResponseEntity<?> searchPassage(@PathVariable Long document, @PathVariable Long flightNumber) throws Exception {
        Optional<Passage> passageOptional = passageService.search(document, flightNumber);

        if (passageOptional.isPresent()) {
            Passage passage = passageOptional.get();

            Link selfLink = Link.of("/passage/" + document + "/" + flightNumber);
            EntityModel<Passage> resourceWithLink = EntityModel.of(passage, selfLink);

            return ResponseEntity.ok(resourceWithLink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private String formatterError(BindingResult result) throws JsonProcessingException {
        List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
            Map<String, String> error= new HashMap<>();
            error.put(err.getField(),err.getDefaultMessage() );
            return error;
        }).collect(Collectors.toList());
        MessageError e = new MessageError();
        e.setCode("01");
        e.setMenssage(errors);

        ObjectMapper maper = new ObjectMapper();
        String json = maper.writeValueAsString(e);
        return json;
    }
}
