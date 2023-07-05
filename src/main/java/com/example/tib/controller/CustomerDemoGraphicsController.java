package com.example.tib.controller;

import com.example.tib.entity.CustomerDemoGraphics;
import com.example.tib.repository.CustomerDemoGraphicsRepository;
import com.example.tib.repository.CustomerRepository;
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
@RequestMapping("/customertype")
public class CustomerDemoGraphicsController {
    @Autowired
    CustomerDemoGraphicsRepository customerDemoGraphicsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("")
    public List<CustomerDemoGraphics> customerDemoGraphicsList(){
        return customerDemoGraphicsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerCustomerDemoGraphics(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        Optional<CustomerDemoGraphics> optID = customerDemoGraphicsRepository.findById(id);
        if (optID.isPresent()) {
            hashMap.put("existe",true);
            hashMap.put("CustomerType",optID.get());
        } else {
            hashMap.put("existe",false);
            hashMap.put("msg","No existe un CustomerType con el ID");
        }
        return ResponseEntity.ok(hashMap);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearCustomerDemoGraphics(@RequestBody CustomerDemoGraphics customerDemoGraphics){
        HashMap<String,String> hashMap = new HashMap<>();
        customerDemoGraphicsRepository.save(customerDemoGraphics);
        hashMap.put("idCreado", String.valueOf(customerDemoGraphics.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarCustomerDemoGraphics(@RequestBody CustomerDemoGraphics customerDemoGraphics) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<CustomerDemoGraphics> optionalCustomerDemoGraphics= customerDemoGraphicsRepository.findById(customerDemoGraphics.getId());
        if(optionalCustomerDemoGraphics.isPresent()){
            CustomerDemoGraphics customerDemoGraphicsOrginal = optionalCustomerDemoGraphics.get();

            if(customerDemoGraphics.getCustomerDesc() != null){
                customerDemoGraphicsOrginal.setCustomerDesc(customerDemoGraphics.getCustomerDesc());
            }

            customerDemoGraphicsRepository.save(customerDemoGraphicsOrginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El CustomerType a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarCustomerDemoGraphics(@RequestParam("id") String id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<CustomerDemoGraphics> optionalCustomerDemoGraphics = customerDemoGraphicsRepository.findById(id);
        if(optionalCustomerDemoGraphics.isPresent()){
            try {
                customerDemoGraphicsRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el CustomerType");
            }
        }else{
            hashMap.put("status", "El CustomerType con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearCustomerDemoGraphics(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el CustomerType en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
