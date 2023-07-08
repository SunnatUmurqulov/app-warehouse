package uz.pdp.appwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.MeasurementDto;
import uz.pdp.appwarehouse.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/api/measurement")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;

    @PostMapping
    public HttpEntity<?> addMeasure(@RequestBody MeasurementDto measurementDto){
        return measurementService.addMeasure(measurementDto);
    }

    @GetMapping
    public List<Measurement> getAllMeasure(){
      return measurementService.getAllMeasure();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getMeasure(@PathVariable Integer id){
        return measurementService.getMeasure(id);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editMeasurement(@PathVariable Integer id, @RequestBody MeasurementDto measurementDto){
        return measurementService.editMeasurement(id,measurementDto);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteMeasurement(@PathVariable Integer id){
        return measurementService.deleteMeasurement(id);
    }

}
