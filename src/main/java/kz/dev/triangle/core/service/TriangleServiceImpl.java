package kz.dev.triangle.core.service;

import kz.dev.triangle.core.model.TriangleDTO;
import kz.dev.triangle.core.model.TriangleEntity;
import kz.dev.triangle.core.repository.ITriangleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TriangleServiceImpl implements ITriangleService{

    @Autowired
    ITriangleRepository iTriangleRepository;

    @Override
    public void saveTriangle(TriangleDTO triangle) {

        log.info("!!Saving good triangle: {}" + triangle.toString());
        iTriangleRepository.save(TriangleEntity.builder(
                triangle.getSide1(),
                triangle.getSide2(),
                triangle.getSide3(),
                triangle.getPerimeter(),
                triangle.getArea()).build());
    }

}
