package com.example.tib.controller;

import com.example.tib.entity.Territory;
import com.example.tib.repository.TerritoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/territory")
public class TerritoryController {

    @Autowired
    TerritoryRepository territoryRepository;

    @GetMapping("")
    public List<Territory> territoryList(){
        return territoryRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerTerritory(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        Optional<Territory> optID = territoryRepository.findById(id);
        if (optID.isPresent()) {
            hashMap.put("existe",true);
            hashMap.put("territory",optID.get());
        } else {
            hashMap.put("existe",false);
            hashMap.put("msg","No existe un territory con el ID");
        }
        return ResponseEntity.ok(hashMap);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearTerritory(@RequestBody Territory territory){
        HashMap<String,String> hashMap = new HashMap<>();
        territoryRepository.save(territory);
        hashMap.put("idCreado", String.valueOf(territory.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarTerritory(@RequestBody Territory territory) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Territory> optionalTerritory= territoryRepository.findById(territory.getId());
        if(optionalTerritory.isPresent()){
            Territory territoryOriginal = optionalTerritory.get();

            if(territory.getTerritoryDescription() != null){
                territoryOriginal.setTerritoryDescription(territory.getTerritoryDescription());
            }
            if(territory.getRegionID() != null){
                territoryOriginal.setRegionID(territory.getRegionID());
            }

            territoryRepository.save(territoryOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El territory a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarTerritory(@RequestParam("id") String id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Territory> optionalTerritory = territoryRepository.findById(id);
        if(optionalTerritory.isPresent()){
            try {
                territoryRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el territory");
            }
        }else{
            hashMap.put("status", "El territory con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearTerritory(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Territory en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
