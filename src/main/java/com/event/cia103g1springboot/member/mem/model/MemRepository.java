package com.event.cia103g1springboot.member.mem.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemRepository extends JpaRepository<MemVO, Integer> {
	@Query("select memAcct from MemVO")
	public List<String> getAllMemAccts();

	@Query("select memPwd from MemVO where memAcct=?1")
	public String getMemPwd(String memAcct);

	@Query("from MemVO where memAcct=?1")
	public MemVO findMemByAcct(String memAcct);

	@Override
	Optional<MemVO> findById(Integer integer);
}
