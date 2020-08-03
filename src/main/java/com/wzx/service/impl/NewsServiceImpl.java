package com.wzx.service.impl;

import com.wzx.dao.NewsDao;
import com.wzx.po.News;
import com.wzx.po.NewsQuery;
import com.wzx.po.Type;
import com.wzx.service.NewsService;
import com.wzx.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;


    @Override
    public News findNewsById(Long id) {
        return newsDao.getOne(id);
    }

    @Override
    public void input(News news) {
        if(news.getId()==null){
            news.setUpdateTime(new Date());
            news.setCreateTime(new Date());
            newsDao.save(news);
        }else {
            news.setUpdateTime(new Date());
            News n=newsDao.getOne(news.getId());
            BeanUtils.copyProperties(news,n, MyBeanUtils.getNullPropertyNames(news));
            newsDao.save(n);
        }
    }

    @Override
    public Page<News> findByPageable(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    @Override
    public Page<News> searchNews(Pageable pageable, NewsQuery newsQuery) {
        Page<News> news=newsDao.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if(!StringUtils.isEmpty(newsQuery.getTitle())){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+newsQuery.getTitle()+"%"));
                }
                if(!StringUtils.isEmpty(newsQuery.getTypeId())){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),newsQuery.getTypeId()));
                }
                if(newsQuery.getRecommend()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),newsQuery.getRecommend()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
        return news;
    }

    @Override
    public Page<News> searchNews(Pageable pageable, Long id) {
        return newsDao.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join=root.join("tags");
                return criteriaBuilder.equal(join.get("id"),id);
            }
        }, pageable);
    }

    @Override
    public void deleteById(Long id) {
        newsDao.deleteById(id);
    }

    @Override
    public HashMap<String, List<News>> archiveNews() {
        LinkedHashMap<String, List<News>> map=new LinkedHashMap<>();
        List<String> years=newsDao.findGroupYear();
        for(String y:years){
            List<News> news=newsDao.findByYear(y);
            map.put(y,news);
        }

        return map;
    }

    @Override
    public Long countNews() {
        return newsDao.count();
    }

    @Override
    public Page<News> findNewsByQuery(String query, Pageable pageable) {
        return newsDao.findByquery("%"+query+"%",pageable);
    }

    @Override
    public List<News> findTop(int i) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,i,sort);
        return newsDao.findTop(pageable);
    }

}
