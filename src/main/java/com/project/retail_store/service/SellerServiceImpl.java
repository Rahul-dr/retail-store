package com.project.retail_store.service;

import com.project.retail_store.converter.SellerMapper;
import com.project.retail_store.dtos.SellerDto;
import com.project.retail_store.entity.Seller;
import com.project.retail_store.repository.SellerRepository;
import com.project.retail_store.service.interfaces.SellerService;
import com.project.retail_store.util.CommonUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

  private final SellerRepository sellerRepository;

  @Override
  @Transactional
  public SellerDto addSeller(SellerDto sellerDto, String username) {
    Seller seller = SellerMapper.INSTANCE.toEntity(sellerDto);
    seller.setDateCreated(LocalDateTime.now());
    seller.setUserCreated(username);
    seller.setActive(true);

    Seller savedSeller = sellerRepository.save(seller);
    return SellerMapper.INSTANCE.toDto(savedSeller);
  }

  @Override
  public List<SellerDto> getAllSellers() {
    return sellerRepository.findAll().stream()
        .map(SellerMapper.INSTANCE::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public SellerDto updateSeller(Long sellerId, SellerDto sellerDto, String username) {
    Seller seller =
        sellerRepository
            .findById(sellerId)
            .orElseThrow(
                () -> CommonUtils.logAndGetException("Seller not found with id: " + sellerId));

    seller.setName(Objects.nonNull(sellerDto.getName()) ? sellerDto.getName() : seller.getName());
    seller.setEmail(
        Objects.nonNull(sellerDto.getEmail()) ? sellerDto.getEmail() : seller.getEmail());
    seller.setPhone(
        Objects.nonNull(sellerDto.getPhone()) ? sellerDto.getPhone() : seller.getPhone());
    seller.setAddress(
        Objects.nonNull(sellerDto.getAddress()) ? sellerDto.getAddress() : seller.getAddress());
    seller.setDescription(
        Objects.nonNull(sellerDto.getDescription())
            ? sellerDto.getDescription()
            : seller.getDescription());

    seller.setDateModified(LocalDateTime.now());
    seller.setUserModified(username);

    Seller updatedSeller = sellerRepository.save(seller);
    return SellerMapper.INSTANCE.toDto(updatedSeller);
  }

  @Override
  @Transactional
  public void disableSeller(Long sellerId, String username) {
    Seller seller =
        sellerRepository
            .findById(sellerId)
            .orElseThrow(
                () -> CommonUtils.logAndGetException("Seller not found with id: " + sellerId));

    seller.setActive(false);
    seller.setDateModified(LocalDateTime.now());
    seller.setUserModified(username);
    sellerRepository.save(seller);
  }
}
