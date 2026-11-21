package org.sopt.post.entity;

import org.sopt.common.entity.BaseEntity;
import org.sopt.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
	@Column(nullable = false, length = 255)
	private String title;

	@Column(length = 255, nullable = false)
	private String imageKey;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private PostVisibility visibility;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "target_user_id", nullable = false)
	private User targetUser;

	@Builder
	public Post(String title, String imageKey, String description, PostVisibility visibility, User author, User targetUser) {
		this.title = title;
		this.imageKey = imageKey;
		this.description = description;
		this.visibility = visibility;
		this.author = author;
		this.targetUser = targetUser;
	}
}
