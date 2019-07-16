/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.payment.vo.PaymentVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Bell
 */
@Named
@SessionScoped
public class PaymentMobileView extends MessageHelper {
    
    private List<PaymentVO> entityList;
    private PaymentVO selectedEntity;
    private int activeIndex;
    
    @PostConstruct
    public void init() {
        selectedEntity = new PaymentVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
    }

    public List<PaymentVO> getEntityList() {
        return entityList;
    }

    public PaymentVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(PaymentVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public PaymentOptions[] getPaymentOptions() {
        return PaymentOptions.values();
    }
}
