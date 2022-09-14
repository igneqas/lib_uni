package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.Authors;
import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.domain.Genre;
import com.libraryproject.librarysystem.repositories.AuthorsRepository;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Books books;

    @MockBean
    Model model;

    @MockBean
    BooksRepository booksRepository;

    @MockBean
    AuthorsRepository authorsRepository;


    @Test
    public void shouldReturnRedirectToBooksListWhenBookWithValidParametersIsSavedInTheDB() throws Exception {
        Authors author = new Authors(6, "France", "Marcel Proust");
        List<Authors> authorsList = new ArrayList<>();
        authorsList.add(author);
        Books book = new Books("Don Quixote", authorsList, Genre.Fantasy, "2-6845-891253", "2020", "Nice book", "", Availability.AVAILABLE, new ArrayList<>());
        when(booksRepository.save(any())).thenReturn(book);
        when(authorsRepository.findById(anyInt())).thenReturn(Optional.of(author));
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "Fantasy").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/bookslist"));
    }

    @Test
    public void shouldReturnErrorPageWhenUploadingDuplicateBookRecord() throws Exception {
        Authors author = new Authors(6, "France", "Marcel Proust");
        Books book = new Books();
        book.setIsbn("2-6845-891253");
        book.setTitle("The Lord of The Rings");
        when(authorsRepository.findById(anyInt())).thenReturn(Optional.of(author));
        when(booksRepository.save(any())).thenReturn(book);
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "Fantasy").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "Book with this ISBN already exists. Record not created."));
    }

    @Test
    public void shouldReturnErrorPageWhenTitleIsEmpty() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "").param("url", "")
                        .param("author", "6").param("genre", "Fantasy").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "The book title should be at least 1 symbol and no more than 100 symbols long."));
    }

    @Test
    public void shouldReturnErrorPageWhenTitleIsOver100Characters() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "sfdghksdfljkgbjsdfjgsdfgbsdfjkgdjsfgbjdsfdgbjsdfgbjsdfgbjksdfgbjsdfgbjksdfgbsdfgbjkdsfgbjdsfgbjkdssdfgbjkjksdfgbjkvncxkvndnvjkdkvjndjndfgjnsdfgnsdfgsdfngjnsdfgjnsdfgsdfgjnsdfgsdfgnjsdfgsdfgfsdgnjsdfgnfsdngnsdgd")
                        .param("url", "")
                        .param("author", "6").param("genre", "Fantasy").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "The book title should be at least 1 symbol and no more than 100 symbols long."));
    }

    @Test
    public void shouldReturnErrorPageWhenAuthorIsNotInDB() throws Exception {
        when(authorsRepository.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "-5").param("genre", "Fantasy").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "The chosen author does not exist in the database."));
    }

    @Test
    public void shouldReturnErrorPageWhenGenreIsNotDefinedInSystem() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "YA").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "The book record should have a genre, that is defined in the system."));
    }

    @Test
    public void shouldReturnErrorPageWhenISBNisShorterThan10Characters() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "2-64-562")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's ISBN code should be between 10 and 18 symbols and be made up of numbers and dashes."));
    }

    @Test
    public void shouldReturnErrorPageWhenISBNisLongerThan18Characters() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "2-64-56246545465-56")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's ISBN code should be between 10 and 18 symbols and be made up of numbers and dashes."));
    }

    @Test
    public void shouldReturnErrorPageWhenISBNisIncorrectlyFormatted() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "f56/-45623-ag")
                        .param("releaseYear", "2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's ISBN code should be between 10 and 18 symbols and be made up of numbers and dashes."));
    }

    @Test
    public void shouldReturnErrorPageWhenReleaseYearIsGreaterThanCurrentYear() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2023").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's year of release shouldn't greater than the current year."));
    }

    @Test
    public void shouldReturnErrorPageWhenReleaseYearIsNotAValidNumber() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "2-6845-891253")
                        .param("releaseYear", "20fdsa").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's year of release should be a positive integer."));
    }

    @Test
    public void shouldReturnErrorPageWhenReleaseYearIsNotAPositiveInteger() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "2-6845-891253")
                        .param("releaseYear", "-2020").param("description", "Nice book"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's year of release should be a positive integer."));
    }

    @Test
    public void shouldReturnErrorPageWhenDescriptionIsLongerThan500Characters() throws Exception {
        mockMvc.perform(get("/addthisnewbook").param("title", "Don Quixote").param("url", "")
                        .param("author", "6").param("genre", "SciFi").param("isbn", "2-6845-891253")
                        .param("releaseYear", "2020").param("description", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus"))
                .andExpect(MockMvcResultMatchers.view().name("error.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage",
                        "The book's description should not be longer than 500 characters."));
    }

}
