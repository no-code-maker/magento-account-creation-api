package com.magento.account.creation.health;

import com.magento.account.creation.constants.AccountCreationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Rajeev Krishna
 * @description Check if the Magento Service is available or not.
 */
@Component
@Slf4j
public class ServiceHealthIndicator implements HealthIndicator, HealthContributor {

    @Value("${SERVICE_GET_URL}")
    private String serviceGetUrl;

    @Override
    public Health health() {
        try {

            URL urlServer = new URL(this.serviceGetUrl);
            HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
            urlConn.setConnectTimeout(AccountCreationConstants.CONNECTION_TIMEOUT);
            urlConn.connect();

            if (urlConn.getResponseCode() != 200) {
                throw new Exception("Failed to connect to Magento Service");
            }
        } catch (Exception exception) {
            log.warn("Failed to connect to : {}", this.serviceGetUrl);
            return Health.down().build();
        }
        return Health.up().build();
    }
}


