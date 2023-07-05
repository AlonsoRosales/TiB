package com.example.tib.controller;

import com.example.tib.entity.Product;
import com.example.tib.repository.ProductRepository;
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
@RequestMapping("/product")
public class ProductoController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/prueba")
    public String index() {
        return "hola";
    }

    @GetMapping("/producto/buscar1")
    public Product buscarProductoForma1(@RequestParam("id") String id) {
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Product> byId = productRepository.findById(idBuscar);
            if (byId.isPresent()) {
                return byId.get();
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @GetMapping(value = "/producto/buscar2")
    public HashMap<String,Object> buscarProductoForma2(@RequestParam("id") String id) {
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Product> byId = productRepository.findById(idBuscar);
            if (byId.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("producto",byId.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","El producto con ese ID no existe");
            }
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El id no es un número!!!");
        }
        return hashMap;
    }

    @GetMapping("")
    public List<Product> listaProductos() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerProducto(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Product> byId = productRepository.findById(idBuscar);
            if (byId.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("producto",byId.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","El producto con ese ID no existe");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El id no es un número!!!");
           return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearProducto(@RequestBody Product product){
        HashMap<String,String> hashMap = new HashMap<>();

        productRepository.save(product);

        hashMap.put("idCreado", String.valueOf(product.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarProducto(@RequestBody Product product){
        HashMap<String,String> hashMap = new HashMap<>();

        productRepository.save(product);

        hashMap.put("status", "actualizado");
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping(value = "/parcial")
    public ResponseEntity<HashMap<String,String>> actualizarProductoFormaParcial(@RequestBody Product product){
        HashMap<String,String> hashMap = new HashMap<>();

        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if(optionalProduct.isPresent()){
            Product productoOriginal = optionalProduct.get();

            if(product.getProductName() != null)
                productoOriginal.setProductName(product.getProductName());
            if(product.getUnitPrice() != null)
                productoOriginal.setUnitPrice(product.getUnitPrice());
            //falta terminar los otros campos...

            productRepository.save(productoOriginal);
            hashMap.put("status", "actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","error");
            hashMap.put("msg","el producto a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }
    }

    @PutMapping("/parcialValidacionId/{id}")
    public ResponseEntity<HashMap<String,Object>> actualizarProductoFormaParcialValId(@RequestBody Product product,
                                                                                      @RequestParam("id") String idStr){
        HashMap<String,Object> hashMap = new HashMap<>();

        try{
            int id = Integer.parseInt(idStr);
            Optional<Product> optionalProduct = productRepository.findById(id);
            if(optionalProduct.isPresent()){
                Product productoOriginal = optionalProduct.get();

                if(product.getProductName() != null)
                    productoOriginal.setProductName(product.getProductName());
                if(product.getUnitPrice() != null)
                    productoOriginal.setUnitPrice(product.getUnitPrice());
                //falta terminar los otros campos...

                productRepository.save(productoOriginal);
                hashMap.put("status", "actualizado");
                return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
            }else{
                hashMap.put("status","error");
                hashMap.put("msg","el producto a actualizar no existe");
                return ResponseEntity.ok(hashMap);
            }
        }catch (NumberFormatException e){
            hashMap.put("existe",false);
            hashMap.put("msg","El id no es un número!!!");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarProducto(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            try {
                productRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "error-4000");
            }
        }else{
            hashMap.put("status", "error-3000");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearProducto(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","true");
        hashMap.put("msg","Debes enviar el producto como json");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
