package br.com.ifsp.edu.br.nxtdrive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Ativa o funcionamento do @PreAuthorize nos Controllers
public class SecurityConfig {

    @Bean
    SecurityFilterChain seguranca(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(regras -> regras
                        // 1. Apenas recursos estáticos, tela de login, cadastro e erro são públicos
                        .requestMatchers("/", "/login", "/cadastro", "/403", "/css/**", "/js/**", "/img/**", "/*.css").permitAll()
                        
                        // 2. Imagem pública do produto (caso a loja permita ver fotos sem estar logado)
                        .requestMatchers("/produto/imagem/**").permitAll()

                        // 3. Rotas de administração exclusivas para ADMIN
                        .requestMatchers(
                                "/adicionarCategoria/**", "/editarCategoria/**", "/removerCategoria/**",
                                "/adicionarProduto/**", "/editarProduto/**", "/removerProduto/**", 
                                "/usuarios/**"
                        ).hasRole("ADMIN")

                        // 4. Painel e páginas do sistema exigem login (USER ou ADMIN)
                        .requestMatchers("/home", "/dashboard", "/produtos/**", "/listarCategorias/**")
                        .hasAnyRole("USER", "ADMIN")

                        // 5. Qualquer outra rota exige autenticação
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/")               // Página onde está o formulário de login (index)
                        .loginProcessingUrl("/login")  // Endpoint POST acionado pelo formulário
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/?error")
                        .permitAll()
                )

                .logout(sair -> sair
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout")
                        .permitAll()
                )

                .exceptionHandling(excecao -> excecao
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/403");
                        })
                )

                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}