package com.event.cia103g1springboot.member.empjob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmpJobId implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer funId;

	private Integer empId;


	@Override
	public int hashCode() {
		return Objects.hash(empId, funId);
	}

}
