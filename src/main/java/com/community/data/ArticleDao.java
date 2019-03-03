package com.community.data;

import com.community.entity.Article;
import com.community.util.JdbcHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    JdbcHelper helper=JdbcHelper.getInstance();

    public boolean publish(Article article){
        String sql="INSERT INTO article(title,content,authorname,publishtime) VALUES(?,?,?,?)";
        int i=helper.update(sql,article.getTitle(),article.getContent(),article.getAuthorname(),article.getPublishtime());
        helper.destory();
        return i==1;
    }

    public List<Article> showArticle(){
        String sql="SELECT id,title,content,authorname,publishtime FROM article";
        return getArticles(sql);
    }

    public List<Article> showMyArticle(String username){
        String sql="SELECT id,title,content,authorname,publishtime FROM article WHERE authorname="+username;
        return getArticles(sql);
    }

    public List<Article> searchArticle(String title){
        String sql="SELECT id,title,content,authorname,publishtime FROM article WHERE title LIKE '%"+title+"%'";
        return getArticles(sql);
    }


    public Article articleDetail(String titleID){
        Article article=new Article();
        String sql="SELECT id,title,content,authorname,publishtime FROM article WHERE id="+titleID;
        List<Article> list = getArticles(sql);
        if(list==null||list.size()==0){
            article=null;
        }else {
            article=list.get(0);
        }
        return article;
    }

    public boolean deleteMyArticle(String deleteID){
        String sql="DELETE FROM article WHERE id IN("+deleteID+")";
        int i=helper.update(sql);
        helper.destory();
        return i!=0;
    }

    private List<Article> getArticles(String sql) {
        List<Map<String, Object>> list=helper.query(sql);
        helper.destory();
        List<Article> articles=new ArrayList<>();
        if(list==null||list.size()==0){
            articles=null;
        }else {
            for (Map<String,Object> map:list){
                Article article=wrap(map);
                articles.add(article);
            }
        }
        return articles;
    }

    private Article wrap(Map<String,Object> map){
        Article article=new Article();
        article.setId((Integer) map.get("id"));
        article.setTitle((String) map.get("title"));
        article.setContent((String) map.get("content"));
        article.setAuthorname((String) map.get("authorname"));
        article.setPublishtime((Date) map.get("publishtime"));
        return article;
    }

}
