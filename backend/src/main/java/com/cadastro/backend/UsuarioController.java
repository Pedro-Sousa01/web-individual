package com.cadastro.backend;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final JdbcTemplate jdbc;

    public UsuarioController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(Usuario.class)
        );
        if(!usuarios.isEmpty()) {
            return ResponseEntity.status(200).body(usuarios);
        }else {
            return ResponseEntity.status(204).build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario user) {
        if(user != null) {
            String sql = """
                INSERT INTO usuario (nome, email, senha, telefone, dtNascimento, tipo)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

            KeyHolder keyHolder = new GeneratedKeyHolder();

            try {
                jdbc.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    String tipo = String.valueOf(user.getTipo());

                    ps.setString(1, user.getNome());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getSenha());
                    ps.setString(4, user.getTelefone());
                    ps.setObject(5, user.getDtNascimento());
                    ps.setString(6, tipo);

                    return ps;
                }, keyHolder);
            }catch (DataAccessException e) {
                System.err.println("Erro no SQL: " + e.getMessage());
            }catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
            }
            Integer id = keyHolder.getKeyAs(Integer.class);
            user.setId(id);
            return ResponseEntity.status(201).body(user);
        }else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<Tipo>> listarTipos() {
        List<Tipo> tipos = Arrays.asList(Tipo.class.getEnumConstants());
        return ResponseEntity.status(200).body(tipos);
    }
}
