package hayk.harutyunyan.aws.s3.util;

import hayk.harutyunyan.aws.s3.exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class S3Util {

    private final S3Client s3Client;
    private final String s3Bucket;

    public boolean save(String id, MultipartFile file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Bucket)
                .key(id + "/" + file.getOriginalFilename())
                .build();
        try {
            InputStream inputStream = file.getInputStream();
            s3Client.putObject(request,
                    RequestBody.fromInputStream(inputStream, inputStream.available()));
            return true;
        } catch (IOException io){
            new ResourceNotFound("File not found");
        }
        return false;
    }

    public byte[] load(String id, String filename)  {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(s3Bucket)
                .key(id + "/" + filename)
                .build();

        try {
            InputStream objectStream =
                    s3Client.getObject(request,
                            ResponseTransformer.toInputStream());
            return objectStream.readAllBytes();
        } catch (IOException io){
            new ResourceNotFound("File not found");
        }
        return new byte[0];
    }

    public void deleteFile(String id, String fileName){
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(s3Bucket)
                .key(id + "/" + fileName)
                .build();
        s3Client.deleteObject(request);
    }
}
