package lab1.practice;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * класс спрингового пост процессора, должен имплементировать интерфейс
 *
 * @see BeanPostProcessor
 * <p>
 * Класс отвечает за логику инжекта случайного числа в поле проаннотированное, специально обученной аннотацией
 */
public class InjectRandomPostProcessor implements BeanPostProcessor {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 10;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {

        Class clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            InjectRandomValue annotation = field.getDeclaredAnnotation(InjectRandomValue.class);
            if (annotation != null) {
                field.setAccessible(true);
                setValue(bean, field);
            }
        }
        return bean;
    }

    private void setValue(Object bean, Field field) {
        Class<?> type = field.getType();

        int randomValue = getRandomNumber();
        try {
            if (type == int.class) {
                field.set(bean, randomValue);
            }
            if (type == String.class) {
                field.set(bean, String.valueOf(randomValue));
            }
            if (type == double.class) {
                field.set(bean, randomValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int getRandomNumber() {
        return (int) ((Math.random() * (MAX_VALUE - MIN_VALUE)) + MIN_VALUE);
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
