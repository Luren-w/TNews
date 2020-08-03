package com.wzx.service;

import com.wzx.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Page<Type> listType(Pageable pageable);
    void deleteById(Long id);

    Type findTypeById(Long id);

    void input(Type type);

    List<Type> listType();

    List<Type> findTop(int i);
}
