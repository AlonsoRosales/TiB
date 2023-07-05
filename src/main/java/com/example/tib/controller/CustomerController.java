package com.example.tib.controller;

import com.example.tib.entity.Customer;
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
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("")
    public List<Customer> customerList(){
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerCustomer(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        Optional<Customer> optID = customerRepository.findById(id);
        if (optID.isPresent()) {
            hashMap.put("existe",true);
            hashMap.put("customer",optID.get());
        } else {
            hashMap.put("existe",false);
            hashMap.put("msg","No existe un customer con el ID");
        }
        return ResponseEntity.ok(hashMap);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearCustomer(@RequestBody Customer customer){
        HashMap<String,String> hashMap = new HashMap<>();
        customerRepository.save(customer);
        hashMap.put("idCreado", String.valueOf(customer.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }


    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarCustomer(@RequestBody Customer customer) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if(optionalCustomer.isPresent()){
            Customer customerOriginal = optionalCustomer.get();

            if(customer.getCompanyName() != null){
                customerOriginal.setCompanyName(customer.getCompanyName());
            }
            if(customer.getContactName() != null){
                customerOriginal.setContactName(customer.getContactName());
            }
            if(customer.getContactTitle() != null){
                customerOriginal.setContactTitle(customer.getContactTitle());
            }
            if(customer.getAddress() != null){
                customerOriginal.setAddress(customer.getAddress());
            }
            if(customer.getCity() != null){
                customerOriginal.setCity(customer.getCity());
            }
            if(customer.getRegion() != null){
                customerOriginal.setRegion(customer.getRegion());
            }
            if(customer.getPostalCode() != null){
                customerOriginal.setPostalCode(customer.getPostalCode());
            }
            if(customer.getCountry() != null){
                customerOriginal.setCountry(customer.getCountry());
            }
            if(customer.getPhone() != null){
                customerOriginal.setPhone(customer.getPhone());
            }
            if(customer.getFax() != null){
                customerOriginal.setFax(customer.getFax());
            }

            customerRepository.save(customerOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El customer a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarCustomer(@RequestParam("id") String id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            try {
                customerRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el customer");
            }
        }else{
            hashMap.put("status", "El customer con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearCustomer(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Customer en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }


}
