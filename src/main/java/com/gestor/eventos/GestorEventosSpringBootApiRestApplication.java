package com.gestor.eventos;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;
//version completa//
@SpringBootApplication
public class GestorEventosSpringBootApiRestApplication {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // Configuración para manejar colecciones
        modelMapper.createTypeMap(Set.class, Set.class)
                .setConverter(ctx -> ctx.getSource() == null ? null : new HashSet<>(ctx.getSource()));

        return modelMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(GestorEventosSpringBootApiRestApplication.class, args);
    }

}
