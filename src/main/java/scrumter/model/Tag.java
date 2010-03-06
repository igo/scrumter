package scrumter.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tag {
	
	@Id
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Status> statuses;

}
