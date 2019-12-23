package guru.springframework.domain;

/**
 * Created by jt on 6/13/17.
 */
public enum Difficulty {

    EASY("Easy"), MODERATE("Modearate"), KIND_OF_HARD("Kind of hard"), HARD("Hard");

    private String value;

    Difficulty(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
