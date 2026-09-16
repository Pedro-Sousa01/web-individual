package com.celeste.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/midias")
public class MidiaController {

    private final JdbcTemplate jdbc;

    public MidiaController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping
    public ResponseEntity<List<Midia>> listar() {
        List<Midia> midias = jdbc.query("SELECT * FROM midia",
                new BeanPropertyRowMapper<>(Midia.class));

        return ResponseEntity.status(200).body(midias);
    }

    @PostMapping
    public ResponseEntity<Midia> cadastrar(@RequestBody Midia m) {
        if (!validarCampos(m)) {
            return ResponseEntity.status(400).build();
        }
        if (!validarID(m.getId())) {
            return ResponseEntity.status(409).build();
        }

        String sql = "INSERT INTO midia (titulo, tipo, nota";

        if(m.getFoto_url() != null) {
            sql += ", foto_url";
        }
        if(m.getComentario() != null) {
            sql += ", comentario";
        }

        sql += ") VALUES(?, ?, ?";

        if(m.getFoto_url() != null) {
            sql += ", ?";
        }

        if(m.getComentario() != null) {
            sql += ", ?";
        }

        sql += ")";

        final String sqlFinal = sql;

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sqlFinal,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            int index = 1;

            ps.setString(index, m.getTitulo());
            index++;
            ps.setObject(index, m.getTipo().toString());
            index++;
            ps.setInt(index, m.getNota());
            index++;

            if(m.getFoto_url() != null) {
                ps.setString(index, m.getFoto_url());
                index++;
            }

            if(m.getComentario() != null) {
                ps.setString(index, m.getComentario());
            }

            return ps;
        }, kh);

        Number chaveGerada = kh.getKey();
        Integer idGerado = chaveGerada.intValue();
        m.setId(idGerado);

        return ResponseEntity.status(201).body(m);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<Tipo>> listarTipos() {
        List<Tipo> tipos = List.of(Tipo.values());

        return ResponseEntity.status(200).body(tipos);

    }

    public boolean validarCampos(Midia m) {
        boolean validarTitulo =
                m.getTitulo() != null &&
                        !m.getTitulo().trim().isEmpty();

        boolean validarTipo =
                m.getTipo() != null;

        boolean validarNota =
                m.getNota() != null &&
                        m.getNota() > 0 &&
                        m.getNota() <= 10;

        return validarTitulo && validarTipo && validarNota;
    }

    public boolean validarID (Integer id) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM midia WHERE id = ?",
                Integer.class, id);

            return count != null && count == 0;
        }

}

