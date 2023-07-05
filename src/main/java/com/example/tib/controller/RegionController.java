package com.example.tib.controller;

import com.example.tib.entity.Region;
import com.example.tib.repository.RegionRepository;
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
@RequestMapping("/region")
public class RegionController {
    @Autowired
    RegionRepository regionRepository;

    @GetMapping("")
    public List<Region> regionList(){
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerRegion(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Region> optID = regionRepository.findById(idBuscar);
            if (optID.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("region",optID.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","No existe un region con el ID");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El ID no es un número");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearRegion(@RequestBody Region region){
        HashMap<String,String> hashMap = new HashMap<>();
        regionRepository.save(region);
        hashMap.put("idCreado", String.valueOf(region.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarRegion(@RequestBody Region region) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Region> optinalRegion = regionRepository.findById(region.getId());
        if(optinalRegion.isPresent()){
            Region regionOriginal = optinalRegion.get();

            if(region.getRegionDescription() != null){
                regionOriginal.setRegionDescription(region.getRegionDescription());
            }
            regionRepository.save(regionOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El region a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarRegion(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if(optionalRegion.isPresent()){
            try {
                regionRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el region");
            }
        }else{
            hashMap.put("status", "El region con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearRegion(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Region en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
