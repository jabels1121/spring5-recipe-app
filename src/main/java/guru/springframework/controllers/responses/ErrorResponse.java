package guru.springframework.controllers.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Objects;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:MM:ss")
    private Timestamp timestamp;
    private String message;
    private Integer code;
    private String type;

    public ErrorResponse(Timestamp timestamp, String message, Integer code, String type) {
        this.timestamp = timestamp;
        this.message = message;
        this.code = code;
        this.type = type;
    }

    public ErrorResponse() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse)) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(getTimestamp(), that.getTimestamp()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getMessage(), getCode(), getType());
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", type='" + type + '\'' +
                '}';
    }
}
