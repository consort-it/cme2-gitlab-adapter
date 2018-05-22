package com.consort.util;

import com.consort.entities.Commit;
import com.consort.entities.CommitWithoutUnderline;

public class CommitMapper {

	public static CommitWithoutUnderline mapCommit(Commit commit) {
		CommitWithoutUnderline cwu = new CommitWithoutUnderline();
		cwu.setAuthoredDate(commit.getAuthored_date());
		cwu.setAuthorEmail(commit.getAuthor_email());
		cwu.setAuthorName(commit.getAuthor_name());
		cwu.setCommitterDate(commit.getCommitter_date());
		cwu.setCommitterEmail(commit.getCommitter_email());
		cwu.setCommitterName(commit.getCommitter_name());
		cwu.setCreatedAt(commit.getCreated_at());
		cwu.setId(commit.getId());
		cwu.setMessage(commit.getMessage());
		cwu.setParentIds(commit.getParent_ids());
		cwu.setShortId(commit.getShort_id());
		cwu.setTitle(commit.getTitle());

		return cwu;
	}

}
