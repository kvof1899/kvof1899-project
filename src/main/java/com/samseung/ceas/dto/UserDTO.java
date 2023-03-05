package com.samseung.ceas.dto;

import com.samseung.ceas.model.ProductEntity;
import com.samseung.ceas.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String token;
	private String userName;
	private String userId;
	private String userPassword;
	private String userEmail;
	private String id;

	
	
	
	
}














//public UserDTO(final UserEntity entity) {
//this.userName = entity.getUserName();
//this.userId = entity.getUserId();
//this.userPassword = entity.getUserPassword();
//this.userEmail = entity.getUserEmail();
//this.id = entity.getId();
//}
//
//public static UserEntity toEntity(final UserDTO dto) {
//return UserEntity.builder()
//		.userName(dto.getUserName())
//		.userId(dto.getUserId())
//		.userPassword(dto.getUserPassword())
//		.userEmail(dto.getUserEmail())
//		.id(dto.getId())
//		.build();
//}
