package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    public static final Long ID = 2L;
    public static final String NAME = "Jimmy";

    CategoryService categoryService;

    ModelMapper modelMapper;

    @Mock
    CategoryRepository categoryRepository;


    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();

        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);

    }

    @Test
    public void testFindAllCategories() {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(3, categoryDTOS.size());
    }


    @Test
    public void testFindCategoryByName() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findCaregoryByName(NAME)).thenReturn(category);

        Category caregoryByName = categoryRepository.findCaregoryByName(NAME);

        assertEquals(NAME, caregoryByName.getName());
    }
}