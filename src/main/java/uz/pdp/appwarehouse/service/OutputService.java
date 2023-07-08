package uz.pdp.appwarehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.WareHouseRepository;

@Service
@RequiredArgsConstructor
public class OutputService {
    private final OutputRepository outputRepository;
    private final WareHouseRepository wareHouseRepository;
    //todo
    public HttpEntity<?> addOutput(OutputDto outputDto) {
        return null;
    }

    public HttpEntity<?> getAllOutput() {
        return null;
    }

    public HttpEntity<?> getOutput(Integer id) {
        return null;
    }

    public HttpEntity<?> updateOutput(Integer id, OutputDto outputDto) {
        return null;
    }

    public HttpEntity<?> deleted(Integer id) {
        return null;
    }
}
