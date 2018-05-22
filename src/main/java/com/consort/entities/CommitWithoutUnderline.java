package com.consort.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitWithoutUnderline {

	private String id;

	private String shortId;

	private String title;

	private String createdAt;

	private List<String> parentIds;

	private String message;

	private String authorName;

	private String authorEmail;

	private String authoredDate;

	private String committerName;

	private String committerEmail;

	private String committerDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortId() {
		return shortId;
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getAuthoredDate() {
		return authoredDate;
	}

	public void setAuthoredDate(String authoredDate) {
		this.authoredDate = authoredDate;
	}

	public String getCommitterName() {
		return committerName;
	}

	public void setCommitterName(String committerName) {
		this.committerName = committerName;
	}

	public String getCommitterEmail() {
		return committerEmail;
	}

	public void setCommitterEmail(String committerEmail) {
		this.committerEmail = committerEmail;
	}

	public String getCommitterDate() {
		return committerDate;
	}

	public void setCommitterDate(String committerDate) {
		this.committerDate = committerDate;
	}

}
