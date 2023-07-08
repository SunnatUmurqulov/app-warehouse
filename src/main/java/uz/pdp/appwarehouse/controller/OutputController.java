package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("/api/output")
@RequiredArgsConstructor
public class OutputController {
    private final OutputService outputService;

    @PostMapping
    public HttpEntity<?> addOutput(@RequestBody OutputDto outputDto){
        return outputService.addOutput(outputDto);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return outputService.getAllOutput();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOutput(@PathVariable Integer id){
        return outputService.getOutput(id);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> updateOutput(@PathVariable Integer id, @RequestBody OutputDto outputDto){
        return outputService.updateOutput(id,outputDto);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deletedOutput(@PathVariable Integer id){
        return outputService.deleted(id);
    }

}
