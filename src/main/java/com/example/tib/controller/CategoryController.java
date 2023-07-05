package com.example.tib.controller;

import com.example.tib.entity.Category;
import com.example.tib.repository.CategoryRepository;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    public List<Category> categoryList(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerCategory(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Category> optID = categoryRepository.findById(idBuscar);
            if (optID.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("category",optID.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","No existe un category con el ID");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El ID no es un número");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearCategory(@RequestBody Category category){
        HashMap<String,String> hashMap = new HashMap<>();
        categoryRepository.save(category);
        hashMap.put("idCreado", String.valueOf(category.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarCategory(@RequestBody Category category) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if(optionalCategory.isPresent()){
            Category categoryOriginal = optionalCategory.get();

            if(category.getCategoryName() != null){
                categoryOriginal.setCategoryName(category.getCategoryName());
            }
            if(category.getDescription() != null){
                categoryOriginal.setDescription(category.getDescription());
            }
            if(category.getPicture() != null){
                categoryOriginal.setPicture(category.getPicture());
            }

            categoryRepository.save(categoryOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El category a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }


    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarCategory(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            try {
                categoryRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el category");
            }
        }else{
            hashMap.put("status", "El category con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearCategory(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Category en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
