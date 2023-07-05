package com.example.tib.controller;

import com.example.tib.entity.Shipper;
import com.example.tib.repository.ShipperRepository;
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
@RequestMapping("/shipper")
public class ShipperController {
    @Autowired
    ShipperRepository shipperRepository;

    @GetMapping("")
    public List<Shipper> listarShippers() {
        return shipperRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerShipper(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Shipper> optID = shipperRepository.findById(idBuscar);
            if (optID.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("shipper",optID.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","No existe un shipper con el ID");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El ID no es un número");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearShipper(@RequestBody Shipper shipper){
        HashMap<String,String> hashMap = new HashMap<>();
        shipperRepository.save(shipper);
        hashMap.put("idCreado", String.valueOf(shipper.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarShipper(@RequestBody Shipper shipper) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Shipper> optionalShipper = shipperRepository.findById(shipper.getId());
        if(optionalShipper.isPresent()){
            Shipper shipperOriginal = optionalShipper.get();

            if(shipper.getCompanyName() != null){
                shipperOriginal.setCompanyName(shipper.getCompanyName());
            }
            if(shipper.getPhone() != null){
                shipperOriginal.setPhone(shipper.getPhone());
            }

            shipperRepository.save(shipperOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El shipper a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarShipper(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Shipper> optionalShipper = shipperRepository.findById(id);
        if(optionalShipper.isPresent()){
            try {
                shipperRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el shipper");
            }
        }else{
            hashMap.put("status", "El shipper con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearShipper(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Shipper en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }


}
