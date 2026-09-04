DROP TABLE IF EXISTS midia;

CREATE TABLE midia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    nota INT NOT NULL,
    foto_url VARCHAR(1000),
    comentario VARCHAR(1000)
    );

INSERT INTO midia (titulo, tipo, nota, foto_url, comentario)
VALUES (
           'Oyasumi Punpun',
           'Manga',
           10,
           'https://upload.wikimedia.org/wikipedia/pt/a/a7/Oyasumi_punpun_vol_1_cover.jpg?utm_source=pt.wikipedia.org&utm_campaign=index&utm_content=thumbnail_unscaled&_=20250710185614',
           'Uma obra muito incrível, não importa quantas vezes eu leia, o sentimento nunca passa.'
       );