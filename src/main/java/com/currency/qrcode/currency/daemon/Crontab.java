package com.currency.qrcode.currency.daemon;

import com.currency.qrcode.currency.daemon.task.EthTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Crontab {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EthTask ethTask;

    /**
     * 60秒钟检查一次
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void getEthBalance(){
        ethTask.updateEthAddressBalance();
    }
}
