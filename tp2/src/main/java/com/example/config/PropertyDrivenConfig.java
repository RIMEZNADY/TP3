package com.example.config;

import com.example.dao.IDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Profile("property")
@PropertySource("classpath:app.properties")
public class PropertyDrivenConfig {

    private final ApplicationContext applicationContext;

    public PropertyDrivenConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Value("${dao.target:dao}")
    private String target;

    @Bean(name = "selectedDao")  // ⭐ Changé le nom pour éviter le conflit
    public IDao selectedDao() {
        System.out.println("[CONFIG] Sélection de l'implémentation : " + target);

        try {
            IDao bean = applicationContext.getBean(target, IDao.class);
            System.out.println("[CONFIG] Bean trouvé : " + bean.getClass().getSimpleName());
            return bean;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "❌ Implémentation inconnue: " + target +
                            " (valeurs possibles: dao | dao2 | daoFile | daoApi). Erreur: " + e.getMessage()
            );
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}