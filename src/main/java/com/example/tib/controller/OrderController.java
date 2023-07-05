package com.example.tib.controller;
import com.example.tib.entity.Order;
import com.example.tib.repository.OrderRepository;
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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    /*** Servicio que permite listar todos los objetos Orders***/
    @GetMapping("")
    public List<Order> listarOrders(){
        return orderRepository.findAll();
    }

    /*** Servicio que permite listar los atributos de un objeto Order recibiendo como parámetro su id***/
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerOrder(@PathVariable("id") String id){
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            int idBuscar = Integer.parseInt(id);
            Optional<Order> optID = orderRepository.findById(idBuscar);
            if (optID.isPresent()) {
                hashMap.put("existe",true);
                hashMap.put("order",optID.get());
            } else {
                hashMap.put("existe",false);
                hashMap.put("msg","No existe un order con el ID");
            }
            return ResponseEntity.ok(hashMap);
        } catch (NumberFormatException e) {
            hashMap.put("existe",false);
            hashMap.put("msg","El ID no es un número");
            return ResponseEntity.badRequest().body(hashMap);
        }
    }

    /*** Servicio que permite crear un objeto Order, recibiendo en el body, un JSON con los atributos a tener***/
    @PostMapping("")
    public ResponseEntity<HashMap<String,String>> crearOrder(@RequestBody Order order){
        HashMap<String,String> hashMap = new HashMap<>();
        orderRepository.save(order);
        hashMap.put("idCreado", String.valueOf(order.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
    }

    /*** Servicio que permite actualizar los atributos de un objeto Order, recibiendo en el body,
     un JSON con los atributos a modificar***/
    @PutMapping("")
    public ResponseEntity<HashMap<String,String>> actualizarOrder(@RequestBody Order order) {
        HashMap<String, String> hashMap = new HashMap<>();
        Optional<Order> optionalOrder = orderRepository.findById(order.getId());
        if(optionalOrder.isPresent()){
            Order orderOriginal = optionalOrder.get();

            if(order.getCustomerID() != null){
                orderOriginal.setCustomerID(order.getCustomerID());
            }
            if(order.getEmployeeID() != null){
                orderOriginal.setEmployeeID(order.getEmployeeID());
            }
            if(order.getOrderDate() != null){
                orderOriginal.setOrderDate(order.getOrderDate());
            }
            if(order.getRequiredDate() != null){
                orderOriginal.setRequiredDate(order.getRequiredDate());
            }
            if(order.getShippedDate() != null){
                orderOriginal.setShippedDate(order.getShippedDate());
            }
            if(order.getShipVia() != null){
                orderOriginal.setShipVia(order.getShipVia());
            }
            if(order.getFreight() != null){
                orderOriginal.setFreight(order.getFreight());
            }
            if(order.getShipName() != null){
                orderOriginal.setShipName(order.getShipName());
            }
            if(order.getShipAddress() != null){
                orderOriginal.setShipAddress(order.getShipAddress());
            }
            if(order.getShipCity() != null){
                orderOriginal.setShipCity(order.getShipCity());
            }
            if(order.getShipRegion() != null){
                orderOriginal.setShipRegion(order.getShipRegion());
            }
            if(order.getShipPostalCode() != null){
                orderOriginal.setShipPostalCode(order.getShipPostalCode());
            }
            if(order.getShipCountry() != null){
                orderOriginal.setShipCountry(order.getShipCountry());
            }

            orderRepository.save(orderOriginal);
            hashMap.put("status", "Actualizado");
            return ResponseEntity.status(HttpStatus.CREATED).body(hashMap);
        }else{
            hashMap.put("status","Error");
            hashMap.put("msg","El order a actualizar no existe");
            return ResponseEntity.ok(hashMap);
        }

    }

    /*** Servicio que permite eliminar un objeto Order recibiendo como parámetro su id***/
    @DeleteMapping("")
    public ResponseEntity<HashMap<String,String>> borrarOrder(@RequestParam("id") int id){
        HashMap<String,String> hashMap = new HashMap<>();
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            try {
                orderRepository.deleteById(id);
                hashMap.put("status","ok");
            }catch (Exception e){
                hashMap.put("status", "Error al borrar el order");
            }
        }else{
            hashMap.put("status", "El order con ese ID no existe");
        }
        return ResponseEntity.ok(hashMap);
    }

    /***Excepción que se lanza al ocurrir un error al crear un Order***/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionarErrorCrearOrder(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("error","True");
        hashMap.put("msg","Debes enviar el Order en formato JSON");
        return ResponseEntity.badRequest().body(hashMap);
    }


}
