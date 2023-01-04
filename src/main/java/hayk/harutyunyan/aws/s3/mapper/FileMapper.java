package hayk.harutyunyan.aws.s3.mapper;

import hayk.harutyunyan.aws.s3.dto.FileDTO;
import hayk.harutyunyan.aws.s3.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface FileMapper {

    List<FileDTO> map(List<FileEntity> documents);

    FileDTO toDTO(FileEntity entity);

    FileEntity toEntity(FileDTO dto);

    void updateDocumentFromDTO(FileDTO dto, @MappingTarget FileEntity entity);
}
