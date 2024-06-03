package code.service.impl;

import code.domain.DealerCode;
import code.repository.DealerCodeDao;
import code.service.DealerCodeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@ApplicationScoped
@Slf4j
public class DealerCodeServiceImpl implements DealerCodeService {

    @Inject
    DealerCodeDao dealerCodeDao;

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public synchronized DealerCode findByYearAndDealerId(Short year, Integer dealerId) {
        log.info("start findByYearAndDealerId: {} {}", year, dealerId);
        DealerCode dealerCode = null;
        try{
            dealerCode = dealerCodeDao.findByDealerIdAndYear(dealerId,year);
            Thread.sleep(3000);
            if (dealerCode != null) {
                dealerCode = generateNewDealerCode(dealerCode);
            } else {
                dealerCode = createDealerCode(dealerId, year);
            }
        } catch (InterruptedException ex){
            log.error(ex.getMessage());
        }
        log.info("end findByYearAndDealerId: {}", year);
        return dealerCode;
    }

    private DealerCode generateNewDealerCode(DealerCode dealerCode) {
        Long newValue = dealerCode.getLastCode() + 1L;
        log.info("new value {}",newValue);
        dealerCode.setLastCode(newValue);
        return dealerCodeDao.update(dealerCode);
    }

    private DealerCode createDealerCode(Integer dealerId, Short year) {
        DealerCode dealerCode = new DealerCode();
        dealerCode.setDealerId(dealerId.longValue());
        dealerCode.setYear(year);
        dealerCode.setLastCode(1L);
        return dealerCodeDao.create(dealerCode);
    }
}
