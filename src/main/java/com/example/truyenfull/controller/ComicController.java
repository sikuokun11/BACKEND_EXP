package com.example.truyenfull.controller;

import com.example.truyenfull.model.Category;
import com.example.truyenfull.model.Chaper;
import com.example.truyenfull.model.Comic;
import com.example.truyenfull.repository.CategoryRepository;
import com.example.truyenfull.repository.ChapterRepository;
import com.example.truyenfull.repository.ComicRepsitory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.truyenfull.util.ResponseUtil.returnListComics;

@RestController
@RequestMapping("/api")
public class ComicController {
    @Autowired
    ComicRepsitory comicRepsitory;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @GetMapping(value = "/comic",produces = "application/json")
    public String getAllComic(){

        return returnListComics(comicRepsitory.findAll()).toString();
    }


    @PostMapping("/comic")
    public Comic createComic(@Valid @RequestBody Comic comic){

      List<Chaper> chapers = comic.getChapers();
        for (Chaper chaper:chapers){
            chaper.setComic(comic);
            chapterRepository.save(chaper);
        }

        return comicRepsitory.save(comic);
    }

    @GetMapping("/crawlerComic")
    public boolean CrawlerComic() throws IOException {
        List<Category> list = categoryRepository.findAll();
        for (Category category : list) {
            Document document = Jsoup.connect("https://truyenfull.vn/the-loai/"+category.getUrlName()+"/").get();
            System.out.println("Title: " + document.title());
            Elements comics = document.select("div.row > div.col-xs-7 > div > h3 > a");
            for (Element comic:comics){
                Comic createComic = new Comic();
                String[] temp = comic.attr("href").split("/");
//                System.out.println("Name: "+ comic.text());
                createComic.setName(comic.text());
//                System.out.println("Link: "+comic.attr("href"));
//                System.out.println("UrlComicName: "+temp[temp.length-1]);
                Document document1 = Jsoup.connect("https://truyenfull.vn/"+temp[temp.length-1]+"/").get();
//                System.out.println("Title Comic : "+document1.title());
                Elements comics1 = document1.select("div.info > div > a");
                for(Element comic1: comics1){
                    List<Category> categoryList = new ArrayList<>();
                   String itemProp = comic1.attr("itemprop");
                   if(itemProp.equals("author")){
//                       System.out.println("Author: "+comic1.text());
                       createComic.setAuthor(comic1.text());

                   }
                   else if(itemProp.equals("genre")){

                       for(Category category1:list){
                       if(comic1.text().equals(category1.getName())){
                           categoryList.add(category1);
                       }
//                       System.out.println("The Loai: "+comic1.text());
                     }
                   }
                    createComic.setCategories(categoryList);
                }
                Elements comics2 =document1.select("div.info > div > span.source");
                for(Element comic2: comics2){
//                    System.out.println("source: "+comic2.text());
                    createComic.setSource(comic2.text());
                }
                Elements comics3 =document1.select("div.info > div > span.text-primary");
                for(Element comic3: comics3){
//                    System.out.println("Status: "+comic3.text());
                    createComic.setStatus(comic3.text());
                }
                Elements comics4 = document1.select("div.row > div.col-xs-12 > ul.list-chapter > li > a");
                List<Chaper> chaperList = new ArrayList<>();
                for(Element comic4: comics4){
                    Chaper chaper = new Chaper();
                    String[] tmp = comic4.html().split(">");
                    String chaptername = tmp[tmp.length-1];
//                    System.out.println("Chuong "+chaptername);
                    chaper.setName(chaptername);
                    String[] tmp1 = chaptername.split(":");
                    Document document2 = Jsoup.connect("https://truyenfull.vn/"+temp[temp.length-1]+"/chuong-"+tmp1[0]+"/").get();
                    Elements comics5 = document2.select("div.chapter-c");
                    for(Element comic5:comics5){
//                        System.out.println("CONTENT :"+comic5.html());

                        chaper.setContent(comic5.text());
                    }
                    chaper.setComic(createComic);
                    chapterRepository.save(chaper);
                    chaperList.add(chaper);
                }
                createComic.setChapers(chaperList);


                comicRepsitory.save(createComic);
            }

        }
        return true;
    }
}
