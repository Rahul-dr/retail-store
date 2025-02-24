package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.SellerDto;
import java.util.List;

public interface SellerService {
    SellerDto addSeller(SellerDto sellerDto, String username);
    List<SellerDto> getAllSellers();
    SellerDto updateSeller(Long sellerId, SellerDto sellerDto, String username);
    void disableSeller(Long sellerId, String username);
}