package com.sop.manageserver.repository;

//import com.sop.manageserver.pojo.Member;
import com.sop.manageserver.pojo.Categories;
import com.sop.manageserver.pojo.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends MongoRepository<Categories, String> {
    @Query(value = "{_id:  '?0'}")
    public Categories findByID(String id);

    @Query(value = "{name: '?0'}")
    public Categories findByName(String name);

}
