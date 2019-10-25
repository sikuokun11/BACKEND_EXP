package com.example.truyenfull.controller;

import com.example.truyenfull.model.Category;
import com.example.truyenfull.repository.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static com.example.truyenfull.util.ResponseUtil.returnListCategory;

@RestController
@RequestMapping("/api")
public class CategoryController  {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value = "/category",produces = "application/json")
    public String getAll() throws JsonProcessingException{
        return returnListCategory(categoryRepository.findAll()).toString();
    }

    @GetMapping(value = "/category_by_name",produces = "application/json")
    public String getByName(HttpServletRequest httpServletRequest) throws JsonProcessingException{
        String name = httpServletRequest.getParameter("name");
        List<Category> categories = categoryRepository.findByNameJPQLQuery(name);
        String response = returnListCategory(categories).toString();
        return response;
    }

    @PostMapping("/category")
    public Category create(@Valid @RequestBody Category category){
        return categoryRepository.save(category);
    }

    @GetMapping("/crawler")
    public boolean crawlerTest() throws IOException{
        Document document = Jsoup.connect("https://truyenfull.vn/").get();
        System.out.println("Title: "+document.title());

        Elements categories = document.select("div.row > div.col-md-4 > ul > li > a");
        for(Element category: categories){
//            System.out.println("Category name: "+category.text());
//            System.out.println("Link: "+category.attr("href"));
            Category newCategory =new Category();
            String[] temp= (category.attr("href").split("/"));
//            temp= temp[temp.length-1];
            String name = category.text();
            String urlName = temp[temp.length-1];
            newCategory.setName(name);
            newCategory.setUrlName(urlName);
           categoryRepository.save(newCategory);
        }
        return true;
    }
    @GetMapping("/getchapter")
    public boolean crawlerChapter() throws IOException{
        Document document = Jsoup.connect("https://truyenfull.vn/toi-cuong-vo-thuong-tong/").get();
        System.out.println("Title: "+document.title());
        Elements chapters = document.select("div.row > div.col-xs-12 >  ul.list-chapter > li > a");
        for(Element chapter: chapters){
            System.out.println("Chapter name: "+chapter.text());
            System.out.println("Link: "+chapter.attr("href"));
        }
        return true;
    }

}
