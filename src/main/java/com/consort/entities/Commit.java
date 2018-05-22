package com.consort.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {

	private String id;

	private String short_id;

	private String title;

	private String created_at;

	private List<String> parent_ids;

	private String message;

	private String author_name;

	private String author_email;

	private String authored_date;

	private String committer_name;

	private String committer_email;

	private String committer_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShort_id() {
		return short_id;
	}

	public void setShort_id(String short_id) {
		this.short_id = short_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public List<String> getParent_ids() {
		return parent_ids;
	}

	public void setParent_ids(List<String> parent_ids) {
		this.parent_ids = parent_ids;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_email() {
		return author_email;
	}

	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}

	public String getAuthored_date() {
		return authored_date;
	}

	public void setAuthored_date(String authored_date) {
		this.authored_date = authored_date;
	}

	public String getCommitter_name() {
		return committer_name;
	}

	public void setCommitter_name(String committer_name) {
		this.committer_name = committer_name;
	}

	public String getCommitter_email() {
		return committer_email;
	}

	public void setCommitter_email(String committer_email) {
		this.committer_email = committer_email;
	}

	public String getCommitter_date() {
		return committer_date;
	}

	public void setCommitter_date(String committer_date) {
		this.committer_date = committer_date;
	}
}
