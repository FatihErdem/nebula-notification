package com.nebula.notification.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailReviewDto {

	private EmailType emailType;

	private Integer productId;

	private String comment;

	private String reviewerName;

	private LocalDateTime reviewDate;

	private String emailAddress;

	private Integer rating;

	private ReviewStatus status;
}
