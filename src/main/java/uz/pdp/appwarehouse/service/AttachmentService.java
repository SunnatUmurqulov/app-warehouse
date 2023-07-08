package uz.pdp.appwarehouse.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appwarehouse.entity.ApiResponse;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public ApiResponse uploadFile(MultipartFile multipartFile) {
        Attachment attachment = Attachment.builder()
                .name(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .build();
        attachmentRepository.save(attachment);


        AttachmentContent attachmentContent = AttachmentContent.builder()
                .bytes(multipartFile.getBytes())
                .attachment(attachment)
                .build();
        attachmentContentRepository.save(attachmentContent);

        return new ApiResponse("File successfully saved", true, attachment.getId());
    }

    public HttpEntity<?> getFile(Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            Optional<AttachmentContent> attachmentId = attachmentContentRepository.findByAttachment_Id(id);
            if (attachmentId.isPresent()) {
                AttachmentContent attachmentContent = attachmentId.get();
                response.setHeader("Content-Disposition", "attachment; filename = \"" + attachment.getName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
        return ResponseEntity.ok(new ApiResponse("File", true));
    }
}
