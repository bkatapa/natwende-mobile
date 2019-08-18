/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.service.TripMobileService;
import com.mweka.natwende.mobile.util.Database;
import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bell
 */
@Named
@SessionScoped
public class TripMobileView extends MessageHelper {
        
    private List<TripVO> entityList;
    private TripVO selectedEntity;
    private TripSearchVO searchVO;
    private List<TripSearchResultVO> searchResultList;
    private TripSearchResultVO searchResult;
    private int activeIndex;
    private Date travelDate, travelDateReturn;
    private boolean roundTrip, returnDate, destination, indemnity, subscribe;
    private List<Town> townList;
    private Map<String, String> busTemplateMap;
    
    @Inject
    private TripMobileService tripMobileService;
     
    @PostConstruct
    public void init() {
        selectedEntity = new TripVO();
        searchVO = new TripSearchVO();
        searchResult = new TripSearchResultVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
        travelDate = new Date();
        travelDateReturn = new Date();
        townList = Arrays.asList(Town.values());
        roundTrip = false;
        returnDate = false;
        destination = false;
        indemnity = false;
        subscribe = false;
        busTemplateMap = new ConcurrentHashMap<>();
    }

    public List<TripVO> getEntityList() {
        loadEntityList();
        return entityList;
    }
    
    public List<TripSearchResultVO> getSearchResultList() {        
        return searchResultList;
    }

    public TripVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(TripVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
    
    public TripSearchVO getSearchVO() {
        if (searchVO == null) {
            searchVO = new TripSearchVO();
        }
        return searchVO;
    }
    
    public void setSearchVO(TripSearchVO searchVO) {
        this.searchVO = searchVO;
    }

    public TripSearchResultVO getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(TripSearchResultVO searchResult) {
        this.searchResult = searchResult;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public Date getTravelDate() {
        return travelDate;
    }
    
    public String getTravelDateAsString() {
        String travelDateAsString = DateFormat.getDateInstance().format(travelDate);        
        return travelDateAsString;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Date getTravelDateReturn() {
        return travelDateReturn;
    }
    
    public String getTravelDateReturnAsString() {
        String travelDateReturnAsString = DateFormat.getDateInstance().format(travelDateReturn);        
        return travelDateReturnAsString;
    }

    public void setTravelDateReturn(Date travelDateReturn) {
        this.travelDateReturn = travelDateReturn;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public boolean isReturnDate() {
        return returnDate;
    }

    public void setReturnDate(boolean returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isDestination() {
        return destination;
    }

    public void setDestination(boolean destination) {
        this.destination = destination;
    }

    public boolean isIndemnity() {
        return indemnity;
    }

    public void setIndemnity(boolean indemnity) {
        this.indemnity = indemnity;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
    
    public void toggleRoundTrip() {
        roundTrip = !roundTrip;
        logger.log(Level.CONFIG, "roundTrip is: {0}", roundTrip);
    }
    
    public void search() {
        try {
            searchResultList = tripMobileService.searchTrips(searchVO);
            RequestContext.getCurrentInstance().update("tripList-page");
            RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#tripList-page', {reverse: false, transition: 'slidedown'});");
        }
        catch (Exception ex) {
            onMessage("error", ex.getMessage());
            RequestContext.getCurrentInstance().update("booking-page:tripSearch-form");
        }
    }
    
    public String viewEntity() {
        return "pm:seatSelection-page?transition=slide";
    }
    
    public void onSeatSelectionDialogClose() {
        String script = "$('#selected-seats').empty(); sc.find('selected').status('available'); PF('var_ConfirmSeatSelection').hide();";
        RequestContext.getCurrentInstance().execute(script);
    }
    
    public void onRoundTrip() {
        //System.out.println("Roud trip is: " + roundTrip);
    }
    
    public void onDateSelect(SelectEvent event) {
        Date selectedDate = (Date) event.getObject();
        if (returnDate) {
            searchVO.setReturnDate(selectedDate);
            //returnDate = false;
        }
        else {
            searchVO.setTravelDate(selectedDate);
        }
        RequestContext.getCurrentInstance().update("booking-page");
        RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#booking-page', {reverse: false, transition: 'slidedown'});");        
    }
    
    public List<Town> completeTown(String query) {
        //System.out.println("query: " + query);
        List<Town> townList = new ArrayList<>();
        for (Town t : Town.values()) {
            if (query == null || query.trim().isEmpty()) {
                continue;
            }
            if (StringUtils.containsIgnoreCase(t.getDisplay(), query)) {
                townList.add(t);
            }
        }
        //System.out.println(townList);
        return townList;
    }
    
    public void onTownSelect(SelectEvent event) {
        if (event.getObject() == null) {
            return;
        }
        Town selectedTown = (Town) event.getObject();
        if (destination) {
            searchVO.setToTown(selectedTown);
            //destination = false;
        }
        else {
            searchVO.setFromTown(selectedTown);
        }
        RequestContext.getCurrentInstance().update("booking-page");
        RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#booking-page', {reverse: false, transition: 'slidedown'});");       
    }
    
    public String getBusTemplate() {
        if (searchResult == null || searchResult.getTrip() == null || searchResult.getStretch() == null) {
            return StringUtils.EMPTY;
        }
        String busTemplate = null;
        try {
            String busReg = searchResult.getTrip().getBusReg();
            BigDecimal busFare = searchResult.getStretch().getFareAmount();
            busTemplate = tripMobileService.fetchBusTemplateJson(busReg, busFare);
        }
        catch (Exception ex) {
            onMessage("error", ex.getMessage());
        }
        return busTemplate;
    }
    
    public boolean containsIgnoreCase(String searchStr, String sourceStr) {
        return Pattern.compile(Pattern.quote(searchStr), Pattern.CASE_INSENSITIVE).matcher(sourceStr).find();
    }
    
    private void loadEntityList() {
        entityList.clear();
        // read trips from web service call
        Database.addTrips(entityList);
    }
}