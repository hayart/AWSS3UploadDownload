package hayk.harutyunyan.aws.s3.controller;

import hayk.harutyunyan.aws.s3.dto.FileDTO;
import hayk.harutyunyan.aws.s3.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/files")
public class FileController {

    private final FileService uploadService;

    @PostMapping
    public ResponseEntity<FileDTO> createFile(
            @RequestParam("file") MultipartFile file
    )  {
        return ResponseEntity
                .ok()
                .body(uploadService.save(file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileDTO> updateFile(
            @PathVariable(value = "id")
            UUID id,
            @RequestBody
                    FileDTO dto
    ) {
        return ResponseEntity
                .ok()
                .body(uploadService.update(id, dto));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<FileDTO> partialUpdate(
            @PathVariable(value = "id", required = false)
            UUID id,
            @RequestBody
                    Map<String, Object> map
    ) {
        return ResponseEntity
                .ok()
                .body(uploadService.partialUpdate(id, map));
    }


    @GetMapping
    public ResponseEntity<Page<FileDTO>> getAll(@PageableDefault(size = 20)
                                                                 @SortDefault.SortDefaults({
                                                                         @SortDefault(sort = "title", direction = DESC)
                                                                 })
                                                                         Pageable pageable) {
        return ResponseEntity.ok().body(uploadService.findAllDocument(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFile(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(uploadService.findByDocumentId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        uploadService.deleteDocumentById(id);
        return ResponseEntity.noContent().build();
    }
}
