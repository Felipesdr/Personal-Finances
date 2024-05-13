package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.infra.exception.ValidationExceptionDTO;
import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.category.*;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidations;
import com.lhama.lhamapersonalfinances.model.services.CategoryService;
import jakarta.transaction.Transactional;
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
    public ResponseEntity registerCategory(@RequestBody CategoryRegisterDTO categoryRegisterData, UriComponentsBuilder uriBuilder, @RequestHeader HttpHeaders headers){
        try {
            RequestValidations.validateNullDTO(categoryRegisterData);
            Integer idUser = tokenService.recoverIdFromToken(headers);
            Category newCategory = categoryService.registerCategory(categoryRegisterData, idUser);

            Integer newCategoryId = newCategory.getIdCategory();

            URI uri = uriBuilder.path("register/category/{id}").buildAndExpand(newCategoryId).toUri();

            return ResponseEntity.created(uri).body(new CategoryDTO(newCategory));
        }catch (ValidationException e){
            return ResponseEntity.badRequest().body(new ValidationExceptionDTO(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategoryByIdUserAndGlobal(@RequestHeader HttpHeaders headers){
        Integer idUser = tokenService.recoverIdFromToken(headers);

        List<Category> temp = categoryService.getAllCategoryByIdUserAndGlobal(idUser);
        List<CategoryDTO> categorys = temp.stream().map(CategoryDTO::new).toList();

        return ResponseEntity.ok().body(categorys);
    }

    @PutMapping("update")
    @Transactional
    public ResponseEntity updateCategoryById(@RequestBody CategoryUpdateDTO categoryUpdateData, @RequestHeader HttpHeaders headers){
        try{
            RequestValidations.validateNullDTO(categoryUpdateData);
            Integer idRequestingUser = tokenService.recoverIdFromToken(headers);
            Category updatedCategory = categoryService.updateCategoryById(categoryUpdateData, idRequestingUser);
            return ResponseEntity.ok(new CategoryDTO(updatedCategory));
        } catch (ValidationException e){
            return ResponseEntity.badRequest().body(new ValidationExceptionDTO(e.getMessage()));
        }
    }

    @DeleteMapping("deactivate/{idCategory}")
    public ResponseEntity deactivateCategoryById(@PathVariable Integer idCategory, @RequestHeader HttpHeaders headers){
        try{
            RequestValidations.validateNullDTO(idCategory);
            Integer idRequestingUSer = tokenService.recoverIdFromToken(headers);
            categoryService.deactivateCategoryCreatedByUserById(idCategory, idRequestingUSer);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e){
            return ResponseEntity.badRequest().body(new ValidationExceptionDTO(e.getMessage()));
        }
    }
}
