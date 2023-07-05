package com.example.tib.controller;

import com.example.tib.entity.Supplier;
import com.example.tib.repository.SupplierRepository;
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
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierRepository supplierRepository;

    @GetMapping("")
    public List<Supplier> supplierList(){
        return supplierRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerSupplier(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Supplier> optID = supplierRepository.findById(idBuscar);
            if (optID.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("supplier",optID.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","No existe un supplier con el ID");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El ID no es un número");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearSupplier(@RequestBody Supplier supplier){
        HashMap<String,String> hashMap = new HashMap<>();
        supplierRepository.save(supplier);
        hashMap.put("idCreado", String.valueOf(supplier.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarSupplier(@RequestBody Supplier supplier) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplier.getId());
        if(optionalSupplier.isPresent()){
            Supplier supplierOriginal = optionalSupplier.get();

            if(supplier.getCompanyName() != null){
                supplierOriginal.setCompanyName(supplier.getCompanyName());
            }
            if(supplier.getContactName() != null){
                supplierOriginal.setContactName(supplier.getContactName());
            }
            if(supplier.getContactTitle() != null){
                supplierOriginal.setContactTitle(supplier.getContactTitle());
            }
            if(supplier.getAddress() != null){
                supplierOriginal.setAddress(supplier.getAddress());
            }
            if(supplier.getCity() != null){
                supplierOriginal.setCity(supplier.getCity());
            }
            if(supplier.getRegion() != null){
                supplierOriginal.setRegion(supplier.getRegion());
            }
            if(supplier.getPostalCode() != null){
                supplierOriginal.setPostalCode(supplier.getPostalCode());
            }
            if(supplier.getCountry() != null){
                supplierOriginal.setCountry(supplier.getCountry());
            }
            if(supplier.getPhone() != null){
                supplierOriginal.setPhone(supplier.getPhone());
            }
            if(supplier.getFax() != null){
                supplierOriginal.setFax(supplier.getFax());
            }
            if(supplier.getHomePage() != null){
                supplierOriginal.setHomePage(supplier.getHomePage());
            }

            supplierRepository.save(supplierOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El supplier a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarSupplier(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if(optionalSupplier.isPresent()){
            try {
                supplierRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el supplier");
            }
        }else{
            hashMap.put("status", "El supplier con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearSupplier(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Supplier en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
