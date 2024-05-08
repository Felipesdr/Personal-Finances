package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.entities.category.Category;
import com.lhama.lhamapersonalfinances.entities.category.CategoryDTO;
import com.lhama.lhamapersonalfinances.entities.category.CategoryRegisterDTO;
import com.lhama.lhamapersonalfinances.repositorys.CategoryRespository;
import com.lhama.lhamapersonalfinances.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity registerCategory(@RequestBody CategoryRegisterDTO categoryRegisterData, UriComponentsBuilder uriBuilder){

        if (categoryRegisterData == null){
            return ResponseEntity.badRequest().build();
        }

        Category newCategory = categoryService.registerCategory(categoryRegisterData);
        Integer newCategoryId = newCategory.getIdCategory();

        URI uri = uriBuilder.path("register/category/{id}").buildAndExpand(newCategoryId).toUri();

        return ResponseEntity.created(uri).body(new CategoryDTO(newCategory));
    }

    @GetMapping
    public ResponseEntity getAllCategorys() {

    }
}
