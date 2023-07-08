package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.service.InputService;

@RestController
@RequestMapping("/api/input")
@RequiredArgsConstructor
public class InputController {

    private final InputService inputService;

    @PostMapping
    public HttpEntity<?> warehouseInput(@RequestBody InputDto inputDto) {
        return inputService.warehouseInput(inputDto);
    }

    @GetMapping
    public HttpEntity<?> getAllInput(){
        return inputService.getAllInput();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getInput(@PathVariable Integer id){
        return inputService.getInput(id);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> updateInput(@PathVariable Integer id, @RequestBody InputDto inputDto){
        return inputService.updateInput(id,inputDto);
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deletedInput(@PathVariable Integer id){
        return inputService.deleted(id);
    }
}
