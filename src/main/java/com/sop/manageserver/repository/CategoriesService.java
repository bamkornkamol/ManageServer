package com.sop.manageserver.repository;


import com.sop.manageserver.pojo.Categories;
import com.sop.manageserver.pojo.Room;
import com.sop.manageserver.pojo.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository repository;

    public CategoriesService(CategoriesRepository repository){
        this.repository=repository;
    }

    public List<Categories> allCategories(){
        return repository.findAll();
    }

    public Categories findById(String id){
        return repository.findByID(id);
    }

    public Categories findByName(String name){
        return repository.findByName(name);
    }

    public  Categories createCategories(Categories categories){
        return repository.insert(categories);
    }

    public Categories updateCategories(Categories categories){
        return repository.save(categories);
    }

    public boolean deleteCategories(Categories categories){
        try{
            repository.delete((categories));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
