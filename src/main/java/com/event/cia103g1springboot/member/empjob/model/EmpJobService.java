package com.event.cia103g1springboot.member.empjob.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("empJobService")
public class EmpJobService {

	@Autowired
	EmpJobRepository repository;

	public List<EmpJobVO> getAll() {
		return repository.findAll();
	}

	public List<Integer> findAuthByEmpId(Integer empId) {
		return repository.findByEmpId(empId);
	}

	public void deleteOldAuth(Integer empId) {
		repository.deleteAuthByEmpId(empId);
	}

	public void addAuth(EmpJobVO empJob) {
		repository.save(empJob);
	}

}
