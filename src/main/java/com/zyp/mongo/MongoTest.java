package com.zyp.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Projections.*;

/**
 * @author zyp
 * @since 2017年3月1日-上午8:58:17
 * MongoDB 
 */
public class MongoTest 
{
    private MongoCollection<Document> collection;
    
    @Before
    public void beforeTest(){
    	//1.获取MongoClient实例对象，相当于获取数据库连接，一个MongoClient相当于数据库的连接池
    	MongoClient mc = MongoClientFactory.getInstanceDefault();
        
        //2.访问数据库
        MongoDatabase db=mc.getDatabase("myNewDatabase");
        
        //3.访问集合
       this.collection = db.getCollection("restaurants");
    }
    
    @Test
    public void getAllDocuments(){
    	//获取一个集合中的所有文档对象
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
			while(cursor.hasNext()){
				System.out.println(cursor.next().toJson());
			}
		} finally{
			cursor.close();
		}
    }
    
    @Test
    public void getDocumentByFilter(){
    	//查询过滤器，查询指定的文档对象只查询一条
        Document document = collection.find(eq("name","zhangsan")).first();
        System.out.println(document.toJson());
    }
    @Test
    public void getDocumentsByFileter(){
    	//Get All Documents That Match a Filter
        Block<Document> printBlock = new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
       };
       collection.find(gt("i", 90)).forEach(printBlock);
       
       System.out.println("-----------下面是查询符合多个条件的文档对象---------");
       collection.find(and(gt("i", 50),lt("i", 60))).forEach(printBlock);
       
    }
    
    @Test
    public void insertDocument(){
    	//插入文档
        Document doc = new Document("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
        .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);
    }
    
    @Test
    public void insertDocuments(){
    	//插入多条文档
        List<Document> list=new ArrayList<Document>();
        for(int i=0;i<100;i++){
     	   list.add(new Document("i", i));
        }
        collection.insertMany(list); 
    }
    @Test
    public void countCollection(){
    	//计算集合大小
    	System.out.println(collection.count());
    }
    
    @Test
    public void updateOne(){
    	//更新一条符合条件的文档对象
    	collection.updateOne(eq("i", 10), new Document("$set", new Document("i",110)));
    }
    
    
    @Test
    public void updateMulti(){
    	//更新多条文档对象
    	UpdateResult updateResult = collection.updateMany(gt("i", 95), inc("i", 100));
    	System.out.println(updateResult.getModifiedCount());
    }
    
    @Test
    public void deleteOne(){
    	//删除符合条件的一条文档对象
    	DeleteResult deleteResult = collection.deleteOne(eq("i", 100));
    	System.out.println(deleteResult.getDeletedCount());
    }
    @Test
    public void deleteMany(){
    	//删除符合条件的多条文档对象
    	DeleteResult deleteResult = collection.deleteMany(gte("i", 100));//删除i大于等于100的值
    	System.out.println(deleteResult.getDeletedCount());
    }
    
    @Test
    public void getDodocumentField(){
    	//按条件查询，显示指定的元素
    	Block<Document> printBlock = new Block<Document>() {
    	       public void apply(final Document document) {
    	           System.out.println(document.toJson());
    	       }
    	};
    	collection.find(eq("name","MongoDB"))
    	.projection(fields(include("name", "count", "info"), excludeId()))//除开默认的ID
    	.forEach(printBlock);;
    	
    }
    
    @Test
    public void updateExistDocument(){
    	//更新指定的元素，如果该元素没有，则会添加进去
    	collection.updateOne(eq("i", 50), 
    			combine(set("starts", 1),currentDate("lastModified")));
    }
    
    
    
}
