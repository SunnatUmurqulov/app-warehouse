package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.MeasurementDto;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public HttpEntity<?> addMeasure(MeasurementDto measurementDto) {
        boolean exists = measurementRepository.existsByName(measurementDto.getName());
        if (exists) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("This measurement is exist", true));
        }
        Measurement measurement = new Measurement();
        measurement.setName(measurementDto.getName());
        measurementRepository.save(measurement);
        return ResponseEntity.ok(new ApiResponse("Successfully saved", true));
    }

    public List<Measurement> getAllMeasure() {
        return measurementRepository.findAllAndActiveTrue();
    }

    public HttpEntity<?> getMeasure(Integer id) {
        Optional<Measurement> measurementOptional = measurementRepository.findByIdAndActiveTrue(id);
        if (measurementOptional.isPresent()) {
            Measurement measurement = measurementOptional.get();
            return ResponseEntity.ok(measurement);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Measurement is not found", false));
    }

    public HttpEntity<ApiResponse> editMeasurement(Integer id, MeasurementDto measurementDto) {
        Optional<Measurement> measurementOptional = measurementRepository.findByIdAndActiveTrue(id);
        if (measurementOptional.isPresent()) {
            Measurement measurement = measurementOptional.get();
            measurement.setName(measurementDto.getName());
            measurementRepository.save(measurement);
            return ResponseEntity.ok(new ApiResponse("Successfully edited", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Measurement is not found", false));
    }

    public HttpEntity<?> deleteMeasurement(Integer id) {
        Optional<Measurement> measurementOptional = measurementRepository.findByIdAndActiveTrue(id);
        if (measurementOptional.isPresent()) {
            Measurement measurement = measurementOptional.get();
                measurement.setActive(false);
                measurementRepository.save(measurement);
                return ResponseEntity.ok(new ApiResponse("Successfully deleted", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Measurement not found", false));
    }
}
