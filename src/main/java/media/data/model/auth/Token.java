package media.data.model.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import media.data.model.User;
import media.data.model.base.NumericIdBasedEntity;

@SuppressWarnings("serial")
@Entity
@Getter @Setter
@Table(name = "auth_tokens")
public class Token extends NumericIdBasedEntity {
	
	public Token() {}
	
	public Token(String accessToken, String refreshToken, LocalDateTime accessTokenExpDate, LocalDateTime refreshTokenExpDate) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.accessTokenExpDate = accessTokenExpDate;
		this.refreshTokenExpDate = refreshTokenExpDate;
	}

	public Token(String accessToken, String refreshToken) {
		this(accessToken, refreshToken, generateAccessTokenExpDate(), generateRefreshTokenExpDate());
	}
	
	private String accessToken;
	private String refreshToken;
	private LocalDateTime accessTokenExpDate;
	private LocalDateTime refreshTokenExpDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	public static final long ACCESS_TOKEN_VALID_TIME = 60;
	public static final long REFRESH_TOKEN_VALID_TIME = 120;
	
	public static LocalDateTime generateAccessTokenExpDate(LocalDateTime start) {
		return start.plus(ACCESS_TOKEN_VALID_TIME, ChronoUnit.MINUTES);
	}
	
	public static LocalDateTime generateAccessTokenExpDate() {
		return LocalDateTime.now().plus(ACCESS_TOKEN_VALID_TIME, ChronoUnit.MINUTES);
	}
	
	public static LocalDateTime generateRefreshTokenExpDate(LocalDateTime start) {
		return start.plus(REFRESH_TOKEN_VALID_TIME, ChronoUnit.MINUTES);
	}
	
	public static LocalDateTime generateRefreshTokenExpDate() {
		return LocalDateTime.now().plus(REFRESH_TOKEN_VALID_TIME, ChronoUnit.MINUTES);
	}
	
	public boolean isRefreshTokenValid() {
		return this.refreshTokenExpDate.isAfter(LocalDateTime.now());
	}
	
	public boolean isAccessTokenValid() {
		return this.accessTokenExpDate.isAfter(LocalDateTime.now());
	}
	
	public long getAccessTokenExpDateMilis() {
		return getAccessTokenExpDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public long getRefreshTokenExpDateMilis() {
		return getRefreshTokenExpDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
