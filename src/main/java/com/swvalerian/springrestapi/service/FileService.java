package com.swvalerian.springrestapi.service;

import com.swvalerian.springrestapi.model.File;
import com.swvalerian.springrestapi.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements BaseService<File, Long>{

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File getId(Long aLong) {
        return fileRepository.getOne(aLong);
    }

    @Override
    public List<File> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public File save(File object) {
        return fileRepository.save(object);
    }

    @Override
    public List<File> update(File object) {
        fileRepository.save(object);
        return this.getAll();
    }

    @Override
    public void deleteId(Long aLong) {
        fileRepository.deleteById(aLong);
    }
}
