package com.Eventhub.EventHubIntexSoft.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Details about the User")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
  @Column(name = "user_id")
  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the User",
      example = "4")
  private Long userId;

  @Column(name = "user_name", unique = true, nullable = false)
  @Schema(description = "The unique username of the User", example = "Alexander Mikhniuk")
  private String userName;

  @Column(name = "user_email", unique = true, nullable = false)
  @Schema(description = "The unique email of the User", example = "alexmix@mail.ru")
  private String email;

  @Column(name = "password")
  @Schema(description = "The password of the User", example = "12345aA%")
  private String password;
}
