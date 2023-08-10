package com.mikaelovi.mdbtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikaelovi.mdbtask.config.AppTestConfig;
import com.mikaelovi.mdbtask.exception.ResponseExceptionHandler;
import com.mikaelovi.mdbtask.service.TeacherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TeacherController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AppTestConfig.class, ResponseExceptionHandler.class})
class TeacherControllerTest {

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final static Integer somedummyteacherid = 4587963;

    @BeforeEach
    void setUp() {
        ((MockServletContext) mockMvc.getDispatcherServlet().getServletContext()).setContextPath("/api");
    }


    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void given_that_teacher_has_students_when_getStudentCountForTeacher_controller_is_called_then_succeed_return_student_count()
            throws Exception {

        when(teacherService.countStudents(somedummyteacherid)).thenReturn(2);

        final var result = mockMvc.perform(get("/api/teacher/count/{teacherId}", somedummyteacherid)
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        final var responseBody = mapper.readValue(result.getResponse().getContentAsString(), Integer.class);

        Assertions.assertThat(responseBody).isPositive().isEqualTo(2);
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void given_that_teacher_has_no_student_when_getStudentCountForTeacher_controller_is_called_then_succeed_return_zero_student_count()
            throws Exception {

        when(teacherService.countStudents(somedummyteacherid)).thenReturn(0);

        final var result = mockMvc.perform(get("/api/teacher/count/{teacherId}", somedummyteacherid)
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        final var responseBody = mapper.readValue(result.getResponse().getContentAsString(), Integer.class);

        Assertions.assertThat(responseBody).isZero();
    }
}