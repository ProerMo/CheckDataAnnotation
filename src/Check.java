import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Check {

    public enum DataType {STRING, NUM}

    DataType dataType();

    public enum CheckType {STRING_NOT_EMPTY, STRING_EQUAL, STRING_IN_LIST, NUM_GREATER_THAN, NUM_EQUAL, NUM_LESS_THAN, NUM_IN_LIST}

    CheckType checkType();

    String toastString() default "信息填写不完整";

    String[] stringList() default {};

    int[] numList() default {};

    String compareString() default "";

    int compareInt() default -1;
}
