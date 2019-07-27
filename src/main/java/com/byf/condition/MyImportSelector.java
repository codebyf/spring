package com.byf.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

// 自定义需要导入的组件
public class MyImportSelector implements ImportSelector {
    // 返回值：就是要导入到容器中的组件全类名
    // AnnotationMetaData：当前标注@Import注释类的所有注释信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.byf.bean.Heigh","com.byf.bean.Weight"};
    }
}
