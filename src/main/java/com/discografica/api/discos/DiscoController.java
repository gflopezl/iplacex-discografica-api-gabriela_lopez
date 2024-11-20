package com.discografica.api.discos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {
   
    @Autowired
    private IDiscoRepository discoRepo;

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleInsertDiscoRequest(@RequestBody Disco disco) {
       // Cambiado a findDiscosByIdArtista para que coincida con la propiedad idArtista
       if (!discoRepo.findDiscosByIdArtista(disco.idArtista).isEmpty()) {
           return new ResponseEntity<>(null, HttpStatus.FOUND);
       }
       Disco temp = discoRepo.insert(disco);
       return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscoRequest() {
        List<Disco> discos = discoRepo.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    @GetMapping(
        value = "/Disco/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> temp = discoRepo.findById(id);

        if (!temp.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(), HttpStatus.OK);
    }
    
    @GetMapping(
        value = "/artista/{id}/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
       List<Disco> discos = discoRepo.findDiscosByIdArtista(idArtista);

       // Cambiado a discos.isEmpty()
       if (discos.isEmpty()) {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
       
       return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}
