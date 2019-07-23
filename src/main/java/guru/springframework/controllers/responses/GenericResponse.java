package guru.springframework.controllers.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    private boolean result;

    private T data;

    private ErrorResponse error;

    public GenericResponse() {
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public void initResult() {
        if (this.error == null) {
            this.result = true;
        }
        if (this.data == null) {
            this.result = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericResponse)) return false;
        GenericResponse<?> that = (GenericResponse<?>) o;
        return Objects.equals(getResult(), that.getResult()) &&
                Objects.equals(getData(), that.getData()) &&
                Objects.equals(getError(), that.getError());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult(), getData(), getError());
    }
}
