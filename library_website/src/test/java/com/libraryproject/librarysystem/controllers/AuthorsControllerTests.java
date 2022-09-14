package com.libraryproject.librarysystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.libraryproject.librarysystem.domain.Authors;
import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.domain.Genre;
import com.libraryproject.librarysystem.repositories.AuthorsRepository;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorsControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Authors authors;

    @MockBean
    Model model;

    @MockBean
    AuthorsRepository authorsRepository;


    @Test
    public void aaa() throws Exception {
        Authors author = new Authors(6, "France", "Marcel Proust");
        when(authorsRepository.getById(anyInt())).thenReturn(author);
        mockMvc.perform(get("/viewauthor/edit/6"))
                .andExpect(MockMvcResultMatchers.view().name("editauthor.html"));
    }

    @Test
    public void bbb() throws Exception {
        mockMvc.perform(get("/viewauthor/edit/6"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The chosen author does not exist in the database."));
    }

    @Test
    public void ccc() throws Exception {
        Authors author = new Authors(6, "Marcel Proust", "France");
        MultiValueMap<String, String> formParams = toFormParams(author, new HashSet<>());
        mockMvc.perform(post("/editthisauthor")
                        .params(formParams))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/authorfdsslist"));
    }

    private MultiValueMap<String, String> toFormParams(Object o, Set<String> excludeFields) throws Exception {
        ObjectReader reader = objectMapper.readerFor(Map.class);
        Map<String, String> map = reader.readValue(objectMapper.writeValueAsString(o));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        map.entrySet().stream()
                .filter(e -> !excludeFields.contains(e.getKey()))
                .forEach(e -> multiValueMap.add(e.getKey(), (e.getValue() == null ? "" : String.valueOf(e.getValue()))));
        return multiValueMap;
    }

}
