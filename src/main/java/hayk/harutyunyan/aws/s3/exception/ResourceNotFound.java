package hayk.harutyunyan.aws.s3.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String str){
        log.error(str);
    }
}
