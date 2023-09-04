package ch.zli.m223.model;

import javax.persistence.*;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Entity
@Table(name = "Entry")
public class Buchung {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private String reason;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private boolean halfDay;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  /** GETTERS AND SETTERS  **/

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getReason() {
    return this.reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public boolean getHalfDay() {
    return this.halfDay;
  }

  public void setHalfDay(boolean halfDay) {
    this.halfDay = halfDay;
  }

public Buchung getUser() {
    return null;
}
}