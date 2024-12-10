package com.event.cia103g1springboot.member.empjob.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.event.cia103g1springboot.member.emp.model.EmployeeVO;
import com.event.cia103g1springboot.member.systemFunction.model.SystemFunctionVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "employeejob")
@IdClass(EmpJobId.class)
public class EmpJobVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	private Integer empId;
	@Id
	private Integer funId;

	@ManyToOne
	@JoinColumn(name = "empId", nullable = false, insertable = false, updatable = false)
	private EmployeeVO employee;

	@ManyToOne
	@JoinColumn(name = "funId", insertable = false, updatable = false, nullable = false)
	private SystemFunctionVO systemFunctionVO;

	@Override
	public String toString() {
		return "EmpJobVO{" +
				"employee=" + employee +
				", systemFunctionVO=" + systemFunctionVO +
				'}';
	}
}
