package com.magic.acme.assist.module.kit.service.impl;

import com.magic.acme.assist.jpa.kit.entity.Log;
import com.magic.acme.assist.jpa.kit.repository.TestRepository;
import com.magic.acme.assist.module.kit.service.TestService;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRepository testRepository;

    @Override
    public List<Log> selectLogList() {
        return testRepository.findAll(new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        });
    }
}

