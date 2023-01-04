package hayk.harutyunyan.aws.s3.service;

import hayk.harutyunyan.aws.s3.dto.FileDTO;
import hayk.harutyunyan.aws.s3.exception.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public interface FileService {

    FileDTO save(MultipartFile file);

    FileDTO update(UUID documentId, FileDTO dto);

    FileDTO partialUpdate(UUID documentId, Map<String, Object> fields);

    Page<FileDTO> findAllDocument(Pageable pageable);

    FileDTO findByDocumentId(UUID documentId);

    void deleteDocumentById(UUID id);

    default Supplier<ResourceNotFound> doThrowException(String message) {
        return () -> new ResourceNotFound(message);
    }

    default ResourceNotFound doThrowOnDocumentNotFound(UUID id) {
        return new ResourceNotFound(String.format("Document not found for document id: %s", id));
    }
}
