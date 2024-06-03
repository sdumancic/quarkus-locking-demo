package code.service;

import code.domain.DealerCode;

public interface DealerCodeService {
    DealerCode findByYearAndDealerId(Short year, Integer dealerId);
}
