package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.payload.WareHouseDto;
import uz.pdp.appwarehouse.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WareHouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public HttpEntity<?> addWareHouse(@RequestBody WareHouseDto wareHouseDto){
        return warehouseService.addWareHouse(wareHouseDto);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneWareHouse(@PathVariable Integer id){
        return warehouseService.getOneWareHouse(id);
    }

    @GetMapping()
    public HttpEntity<?> getAll(){
        return warehouseService.getAll();
    }

    @PutMapping("/change/{id}")
    public HttpEntity<?> updateWareHouse(@PathVariable Integer id, @RequestBody WareHouseDto wareHouseDto){
        return warehouseService.update(id, wareHouseDto);
    }

    @DeleteMapping("/deleted/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return warehouseService.deleteWareHouse(id);
    }
}
