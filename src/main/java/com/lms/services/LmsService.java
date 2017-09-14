package com.lms.services;

import com.lms.domain.Leave;
import com.lms.repositories.LeavesRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LmsService {

    private LeavesRepository leavesRepository;

    @Inject
    public LmsService(LeavesRepository leavesRepository) {
        this.leavesRepository = leavesRepository;
    }

    public Leave save(Leave leaveToBeSaved) {
        return leavesRepository.save(leaveToBeSaved);
    }

    public Leave getById(String id) {
        return leavesRepository.getById(id);
    }

    public List<Leave> getAll() {
        return leavesRepository.getAll();
    }
}
