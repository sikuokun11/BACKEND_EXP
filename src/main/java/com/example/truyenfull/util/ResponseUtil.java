package com.example.truyenfull.util;

import com.example.truyenfull.constants.StatusCode;
import com.example.truyenfull.model.Category;
import com.example.truyenfull.model.Chaper;
import com.example.truyenfull.model.Comic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class ResponseUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static ArrayNode returnListCategory(List<Category> categories){
        ArrayNode node = mapper.createArrayNode();
        for (Category category : categories) {
            node.add(returnCategory(category));
        }
        return node;
    }


    public static ObjectNode returnCategory(Category category) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id",category.getId());
        node.put("name",category.getName());
       node.set("comic",returnListComics(category.getComics()));
        return node;
    }

    public static ObjectNode returnNameCategory(Category category) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id",category.getId());
        node.put("name",category.getName());
        return node;
    }
    public static ArrayNode returnListNameCategory(List<Category> categories){
        ArrayNode node = mapper.createArrayNode();
        for (Category category : categories) {
            node.add(returnNameCategory(category));
        }
        return node;
    }

    public static ArrayNode returnListComics(List<Comic> comics) {
        ArrayNode node =mapper.createArrayNode();
        for(Comic comic:comics){
            node.add(returnComic(comic));
        }
        return node;
    }

    public static ObjectNode returnComic(Comic comic) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id",comic.getId());
        node.put("name",comic.getName());
        node.put("author",comic.getAuthor());
        node.put("source",comic.getSource());
        node.put("content",comic.getContent());
        node.put("status",comic.getStatus());
       node.set("categories",returnListNameCategory(comic.getCategories()));
      node.set("chapters",returnListChapter(comic.getChapers()));
        return node;
    }

    public static ObjectNode returnNameComic(Comic comic){
        ObjectNode node = mapper.createObjectNode();
        node.put("name",comic.getName());
        return node;
    }

    public static ArrayNode returnListChapter(List<Chaper> chapers) {
        ArrayNode nodes =mapper.createArrayNode();
        for(Chaper chaper:chapers){
            nodes.add(returnChapter(chaper));
        }
        return nodes;
    }
    public static ObjectNode returnChapter(Chaper chaper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id",chaper.getId());
        node.put("name",chaper.getName());
        node.put("content",chaper.getContent());
        node.set("comic",returnNameComic(chaper.getComic()));
        return node;
    }




    public static String success(JsonNode body){
        ObjectNode node = mapper.createObjectNode();
        node.put(StatusCode.class.getSimpleName(),StatusCode.SUCCESS.getValue());
        node.put("Message",StatusCode.SUCCESS.name());
        node.set("Response",body);
        return node.toString();
    }
    public static String NotFound(){
        ObjectNode node = mapper.createObjectNode();
        node.put(StatusCode.class.getSimpleName(),StatusCode.NOT_FOUND.getValue());
        node.put("Message",StatusCode.NOT_FOUND.name());
        node.put("Response", "");
        return node.toString();
    }

    public static String invalid() {
        ObjectNode node = mapper.createObjectNode();
        node.put(StatusCode.class.getSimpleName(),StatusCode.PARAMETER_INVALID.getValue());
        node.put("Message",StatusCode.PARAMETER_INVALID.name());
        node.put("Response","title or content must not be blank");
        return node.toString();
    }

    public static String serverError() {
        ObjectNode node = mapper.createObjectNode();
        node.put(StatusCode.class.getSimpleName(),StatusCode.SERVER_ERROR.getValue());
        node.put("Message",StatusCode.SERVER_ERROR.name());
        node.put("Response","SEVER ERROR");
        return node.toString();
    }
}
