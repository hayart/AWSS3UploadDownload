package hayk.harutyunyan.aws.s3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hayk.harutyunyan.aws.s3.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FileController.class)
class FileEntityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    FileService fileService;


    private static final String FILE_NAME = "fileName";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        //assertNotNull(tokenProvider);
    }

    @Test
    void shouldLoadUploadedFile() throws Exception {
        InputStream objectStream = new ByteArrayInputStream("test data".getBytes());
        /*when(userService.getAppUserId(anyString()))
                .thenReturn(UUID.randomUUID());*/
        when(fileService.save(any())).thenReturn(any());
        mockMvc
            .perform(get("/api/v1/file/test.txt").contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "excel.xlsx", "multipart/form-data", "test file content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/file").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200)).andReturn();
    }

}
