package com.example.truyenfull.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "category",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt","updatedAt"},allowGetters = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
    @Column(unique = true)
    private String name;


    private String urlName;

//    @Column(nullable = false,updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
//    private Date createdAt;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedBy
//    private Date updatedAt;

    @ManyToMany(cascade = {CascadeType.ALL,CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_comic",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id")
    )
    private List<Comic> comics;

    public Category(){

    }
    public Category(Long id,String name){
        this.id=id;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        if(this.comics==null){
            this.comics=comics;
        }
        else if(this.comics!=comics){
            this.comics.clear();
            if(comics!=null){
                this.comics.addAll(comics);
            }
        }
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }
}
