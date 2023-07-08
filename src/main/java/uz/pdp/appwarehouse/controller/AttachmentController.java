package uz.pdp.appwarehouse.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.service.AttachmentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public HttpEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        ApiResponse apiResponse = attachmentService.uploadFile(multipartFile);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/download/{id}")
    public HttpEntity<?> getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        return attachmentService.getFile(id, response);
    }
}
