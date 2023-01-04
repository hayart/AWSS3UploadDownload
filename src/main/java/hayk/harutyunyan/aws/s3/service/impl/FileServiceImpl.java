package hayk.harutyunyan.aws.s3.service.impl;

import hayk.harutyunyan.aws.s3.dto.FileDTO;
import hayk.harutyunyan.aws.s3.entity.FileEntity;
import hayk.harutyunyan.aws.s3.mapper.FileMapper;
import hayk.harutyunyan.aws.s3.repository.FileRepository;
import hayk.harutyunyan.aws.s3.service.FileService;
import hayk.harutyunyan.aws.s3.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final S3Util s3Util;

    @Override
    public FileDTO save(MultipartFile file) {
       log.info("Adding new file");

        if(s3Util.save("userid", file)) {
            FileEntity entity = new FileEntity();
            entity.setTitle(file.getOriginalFilename());
            entity.setDocumentSize(file.getSize());
            return fileMapper.toDTO(fileRepository.save(entity));
        }

       return new FileDTO();
    }

    @Override
    public FileDTO update(UUID id, FileDTO dto) {
        log.info("update existing document");
        FileEntity document = fileRepository.findById(id).orElseThrow(() -> doThrowOnDocumentNotFound(id));
        dto.setId(id);
        fileMapper.updateDocumentFromDTO(dto, document);
        return fileMapper.toDTO(fileRepository.save(document));
    }

    @Override
    public FileDTO partialUpdate(UUID id, Map<String, Object> fields) {
        log.info("partial update existing document");
        FileEntity document = fileRepository.findById(id).orElseThrow(() -> doThrowOnDocumentNotFound(id));
        fields.forEach((key, value)-> {
            Field field = ReflectionUtils.findField(FileEntity.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, document, value);
        });
        FileEntity saveDocument = fileRepository.save(document);
        return fileMapper.toDTO(saveDocument);
    }

    @Override
    public Page<FileDTO> findAllDocument(Pageable pageable) {
        log.info("get all existing document");
        Page<FileEntity> allPageableEntity = fileRepository.findAll(pageable);
        List<FileDTO> dtoList = fileMapper.map(allPageableEntity.getContent());
        return new PageImpl<>(dtoList, pageable, allPageableEntity.getTotalElements());
    }

    @Override
    public FileDTO findByDocumentId(UUID id) {
        log.info("get file by id");
        FileEntity document = fileRepository.findById(id).orElseThrow(() -> doThrowOnDocumentNotFound(id));
        return fileMapper.toDTO(document);
    }

    @Override
    public void deleteDocumentById(UUID id) {
        log.info("delete document by id");
        FileEntity document = fileRepository.findById(id).orElseThrow(() -> doThrowOnDocumentNotFound(id));
        fileRepository.delete(document);
    }

}
