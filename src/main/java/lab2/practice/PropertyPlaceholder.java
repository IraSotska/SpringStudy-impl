package lab2.practice;


import lab1.practice.InjectRandomValue;
import lab2.example.DeprecatedClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;

import java.lang.reflect.Field;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

public class PropertyPlaceholder implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String currentClassName = beanDefinition.getBeanClassName();

            if (currentClassName.equals(MessagePrinter.class.getCanonicalName())) {
                MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
                TypedStringValue propertyName = (TypedStringValue) mutablePropertyValues.get("message");
                mutablePropertyValues.add("message", PropertyRepository.get(propertyName.getValue()));
            }
        }
    }
}
