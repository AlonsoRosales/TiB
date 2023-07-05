package com.example.tib.controller;

import com.example.tib.entity.Employee;
import com.example.tib.repository.EmployeeRepository;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    public List<Employee> listarEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerEmployee(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Employee> optID = employeeRepository.findById(idBuscar);
            if (optID.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("employee",optID.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","No existe un employee con el ID");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El ID no es un número");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearEmployee(@RequestBody Employee employee){
        HashMap<String,String> hashMap = new HashMap<>();
        employeeRepository.save(employee);
        hashMap.put("idCreado", String.valueOf(employee.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarEmployee(@RequestBody Employee employee) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
        if(optionalEmployee.isPresent()){
            Employee employeeOriginal = optionalEmployee.get();

            if(employee.getFirstName() != null){
                employeeOriginal.setFirstName(employee.getFirstName());
            }
            if(employee.getLastName() != null){
                employeeOriginal.setLastName(employee.getLastName());
            }
            if(employee.getTitle() != null){
                employeeOriginal.setTitle(employee.getTitle());
            }
            if(employee.getTitleOfCourtesy() != null){
                employeeOriginal.setTitleOfCourtesy(employee.getTitleOfCourtesy());
            }
            if(employee.getBirthDate() != null){
                employeeOriginal.setBirthDate(employee.getBirthDate());
            }
            if(employee.getHireDate() != null){
                employeeOriginal.setHireDate(employee.getHireDate());
            }
            if(employee.getAddress() != null){
                employeeOriginal.setAddress(employee.getAddress());
            }
            if(employee.getCity() != null){
                employeeOriginal.setCity(employee.getCity());
            }
            if(employee.getRegion() != null){
                employeeOriginal.setRegion(employee.getRegion());
            }
            if(employee.getPostalCode() != null){
                employeeOriginal.setPostalCode(employee.getPostalCode());
            }
            if(employee.getCountry() != null){
                employeeOriginal.setCountry(employee.getCountry());
            }
            if(employee.getHomePhone() != null){
                employeeOriginal.setHomePhone(employee.getHomePhone());
            }
            if(employee.getExtension() != null){
                employeeOriginal.setExtension(employee.getExtension());
            }
            if(employee.getNotes() != null){
                employeeOriginal.setNotes(employee.getNotes());
            }
            if(employee.getSalary() != null){
                employeeOriginal.setSalary(employee.getSalary());
            }

            employeeRepository.save(employeeOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El employee a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarEmployee(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            try {
                employeeRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el employee");
            }
        }else{
            hashMap.put("status", "El employee con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearEmployee(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Employee en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }

}
