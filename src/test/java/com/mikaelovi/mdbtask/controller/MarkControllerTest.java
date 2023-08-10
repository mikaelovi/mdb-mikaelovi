package com.mikaelovi.mdbtask.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikaelovi.mdbtask.config.AppTestConfig;
import com.mikaelovi.mdbtask.dto.MarkDto;
import com.mikaelovi.mdbtask.exception.EntityNotFoundException;
import com.mikaelovi.mdbtask.exception.ErrorPayload;
import com.mikaelovi.mdbtask.exception.ResponseExceptionHandler;
import com.mikaelovi.mdbtask.service.MarkService;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MarkController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AppTestConfig.class, ResponseExceptionHandler.class})
class MarkControllerTest {

    @MockBean
    private MarkService markService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;


    private final static Integer somedummystudentid = 192349234;
    private final static Integer somedummymarkid = 1492;
    private final static Integer somedummymarkid2 = 2669;

    private final static String somedummysubjectname = "dummysubject1";
    private final static String anotherdummysubjectname = "dummysubject2";

    @BeforeEach
    void setUp() {
        ((MockServletContext) mockMvc.getDispatcherServlet().getServletContext()).setContextPath("/api");
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void given_student_id_does_not_exist_when_getMarksForStudent_controller_is_called_then_return_a_400_response_code() throws Exception {
        when(markService.getMarksForStudent(somedummystudentid)).thenThrow(new EntityNotFoundException("student", String.valueOf(somedummystudentid)));

        final var result = mockMvc.perform(get("/api/mark/all/{studentId}", somedummystudentid)
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(res -> assertThat(
                        res.getResolvedException(),
                        CoreMatchers.instanceOf(EntityNotFoundException.class)))
                .andReturn();

        final var errorPayload = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ErrorPayload>() {
        });

        Assertions.assertThat(errorPayload.getCode()).isEqualTo(String.valueOf(HttpStatus.NOT_FOUND.value()));
        Assertions.assertThat(errorPayload.getMsg()).isEqualTo("student with id " + somedummystudentid + " not found");
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void given_student_id_exists_when_getMarksForStudent_controller_is_called_then_succeed() throws Exception {
        List<MarkDto> markDtos = new ArrayList<>();
        markDtos.add(new MarkDto(somedummymarkid, LocalDateTime.now(), 70, somedummystudentid, 9211));

        when(markService.getMarksForStudent(somedummystudentid)).thenReturn(markDtos);

        mockMvc.perform(get("/api/mark/all/{studentId}", somedummystudentid)
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void given_student_id_exists_and_student_has_no_mark_when_getMarksForStudent_controller_is_called_then_succeed_with_empty_markList() throws Exception {
        when(markService.getMarksForStudent(somedummystudentid)).thenReturn(new ArrayList<>());

        final var result = mockMvc.perform(get("/api/mark/all/{studentId}", somedummystudentid)
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        final var responsePayload = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        Assertions.assertThat(responsePayload).isEmpty();
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void given_student_id_exists_when_getStudentMarksForAllSubjects_controller_is_called_then_succeed() throws Exception {
        List<MarkDto> markDtos = new ArrayList<>();
        markDtos.add(new MarkDto(somedummymarkid2, LocalDateTime.now(), 70, somedummystudentid, 9211));
        markDtos.add(new MarkDto(somedummymarkid2, LocalDateTime.now(), 70, somedummystudentid, 5978));

        List<Map<String, MarkDto>> studentSubjectMarks = new ArrayList<>();

        Map<String, MarkDto> markDtoMap = new HashMap<>();

        markDtoMap.put(somedummysubjectname, markDtos.get(0));
        markDtoMap.put(anotherdummysubjectname, markDtos.get(1));

        studentSubjectMarks.add(markDtoMap);

        when(markService.getStudentMarksForAllSubjects(somedummystudentid)).thenReturn(studentSubjectMarks);

        final var result = mockMvc.perform(get("/api/mark/all-subjects/{studentId}", somedummystudentid)
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        final var responsePayload = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ArrayList>() {
        });

        Assertions.assertThat(responsePayload).hasSize(1);
    }


}