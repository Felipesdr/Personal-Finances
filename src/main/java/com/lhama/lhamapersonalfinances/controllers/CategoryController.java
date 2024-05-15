package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.category.*;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidator;
import com.lhama.lhamapersonalfinances.model.services.CategoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    TokenService tokenService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity registerCategory(@Valid @RequestBody CategoryRegisterDTO categoryRegisterData, UriComponentsBuilder uriBuilder, @RequestHeader HttpHeaders headers){
            RequestValidator.validateNullDTO(categoryRegisterData);
            Long idUser = tokenService.recoverIdFromToken(headers);
            Category newCategory = categoryService.registerCategory(categoryRegisterData, idUser);

            Long newCategoryId = newCategory.getIdCategory();
            URI uri = uriBuilder.path("register/category/{id}").buildAndExpand(newCategoryId).toUri();

            return ResponseEntity.created(uri).body(new CategoryDTO(newCategory));

    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategoryByIdUserAndGlobal(@Valid @RequestHeader HttpHeaders headers){
        Long idUser = tokenService.recoverIdFromToken(headers);

        List<Category> temp = categoryService.getAllCategoryByIdUserAndGlobal(idUser);
        List<CategoryDTO> categorys = temp.stream().map(CategoryDTO::new).toList();

        return ResponseEntity.ok().body(categorys);
    }

    @PutMapping("update")
    @Transactional
    public ResponseEntity updateCategoryById(@Valid @RequestBody CategoryUpdateDTO categoryUpdateData, @RequestHeader HttpHeaders headers){
            RequestValidator.validateNullDTO(categoryUpdateData);
            Long idRequestingUser = tokenService.recoverIdFromToken(headers);
            Category updatedCategory = categoryService.updateCategoryById(categoryUpdateData, idRequestingUser);

            return ResponseEntity.ok(new CategoryDTO(updatedCategory));
    }

    @DeleteMapping("deactivate/{idCategory}")
    public ResponseEntity deactivateCategoryById(@Valid @PathVariable Long idCategory, @RequestHeader HttpHeaders headers){
            RequestValidator.validateNullDTO(idCategory);
            Long idRequestingUSer = tokenService.recoverIdFromToken(headers);
            categoryService.deactivateCategoryCreatedByUserById(idCategory, idRequestingUSer);

            return ResponseEntity.noContent().build();
    }


}
