package com.example.demo.dto;

public class TaskDTO {
	
	private Long id;
	private String title;
	private String description;

	    

		public TaskDTO() {
		super();
	}

		public TaskDTO(Long id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
	}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "TaskDTO [id=" + id + ", title=" + title + ", description=" + description + "]";
		}

		
	    
	    
}
