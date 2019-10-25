package com.example.truyenfull.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comic")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt","updatedAt"},allowGetters = true)
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private  String name;

    @NotBlank
    private  String author;

    @Column(nullable = true)
    private String source;

   @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private String content;

//    @Column(nullable = false,updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
//    private Date createdAt;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedBy
//    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    //one to many chuong
    @OneToMany(mappedBy = "comic", cascade = {CascadeType.ALL,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Chaper> chapers = new ArrayList<>();
    //many to many the loai
    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_comic",
            joinColumns = @JoinColumn(name = "comic_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Comic(){

    }
    public Comic(Long id,String name,String author, String source, String status, String content){
        this.id=id;
        this.name=name;
        this.author=author;
        this.source=source;
        this.status=status;
        this.content=content;
//        this.createdAt=createdAt;
//        this.updatedAt=updatedAt;
    }
    public List<Category> getCategories() {

        return categories;
    }

    public void setCategories(List<Category> categories) {
        if(this.categories==null){
            this.categories=categories;
        }
        else if(this.categories!=categories){
            this.categories.clear();
            if(categories!=null){
                this.categories.addAll(categories);
            }
        }
    }

    public List<Chaper> getChapers() {
        return chapers;
    }

    public void setChapers(List<Chaper> chapers) {
        if(this.chapers==null){
            this.chapers=chapers;
        }
        else if(this.chapers!=chapers){
            this.chapers.clear();
            if(chapers!=null){
                this.chapers.addAll(chapers);
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
