package wowplan.simple.mappertest.context;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class UnInitializedBean {
    // 哪个 object 的 哪个属性还没初始化
    private Object object;

    private Field field;
}
