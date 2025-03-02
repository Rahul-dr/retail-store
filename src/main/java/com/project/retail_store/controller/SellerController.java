package com.project.retail_store.controller;

import com.project.retail_store.dtos.ApiResponse;
import com.project.retail_store.dtos.SellerDto;
import com.project.retail_store.service.interfaces.SellerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/sellers")
@RequiredArgsConstructor
public class SellerController {

  private final SellerService sellerService;

  @PostMapping
  public ApiResponse<SellerDto> addSeller(
      @RequestBody SellerDto sellerDto, @AuthenticationPrincipal UserDetails userDetails) {
    return new ApiResponse<>(
        "Seller added successfully", sellerService.addSeller(sellerDto, userDetails.getUsername()));
  }

  @GetMapping
  public ApiResponse<List<SellerDto>> getAllSellers() {
    return new ApiResponse<>("Sellers retrieved successfully", sellerService.getAllSellers());
  }

  @PutMapping("/{sellerId}")
  public ApiResponse<SellerDto> updateSeller(
      @PathVariable Long sellerId,
      @RequestBody SellerDto sellerDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    return new ApiResponse<>(
        "Seller updated successfully",
        sellerService.updateSeller(sellerId, sellerDto, userDetails.getUsername()));
  }

  @DeleteMapping("/{sellerId}")
  public ApiResponse<Void> disableSeller(
      @PathVariable Long sellerId, @AuthenticationPrincipal UserDetails userDetails) {
    sellerService.disableSeller(sellerId, userDetails.getUsername());
    return new ApiResponse<>("Seller disabled successfully", null);
  }
}
