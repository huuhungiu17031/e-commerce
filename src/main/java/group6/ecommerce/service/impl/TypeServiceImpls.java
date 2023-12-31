package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.TypeRepository;
import group6.ecommerce.model.Type;
import group6.ecommerce.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeServiceImpls implements TypeService {
    private final TypeRepository typeRepository;
    @Override
    public Type getTypeById(String name) {
        return typeRepository.findTypeByName(name);
    }
}
