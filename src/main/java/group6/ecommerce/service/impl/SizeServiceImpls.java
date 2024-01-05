package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.SizeRepository;
import group6.ecommerce.model.Size;
import group6.ecommerce.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class SizeServiceImpls implements SizeService {
    private final SizeRepository sizeRepository;
    @Override
    public Size findSizeByName(String name) {
        return sizeRepository.findSizeByName(name);
    }
}
