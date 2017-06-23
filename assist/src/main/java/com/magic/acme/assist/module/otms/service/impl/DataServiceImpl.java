package com.magic.acme.assist.module.otms.service.impl;

import com.magic.acme.assist.jpa.xtt.entity.EpodTask;
import com.magic.acme.assist.jpa.xtt.entity.Handshake;
import com.magic.acme.assist.jpa.xtt.entity.HandshakeCargoDiscrepancy;
import com.magic.acme.assist.jpa.xtt.entity.HandshakeFile;
import com.magic.acme.assist.jpa.xtt.entity.HandshakeNoCargo;
import com.magic.acme.assist.jpa.xtt.entity.HandshakeOrder;
import com.magic.acme.assist.jpa.xtt.entity.HandshakeOrderLineRejection;
import com.magic.acme.assist.jpa.xtt.entity.HubToken;
import com.magic.acme.assist.jpa.xtt.entity.Token;
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
import java.util.Random;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
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

    private LocalDateTime rangTime;

    @Override
    @Transactional("transactionManagerXtt")
    public void batchCreateDataForXtt(int amount) {

        long start = System.currentTimeMillis();
        batchCreateEpodTask(amount);
        System.out.println("Create epodTask success, cost times:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<String> tokenIds = batchCreateToken(amount);
        System.out.println("Create token success, cost times:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<String> hubTokenIds = batchCreateHubToken(amount);
        System.out.println("Create hubtoken success, cost times:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        batchCreateHandshake(amount, tokenIds, hubTokenIds);
        System.out.println("Create handshake success, cost times:" + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {

        long millis = System.currentTimeMillis() % 1490000000000L;
        System.out.println(millis);
    }

    private void batchCreateHandshake(int amount, List<String> tokenIds, List<String> hubTokenIds) {

        Handshake handshake;
        HandshakeFile handshakeFile;
        HandshakeNoCargo handshakeNoCargo;
        HandshakeCargoDiscrepancy handshakeCargoDiscrepancy;
        HandshakeOrder handshakeOrder;
        HandshakeOrderLineRejection handshakeOrderLineRejection;

        List<Handshake> handshakes = new ArrayList<Handshake>();
        Date creationDate = getRangTime().toDate();
        Date confirmationDate = getRangTime().plusMinutes(2).toDate();
        long id = System.currentTimeMillis() % 1490000000000L;
        String handshakeId;
        String tokenId;
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            id++;
            handshakeId = "HAND-" + Crypto.newRandomId();
            handshake = new Handshake();
            handshakeFile = new HandshakeFile();
            handshakeNoCargo = new HandshakeNoCargo();
            handshakeCargoDiscrepancy = new HandshakeCargoDiscrepancy();
            handshakeOrder = new HandshakeOrder();
            handshakeOrderLineRejection = new HandshakeOrderLineRejection();
            // handshake
            handshake.setId(handshakeId);
            handshake.setCreationDate(creationDate);
            handshake.setComment("damage,reject");
            handshake.setLatitude(465.12);
            handshake.setLongitude(121.69);
            handshake.setConfirmationDate(confirmationDate);
            handshake.setMilestone("4");
            handshake.setMultipleOrder(true);
            handshake.setConfirmationResult(0);
            handshake.setOwner("GW22OBYJBFHL56HIGYEJOXMG");
            // handshake_order
            handshakeOrder.setId(id);
            handshakeOrder.setHandshake(handshake);
            handshakeOrder.setBarcodeNumber("0024981175731");
            handshakeOrder.setTokenId(getTokenId(tokenIds, hubTokenIds, random, i));
            handshakeOrder.setOwnerRole(2);
            handshake.getOrderList().add(handshakeOrder);
            // handshake_file
            handshakeFile.setId(id);
            handshakeFile.setHandshake(handshake);
            handshakeFile.setFileId("epod:ZKPKXJV6KTCNQMCX7WJS26R6:YZUHXJVO7HVIT6DD27P67Y45AM======");
            handshakeFile.setFileName("Screenshot_2016-11-10-14-51-39.jpg");
            handshakeFile.setSize(67491L);
            handshake.getFileList().add(handshakeFile);
            // handshake_no_cargo
            handshakeNoCargo.setId(id);
            handshakeNoCargo.setHandshake(handshake);
            handshakeNoCargo.setComment("实提11");
            handshakeNoCargo.setOrderLineIndex(1);
            handshakeNoCargo.setPickedQuantity(11);
            handshake.getNoCargoList().add(handshakeNoCargo);
            // handshake_cargo_discrepancy
            handshakeCargoDiscrepancy.setId(id);
            handshakeCargoDiscrepancy.setHandshake(handshake);
            handshakeCargoDiscrepancy.setComment("2222");
            handshakeCargoDiscrepancy.setOrderLineIndex(0);
            handshakeCargoDiscrepancy.setTuDamage(1);
            handshakeCargoDiscrepancy.setTuLoss(0);
            handshake.getCargoDiscrepancyList().add(handshakeCargoDiscrepancy);
            // handshake_order_line_rejection
            handshakeOrderLineRejection.setId(id);
            handshakeOrderLineRejection.setHandshake(handshake);
            handshakeOrderLineRejection.setRejectedQuantity(1);
            handshakeOrderLineRejection.setReasonId(112);
            handshakeOrderLineRejection.setOrderLineIndex(2);
            handshakeOrderLineRejection.setComment("233");
            handshakeOrderLineRejection.setCargoCode("abc");
            handshakeOrderLineRejection.setCargoName("ABC");
            handshakeOrderLineRejection.setUnit("box");
            handshake.getOrderLineRejectionList().add(handshakeOrderLineRejection);
            handshakes.add(handshake);
        }

        for (Handshake node : handshakes) {
            handshakeRepository.save(node);
        }
    }

    private String getTokenId(List<String> tokenIds, List<String> hubTokenIds, Random random, int index) {
        String tokenId;
        if (index % 2 == 0) {
            if (index < tokenIds.size()) {
                tokenId = tokenIds.get(index);
            } else {
                tokenId = tokenIds.get(random.nextInt(tokenIds.size()));
            }
        } else {
            if (index < hubTokenIds.size()) {
                tokenId = hubTokenIds.get(index);
            } else {
                tokenId = hubTokenIds.get(random.nextInt(hubTokenIds.size()));
            }
        }
        return tokenId;
    }

    private List<String> batchCreateToken(int amount) {

        Token token;
        List<String> tokenIds = new ArrayList<String>();
        List<Token> tokens = new ArrayList<Token>();
        Date creationDate = getRangTime().minusDays(15).toDate();
        Date expirationDate = getRangTime().toDate();
        String tokenId;
        for (int i = 0; i < amount; i++) {

            tokenId = Crypto.newRandomId();
            tokenIds.add(tokenId);

            token = new Token();
            token.setId(tokenId);
            token.setCreationDate(creationDate);
            token.setExpirationDate(expirationDate);
            token.setIssuingCompanyId(3001);
            token.setNotifyOnOrderPickup(true);
            token.setNotifyOnOrderDelivery(true);
            token.setNotifyOnException(true);
            token.setNotifyType(0);
            token.setOrderId(9999L);
            token.setOwnerEmail("15021885262");
            token.setOwnerRole(2);
            token.setType(0);
            token.setRecalled(false);
            token.setBarcodeNumber("0006419819479");
            token.setShipperToken("7XCTLPB2WIYMSC5GSRD5VRBH");
            tokens.add(token);
        }

        for (Token node : tokens) {
            tokenRepository.save(node);
        }

        return tokenIds;
    }

    private List<String> batchCreateHubToken(int amount) {

        HubToken hubToken;
        List<String> hubTokenIds = new ArrayList<String>();
        List<HubToken> hubTokens = new ArrayList<HubToken>();
        Date creationDate = getRangTime().minusDays(60).toDate();
        Date expirationDate = getRangTime().toDate();
        Random random = new Random();
        String hubTokenId;
        for (int i = 0; i < amount; i++) {
            hubTokenId = "HUB-" + Crypto.newRandomId();
            hubTokenIds.add(hubTokenId);
            hubToken = new HubToken();
            hubToken.setId(hubTokenId);
            hubToken.setCreationDate(creationDate);
            hubToken.setExpirationDate(expirationDate);
            hubToken.setOrderId(9999999);
            hubToken.setOwnerRole(random.nextInt(2) + 3);
            hubToken.setIssuingCompanyId(9999);
            hubToken.setNotifyOnDispatch(true);
            hubToken.setNotifyOnException(true);
            hubToken.setNotifyOnOrderDelivery(true);
            hubToken.setNotifyOnOrderPickup(true);
            hubToken.setBarcodeNumber("BARCODE-001");
            hubToken.setNotifyType(0);
            hubToken.setHubId(999);
            hubToken.setRecalled(false);
            hubToken.setCanDispatch(false);
            hubTokens.add(hubToken);
        }
        for (HubToken node : hubTokens) {
            hubTokenRepository.save(node);
        }
        return hubTokenIds;
    }

    private void batchCreateEpodTask(int amount) {

        // 获取当前最大ID
        Long maxId = epodTaskRepository.getMaxId();
        if (maxId == null) {
            maxId = 0L;
        }

        EpodTask epodTask;
        List<EpodTask> epodTasks = new ArrayList<EpodTask>();
        Date createdAt = getRangTime().toDate();
        Date updateAt = getRangTime().plusDays(1).toDate();
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

    private LocalDateTime getRangTime() {
        if (rangTime == null) {
            return LocalDateTime.now().minusDays(500);
        }
        return rangTime;
    }
}
