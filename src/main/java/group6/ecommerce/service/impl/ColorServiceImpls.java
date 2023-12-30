package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ColorRepository;
import group6.ecommerce.model.Color;
import group6.ecommerce.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ColorServiceImpls implements ColorService {
    private final ColorRepository colorRepository;
    @Override
    public Color findColorByName(String name) {
        return colorRepository.findColorByName(name);
    }
}
