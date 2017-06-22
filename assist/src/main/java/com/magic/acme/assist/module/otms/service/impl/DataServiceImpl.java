package com.magic.acme.assist.module.otms.service.impl;

import com.magic.acme.assist.jpa.xtt.entity.EpodTask;
import com.magic.acme.assist.jpa.xtt.repository.EpodTaskRepository;
import com.magic.acme.assist.jpa.xtt.repository.HandshakeRepository;
import com.magic.acme.assist.jpa.xtt.repository.HubTokenRepository;
import com.magic.acme.assist.jpa.xtt.repository.TokenRepository;
import com.magic.acme.assist.module.otms.service.DataService;
import com.magic.acme.assist.utils.Crypto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-6-22.
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private HandshakeRepository handshakeRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private HubTokenRepository hubTokenRepository;

    @Autowired
    private EpodTaskRepository epodTaskRepository;

    @Override
    @Transactional("transactionManagerXtt")
    public void batchCreateDataForXtt() {

        long start = System.currentTimeMillis();
        batchCreateEpodTask(100000);
        System.out.println("Batch create epodTask success, cost times:" + (System.currentTimeMillis() - start));

    }

    private void batchCreateEpodTask(int amount) {

        // 获取当前最大ID
        Long maxId = epodTaskRepository.getMaxId();
        if (maxId == null) {
            maxId = 0L;
        }

        EpodTask epodTask;
        List<EpodTask> epodTasks = new ArrayList<EpodTask>();
        Date createdAt = LocalDateTime.now().minusDays(500).toDate();
        Date updateAt = LocalDateTime.now().minusDays(499).toDate();
        BigDecimal latitude = new BigDecimal("31.223255");
        BigDecimal longitude = new BigDecimal("121.53922");
        for (int i = 0; i < amount; i++) {
            epodTask = new EpodTask();
            epodTask.setId(++maxId);
            epodTask.setCreatedAt(createdAt);
            epodTask.setUpdateAt(updateAt);
            epodTask.setLatitude(latitude);
            epodTask.setLongitude(longitude);
            epodTask.setUpdateSource(100);
            epodTask.setIsUploadByHandshake(false);
            epodTask.setPerformedBy("15310909006");
            epodTask.setStatus(1);
            epodTask.setRetryCount(0);
            epodTask.setTaskType(2);
            epodTask.setOrderId(1559465L);
            epodTask.setFileId("epod:TO2PV6KSIFG6OZC4JU45VLA4:W55KTTK3XNRBJ2WNKU6QBRRTHQ======");
            epodTask.setFileName((i + 1) + "-EPOD_161230114252.818.png");
            epodTask.setSize(106038L);
            epodTask.setRole(2);
            epodTask.setFileType(10);
            epodTask.setMilestoneNumber("6");
            epodTask.setUploadedAt(createdAt);
            epodTask.setTotalCount(1);
            epodTasks.add(epodTask);
        }

        for (EpodTask node : epodTasks) {
            epodTaskRepository.save(node);
        }

    }
}
