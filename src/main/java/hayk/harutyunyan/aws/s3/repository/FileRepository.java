package hayk.harutyunyan.aws.s3.repository;


import hayk.harutyunyan.aws.s3.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}
