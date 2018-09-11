package com.rengu.operationsmanagementsuitev3.Service;

import com.rengu.operationsmanagementsuitev3.Entity.DeploymentDesignScanResultEntity;
import com.rengu.operationsmanagementsuitev3.Repository.DeploymentDesignScanResultRepository;
import com.rengu.operationsmanagementsuitev3.Utils.ApplicationMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-09-10 15:55
 **/

@Slf4j
@Service
@Transactional
public class DeploymentDesignScanResultService {

    private final DeploymentDesignScanResultRepository deploymentDesignScanResultRepository;

    @Autowired
    public DeploymentDesignScanResultService(DeploymentDesignScanResultRepository deploymentDesignScanResultRepository) {
        this.deploymentDesignScanResultRepository = deploymentDesignScanResultRepository;
    }

    public DeploymentDesignScanResultEntity saveDeploymentDesignScanResult(DeploymentDesignScanResultEntity deploymentDesignScanResultEntity) {
        return deploymentDesignScanResultRepository.save(deploymentDesignScanResultEntity);
    }

    public boolean hasDeploymentDesignScanResultByOrderId(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return false;
        }
        return deploymentDesignScanResultRepository.existsByOrderId(orderId);
    }

    @Cacheable(value = "Deployment_Design_Scan_Result_Cache", key = "#orderId")
    public List<DeploymentDesignScanResultEntity> getDeploymentDesignScanResultsByOrderId(String orderId) {
        if (!hasDeploymentDesignScanResultByOrderId(orderId)) {
            throw new RuntimeException(ApplicationMessages.DEPLOYMENT_DESIGN_SCAN_RESULT_ORDER_ID_NOT_FOUND + orderId);
        }
        return deploymentDesignScanResultRepository.findAllByOrderId(orderId);
    }
}