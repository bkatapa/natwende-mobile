/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.BusVO;
import com.mweka.natwende.mobile.util.MessageHelper;
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
public class BusMobileView extends MessageHelper {
    
    private List<BusVO> entityList;
    private BusVO selectedEntity;
    private int activeIndex;
    
    @PostConstruct
    public void init() {
        selectedEntity = new BusVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
    }

    public List<BusVO> getEntityList() {
        return entityList;
    }

    public BusVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(BusVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public String getBusTemplate() {
        return BUS_TEMPLATE;
    }

    private static final String BUS_TEMPLATE = "";
}
